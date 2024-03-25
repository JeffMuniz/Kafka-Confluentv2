package br.com.mac.funcionario.component;

import br.com.mac.funcionario.blue.domain.Funcionario;
import br.com.mac.funcionario.blue.domain.Historico;
import br.com.mac.funcionario.dto.FuncionarioDTO;
import br.com.mac.funcionario.dto.FuncionarioRequestDTO;
import br.com.mac.funcionario.dto.FuncionarioResponseDTO;
import br.com.mac.funcionario.dto.HistoricoLogDTO;
import br.com.mac.funcionario.dto.request.RequestFuncionarioDTO;
import br.com.mac.funcionario.dto.response.ResponseFuncionarioDTO;
import br.com.mac.funcionario.enums.Acao;
import br.com.mac.funcionario.enums.MotivoAtivar;
import br.com.mac.funcionario.enums.MotivoInativar;
import br.com.mac.funcionario.repository.blue.CustomQueryRepositoryImpl;
import br.com.mac.funcionario.repository.blue.FuncionarioRepository;
import br.com.mac.funcionario.service.HistoricoLogService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

@Component
public class FuncionarioComponente {

  public static final Long ACAO_ATIVAR = 1L;
  private static final Logger logger = LoggerFactory.getLogger(FuncionarioComponente.class);
  @Autowired
  private CustomQueryRepositoryImpl queryRepositoryImpl;
  @Autowired
  private FuncionarioRepository funcionarioRepository;
  @Autowired
  private HistoricoLogService historicoLogService;

  public void criarLogFuncionario(final String loginUsuario, final String nomeUsuario,
                                  final List<FuncionarioResponseDTO> responseDTOS,
                                  final List<Historico> historicos,
                                  final Long codigoAtivarInativar) {
    List<HistoricoLogDTO> logs = new ArrayList<>();
    for (Historico h : historicos) {

      HistoricoLogDTO log = HistoricoLogDTO.builder()
          .modulo("RH")
          .subModulo("")
          .descricaoSubModulo(Acao.toEnum(codigoAtivarInativar).getDescricao()
              + " funcionário para recebimento de macfícios")
          .origem("PORTAL_RH")
          .chaveModulo("")
          .chaveSubModulo(h.getMotivo())
          .data(LocalDateTime.now())
          .login(loginUsuario)
          .nome(nomeUsuario)
          .valorAnterior(
              Funcionario.builder()
                  .cpf(
                      responseDTOS.stream()
                          .filter(funcionarioResponseDTO -> {
                            return h.getCpf().equals(funcionarioResponseDTO.getCpf());
                          })
                          .findFirst()
                          .orElse(null).getCpf())
                  .status(
                      Long.valueOf(responseDTOS.stream()
                          .filter(funcionarioResponseDTO -> {
                            return h.getCpf().equals(funcionarioResponseDTO.getCpf());
                          })
                          .findFirst()
                          .orElse(null).getStatus()))
                  .idFuncionario(responseDTOS.stream()
                      .filter(funcionarioResponseDTO -> {
                        return h.getCpf().equals(funcionarioResponseDTO.getCpf());
                      })
                      .findFirst()
                      .orElse(null).getIdFuncionario())
                  .build()
          )
          .valorPosterior(
              FuncionarioDTO.builder()
                  .cpf(h.getCpf())
                  .cnpj(h.getCnpj())
                  .nome(
                      responseDTOS.stream()
                          .filter(funcionarioResponseDTO -> {
                            return h.getCpf().equals(funcionarioResponseDTO.getCpf());
                          })
                          .findFirst()
                          .orElse(null).getNome())
                  .status(codigoAtivarInativar)
                  .motivo(codigoAtivarInativar.equals(ACAO_ATIVAR)
                      ? MotivoAtivar.toEnum(
                      responseDTOS.stream()
                          .filter(funcionarioResponseDTO -> {
                            return h.getCpf().equals(funcionarioResponseDTO.getCpf());
                          })
                          .findFirst()
                          .orElse(null).getMotivo()).getDescricao()
                      : MotivoInativar.toEnum(
                      responseDTOS.stream()
                          .filter(funcionarioResponseDTO -> {
                            return h.getCpf().equals(funcionarioResponseDTO.getCpf());
                          })
                          .findFirst()
                          .orElse(null).getMotivo()).getDescricao())
                  .idFuncionario(responseDTOS.stream()
                      .filter(funcionarioResponseDTO -> {
                        return h.getCpf().equals(funcionarioResponseDTO.getCpf());
                      })
                      .findFirst()
                      .orElse(null).getIdFuncionario())
                  .build()
          )
          .build();
      logs.add(log);
    }

    historicoLogService.criarHistorico(logs);
  }

  public String getMotivoFromDTO(Long codigo, RequestFuncionarioDTO requestDto,
                                 Long idFuncionario) {
    String motivo = null;
    for (FuncionarioRequestDTO funcionarioDTO : requestDto.getFuncionarios()) {
      if (Acao.ATIVAR.getCodigo().equals(codigo)
          && funcionarioDTO.getIdFuncionario().equals(idFuncionario)) {
        motivo = MotivoAtivar.toEnum(funcionarioDTO.getMotivo()).getTextoRequest();
        break;
      } else if (Acao.INATIVAR.getCodigo().equals(codigo)
          && funcionarioDTO.getIdFuncionario().equals(idFuncionario)) {
        motivo = MotivoInativar.toEnum(funcionarioDTO.getMotivo()).getTextoRequest();
        break;
      }
    }
    return motivo;
  }

  public ResponseEntity<ResponseFuncionarioDTO> mudarStatusFuncionarios(final Long statusCode,
                                                                        final RequestFuncionarioDTO requestDto,
                                                                        final BindingResult result,
                                                                        final List<Funcionario> funcionarios) {
    Funcionario funcionario = null;
    try {
      for (FuncionarioRequestDTO funcionarioDTO : requestDto.getFuncionarios()) {
        funcionario = funcionarioRepository.findById(funcionarioDTO.getIdFuncionario()).get();
        funcionario.setStatus(Acao.toEnum(statusCode).getCodigo());
        if (Acao.toEnum(statusCode).getCodigo().equals(1L)) {
          MotivoAtivar motivoAtivar = MotivoAtivar.toEnum(funcionarioDTO.getMotivo());
          funcionario.setMotivo(motivoAtivar.getDescricao());
        } else {
          MotivoInativar motivoInativar = MotivoInativar.toEnum(funcionarioDTO.getMotivo());
          funcionario.setMotivo(motivoInativar.getDescricao());
        }
        funcionarios.add(funcionario);
      }

    } catch (IllegalArgumentException e) {
      result.addError(new ObjectError("motivo", e.getMessage()));
      logger.error("Erro ao " + Acao.toEnum(statusCode).getDescricao() + " o funcionário - Id: "
          + funcionario.getIdFuncionario()
          + " - " + e.getMessage());

      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(ResponseFuncionarioDTO.builder()
              .funcionarios(Arrays.asList(FuncionarioResponseDTO.builder()
                  .idFuncionario(funcionario.getIdFuncionario())
                  .motivo(e.getMessage())
                  .build()))
              .build());
    }
    return null;
  }

  public String obterCnpjPeloIdFuncionario(Long codigo, final Long idFuncionario) {
    String cnpj = null;
    try {
      cnpj = this.queryRepositoryImpl.getCNPJByIdFuncionario(idFuncionario);
    } catch (Exception e) {
      logger.error("Erro buscar cnpj pelo Id do funcionario - Id: " + idFuncionario);
    }

    return cnpj;
  }
}
