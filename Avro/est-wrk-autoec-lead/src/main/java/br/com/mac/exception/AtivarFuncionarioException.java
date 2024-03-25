package br.com.mac.funcionario.exception;

public class AtivarFuncionarioException extends RuntimeException {

  public AtivarFuncionarioException(String message) {
    super(message);
  }

  public AtivarFuncionarioException(String message, Throwable cause) {
    super(message, cause);
  }
}
