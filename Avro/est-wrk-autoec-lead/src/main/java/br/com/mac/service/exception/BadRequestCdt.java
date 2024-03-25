package br.com.mac.funcionario.service.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class BadRequestCdt extends ExceptionCdt {
  /**
   *
   */
  private static final long serialVersionUID = 1L;

  public BadRequestCdt(final String message) {
    super(BAD_REQUEST, message);
  }

}
