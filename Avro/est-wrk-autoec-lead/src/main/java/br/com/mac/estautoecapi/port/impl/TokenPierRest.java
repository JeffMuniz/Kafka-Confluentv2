package br.com.mac.estautoecapi.port.impl;

import br.com.mac.estautoecapi.bean.TokenRequest;
import br.com.mac.estautoecapi.bean.TokenUpdate;
import org.springframework.http.HttpStatus;

public interface TokenPierRest {

    String PATH_PIER_NOTIFICACOES_SMS = "/notificacoes-sms";

    String PATH_PIER_CODIGOS_SEGURANCA_ENVIAR = PATH_PIER_NOTIFICACOES_SMS + "/gerar-codigo-seguranca";

    String PATH_PIER_CODIGOS_SEGURANCA_VALIDAR = PATH_PIER_NOTIFICACOES_SMS + "/validar-codigo-seguranca";

    String PATH_PIER_CODIGOS_SEGURANCA_REENVIAR = PATH_PIER_NOTIFICACOES_SMS + "/reenviar-codigo-seguranca";

    HttpStatus validarToken(final TokenUpdate tokenSMSUpdate);

    HttpStatus gerarToken(final TokenRequest tokenSMSRequest);

}
