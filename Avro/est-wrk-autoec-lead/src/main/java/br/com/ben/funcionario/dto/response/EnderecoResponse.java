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
public class EnderecoResponse {

  @JsonProperty("logradouro")
  private String logradouro;

  @JsonProperty("complemento")
  private String complemento;

  @JsonProperty("cep")
  private String cep;

  @JsonProperty("bairro")
  private String bairro;

  @JsonProperty("numero")
  private String numero;

  @JsonProperty("uf")
  private String uf;

  @JsonProperty("cidade")
  private String cidade;
}
