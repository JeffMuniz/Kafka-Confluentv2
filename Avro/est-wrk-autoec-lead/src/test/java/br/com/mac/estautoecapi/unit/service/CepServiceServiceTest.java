package br.com.mac.estautoecapi.unit.service;

import br.com.mac.adapter.rest.macRestAdapter;
import br.com.mac.adapter.rest.model.ResponseModel;
import br.com.mac.estautoecapi.bean.Endereco;
import br.com.mac.estautoecapi.service.CepService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RunWith(MockitoJUnitRunner.class)
public class CepServiceServiceTest {

    @Mock
    private macRestAdapter restTemplate;

    @InjectMocks
    private CepService service;

    @Test
    public void getLogradourosTest() {
        ResponseModel<Object> mockResponse = new ResponseModel<>(UUID.randomUUID(), NOT_FOUND.value(), "", null, null, ZonedDateTime.now());
        List<Endereco> enderecos = new ArrayList<>();
        enderecos.add(Endereco.builder().build());
        enderecos.add(Endereco.builder().build());

        Set<String> logradouros = service.getLogradouros(Endereco.builder().logradouro("Rua praca 1").build());

        assertThat(logradouros.size()).isEqualTo(4);
    }

    @Test
    public void consultarEnderecosDefaultTest(){
        List<Endereco> enderecos = new ArrayList<>();
        enderecos.add(Endereco.builder().build());
        enderecos.add(Endereco.builder().build());

        ResponseModel<Object> mockResponse = new ResponseModel<>(UUID.randomUUID(), NOT_FOUND.value(), "", enderecos, null, ZonedDateTime.now());

        when(restTemplate.getRequest(any())).thenReturn(mockResponse);

        service.getLogradouros(Endereco.builder().uf("SP").cidade("Santos").logradouro("Rua Santa Marcelina de Vaz Sás").build());

        verify(restTemplate, times(1)).getRequest(any());
    }

    @Test
    public void consultarEnderecosSoEncontra1RuaPorCallTest(){
        Endereco[] mock = {Endereco.builder().build()};

        ResponseModel<Object> mockResponse = new ResponseModel<>(UUID.randomUUID(), NOT_FOUND.value(), "", mock, null, ZonedDateTime.now());

        when(restTemplate.getRequest(any())).thenReturn(mockResponse);

        service.getLogradouros(Endereco.builder().uf("SP").cidade("Santos").logradouro("Rua Santa Marcelina de Vaz Sás").build());

        verify(restTemplate, times(1)).getRequest(any());
    }
}
