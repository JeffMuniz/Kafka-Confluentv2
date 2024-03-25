package br.com.mac.funcionario.exception;

public class AtivarInativarFuncionarioException extends RuntimeException {

  public AtivarInativarFuncionarioException(String message) {
    super(message);
  }

  public AtivarInativarFuncionarioException(String message, Throwable cause) {
    super(message, cause);
  }
}
