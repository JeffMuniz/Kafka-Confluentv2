package br.com.mac.funcionario.service;

import br.com.mac.funcionario.blue.domain.Funcionario;
import br.com.mac.funcionario.dto.FuncionarioRequestDTO;
import br.com.mac.funcionario.enums.MotivoInativar;
import br.com.mac.funcionario.repository.blue.FuncionarioRepository;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles({"dev", "test"})
public class FuncionarioAtivarServiceTest {

    private static final Long FUNCIONARIO_ID = 1L;
    private static final String FUNCIONARIO_CNPJ = "85239423000111";
    private static final String FUNCIONARIO_CPF = "44949535048";
    private static final String FUNCIONARIO_NOME = "Jose Antonio da Silva";
    private static final String CLIENTE_ID = "12345678901";
    private static final Integer STATUS_ATIVAR = 1;
    private static final Integer STATUS_INATIVAR = 2;
    public static final Long USUARIO_ID = 1L;

    @Autowired
    MockMvc mvc;

    @MockBean
    private FuncionarioRepository funcionarioRepository;

    private FuncionarioAtivarService funcionarioAtivarService;

    private Funcionario funcionario;

    ZonedDateTime dataHora;

    @BeforeEach
    public void setup() throws Exception {
        dataHora = ZonedDateTime.now(ZoneId.systemDefault());
        funcionarioAtivarService = new FuncionarioAtivarService();

        funcionario = Funcionario.builder()
                .cnpj(FUNCIONARIO_CNPJ)
                .cpf(FUNCIONARIO_CPF)
                .dataHora(ZonedDateTime.of(LocalDateTime.of(2020, Month.OCTOBER,9, 0, 0), ZoneId.systemDefault()))
                .idFuncionario(FUNCIONARIO_ID)
                .motivo(MotivoInativar.toEnum(1).getDescricao())
                .nome(FUNCIONARIO_NOME)
                .status(1L)
                .build();

    }

//    @Test
    @DisplayName("Deve inativar lista com apenas um funcionario no banco de dados")
    public void inativarListaComUmFuncionario() {
        FuncionarioRequestDTO funcionarioNovo = FuncionarioRequestDTO.builder()
                .idFuncionario(FUNCIONARIO_ID)
                .motivo(MotivoInativar.toEnum(2).getDescricao())
                .build();

        Mockito.when(funcionarioRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(funcionario));

//        funcionarioService.inativarFuncionario(RequestFuncionarioDTO.builder()
//                        .id(USUARIO_ID)
//                        .funcionarios(Arrays.asList(funcionarioNovo))
//                        .build(), "login");
        verify(funcionarioRepository, never()).saveAll(Arrays.asList(funcionario));
    }

//    @Test
    @DisplayName("Deve ativar lista com mais de um funcionario no banco de dados")
    public void ativarListaComMaisDeUmFuncionario() {
        FuncionarioRequestDTO funcionarioDTO_0 = FuncionarioRequestDTO.builder()
                .idFuncionario(FUNCIONARIO_ID)
                .motivo(MotivoInativar.toEnum(1).getDescricao())
                .build();

        FuncionarioRequestDTO funcionarioDTO_1 = FuncionarioRequestDTO.builder()
                .idFuncionario(FUNCIONARIO_ID)
                .motivo(MotivoInativar.toEnum(1).getDescricao())
                .build();

        Funcionario funcionario_0 = Funcionario.builder()
                .cnpj(FUNCIONARIO_CNPJ)
                .cpf(FUNCIONARIO_CPF)
                .dataHora(dataHora)
                .idFuncionario(FUNCIONARIO_ID)
                .motivo(MotivoInativar.toEnum(1).getDescricao())
                .nome(FUNCIONARIO_NOME)
                .status(1L)
                .build();

        Funcionario funcionario_1 = Funcionario.builder()
                .cnpj(FUNCIONARIO_CNPJ)
                .cpf(FUNCIONARIO_CPF)
                .dataHora(dataHora)
                .idFuncionario(FUNCIONARIO_ID)
                .motivo(MotivoInativar.toEnum(1).getDescricao())
                .nome(FUNCIONARIO_NOME)
                .status(1L)
                .build();

        Mockito.when(funcionarioRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(funcionario_1));

//        funcionarioService.ativarFuncionario(Acao.ATIVAR.getCodigo(), RequestFuncionarioDTO.builder()
//                .id(USUARIO_ID)
//                .funcionarios(Arrays.asList(funcionarioDTO_0,funcionarioDTO_1))
//                .build(), "login");
        verify(funcionarioRepository, never()).saveAll(Arrays.asList(funcionario_0, funcionario_1));
    }

//    @Test
    @DisplayName("Deve inativar lista com mais de um funcionario no banco de dados")
    public void inativarListaComMaisDeUmFuncionario() {
        FuncionarioRequestDTO funcionario_0 = FuncionarioRequestDTO.builder()
                .idFuncionario(FUNCIONARIO_ID)
                .motivo(MotivoInativar.toEnum(2).getDescricao())
                .build();

        FuncionarioRequestDTO funcionario_1 = FuncionarioRequestDTO.builder()
                .idFuncionario(FUNCIONARIO_ID)
                .motivo(MotivoInativar.toEnum(2).getDescricao())
                .build();

        Mockito.when(funcionarioRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(funcionario));

//        funcionarioService.inativarFuncionario(RequestFuncionarioDTO.builder()
//                .id(USUARIO_ID)
//                .funcionarios(Arrays.asList(funcionario_0,
//                funcionario_1)).build(), "login");
        verify(funcionarioRepository, never()).saveAll(Arrays.asList(funcionario, funcionario));
    }

//    @Test
    @DisplayName("Deve ativar lista com apenas um funcionario no banco de dados")
    public void ativarListaComApenasUmFuncionario() {
        FuncionarioRequestDTO funcionarioAtivo_0 = FuncionarioRequestDTO.builder()
                .idFuncionario(FUNCIONARIO_ID)
                .motivo(MotivoInativar.toEnum(1).getDescricao())
                .build();

        Mockito.when(funcionarioRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(funcionario));

//        funcionarioService.ativarFuncionario(Acao.ATIVAR.getCodigo(), RequestFuncionarioDTO.builder()
//                        .id(USUARIO_ID)
//                        .funcionarios(Arrays.asList(funcionarioAtivo_0))
//                        .build(), "login");

        verify(funcionarioRepository, never()).saveAll(Arrays.asList(funcionario));
    }
}
