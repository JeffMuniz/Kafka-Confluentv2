package br.com.mac.funcionario.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FuncionarioStatusRequestDTO implements Serializable {

  private static final long serialVersionUID = 1l;

  String cpf;
}
