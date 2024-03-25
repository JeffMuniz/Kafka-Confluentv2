package br.com.mac.funcionario.exception;

public class UsuarioNaoEncontradoException extends RuntimeException {

  public UsuarioNaoEncontradoException(String message) {
    super(message);
  }

  public UsuarioNaoEncontradoException(String message, Throwable cause) {
    super(message, cause);
  }
}