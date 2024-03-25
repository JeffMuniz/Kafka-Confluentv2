package br.com.mac.funcionario.enums;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public enum Acao {
  ATIVAR(1L, "Ativar", "ATIVO"),
  INATIVAR(2L, "Inativar", "INATIVO"),
  NAO_ENCONTRADO(3L, "Não encontrado", "NÃO ENCONTRADO");

  private final Long codigo;
  private final String descricao;
  private final String clienteStatus;

  Acao(Long codigo, String descricao, String clienteStatus) {
    this.codigo = codigo;
    this.descricao = descricao;
    this.clienteStatus = clienteStatus;
  }

  public static Acao toEnum(Long codigo) {
    if (codigo == null) {
      return null;
    }
    for (Acao acao : Acao.values()) {
      if (codigo.equals(acao.getCodigo())) {
        return acao;
      }
    }
    throw new IllegalArgumentException("Codigo invalido: " + codigo);
  }

  public static Acao toEnumDescrcicao(String descricao) {
    if (descricao == null) {
      return null;
    }
    for (Acao acao : Acao.values()) {
      if (descricao.equals(acao.getDescricao())) {
        return acao;
      }
    }
    throw new IllegalArgumentException("Motivo invalido: " + descricao);
  }

}
