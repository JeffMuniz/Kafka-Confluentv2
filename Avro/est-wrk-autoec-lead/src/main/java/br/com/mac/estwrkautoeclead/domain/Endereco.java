package br.com.machina.estwrkautoeclead.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Endereco implements Serializable {

    private static final long serialVersionUID = 4421061332940919690L;

    @NotNull
    private String logradouro;

    private String complemento;

    @NotNull
    private String cep;

    @NotNull
    private String bairro;

    @NotNull
    private Long numero;

    @NotNull
    private String uf;

    @NotNull
    private String cidade;

    @NotNull
    private String localidade;
}
