package br.com.mac.funcionario.service;

import br.com.mac.funcionario.blue.domain.Funcionario;
import br.com.mac.funcionario.blue.domain.Funcionario_;
import br.com.mac.funcionario.blue.domain.Historico;
import br.com.mac.funcionario.blue.domain.PessoaFisica;
import br.com.mac.funcionario.blue.domain.Pessoa_;
import br.com.mac.funcionario.component.FuncionarioComponente;
import br.com.mac.funcionario.controleAcesso.domain.Usuario;
import br.com.mac.funcionario.dto.FuncionarioResponseDTO;
import br.com.mac.funcionario.dto.request.RequestFuncionarioDTO;
import br.com.mac.funcionario.dto.response.ResponseFuncionarioDTO;
import br.com.mac.funcionario.enums.Acao;
import br.com.mac.funcionario.enums.MotivoAtivar;
import br.com.mac.funcionario.exception.AtivarFuncionarioException;
import br.com.mac.funcionario.exception.RecursoNaoEncontradoException;
import br.com.mac.funcionario.repository.blue.FuncionarioRepository;
import br.com.mac.funcionario.repository.blue.HistoricoRepository;
import br.com.mac.funcionario.repository.blue.PessoaFisicaRepository;
import br.com.mac.funcionario.utils.DataUtils;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;

@Service
public class FuncionarioAtivarService {

  private static final Logger logger = LoggerFactory.getLogger(FuncionarioAtivarService.class);

  @Autowired
  private FuncionarioRepository funcionarioRepository;

  @Autowired
  private HistoricoRepository historicoRepository;

  @Autowired
  private PessoaFisicaRepository pessoaFisicaRepository;

  @Autowired
  private HistoricoLogService historicoLogService;

  @Autowired
  private FuncionarioComponente funcionarioComponente;

  private String loginUsuario;
  private String nomeUsuario;

  @Transactional(value = "transactionManagerBlue",
      rollbackFor = AtivarFuncionarioException.class)
  public ResponseEntity<ResponseFuncionarioDTO> ativarFuncionario(final Long codigoAtivarInativar,
                                                                  final RequestFuncionarioDTO requestDto,
                                                                  final Usuario usuario,
                                                                  BindingResult result) {
    List<Funcionario> funcionarios = new ArrayList<>();
    List<FuncionarioResponseDTO> responseDTOS = new ArrayList<>();

    loginUsuario = usuario.getLogin();
    nomeUsuario = usuario.getNome();

    //Mudar status funcionario
    ResponseEntity<ResponseFuncionarioDTO> responseEntity =
        funcionarioComponente.mudarStatusFuncionarios(codigoAtivarInativar, requestDto,
            result, funcionarios);

    if (responseEntity != null) {
      return responseEntity;
    }

    //salvar funcionario com status novo
    salvarListaFuncionarioComNovoStatus(funcionarios);

    for (Funcionario funcionario : funcionarios) {
      Specification<Funcionario> funcionarioSpecification =
          getFuncionarioSpecification(funcionario.getIdFuncionario());
      funcionario = funcionarioRepository.findOne(funcionarioSpecification)
          .orElseThrow(() -> new RecursoNaoEncontradoException("Funcionário não encontrado."));

      if (!ObjectUtils.isEmpty(funcionario.getPessoa())) {
        responseDTOS.add(FuncionarioResponseDTO.builder()
            .idFuncionario(funcionario.getIdFuncionario())
            .cnpj(funcionarioComponente.obterCnpjPeloIdFuncionario(Acao.ATIVAR.getCodigo(),
                funcionario.getIdFuncionario()))
            .cpf(funcionario.getPessoa().getCpf())
            .dataHora(DataUtils.retornarDataHoraAtual())
            .nome(funcionario.getPessoa().getNome())
            .status(funcionario.getStatus().toString())
            .motivo(funcionarioComponente
                .getMotivoFromDTO(Acao.ATIVAR.getCodigo(), requestDto,
                    funcionario.getIdFuncionario()))
            .build());
      }
    }

    List<Historico> historicos = responseDTOS.stream().map(funcionarioDTO -> Historico.builder()
        .acao(Acao.ATIVAR.name())
        .cnpj(funcionarioDTO.getCnpj())
        .cpf(funcionarioDTO.getCpf())
        .dataHora(LocalDateTime.now())
        .funcionario(Funcionario.builder().idFuncionario(funcionarioDTO.getIdFuncionario()).build())
        .loginOperador(loginUsuario)
        .nomeOperador(nomeUsuario)
        .motivo(MotivoAtivar.toEnum(funcionarioDTO.getMotivo()).getDescricao())
        .build())
        .collect(Collectors.toList());

    this.funcionarioComponente
        .criarLogFuncionario(loginUsuario, nomeUsuario, responseDTOS, historicos,
            codigoAtivarInativar);

    try {
      historicoRepository.saveAll(historicos);
    } catch (Exception e) {
      logger.error("Erro ao ativar funcionarios - SaveAll Historico");
      historicoLogService.criarLogHistorico(historicos);
    }

    return ResponseEntity.status(HttpStatus.OK)
        .body(ResponseFuncionarioDTO.builder()
            .funcionarios(responseDTOS)
            .build());
  }

  private Specification<Funcionario> getFuncionarioSpecification(Long id) {
    return (root, query, builder) -> {
      List<Predicate> allPredicates = new ArrayList<>();

      Path<Long> pessoaIdPath =
          root.join(Funcionario_.pessoa, JoinType.LEFT)
              .get(Pessoa_.idPessoa);
      allPredicates.add(builder.isNotNull(pessoaIdPath));

      Path<Long> idPath = root.get(Funcionario_.idFuncionario);
      allPredicates.add(builder.equal(idPath, id));

      return builder.and(allPredicates.toArray(new Predicate[0]));
    };
  }

  private void salvarListaFuncionarioComNovoStatus(List<Funcionario> funcionarios) {

    try {
      funcionarioRepository.saveAll(funcionarios);
    } catch (Exception e) {
      logger.error("Erro ao salvar lista de funcionarios - Save Funcionario - Id: ");
      historicoLogService.criarLogFuncionario(funcionarios);
    }

  }
}

