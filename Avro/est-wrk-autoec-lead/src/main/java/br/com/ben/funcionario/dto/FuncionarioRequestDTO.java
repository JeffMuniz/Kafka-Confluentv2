package br.com.mac.funcionario.dto;


import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FuncionarioRequestDTO implements Serializable {

  @NotNull
  private Long idFuncionario;

  @NotNull
  private String motivo;

}
