package br.com.mac.funcionario.resource;

import br.com.mac.funcionario.controleAcesso.domain.Usuario;
import br.com.mac.funcionario.dto.request.RequestDTO;
import br.com.mac.funcionario.dto.request.RequestFuncionarioDTO;
import br.com.mac.funcionario.dto.response.PageFuncionarioResponse;
import br.com.mac.funcionario.dto.response.Response;
import br.com.mac.funcionario.dto.response.ResponseDTO;
import br.com.mac.funcionario.dto.response.ResponseFuncionarioDTO;
import br.com.mac.funcionario.enums.Acao;
import br.com.mac.funcionario.exception.ApiErrors;
import br.com.mac.funcionario.exception.UsuarioNaoEncontradoException;
import br.com.mac.funcionario.repository.controleAcesso.UsuarioAcessoRepository;
import br.com.mac.funcionario.service.FuncionarioAtivarService;
import br.com.mac.funcionario.service.FuncionarioConsultaService;
import br.com.mac.funcionario.service.FuncionarioInativarService;
import br.com.mac.funcionario.utils.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(Constantes.PATH_FUNCIONARIO)
public class FuncionarioResource implements FuncionarioResourceDefinition {

  private final FuncionarioAtivarService funcionarioAtivarService;
  private final UsuarioAcessoRepository usuarioAcessoRepository;

  private final List<String> erros = new ArrayList<>();

  @Autowired private FuncionarioConsultaService funcionarioConsultaService;

  @Autowired private FuncionarioInativarService funcionarioInativarService;

  @Autowired private MessageSource messageSource;

  public FuncionarioResource(
      FuncionarioAtivarService funcionarioAtivarService,
      UsuarioAcessoRepository usuarioAcessoRepository) {
    this.funcionarioAtivarService = funcionarioAtivarService;
    this.usuarioAcessoRepository = usuarioAcessoRepository;
  }

  @Override
  @PutMapping(Constantes.PATH_FUNCIONARIO_ATIVAR)
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<Response<ResponseFuncionarioDTO>> ativar(
      @RequestBody @Valid RequestFuncionarioDTO requestDto, BindingResult result) {
    Response<ResponseFuncionarioDTO> response = new Response();
    Usuario usuario = this.procurarUsuarioPorId(requestDto.getId());

    ResponseEntity<ResponseFuncionarioDTO> retorno =
        funcionarioAtivarService.ativarFuncionario(
            Acao.ATIVAR.getCodigo(), requestDto, usuario, result);

    ResponseEntity<Response<ResponseFuncionarioDTO>> retornoErros =
        retornaErros(result, response, retorno);
    if (retornoErros != null) {
      return retornoErros;
    }

    response.setData(retorno.getBody());

    return ResponseEntity.ok(response);
  }

  @Override
  @PutMapping(Constantes.PATH_FUNCIONARIO_INATIVAR)
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<Response<ResponseFuncionarioDTO>> inativar(
      @RequestBody @Valid RequestFuncionarioDTO requestDto, BindingResult result) {

    Response<ResponseFuncionarioDTO> response = new Response();
    Usuario usuario = this.procurarUsuarioPorId(requestDto.getId());

    ResponseEntity<ResponseFuncionarioDTO> retorno =
        funcionarioInativarService.inativarFuncionario(
            Acao.INATIVAR.getCodigo(), requestDto, usuario, result);

    ResponseEntity<Response<ResponseFuncionarioDTO>> retornoErros =
        retornaErros(result, response, retorno);
    if (retornoErros != null) {
      return retornoErros;
    }

    response.setData(retorno.getBody());

    return ResponseEntity.ok(response);
  }

  @Override
  @PostMapping(Constantes.PATH_FUNCIONARIO_CONSULTAR_STATUS)
  public ResponseEntity<Response<ResponseDTO>> consultarStatus(
      @RequestBody @Valid RequestDTO requestDto, BindingResult result) {
    Response<ResponseDTO> response = new Response();

    ResponseEntity<ResponseDTO> retorno =
        funcionarioConsultaService.consultarStatusFuncionarios(requestDto, result);

    ResponseEntity<Response<ResponseDTO>> retornoErros =
        retornaErrosConsultarFuncionarios(result, response, retorno);
    if (retornoErros != null) {
      return retornoErros;
    }

    response.setData(retorno.getBody());

    return ResponseEntity.ok(response);
  }

