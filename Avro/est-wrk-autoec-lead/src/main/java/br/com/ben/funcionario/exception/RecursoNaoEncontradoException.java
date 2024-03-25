package br.com.mac.funcionario.exception;

public class RecursoNaoEncontradoException extends RuntimeException {

  public RecursoNaoEncontradoException(String message) {
    super(message);
  }

  public RecursoNaoEncontradoException(String message, Throwable cause) {
    super(message, cause);
  }
}
