package br.com.machina.estwrkautoeclead.service;

import br.com.machina.estwrkautoeclead.domain.DadosCredenciamento;
import br.com.machina.estwrkautoeclead.domain.Estabelecimento;
import br.com.machina.estwrkautoeclead.domain.Proprietario;
import br.com.machina.estwrkautoeclead.domain.mappers.LeadMapper;
import br.com.machina.estwrkautoeclead.port.SalesForcePort;
import br.com.machina.estwrkautoeclead.repository.DadosCredenciamentoRepository;
import org.awaitility.Duration;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.Collections;
import java.util.List;

import static org.awaitility.Awaitility.await;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LeadServiceTest {


    private LeadService service;
    @Mock
    private DadosCredenciamentoRepository dadosCredenciamentoRepository;
    @Mock
    private SalesForcePort port;
    @Mock
    private LeadMapper mapper;

    @Test
    public void extrairLead() {

        service = new LeadService(dadosCredenciamentoRepository, Long.valueOf(1), mapper, port);

        List<DadosCredenciamento> dados = Collections.singletonList(
                DadosCredenciamento.builder()
                        .origem("TESTE")
                        .dataAtualizacao(LocalDateTime.now().minus(Period.ofDays(1)))
                        .proprietario(getProprietario())
                        .estabelecimento(getEstabelecimento())
                        .build());
        when(port.sendLead(any())).thenReturn(null);
        when(dadosCredenciamentoRepository.findDadosCredenciamentoByStatusCredenciamento(anyString())).thenReturn(dados);

        try{
            service.extrairLead();
        }catch (Exception ex){
            System.out.println(ex);
            Assert.fail("Falha ao executar extração de lead");
        }


    }

    @Test
    public void jobRuns() {
        LeadService service = mock(LeadService.class);
        doNothing().when(service).extrairLead();
        service.extrairLead();
        await().atMost(Duration.FIVE_SECONDS)
                .untilAsserted(() -> verify(service, atLeast(1)).extrairLead());
    }

    private Proprietario getProprietario() {

        return Proprietario.builder().cpf("12345678909")
                .email("email@email.com")
                .nome("Teste")
                .telefone("6932155669")
                .build();
    }

    private Estabelecimento getEstabelecimento() {

        return Estabelecimento.builder()
                .razaoSocial("Empresa Teste")
                .cnpj("0235013813515")
                .nomeFantasia("Teste")
                .build();
    }
}