package br.com.mac.funcionario.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FuncionarioResponseDTO implements Serializable {

  private Long idFuncionario;

  @NotNull
  @NotEmpty(message = "Preenchimento obrigatório")
  @Size(min = 11, max = 11, message = "O tamanho do campo deve ser de 11 caracteres numericos!")
  private String cpf;

  @NotNull
  @NotEmpty(message = "Preenchimento obrigatório")
  @Size(min = 14, max = 14, message = "O tamanho do campo deve ser de 14 caracteres numericos!")
  private String cnpj;

  @JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss")
  private String dataHora;
  private String nome;
  private String status;
  private String motivo;

}
