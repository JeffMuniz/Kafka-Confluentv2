package br.com.machina.estwrkautoeclead.port.impl;

import br.com.machina.adapter.rest.MachinaRestAdapter;
import br.com.machina.adapter.rest.model.RequestModel;
import br.com.machina.adapter.rest.model.ResponseModel;
import br.com.machina.estwrkautoeclead.domain.DadosLead;
import br.com.machina.estwrkautoeclead.port.SalesForcePort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;

@Component
public class SalesForcePortImpl implements SalesForcePort {

    @Resource(name = "machina.default.rest.adapter")
    private MachinaRestAdapter rest;

    @Value("${app.salesforce.urlIndicacao}")
    private String url;

    @Override
    public ResponseModel<Void> sendLead(DadosLead dadosLead) {
        RequestModel<Void> requestModel = (RequestModel<Void>) new RequestModel.Builder()
                .configBody(dadosLead)
                .configUrl(url)
                .configHttpHeaders(createHttpHeaders())
                .build();
        return rest.postRequest(requestModel);

    }

    private HttpHeaders createHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return headers;
    }

}
