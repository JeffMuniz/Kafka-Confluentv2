package br.com.mac.funcionario.service.exception;

import static java.util.Arrays.asList;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;


import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.StringUtils;

@Getter
@Slf4j
public enum ExceptionsMessagesCdtEnum {

  GLOBAL_ERRO_SERVIDOR(INTERNAL_SERVER_ERROR.value(), "Erro interno de servidor.",
      ExceptionCdt.class);

  private final Integer code;
  private final Class<? extends ExceptionCdt> klass;
  @Setter
  private String message;

  ExceptionsMessagesCdtEnum(int code, String message, Class<? extends ExceptionCdt> klass) {

    this.message = message;
    this.klass = klass;
    this.code = code;
  }

  public static ExceptionsMessagesCdtEnum getEnum(final String key) {

    return asList(ExceptionsMessagesCdtEnum.values()).stream()
        .map(e -> e).filter(e -> StringUtils.equals(e.getMessage(), key))
        .findAny().orElse(null);
  }

  public void raise(String errorMessage) {

    this.setMessage(errorMessage);
    this.raise();
  }

  public void raise() throws ExceptionCdt {

    log.debug("Raising error: {}", this);
    if (BAD_REQUEST.value() == this.code) {
      throw new ExceptionCdt(INTERNAL_SERVER_ERROR, this.message);
    }
  }

}
