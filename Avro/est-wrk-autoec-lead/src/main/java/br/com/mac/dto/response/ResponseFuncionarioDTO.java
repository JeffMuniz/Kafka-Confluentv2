package br.com.mac.funcionario.dto.response;

import br.com.mac.funcionario.dto.FuncionarioResponseDTO;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseFuncionarioDTO implements Serializable {

  @NotNull
  @NotEmpty
  private List<FuncionarioResponseDTO> funcionarios;
}
