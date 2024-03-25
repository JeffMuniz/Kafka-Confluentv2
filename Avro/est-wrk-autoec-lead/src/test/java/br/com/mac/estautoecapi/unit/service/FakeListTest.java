package br.com.mac.estautoecapi.unit.service;

import br.com.mac.estautoecapi.bean.Endereco;
import br.com.mac.estautoecapi.service.CepService;
import br.com.mac.estautoecapi.service.FakeList;
import br.com.mac.estautoecapi.service.MotorPoliticaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static br.com.mac.estautoecapi.bean.credenciamento.enums.Sexo.MASCULINO;
import static com.mongodb.assertions.Assertions.assertTrue;
import static java.util.Objects.nonNull;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FakeListTest {

    @Mock
    private MotorPoliticaService motorPoliticaService;

    @Mock
    private CepService cepService;

    @InjectMocks
    private FakeList service;

    @Test
    public void buildFakeListNomesMaeTest() {
        List<String> teste = service.buildFakeListNomesMae("Teste");

        assertTrue(nonNull(teste));
        assertThat(teste.size()).isEqualTo(4);
        assertTrue(teste.stream().findAny().orElse("").startsWith("T"));
    }

    @Test
    public void buildFakeListNomesTestStartingN() {
        List<String> teste = service.buildFakeListNomes("MASCULINO", "Normandir");

        assertTrue(nonNull(teste));
        assertThat(teste.size()).isEqualTo(4);
        assertTrue(teste.stream().findAny().orElse("").startsWith("N"));
    }

    @Test
    public void buildFakeListNomesTestStartingF() {
        List<String> teste = service.buildFakeListNomes(MASCULINO, "Fabricio");

        assertTrue(nonNull(teste));
        assertThat(teste.size()).isEqualTo(4);
        assertTrue(teste.stream().findAny().orElse("").startsWith("F"));
    }

    @Test
    public void buildFakeListEnderecosVazioTest() {
        List<String> enderecos = service.buildFakeListEnderecos(null);

        assertThat(enderecos).isEmpty();
    }

    @Test
    public void buildFakeListEnderecosTest() {
        when(cepService.getLogradouros(any())).thenReturn(mockReturnEnderecos());

        List<String> enderecos = service.buildFakeListEnderecos(Endereco.builder().logradouro("Rua da sorte").build());

        assertThat(enderecos).isNotEmpty();
        assertThat(enderecos.size()).isEqualTo(4);
    }

    private Set<String> mockReturnEnderecos() {
        return new HashSet<>(Arrays.asList("Rua", "Avenia", "Estrada", "Praça"));
    }

    @Test
    public void buildFakeListDataNascimentoTest() {
        List<LocalDate> teste = service.buildFakeListDataNascimento(LocalDate.now());

        assertTrue(nonNull(teste));
        assertThat(teste.size()).isEqualTo(4);
    }

    @Test
    public void buildFakeListNomesMasculinoTest() {
        String sexo = "Masculino";
        String nome = "Gilberto";

        List<String> returnedList = service.buildFakeListNomes(sexo, nome);

        assertThat(returnedList).isNotNull();
        assertThat(returnedList).hasSize(4);
        assertThat(returnedList).contains(nome.toUpperCase());
    }

    @Test
    public void buildFakeListNomesFemininoTest() {
        String sexo = "Feminino";
        String nome = "Camila";

        List<String> returnedList = service.buildFakeListNomes(sexo, nome);

        assertThat(returnedList).isNotNull();
        assertThat(returnedList).hasSize(4);
        assertThat(returnedList).contains(nome.toUpperCase());
    }

    @Test
    public void buildFakeListNomesSemSexoTest() {
        String sexo = null;
        String nome = "Paula";

        List<String> returnedList = service.buildFakeListNomes(sexo, nome);

        assertThat(returnedList).isNotNull();
        assertThat(returnedList).hasSize(4);
        assertThat(returnedList).contains(nome.toUpperCase());
    }

    @Test
    public void buildFakeListNomesSexoInvalidoTest() {
        String sexo = "M";
        String nome = "Gilberto";

        List<String> returnedList = service.buildFakeListNomes(sexo, nome);

        assertThat(returnedList).isNotNull();
        assertThat(returnedList).hasSize(4);
        assertThat(returnedList).contains(nome.toUpperCase());
    }

    @Test
    public void buildNomesMaeFakeTest() {
        String nome = "Nome";

        List<String> returnedList = service.buildFakeListNomesMae(nome);

        assertThat(returnedList).isNotNull();
        assertThat(returnedList).hasSize(4);
        assertThat(returnedList).contains(nome.toUpperCase());
    }

    @Test
    public void buildFakeListDatasNascimento(){

        List<LocalDate> returnedList = service.buildFakeListDataNascimento(LocalDate.now());

        assertThat(returnedList).isNotNull();
        assertThat(returnedList).hasSize(4);
    }

    @Test
    public void buildFakeListEnderecos(){
        Set<String> mockEnderecos = new HashSet<>();

        service.buildFakeListEnderecos(Endereco.builder().build());
    }
}
