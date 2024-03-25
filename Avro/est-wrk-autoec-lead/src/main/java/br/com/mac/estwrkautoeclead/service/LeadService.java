package br.com.machina.estwrkautoeclead.service;

import br.com.machina.estwrkautoeclead.domain.DadosCredenciamento;
import br.com.machina.estwrkautoeclead.domain.DadosLead;
import br.com.machina.estwrkautoeclead.domain.enums.StatusCredenciamento;
import br.com.machina.estwrkautoeclead.domain.mappers.LeadMapper;
import br.com.machina.estwrkautoeclead.port.SalesForcePort;
import br.com.machina.estwrkautoeclead.repository.DadosCredenciamentoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class LeadService {

    private DadosCredenciamentoRepository dadosCredenciamentoRepository;
    private final LeadMapper leadMapper;
    private final SalesForcePort port;
    private Long horasUltAtualizacao;

    public LeadService(DadosCredenciamentoRepository dadosCredenciamentoRepository,
                       @Value("${lead.extraction.hourLastUpdate}") Long horasUltAtualizacao,
                       LeadMapper leadMapper,
                       SalesForcePort port) {
        this.dadosCredenciamentoRepository = dadosCredenciamentoRepository;
        this.leadMapper = leadMapper;
        this.port = port;
        this.horasUltAtualizacao = horasUltAtualizacao;
    }

    @Scheduled(fixedDelayString = "${lead.extraction.interval}")
    public void extrairLead() {

        List<DadosCredenciamento> credenciamentos = this.dadosCredenciamentoRepository.findDadosCredenciamentoByStatusCredenciamento(StatusCredenciamento.EM_ANALISE.getValue());

        List<DadosCredenciamento> credenciamentosLead = credenciamentos.stream().filter(credenciamento ->
                Duration.between(credenciamento.getDataAtualizacao(), LocalDateTime.now()).toHours() >= horasUltAtualizacao
        ).collect(Collectors.toList());

        credenciamentosLead.forEach(credenciamento -> {
            if(contemDadosMinimos(credenciamento)){
                DadosLead dadosLead = leadMapper.from(credenciamento);
                port.sendLead(dadosLead);
            }
        });

    }

    private boolean contemDadosMinimos(DadosCredenciamento credenciamento) {

        return Objects.nonNull(credenciamento.getProprietario().getCpf()) &&
                Objects.nonNull(credenciamento.getProprietario().getNome()) &&
                Objects.nonNull(credenciamento.getProprietario().getEmail()) &&
                Objects.nonNull(credenciamento.getProprietario().getTelefone()) &&
                Objects.nonNull(credenciamento.getEstabelecimento().getCnpj()) &&
                Objects.nonNull(credenciamento.getEstabelecimento().getNomeFantasia()) &&
                Objects.nonNull(credenciamento.getEstabelecimento().getRazaoSocial());

    }

}
