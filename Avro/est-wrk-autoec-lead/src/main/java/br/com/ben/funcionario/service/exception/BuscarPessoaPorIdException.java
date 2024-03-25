package br.com.mac.funcionario.service.exception;

public class BuscarPessoaPorIdException extends RuntimeException {

  private static final Long serialVersionUID = 1L;

  public BuscarPessoaPorIdException(String mensagem) {
    super(mensagem);
  }
}
