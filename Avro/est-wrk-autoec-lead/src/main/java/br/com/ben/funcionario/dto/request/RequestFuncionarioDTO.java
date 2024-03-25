package br.com.mac.funcionario.dto.request;

import br.com.mac.funcionario.dto.FuncionarioRequestDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestFuncionarioDTO implements Serializable {

  @NotNull
  private Long id;

  @NotNull
  @NotEmpty
  private List<FuncionarioRequestDTO> funcionarios;
}
