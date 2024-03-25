package br.com.mac.estautoecapi.port;

import br.com.mac.adapter.rest.macRestAdapter;
import br.com.mac.adapter.rest.model.RequestModel;
import br.com.mac.adapter.rest.model.RequestModel.Builder;
import br.com.mac.adapter.rest.model.ResponseModel;
import br.com.mac.customexception.exception.BadRequestCustom;
import br.com.mac.estautoecapi.bean.Token;
import br.com.mac.estautoecapi.bean.TokenRequest;
import br.com.mac.estautoecapi.bean.TokenUpdate;
import br.com.mac.estautoecapi.port.impl.TokenPierRest;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import javax.annotation.Resource;

@Service
@RequiredArgsConstructor
public class TokenPierRestImpl implements TokenPierRest {

    @Value("${app.pier.url}")
    private String pierBasePath;

    @Resource(name = "mac.default.rest.adapter")
    private final macRestAdapter restTemplate;

    private final HeadersDefaultPier headersPier;

    @Override
    public HttpStatus validarToken(final TokenUpdate tokenSMSUpdate) {
        return postRequest(tokenSMSUpdate, PATH_PIER_CODIGOS_SEGURANCA_VALIDAR);
    }

    @Override
    public HttpStatus gerarToken(final TokenRequest tokenSMSRequest) {
        return postRequest(tokenSMSRequest, PATH_PIER_CODIGOS_SEGURANCA_ENVIAR);
    }

    protected HttpStatus postRequest(final Token token, String path) {
        RequestModel<?> requestModel = createRequest(token, path);
        try {
            ResponseModel<?> responseModel = restTemplate.postRequest(requestModel);

            return responseModel.getStatusCode() == HttpStatus.OK.value() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;

        } catch (HttpClientErrorException e) {
            throw new BadRequestCustom(e.getResponseBodyAsString());
        }
    }

    @NotNull
    private RequestModel<?> createRequest(Token token, String path) {
        return new Builder()
                .configBody(token)
                .configUrl(pierBasePath + path)
                .configHttpHeaders(headersPier.createHeaders())
                .build();
    }
}
