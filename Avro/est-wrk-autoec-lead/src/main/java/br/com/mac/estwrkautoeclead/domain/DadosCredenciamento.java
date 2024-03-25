package br.com.machina.estwrkautoeclead.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document("dadosCredenciamento")
public class DadosCredenciamento {

    @Id
    private String id;

    private LocalDateTime dataCriacao;

    private LocalDateTime dataAtualizacao;

    private LocalDateTime dataEnvio;

    private String origem;

    private String statusCredenciamento;

    private Proprietario proprietario;

    private Estabelecimento estabelecimento;


    @Default
    private List<Produto> produtos = null;


}