  @Override
  @GetMapping({Constantes.PATH_CONSULTAR_FUNCIONARIO})
  public ResponseEntity<PageFuncionarioResponse> listar(
      @Valid String cpf,
      @Valid LocalDate dataNascimento,
      @Valid List<Long> idConta,
      @Valid Long idEmpresa,
      @Valid Long idPessoa,
      @Valid Long idSetor,
      @Valid Long idSubgrupo,
      @Valid String matricula,
      @Valid String nome,
      @Valid String numeroIdentidade,
      @Valid String orderBy,
      @Valid Integer page,
      @Valid String sexo,
      @Valid Integer size,
      @Valid List<Long> status) {
    return ResponseEntity.ok(
        funcionarioConsultaService.listar(
            cpf,
            dataNascimento,
            idConta,
            idEmpresa,
            idPessoa,
            idSetor,
            idSubgrupo,
            matricula,
            nome,
            numeroIdentidade,
            orderBy,
            page,
            sexo,
            size,
            status));
  }

  @Override
  @GetMapping({Constantes.PATH_QUANTIDADE_FUNCIONARIO})
  public ResponseEntity<Long> quantidadeFuncionarios(
      @Valid String cpf,
      @Valid LocalDate dataNascimento,
      @Valid List<Long> idConta,
      @Valid Long idEmpresa,
      @Valid Long idPessoa,
      @Valid Long idSetor,
      @Valid Long idSubgrupo,
      @Valid String matricula,
      @Valid String nome,
      @Valid String numeroIdentidade,
      @Valid String sexo,
      @Valid List<Long> status) {
    Integer quantidadeFuncionarios =
        funcionarioConsultaService.quantidadeFuncionarios(
            cpf,
            dataNascimento,
            idConta,
            idEmpresa,
            idPessoa,
            idSetor,
            idSubgrupo,
            matricula,
            nome,
            numeroIdentidade,
            sexo,
            status);
    return ResponseEntity.ok(quantidadeFuncionarios.longValue());
  }

  private ResponseEntity<Response<ResponseDTO>> retornaErrosConsultarFuncionarios(
      BindingResult result,
      Response<ResponseDTO> response,
      ResponseEntity<ResponseDTO> responseEntity) {
    if (result.hasErrors()) {
      erros.clear();
      for (Object obj : result.getAllErrors()) {
        if (obj instanceof ObjectError) {
          ObjectError error = (ObjectError) obj;
          String mensagem = messageSource.getMessage(error, null);
          erros.add(mensagem);
        }
      }
      response.setData(responseEntity.getBody());
      response.setErros(erros);
      return ResponseEntity.badRequest().body(response);
    }
    return null;
  }

  public Usuario procurarUsuarioPorId(Long idUsuario) {
    Optional<Usuario> usuario = usuarioAcessoRepository.findById(idUsuario);
    return usuario.orElseThrow(
        () ->
            new UsuarioNaoEncontradoException(
                "Usuario não encontrado: Id: " + idUsuario + " - " + Usuario.class.getName()));
  }

  private ResponseEntity<Response<ResponseFuncionarioDTO>> retornaErros(
      BindingResult result,
      Response<ResponseFuncionarioDTO> response,
      ResponseEntity<ResponseFuncionarioDTO> responseEntity) {
    if (result.hasErrors()) {
      erros.clear();
      for (Object obj : result.getAllErrors()) {
        if (obj instanceof ObjectError) {
          ObjectError error = (ObjectError) obj;
          String mensagem = messageSource.getMessage(error, null);
          erros.add(mensagem);
        }
      }
      response.setData(responseEntity.getBody());
      response.setErros(erros);
      return ResponseEntity.badRequest().body(response);
    }
    return null;
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiErrors handleValidationExceptioins(MethodArgumentNotValidException ex) {
    BindingResult bindingResult = ex.getBindingResult();
    return new ApiErrors(bindingResult);
  }
}
