package br.com.mac.funcionario.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FuncionarioProdutoResponse {

  @JsonProperty("id")
  private Long id;

  @Valid
  @JsonProperty("conta")
  private ContaFuncionarioProdutoResponse conta;
}
