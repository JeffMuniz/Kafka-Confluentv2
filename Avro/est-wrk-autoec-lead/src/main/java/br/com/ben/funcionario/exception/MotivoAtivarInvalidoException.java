package br.com.mac.funcionario.exception;

public class MotivoAtivarInvalidoException extends RuntimeException {

  public MotivoAtivarInvalidoException(String message) {
    super(message);
  }

  public MotivoAtivarInvalidoException(String message, Throwable cause) {
    super(message, cause);
  }
}
