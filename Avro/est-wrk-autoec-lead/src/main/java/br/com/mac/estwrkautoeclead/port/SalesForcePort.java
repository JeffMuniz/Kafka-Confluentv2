package br.com.machina.estwrkautoeclead.port;

import br.com.machina.adapter.rest.model.ResponseModel;
import br.com.machina.estwrkautoeclead.domain.DadosLead;

public interface SalesForcePort {

    ResponseModel<Void> sendLead(DadosLead dadosLead);

}
