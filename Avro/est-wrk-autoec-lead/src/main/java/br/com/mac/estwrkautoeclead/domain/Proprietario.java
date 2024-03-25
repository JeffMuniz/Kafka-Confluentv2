
package br.com.machina.estwrkautoeclead.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Proprietario {

    private String nome;

    @CPF
    private String cpf;

    @Email
    private String email;

    private String telefone;

}
