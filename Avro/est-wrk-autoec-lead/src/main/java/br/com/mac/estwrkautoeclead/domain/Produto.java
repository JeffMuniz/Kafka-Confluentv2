package br.com.machina.estwrkautoeclead.domain;

import br.com.machina.estwrkautoeclead.domain.enums.TipoProduto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Produto {

    @NotNull
    private TipoProduto tipo;

    @NotNull
    private Boolean aceiteTermos;

}
