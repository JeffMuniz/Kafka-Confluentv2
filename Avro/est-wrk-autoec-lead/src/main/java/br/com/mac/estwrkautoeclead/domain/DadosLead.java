package br.com.machina.estwrkautoeclead.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DadosLead {

    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private String razaoSocial;
    private String cnpj;
    private String nomeFantasia;

}
