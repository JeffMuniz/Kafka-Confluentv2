package br.com.mac.funcionario.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class GlobalExceptionHandler {

  public static final String MENSAGEM_GLOBAL_204 = "Sem conteúdo.";
  public static final String MENSAGEM_GLOBAL_400 = "Requisição inválida.";
  public static final String MENSAGEM_GLOBAL_401 = "Não autorizado.";
  public static final String MENSAGEM_GLOBAL_403 = "Não permitido.";
  public static final String MENSAGEM_GLOBAL_404 = "Recurso não encontrado.";
  public static final String MENSAGEM_GLOBAL_409 = "Objeto já existente.";
  public static final String MENSAGEM_GLOBAL_412 = "Pré condições não atendidas.";
  public static final String MENSAGEM_GLOBAL_500 = "Erro interno do sistema.";
  public static final String MENSAGEM_TIMEOUT = "Problema de conexão.";
  private static final String TIMED_OUT = "timed out";

}
