package br.com.machina.estwrkautoeclead.domain.enums;

public enum StatusCredenciamento {

    PENDENTE("PENDENTE"),
    FINALIZADO("FINALIZADO"),
    LIBERADO("LIBERADO"),
    EM_ANALISE("EM_ANALISE");

    private String descricao;

    StatusCredenciamento(String status) {
        this.descricao = status;
    }

    public String getValue() {
        return descricao;
    }
}
