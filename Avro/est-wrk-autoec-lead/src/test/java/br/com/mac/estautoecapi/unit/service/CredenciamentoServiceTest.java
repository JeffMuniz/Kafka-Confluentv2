package br.com.mac.estautoecapi.unit.service;

import br.com.mac.estautoecapi.repository.DadosCredenciamentoRepository;
import br.com.mac.estautoecapi.service.CredenciamentoService;
import br.com.mac.estautoecapi.service.MotorPoliticaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.validation.beanvalidation.CustomValidatorBean;

@RunWith(MockitoJUnitRunner.class)
public class CredenciamentoServiceTest {

    @Mock
    private MotorPoliticaService motorPoliticaService;

    @Mock
    private DadosCredenciamentoRepository repository;

    @Mock
    private CustomValidatorBean validator;

    @InjectMocks
    private CredenciamentoService service;

    @Test
    public void salvarTest() {
//        service.salvar();
    }

    @Test
    public void atualizar() {
//        service.atualizar(String id, DadosCredenciamento dadosCredenciamento);
    }
}
