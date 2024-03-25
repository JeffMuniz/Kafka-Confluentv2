package br.com.mac.funcionario.resource;

import br.com.mac.funcionario.dto.FuncionarioDTO;
import br.com.mac.funcionario.dto.HistoricoDTO;
import br.com.mac.funcionario.dto.request.RequestHistoricoDTO;
import br.com.mac.funcionario.blue.domain.Funcionario;
import br.com.mac.funcionario.blue.domain.Historico;
import br.com.mac.funcionario.enums.Acao;
import br.com.mac.funcionario.enums.MotivoAtivar;
import br.com.mac.funcionario.enums.MotivoInativar;
import br.com.mac.funcionario.repository.blue.HistoricoRepository;
import br.com.mac.funcionario.service.HistoricoService;
import br.com.mac.funcionario.utils.DataUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResumacatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles({"dev", "test"})
public class HistoricoResourceTest {

    private static final Long FUNCIONARIO_ID = 1L;
    private static final String FUNCIONARIO_CNPJ = "85239423000111";
    private static final String FUNCIONARIO_CPF = "44949535048";
    private static final String FUNCIONARIO_NOME = "Jose Antonio da Silva";
    private static final String CLIENTE_ID = "12345678901";

    @Autowired
    MockMvc mvc;

    @MockBean
    private HistoricoRepository historicoRepository;

    @MockBean
    private HistoricoService historicoService;

    private Historico historico;


    RequestHistoricoDTO requestHistoricoDTO;

    @BeforeEach
    public void setup() throws Exception {

        historicoService = new HistoricoService(historicoRepository);

        FuncionarioDTO funcionarioDTO = FuncionarioDTO.builder()
                .cnpj(FUNCIONARIO_CNPJ)
                .cpf(FUNCIONARIO_CPF)
                .dataHora(DataUtils.retornarDataHoraAtual())
                .idFuncionario(FUNCIONARIO_ID)
                .nome(FUNCIONARIO_NOME)
                .status(0L)
                .build();

        historico = Historico.builder()
                .acao(Acao.ATIVAR.name())
                .cnpj(funcionarioDTO.getCnpj())
                .cpf(funcionarioDTO.getCpf())
//                .dataHora(DataUtils.retornarDataHoraAtual())
                .funcionario(Funcionario.builder().idFuncionario(funcionarioDTO.getIdFuncionario()).build())
                .loginOperador("MARCELO")
                .motivo(MotivoAtivar.RETORNO_AFASTAMENTO.name())
                .build();

        HistoricoDTO historicoDTO = HistoricoDTO.builder()
                .acao(historico.getAcao())
                .cnpj(historico.getCnpj())
                .cpf(historico.getCpf())
//                .dataHora(historico.getDataHora())
                .idHistorico(historico.getIdHistorico())
                .loginOperador(CLIENTE_ID)
                .motivo(historico.getMotivo())
                .build();

        requestHistoricoDTO = RequestHistoricoDTO.builder()
                .historicos(Arrays.asList(historicoDTO))
                .build();
    }

    @Test
    @DisplayName("Deve consultar o historico de um vinculo atraves do cpf")
    public void deveConsultarHistoricoAtravesVinculoCpf() throws Exception {

        Mockito.when(historicoRepository.findByCpf(FUNCIONARIO_CPF))
                .thenReturn(Arrays.asList(historico));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/historico".concat("/cpf/".concat(FUNCIONARIO_CPF)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mvc
                .perform(request)
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve consultar o historico de um vinculo atraves do cnpj")
    public void deveConsultarHistoricoAtravesVinculoCnpj() throws Exception {

        Mockito.when(historicoRepository.findByCpf(FUNCIONARIO_CNPJ))
                .thenReturn(Arrays.asList(historico));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/historico".concat("/cnpj/".concat(FUNCIONARIO_CNPJ)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mvc
                .perform(request)
                .andExpect(status().isOk());
    }

}
