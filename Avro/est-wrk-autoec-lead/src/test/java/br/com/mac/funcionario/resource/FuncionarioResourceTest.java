package br.com.mac.funcionario.resource;

import br.com.mac.funcionario.blue.domain.Funcionario;
import br.com.mac.funcionario.repository.blue.EmpresaRepository;
import br.com.mac.funcionario.repository.blue.FuncionarioRepository;
import br.com.mac.funcionario.repository.controleAcesso.UsuarioAcessoRepository;
import br.com.mac.funcionario.service.FuncionarioAtivarService;
import br.com.mac.funcionario.service.FuncionarioConsultaService;
import br.com.mac.funcionario.service.FuncionarioInativarService;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.Month;


import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResumacatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles({"dev", "test"})
public class FuncionarioResourceTest {

    private static final Long FUNCIONARIO_ID = 1L;
    private static final String FUNCIONARIO_CNPJ = "85239423000111";
    private static final String FUNCIONARIO_CPF = "44949535048";
    private static final String FUNCIONARIO_NOME = "Antonio da Silva";
    public static final Long USUARIO_ID = 1L;

    @Autowired
    MockMvc mvc;

    @MockBean
    private FuncionarioRepository funcionarioRepository;

    @MockBean
    private EmpresaRepository empresaRepository;

    @MockBean
    private FuncionarioAtivarService funcionarioAtivarService;

    @MockBean
    private FuncionarioInativarService funcionarioInativarService;

    @MockBean
    private FuncionarioConsultaService funcionarioConsultaService;

    @MockBean
    private UsuarioAcessoRepository usuarioAcessoRepository;


    private Funcionario funcionario;

    private LocalDateTime dataHora;

    @BeforeEach
    public void setup() throws Exception {
        dataHora = LocalDateTime.now();
        funcionarioAtivarService = new FuncionarioAtivarService();


        funcionario = Funcionario.builder()
                .cnpj(FUNCIONARIO_CNPJ)
                .cpf(FUNCIONARIO_CPF)
                .dataHora(ZonedDateTime.of(LocalDateTime.of(2020, Month.OCTOBER, 9, 0, 0) , ZoneId.systemDefault()))
                .idFuncionario(FUNCIONARIO_ID)
                .nome(FUNCIONARIO_NOME)
                .status(1L)
                .build();
    }

//    @Test
//    @DisplayName("Deve obter erro ao inativar lista com apenas um funcionario e sem o cpf")
//    public void deveObterErroAoInativarListaComApenasUmFuncionario() throws Exception {
//
//        Usuario usuario = new Usuario();
//        usuario.setStatus(1);
//        usuario.setCpf("12312311100");
//
//        FuncionarioRequestDTO funcionarioNovo = FuncionarioRequestDTO.builder()
//                .idFuncionario(FUNCIONARIO_ID)
//                .motivo(MotivoInativar.toEnum(1).getDescricao())
//                .build();
//
//        RequestFuncionarioDTO requestDTO = RequestFuncionarioDTO.builder()
////                .id(1L)
//                .funcionarios(Arrays.asList(funcionarioNovo))
//                .build();
//
//        ResponseEntity<ResponseFuncionarioDTO> responseEntity =  getResponseEntity();
//
//        String json = new ObjectMapper().writeValueAsString(requestDTO);
////
////        Mockito.when(funcionarioInativarService.inativarFuncionario(Mockito.anyInt(),
////                                                                    Mockito.any(),
////                                                                    Mockito.any(),
////                                                                    Mockito.any()))
////                .thenReturn(responseEntity);
////        Mockito.when(usuarioAcessoRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(usuario));
//
//        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
//                .put("/inativar")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .content(json);
//
//        mvc
//                .perform(request)
//                .andExpect(status().isOk());
//
//        org.junit.jupiter.api.Assertions.assertThrows(UsuarioNaoEncontradoException.class, () -> usuarioAcessoRepository
//            .findById(1L));
//
//        Mockito.verify(usuarioAcessoRepository, Mockito.never()).findById(1L);
//
//    }
//
//    private ResponseEntity<ResponseFuncionarioDTO> getResponseEntity() {
//        return ResponseEntity.status(HttpStatus.OK).body(ResponseFuncionarioDTO.builder()
//                .funcionarios(Arrays.asList(FuncionarioResponseDTO.builder()
//                        .cpf(FUNCIONARIO_CPF)
//                        .status(Acao.ATIVAR.getDescricao())
//                        .build()))
//                .build());
//    }

//    @Test
//    @DisplayName("Deve inativar lista com apenas um funcionario")
//    public void deveInativarListaComApenasUmFuncionario() throws Exception {
//
//        FuncionarioRequestDTO funcionarioNovo = FuncionarioRequestDTO.builder()
//                .idFuncionario(FUNCIONARIO_ID)
//                .motivo(MotivoInativar.toEnum(1).getDescricao())
//                .build();
//
//        RequestFuncionarioDTO requestDTO = RequestFuncionarioDTO.builder()
//                .id(1283880L)
//                .funcionarios(Arrays.asList(funcionarioNovo))
//                .build();
//
//        String json = new ObjectMapper().writeValueAsString(requestDTO);
//
//        Mockito.when(funcionariosRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(funcionario));
//
//        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
//                .put("/inativar")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .content(json);
//
//        mvc
//                .perform(request)
//                .andExpect(status().isOk());
//
//    }
//
//    @Test
//    @DisplayName("Deve obter erro ao ativar lista com apenas um funcionario e nao informar o cpf")
//    public void deveObterErroAoAtivarListaComApenasUmFuncionario() throws Exception {
//
//        FuncionarioRequestDTO funcionarioNovo = FuncionarioRequestDTO.builder()
//                .idFuncionario(FUNCIONARIO_ID)
//                .motivo(MotivoInativar.toEnum(1).getDescricao())
//                .build();
//
//        RequestFuncionarioDTO requestDTO = RequestFuncionarioDTO.builder()
//                .funcionarios(Arrays.asList(funcionarioNovo))
//                .build();
//
//        String json = new ObjectMapper().writeValueAsString(requestDTO);
//
//        Mockito.when(funcionariosRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(funcionario));
//
//        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
//                .put("/ativar")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .content(json);
//
//        mvc
//                .perform(request)
//                .andExpect(status().is4xxClientError());
//
//    }

//    @Test
//    @DisplayName("Deve ativar lista com apenas um funcionario")
//    public void deveAtivarListaComApenasUmFuncionario() throws Exception {
//
//        FuncionarioRequestDTO funcionarioNovo = FuncionarioRequestDTO.builder()
//                .idFuncionario(3L)
//                .motivo(MotivoAtivar.toEnum("recontratação").getDescricao())
//                .build();
//
//        RequestFuncionarioDTO requestDTO = RequestFuncionarioDTO.builder()
//                .id(1283880L)
//                .funcionarios(Arrays.asList(funcionarioNovo))
//                .build();
//
//        String json = new ObjectMapper().writeValueAsString(requestDTO);
//
//        Mockito.when(funcionariosRepository.findById(3L)).thenReturn(Optional.of(funcionario));
//
//        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
//                .put("/ativar")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .content(json);
//
//        mvc
//                .perform(request)
//                .andExpect(status().isOk());
//
//    }

//    @Test
//    @DisplayName("Deve lançar erro de validação quando nao houver dados suficientes para inativar funcionarios")
//    public void deveLancarErroAoTentarInativarListaDeFuncionariosVazia() throws Exception {
//
//        RequestFuncionarioDTO requestDTO = RequestFuncionarioDTO.builder()
//                .id(1283880L)
//                .funcionarios(Arrays.asList())
//                .build();
//
//        String json = new ObjectMapper().writeValueAsString(requestDTO);
//
//        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
//                .put("/inativar")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .content(json);
//
//        mvc
//                .perform(request)
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("erros", hasSize(1)));
//
//    }

