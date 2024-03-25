package br.com.mac.funcionario.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContaResponse {

  @JsonProperty("id")
  private Long id;

  @JsonProperty("idProduto")
  private Integer idProduto;

  @JsonProperty("idPessoa")
  private Long idPessoa;
}
