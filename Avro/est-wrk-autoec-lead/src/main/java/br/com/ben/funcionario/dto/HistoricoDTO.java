package br.com.mac.funcionario.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HistoricoDTO {

  private Long idHistorico;

  private String cpf;

  private String cnpj;

  private String loginOperador;

  private String nomeOperador;

  private String acao;

  private String motivo;

  private String dataHora;

  @JsonIgnore
  private FuncionarioDTO funcionarioDTO;
}
