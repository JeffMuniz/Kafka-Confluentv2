package br.com.mac.funcionario.service.exception;

public class PessoaNaoEncontradaException extends RuntimeException {

  private static final Long serialVersionUID = 1L;

  public PessoaNaoEncontradaException(final String mensagem) {
    super(mensagem);
  }
}
