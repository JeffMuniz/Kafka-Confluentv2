package br.com.mac.estautoecapi.unit.service;

import br.com.mac.estautoecapi.bean.PoliticaResponse;
import br.com.mac.estautoecapi.bean.Socio;
import br.com.mac.estautoecapi.service.MotorPoliticaService;
import br.com.mac.estautoecapi.service.SocioService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SocioServiceTest {

    @Mock
    private MotorPoliticaService motorPoliticaService;

    @InjectMocks
    private SocioService service;

    @Test
    public void testSocioOK() {
        String cpf = "33355558000";
        Socio mockSocio = Socio.builder().cpf(cpf).build();
        when(motorPoliticaService.consultarPessoaJuridica(anyString())).thenReturn(PoliticaResponse.builder().socios(singletonList(mockSocio)).build());

        boolean socio = service.isSocio("33444199000155", cpf);

        assertThat(socio).isTrue();
    }

    @Test
    public void testSocioNaoEncontrado() {
        String cpf = "33355558000";
        Socio mockSocio = Socio.builder().cpf("099999999").build();
        when(motorPoliticaService.consultarPessoaJuridica(anyString())).thenReturn(PoliticaResponse.builder().socios(singletonList(mockSocio)).build());

        boolean socio = service.isSocio("33444199000155", cpf);

        assertThat(socio).isFalse();
    }
}
