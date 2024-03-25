package br.com.mac.funcionario.resource;

import static br.com.mac.funcionario.exception.GlobalExceptionHandler.MENSAGEM_GLOBAL_204;
import static br.com.mac.funcionario.exception.GlobalExceptionHandler.MENSAGEM_GLOBAL_400;
import static br.com.mac.funcionario.exception.GlobalExceptionHandler.MENSAGEM_GLOBAL_401;
import static br.com.mac.funcionario.exception.GlobalExceptionHandler.MENSAGEM_GLOBAL_500;
import static br.com.mac.funcionario.utils.Constantes.RESOURCE_ATIVAR_FUNCIONARIO_NOTES;
import static br.com.mac.funcionario.utils.Constantes.RESOURCE_ATIVAR_FUNCIONARIO_VALUE;
import static br.com.mac.funcionario.utils.Constantes.RESOURCE_CONSULTAR_STATUS_FUNCIONARIO_NOTES;
import static br.com.mac.funcionario.utils.Constantes.RESOURCE_CONSULTAR_STATUS_FUNCIONARIO_VALUE;
import static br.com.mac.funcionario.utils.Constantes.RESOURCE_INATIVAR_FUNCIONARIO_NOTES;
import static br.com.mac.funcionario.utils.Constantes.RESOURCE_INATIVAR_FUNCIONARIO_VALUE;
import static br.com.mac.funcionario.utils.Constantes.RESOURCE_LISTAR_FUNCIONARIOS_NOTES;
import static br.com.mac.funcionario.utils.Constantes.RESOURCE_LISTAR_FUNCIONARIOS_QUANTIDADE_NOTES;
import static br.com.mac.funcionario.utils.Constantes.RESOURCE_LISTAR_FUNCIONARIOS_QUANTIDADE_VALUE;
import static br.com.mac.funcionario.utils.Constantes.RESOURCE_LISTAR_FUNCIONARIOS_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


import br.com.mac.funcionario.blue.domain.Funcionario;
import br.com.mac.funcionario.domain.response.ErroInfo;
import br.com.mac.funcionario.dto.request.RequestDTO;
import br.com.mac.funcionario.dto.request.RequestFuncionarioDTO;
import br.com.mac.funcionario.dto.response.PageFuncionarioResponse;
import br.com.mac.funcionario.dto.response.Response;
import br.com.mac.funcionario.dto.response.ResponseDTO;
import br.com.mac.funcionario.dto.response.ResponseFuncionarioDTO;
import br.com.mac.funcionario.utils.Constantes;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.time.LocalDate;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Api(value = Constantes.PATH_FUNCIONARIO, produces = APPLICATION_JSON_VALUE, tags = {
    Constantes.TAG_FUNCIONARIO})
public interface FuncionarioResourceDefinition {

  @ApiOperation(value = RESOURCE_ATIVAR_FUNCIONARIO_VALUE, notes = RESOURCE_ATIVAR_FUNCIONARIO_NOTES, response = Funcionario.class)
  @ApiResponses({
      @ApiResponse(code = 204, message = MENSAGEM_GLOBAL_204, response = ErroInfo.class),
      @ApiResponse(code = 400, message = MENSAGEM_GLOBAL_400, response = ErroInfo.class),
      @ApiResponse(code = 401, message = MENSAGEM_GLOBAL_401, response = ErroInfo.class),
      @ApiResponse(code = 500, message = MENSAGEM_GLOBAL_500, response = ErroInfo.class)
  })
  ResponseEntity<Response<ResponseFuncionarioDTO>> ativar(
      @RequestBody @Valid RequestFuncionarioDTO requestDto,
      BindingResult result);

  @ApiOperation(value = RESOURCE_INATIVAR_FUNCIONARIO_VALUE, notes = RESOURCE_INATIVAR_FUNCIONARIO_NOTES, response = Funcionario.class)
  @ApiResponses({
      @ApiResponse(code = 204, message = MENSAGEM_GLOBAL_204, response = ErroInfo.class),
      @ApiResponse(code = 400, message = MENSAGEM_GLOBAL_400, response = ErroInfo.class),
      @ApiResponse(code = 401, message = MENSAGEM_GLOBAL_401, response = ErroInfo.class),
      @ApiResponse(code = 500, message = MENSAGEM_GLOBAL_500, response = ErroInfo.class)
  })
  ResponseEntity<Response<ResponseFuncionarioDTO>> inativar(
      @RequestBody @Valid RequestFuncionarioDTO requestDto,
      BindingResult result);

