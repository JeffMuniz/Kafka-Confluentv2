package br.com.mac.funcionario.exception;

public class InativarFuncionarioException extends RuntimeException {

  public InativarFuncionarioException(String message) {
    super(message);
  }

  public InativarFuncionarioException(String message, Throwable cause) {
    super(message, cause);
  }
}
