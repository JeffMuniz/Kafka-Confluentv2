package br.com.mac.funcionario.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PessoaResponse {

  @JsonProperty("idPessoa")
  private Long idPessoa;

  @JsonProperty("dataNascimento")
  @org.springframework.format.annotation.DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate dataNascimento;

  @JsonProperty("cpf")
  private String cpf;

  @JsonProperty("numeroIdentidade")
  private String numeroIdentidade;

  @JsonProperty("nome")
  private String nome;

  @JsonProperty("idEmissor")
  private Long idEmissor;

  @JsonProperty("sexo")
  private String sexo;
}
