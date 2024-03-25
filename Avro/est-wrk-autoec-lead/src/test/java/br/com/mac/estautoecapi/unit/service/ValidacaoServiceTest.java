package br.com.mac.estautoecapi.unit.service;

import br.com.mac.customexception.exception.BadRequestCustom;
import br.com.mac.estautoecapi.bean.PessoaFisicaResponse;
import br.com.mac.estautoecapi.bean.ValidaPessoaFisicaRequest;
import br.com.mac.estautoecapi.repository.DadosCredenciamentoRepository;
import br.com.mac.estautoecapi.repository.PessoaFisicaQuestionarioRepository;
import br.com.mac.estautoecapi.service.FakeList;
import br.com.mac.estautoecapi.service.MotorPoliticaService;
import br.com.mac.estautoecapi.service.SocioService;
import br.com.mac.estautoecapi.service.ValidacaoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ValidacaoServiceTest {

    @Mock
    private FakeList fakeList;

    @Mock
    private MotorPoliticaService motorPoliticaService;

    @Mock
    private SocioService socioService;

    @Mock
    private PessoaFisicaQuestionarioRepository pessoaFisicaQuestionarioRepository;

    @Mock
    private DadosCredenciamentoRepository dadosCredenciamentoRepository;

    @InjectMocks
    private ValidacaoService service;

    @Test
    public void consultarPessoaFisicaValidacaoPrimeiraConsultaTest() {
        PessoaFisicaResponse pessoaMock = PessoaFisicaResponse.builder()
                .nome("NOME")
                .cpf("38850095522")
                .nomeMae("Nome Mãe")
                .dataNascimento(LocalDate.now())
                .build();
        when(socioService.isSocio(anyString(), anyString())).thenReturn(true);
        when(pessoaFisicaQuestionarioRepository.findByCpf(anyString())).thenReturn(null);
        when(motorPoliticaService.consultarPessoaFisica(anyString())).thenReturn(pessoaMock);
        when(fakeList.buildFakeListNomesMae(anyString())).thenReturn(null);
        when(fakeList.buildFakeListDataNascimento(any())).thenReturn(null);
        when(pessoaFisicaQuestionarioRepository.save(any())).thenReturn(PessoaFisicaResponse.builder().build());

        PessoaFisicaResponse pessoaFisicaResponse = service.consultarPessoaFisicaValidacao("37444333000193", "38850095522");

        assertThat(pessoaFisicaResponse).isNotNull();
        assertThat(pessoaFisicaResponse.getNome()).isNull();
        assertThat(pessoaFisicaResponse.getNomeMae()).isNull();
        assertThat(pessoaFisicaResponse.getDatasNascimento()).isNull();

        verify(fakeList, times(1)).buildFakeListNomesMae(anyString());
        verify(fakeList, times(1)).buildFakeListDataNascimento(any());
        verify(pessoaFisicaQuestionarioRepository, times(1)).save(any());
    }

    @Test
    public void consultarPessoaFisicaValidacaoConsultaRepetidaTest() {
        PessoaFisicaResponse pessoaMock = PessoaFisicaResponse.builder()
                .nome("NOME")
                .cpf("38850095522")
                .nomeMae("Nome Mãe")
                .build();
        when(socioService.isSocio(anyString(), anyString())).thenReturn(true);
        when(pessoaFisicaQuestionarioRepository.findByCpf(anyString())).thenReturn(pessoaMock);

        PessoaFisicaResponse pessoaFisicaResponse = service.consultarPessoaFisicaValidacao("37444333000193", "38850095522");

        assertThat(pessoaFisicaResponse).isNotNull();
        verify(fakeList, never()).buildFakeListNomesMae(anyString());
        verify(pessoaFisicaQuestionarioRepository, never()).save(any());
        verify(motorPoliticaService, never()).consultarPessoaFisica(anyString());
        verify(pessoaFisicaQuestionarioRepository, times(1)).findByCpf(anyString());
    }

    @Test
    public void validarPessoaValidaTest() {
        String cpf = "123";
        String nome = "Nome Teste Pessoa";
        ValidaPessoaFisicaRequest mockPessoa = new ValidaPessoaFisicaRequest();
        mockPessoa.setCpf(cpf);
        mockPessoa.setNome(nome);

        when(socioService.isSocio(anyString(), anyString())).thenReturn(true);
        when(pessoaFisicaQuestionarioRepository.findByCpf(anyString())).thenReturn(PessoaFisicaResponse.builder().cpf(cpf).nome(nome).build());

        service.validarPessoa("", mockPessoa);
    }

    @Test(expected = BadRequestCustom.class)
    public void validarPessoaInvalidaTest() {
        String cpf = "34455059822";
        ValidaPessoaFisicaRequest mockRequest = new ValidaPessoaFisicaRequest();
        mockRequest.setCpf("11111155555");

        when(pessoaFisicaQuestionarioRepository.findByCpf(anyString())).thenReturn(PessoaFisicaResponse.builder().cpf(cpf).build());

        service.validarPessoa("33463109000199", mockRequest);

        verify(pessoaFisicaQuestionarioRepository, never()).findByCpf(anyString());
        verify(dadosCredenciamentoRepository, never()).save(any());
    }

    @Test(expected = BadRequestCustom.class)
    public void validarPessoaValidaNaoSocioTest() {
        String cpf = "123";
        String nome = "Nome Teste Pessoa";
        ValidaPessoaFisicaRequest mockPessoa = new ValidaPessoaFisicaRequest();
        mockPessoa.setCpf(cpf);
        mockPessoa.setNome(nome);

        when(socioService.isSocio(anyString(), anyString())).thenReturn(false);
        when(pessoaFisicaQuestionarioRepository.findByCpf(anyString())).thenReturn(PessoaFisicaResponse.builder().cpf(cpf).nome(nome).build());

        service.validarPessoa("33463109000199", mockPessoa);

        verify(pessoaFisicaQuestionarioRepository, never()).findByCpf(anyString());
        verify(dadosCredenciamentoRepository, never()).save(any());
    }

    @Test
    public void validarPessoaByNomeSuccessTest(){
        String cpf = "123";
        String nome = "Nome Teste Pessoa";
        ValidaPessoaFisicaRequest mockPessoa = new ValidaPessoaFisicaRequest();
        mockPessoa.setCpf(cpf);
        mockPessoa.setNome(nome);

        when(socioService.isSocio(anyString(), anyString())).thenReturn(true);
        when(pessoaFisicaQuestionarioRepository.findByCpf(anyString())).thenReturn(PessoaFisicaResponse.builder().cpf(cpf).nome(nome).build());

        service.validarPessoa("", mockPessoa);
    }

    @Test(expected = BadRequestCustom.class)
    public void validarPessoaNaoEncontradaTest(){
        String nome = "Nome Teste Pessoa";
        ValidaPessoaFisicaRequest mockPessoa = new ValidaPessoaFisicaRequest();
        mockPessoa.setCpf("123");
        mockPessoa.setNome(nome);

        when(pessoaFisicaQuestionarioRepository.findByCpf(anyString())).thenReturn(null);

        service.validarPessoa("", mockPessoa);

        verify(socioService, never()).isSocio(anyString(), anyString());
    }

    @Test(expected = BadRequestCustom.class)
    public void validarPessoaByNomeErrorTest(){
        String nome = "Nome Teste Pessoa";
        ValidaPessoaFisicaRequest mockPessoa = new ValidaPessoaFisicaRequest();
        mockPessoa.setCpf("123");
        mockPessoa.setNome(nome);

        when(pessoaFisicaQuestionarioRepository.findByCpf(anyString())).thenReturn(PessoaFisicaResponse.builder().nome(nome + "2").build());

        service.validarPessoa("", mockPessoa);
        verify(socioService, never()).isSocio(anyString(), anyString());
    }

    @Test
    public void validarPessoaByNomeMaeSuccessTest(){
        String cpf = "123";
        String nome = "Nome Teste Pessoa";
        ValidaPessoaFisicaRequest mockPessoa = new ValidaPessoaFisicaRequest();
        mockPessoa.setCpf(cpf);
        mockPessoa.setNomeMae(nome);

        when(socioService.isSocio(anyString(), anyString())).thenReturn(true);
        when(pessoaFisicaQuestionarioRepository.findByCpf(anyString())).thenReturn(PessoaFisicaResponse.builder().cpf(cpf).nomeMae(nome).build());

        service.validarPessoa("", mockPessoa);
    }

    @Test(expected = BadRequestCustom.class)
    public void validarPessoaByNomeMaeErrorTest(){
        String cpf = "123";
        String nome = "Nome Teste Pessoa";
        ValidaPessoaFisicaRequest mockPessoa = new ValidaPessoaFisicaRequest();
        mockPessoa.setCpf(cpf);
        mockPessoa.setNomeMae(nome);

        when(pessoaFisicaQuestionarioRepository.findByCpf(anyString())).thenReturn(PessoaFisicaResponse.builder().cpf(cpf).nomeMae("Outro Nome Mae").build());

        service.validarPessoa("", mockPessoa);

        verify(socioService, never()).isSocio(anyString(), anyString());
    }

    @Test
    public void validarPessoaByDateSuccessTest(){
        String cpf = "123";
        String nome = "Nome Teste Pessoa";
        ValidaPessoaFisicaRequest mockPessoa = new ValidaPessoaFisicaRequest();
        mockPessoa.setCpf(cpf);
        mockPessoa.setDataNascimento(LocalDate.now());

        when(socioService.isSocio(anyString(), anyString())).thenReturn(true);
        when(pessoaFisicaQuestionarioRepository.findByCpf(anyString())).thenReturn(PessoaFisicaResponse.builder().cpf(cpf).dataNascimento(LocalDate.now()).nomeMae(nome).build());

        service.validarPessoa("", mockPessoa);
    }

    @Test(expected = BadRequestCustom.class)
    public void validarPessoaByDateErrorTest(){
        LocalDate dtMock = LocalDate.now();
        ValidaPessoaFisicaRequest mockPessoa = new ValidaPessoaFisicaRequest();
        mockPessoa.setCpf("123");
        mockPessoa.setDataNascimento(dtMock.minusDays(1));

        when(pessoaFisicaQuestionarioRepository.findByCpf(anyString())).thenReturn(PessoaFisicaResponse.builder().dataNascimento(dtMock).build());

        service.validarPessoa("", mockPessoa);

        verify(socioService, never()).isSocio(anyString(), anyString());

    }

}
