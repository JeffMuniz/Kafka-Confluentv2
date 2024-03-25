package br.com.mac.funcionario.service.exception;

public class EmpresaNaoEncontradoException extends RuntimeException {

  private static final Long serialVersionUID = 1L;

  public EmpresaNaoEncontradoException(String mensagem) {
    super(mensagem);
  }
}
