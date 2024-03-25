package br.com.mac.funcionario.service;

import br.com.mac.funcionario.dto.HistoricoDTO;
import br.com.mac.funcionario.dto.request.RequestHistoricoDTO;
import br.com.mac.funcionario.repository.blue.HistoricoRepository;
import br.com.mac.funcionario.utils.DataUtils;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class HistoricoService {

  private final HistoricoRepository historicoRepository;

  public HistoricoService(HistoricoRepository historicoRepository) {
    this.historicoRepository = historicoRepository;
  }

  public RequestHistoricoDTO consultarHistoricoVinculoCpf(final String cpf) {

    List<HistoricoDTO> historicoDTOList = historicoRepository.findByCpf(cpf).stream()
        .sorted((h1, h2) -> h2.getDataHora().compareTo(h1.getDataHora()))
        .map(historico -> HistoricoDTO.builder()
            .acao(historico.getAcao())
            .cnpj(historico.getCnpj())
            .cpf(historico.getCpf())
            .dataHora(DataUtils.retornarLocalDateTimeComoString(historico.getDataHora()))
            .idHistorico(historico.getIdHistorico())
            .loginOperador(historico.getLoginOperador())
            .nomeOperador(historico.getNomeOperador())
            .motivo(historico.getMotivo())
            .build())
        .collect(Collectors.toList());

    return RequestHistoricoDTO.builder()
        .historicos(historicoDTOList)
        .build();
  }

  public RequestHistoricoDTO consultarHistoricoVinculoCnpj(final String cnpj) {

    List<HistoricoDTO> historicoDTOList = historicoRepository.findByCnpj(cnpj).stream()
        .sorted((h1, h2) -> h2.getDataHora().compareTo(h1.getDataHora()))
        .map(historico -> HistoricoDTO.builder()
            .acao(historico.getAcao())
            .cnpj(historico.getCnpj())
            .cpf(historico.getCpf())
            .dataHora(DataUtils.retornarLocalDateTimeComoString(historico.getDataHora()))
            .idHistorico(historico.getIdHistorico())
            .loginOperador(historico.getLoginOperador())
            .nomeOperador(historico.getNomeOperador())
            .motivo(historico.getMotivo())
            .build())
        .collect(Collectors.toList());

    return RequestHistoricoDTO.builder()
        .historicos(historicoDTOList)
        .build();
  }
}
