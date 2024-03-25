package br.com.mac.funcionario.exception;

public class CpfInvalidoException extends RuntimeException {

  public CpfInvalidoException(String mensagem) {
    super(mensagem);
  }

  public CpfInvalidoException(String mensagem, Throwable causa) {
    super(mensagem, causa);
  }
}