//    @Test
//    @DisplayName("Deve lançar erro de validação quando nao houver dados suficientes para ativar funcionarios")
//    public void deveLancarErroAoTentarAtivarListaDeFuncionariosVazia() throws Exception {
//
//        RequestFuncionarioDTO requestDTO = RequestFuncionarioDTO.builder()
//                .id(1283880L)
//                .funcionarios(Arrays.asList())
//                .build();
//
//        String json = new ObjectMapper().writeValueAsString(requestDTO);
//
//        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
//                .put("/ativar")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .content(json);
//
//        mvc
//                .perform(request)
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("erros", hasSize(1)));
//
//    }

//    @Test
//    @DisplayName("Deve consultar o cnpj do funcionario que esta consultando o status dos funcionarios")
//    public void deveConsultarCnpjSolicitanteStatusFuncionario() throws Exception {
//        FuncionarioStatusRequestDTO funcionario = FuncionarioStatusRequestDTO.builder()
//                .cpf("87491039030")
//                .build();
//
//        FuncionarioStatusResponseDTO funcionarioResponse = FuncionarioStatusResponseDTO.builder()
//                .status("ATIVO")
//                .cpf("87491039030")
//                .build();
//
//        RequestDTO requestDTO = RequestDTO.builder()
//                .cnpj("50025051000146")
////                .funcionarios(Arrays.asList(funcionario))
//                .build();
//
//        String json = new ObjectMapper().writeValueAsString(requestDTO);
//
//        Mockito.when(empresaRepository.findIEmpresaDTOByCnpj(Mockito.anyString())).thenReturn(Optional.of(new IEmpresaDTO() {
//            @Override
//            public String getCnpj() {
//                return FUNCIONARIO_CNPJ;
//            }
//        }));
//
//        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
//                .post("/consultar-status-funcionario")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .content(json);
//
//        mvc
//                .perform(request)
//                .andExpect(status().isOk());
//    }

//    @Test
//    @DisplayName("Deve consultar o status de uma lista com varios funcionarios")
//    public void deveConsultarStatusFuncionario() throws Exception {
//        FuncionarioStatusRequestDTO funcionario = FuncionarioStatusRequestDTO.builder()
//                .cpf("87491039030")
//                .build();
//
//        FuncionarioStatusResponseDTO funcionarioResponse = FuncionarioStatusResponseDTO.builder()
//                .status("ATIVO")
//                .cpf("87491039030")
//                .build();
//
//        RequestDTO requestDTO = RequestDTO.builder()
//                .cnpj("50025051000146")
////                .funcionarios(Arrays.asList(funcionario))
//                .build();
//
//        String json = new ObjectMapper().writeValueAsString(requestDTO);
//
//        Mockito.when(empresaRepository.findIEmpresaDTOByCnpj(Mockito.anyString())).thenReturn(Optional.of(new IEmpresaDTO() {
//            @Override
//            public String getCnpj() {
//                return FUNCIONARIO_CNPJ;
//            }
//        }));
//
//        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
//                .post("/consultar-status-funcionario")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .content(json);
//
//        mvc
//                .perform(request)
//                .andExpect(status().isOk());
//
//    }
}