  @ApiOperation(value = RESOURCE_CONSULTAR_STATUS_FUNCIONARIO_VALUE, notes = RESOURCE_CONSULTAR_STATUS_FUNCIONARIO_NOTES, response = Funcionario.class)
  @ApiResponses({
      @ApiResponse(code = 204, message = MENSAGEM_GLOBAL_204, response = ErroInfo.class),
      @ApiResponse(code = 400, message = MENSAGEM_GLOBAL_400, response = ErroInfo.class),
      @ApiResponse(code = 401, message = MENSAGEM_GLOBAL_401, response = ErroInfo.class),
      @ApiResponse(code = 500, message = MENSAGEM_GLOBAL_500, response = ErroInfo.class)
  })
  ResponseEntity<Response<ResponseDTO>> consultarStatus(@RequestBody @Valid RequestDTO requestDto,
                                                        BindingResult result);

  @ApiOperation(value = RESOURCE_LISTAR_FUNCIONARIOS_VALUE, notes = RESOURCE_LISTAR_FUNCIONARIOS_NOTES, response = PageFuncionarioResponse.class)
  @ApiResponses({
      @ApiResponse(code = 204, message = MENSAGEM_GLOBAL_204, response = ErroInfo.class),
      @ApiResponse(code = 400, message = MENSAGEM_GLOBAL_400, response = ErroInfo.class),
      @ApiResponse(code = 401, message = MENSAGEM_GLOBAL_401, response = ErroInfo.class),
      @ApiResponse(code = 500, message = MENSAGEM_GLOBAL_500, response = ErroInfo.class)
  })
  ResponseEntity<PageFuncionarioResponse> listar(
      @Valid @RequestParam(value = "cpf", required = false) String cpf,
      @Valid @RequestParam(value = "dataNascimento", required = false) LocalDate dataNascimento,
      @Valid @RequestParam(value = "idConta", required = false) List<Long> idConta,
      @Valid @RequestParam(value = "idEmpresa", required = false) Long idEmpresa,
      @Valid @RequestParam(value = "idPessoa", required = false) Long idPessoa,
      @Valid @RequestParam(value = "idSetor", required = false) Long idSetor,
      @Valid @RequestParam(value = "idSubgrupo", required = false) Long idSubgrupo,
      @Valid @RequestParam(value = "matricula", required = false) String matricula,
      @Valid @RequestParam(value = "nome", required = false) String nome,
      @Valid @RequestParam(value = "numeroIdentidade", required = false) String numeroIdentidade,
      @Valid @RequestParam(value = "orderBy", required = false, defaultValue = "ASC")
          String orderBy,
      @Valid @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
      @Valid @RequestParam(value = "sexo", required = false) String sexo,
      @Valid @RequestParam(value = "size", required = false, defaultValue = "50") Integer size,
      @Valid @RequestParam(value = "status", required = false) List<Long> status);

  @ApiOperation(value = RESOURCE_LISTAR_FUNCIONARIOS_QUANTIDADE_VALUE, notes = RESOURCE_LISTAR_FUNCIONARIOS_QUANTIDADE_NOTES, response = PageFuncionarioResponse.class)
  @ApiResponses({
      @ApiResponse(code = 204, message = MENSAGEM_GLOBAL_204, response = ErroInfo.class),
      @ApiResponse(code = 400, message = MENSAGEM_GLOBAL_400, response = ErroInfo.class),
      @ApiResponse(code = 401, message = MENSAGEM_GLOBAL_401, response = ErroInfo.class),
      @ApiResponse(code = 500, message = MENSAGEM_GLOBAL_500, response = ErroInfo.class)
  })
  ResponseEntity<Long> quantidadeFuncionarios(
      @Valid @RequestParam(value = "cpf", required = false) String cpf,
      @Valid @RequestParam(value = "dataNascimento", required = false) LocalDate dataNascimento,
      @Valid @RequestParam(value = "idConta", required = false) List<Long> idConta,
      @Valid @RequestParam(value = "idEmpresa", required = false) Long idEmpresa,
      @Valid @RequestParam(value = "idPessoa", required = false) Long idPessoa,
      @Valid @RequestParam(value = "idSetor", required = false) Long idSetor,
      @Valid @RequestParam(value = "idSubgrupo", required = false) Long idSubgrupo,
      @Valid @RequestParam(value = "matricula", required = false) String matricula,
      @Valid @RequestParam(value = "nome", required = false) String nome,
      @Valid @RequestParam(value = "numeroIdentidade", required = false) String numeroIdentidade,
      @Valid @RequestParam(value = "sexo", required = false) String sexo,
      @Valid @RequestParam(value = "status", required = false) List<Long> status);

}
