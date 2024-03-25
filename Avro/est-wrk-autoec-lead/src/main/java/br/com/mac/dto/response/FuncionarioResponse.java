package br.com.mac.funcionario.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FuncionarioResponse {

  @JsonProperty("id")
  private Long id;

  @JsonProperty("idEmpresa")
  private Long idEmpresa;

  @JsonProperty("idSubgrupoEmpresa")
  private Long idSubgrupoEmpresa;

  @JsonProperty("idSetor")
  private Long idSetor;

  @JsonProperty("cnpj")
  private String cnpj;

  @JsonProperty("dataCadastro")
  @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME)
  private ZonedDateTime dataCadastro;

  @JsonProperty("status")
  private Long status;

  @JsonProperty("dataStatus")
  @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME)
  private ZonedDateTime dataStatus;

  @JsonProperty("matricula")
  private String matricula;

  @Valid
  @JsonProperty("pessoa")
  private PessoaResponse pessoa;

  @Valid
  @JsonProperty("endereco")
  private EnderecoResponse endereco;

  @JsonProperty("funcionariosProdutos")
  @Valid
  private Set<FuncionarioProdutoResponse> funcionariosProdutos = new HashSet<>();
}
