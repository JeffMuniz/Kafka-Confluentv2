package br.com.mac.estautoecapi.unit.service;

import br.com.mac.adapter.rest.model.ResponseModel;
import br.com.mac.customexception.exception.ExceptionCustom;
import br.com.mac.estautoecapi.bean.PessoaFisicaResponse;
import br.com.mac.estautoecapi.bean.PoliticaResponse;
import br.com.mac.estautoecapi.port.MotorPoliticaPort;
import br.com.mac.estautoecapi.repository.PessoaFisicaQuestionarioRepository;
import br.com.mac.estautoecapi.repository.PessoaJuridicaRepository;
import br.com.mac.estautoecapi.service.MotorPoliticaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.ZonedDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@RunWith(MockitoJUnitRunner.class)
public class MotorPoliticaServiceTest {

    @Mock
    private MotorPoliticaPort port;

    @Mock
    private PessoaFisicaQuestionarioRepository pessoaFisicaQuestionarioRepository;

    @Mock
    private PessoaJuridicaRepository pessoaJuridicaRepository;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private MotorPoliticaService service;

    @Test
    public void consultarPessoaJuridicaPrimeiraConsultaTest() {
        PoliticaResponse mockPoliticaResponse = PoliticaResponse.builder().build();

        when(pessoaJuridicaRepository.findByCnpj(anyString())).thenReturn(null);
        when(port.getEmpresas(any())).thenReturn(new ResponseModel<>(UUID.randomUUID(), OK.value(), "", mockPoliticaResponse, null, ZonedDateTime.now()));
        when(objectMapper.convertValue(any(), ArgumentMatchers.eq(PoliticaResponse.class))).thenReturn(mockPoliticaResponse);

        PoliticaResponse politicaResponse = service.consultarPessoaJuridica("33444199000155");

        assertThat(politicaResponse).isNotNull();

        verify(pessoaJuridicaRepository, times(1)).save(any());
    }

    @Test
    public void consultarPessoaJuridicaConsultaRepetidaTest() {
        PoliticaResponse mockPoliticaResponse = PoliticaResponse.builder().build();

        when(pessoaJuridicaRepository.findByCnpj(anyString())).thenReturn(mockPoliticaResponse);

        PoliticaResponse politicaResponse = service.consultarPessoaJuridica("33444199000155");

        assertThat(politicaResponse).isNotNull();

        verify(port, never()).getEmpresas(any());
        verify(pessoaJuridicaRepository, never()).save(any());
    }

    @Test(expected = ExceptionCustom.class)
    public void consultarPessoaJuridicaPrimeiraConsultaEmptyResponseTest() {
        when(pessoaJuridicaRepository.findByCnpj(anyString())).thenReturn(null);
        when(port.getEmpresas(any())).thenReturn(new ResponseModel<>(UUID.randomUUID(), OK.value(), "", null, null, ZonedDateTime.now()));

        service.consultarPessoaJuridica("33444199000155");

        verify(pessoaJuridicaRepository, never()).save(any());
    }

    @Test(expected = ExceptionCustom.class)
    public void consultarPessoaJuridicaPrimeiraConsultaNotFoundTest() {
        when(pessoaJuridicaRepository.findByCnpj(anyString())).thenReturn(null);
        when(port.getEmpresas(any())).thenReturn(new ResponseModel<>(UUID.randomUUID(), NOT_FOUND.value(), "", null, null, ZonedDateTime.now()));

        service.consultarPessoaJuridica("33444199000155");

        verify(pessoaJuridicaRepository, never()).save(any());
    }

    @Test(expected = ExceptionCustom.class)
    public void consultarPessoaFisicaNotFoundTest() {
        when(port.getPessoa(anyString())).thenReturn(new ResponseModel<>(UUID.randomUUID(), NOT_FOUND.value(), "", null, null, ZonedDateTime.now()));

        PessoaFisicaResponse pessoaFisicaResponse = service.consultarPessoaFisica("38850095522");

        assertThat(pessoaFisicaResponse).isNotNull();
    }

    @Test
    public void consultarPessoaFisicaFoundTest() {
        when(port.getPessoa(anyString())).thenReturn(new ResponseModel<>(UUID.randomUUID(), OK.value(), "", PessoaFisicaResponse.builder().build(), null, ZonedDateTime.now()));
        when(objectMapper.convertValue(any(), ArgumentMatchers.eq(PessoaFisicaResponse.class))).thenReturn(PessoaFisicaResponse.builder().build());

        PessoaFisicaResponse pessoaFisicaResponse = service.consultarPessoaFisica("38850095522");

        assertThat(pessoaFisicaResponse).isNotNull();
    }

}
