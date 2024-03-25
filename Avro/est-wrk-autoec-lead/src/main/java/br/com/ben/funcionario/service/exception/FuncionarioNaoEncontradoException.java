package br.com.mac.funcionario.service.exception;

public class FuncionarioNaoEncontradoException extends RuntimeException {

  private static final Long serialVersionUID = 1L;

  public FuncionarioNaoEncontradoException(String mensagem) {
    super(mensagem);
  }
}
