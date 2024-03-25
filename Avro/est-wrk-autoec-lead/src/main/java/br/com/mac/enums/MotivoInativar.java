package br.com.mac.funcionario.enums;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public enum MotivoInativar {

  DESLIGAMENTO(1, "Desligamento", "desligamento"),
  ERRO_OPERACIONAL(2, "Erro operacional", "erro operacional"),
  FRAUDE(3, "Fraude", "fraude"),
  OBITO(4, "Óbito", "óbito"),
  AFASTAMENTO(5, "Afastamento", "afastamento");

  private final Integer codigo;
  private final String descricao;
  private final String textoRequest;

  MotivoInativar(Integer codigo, String descricao, String textoRequest) {
    this.codigo = codigo;
    this.descricao = descricao;
    this.textoRequest = textoRequest;
  }

  public static MotivoInativar toEnum(final String textoMotivo) {
    if (textoMotivo == null) {
      return null;
    }
    for (MotivoInativar motivoAtivacao : MotivoInativar.values()) {
      if (textoMotivo.equalsIgnoreCase(motivoAtivacao.getTextoRequest())) {
        return motivoAtivacao;
      }
    }
    throw new IllegalArgumentException("Motivo invalido: " + textoMotivo);
  }

  public static MotivoInativar toEnum(final Integer codigo) {
    if (codigo == null) {
      return null;
    }
    for (MotivoInativar motivoAtivacao : MotivoInativar.values()) {
      if (codigo.equals(motivoAtivacao.getCodigo())) {
        return motivoAtivacao;
      }
    }
    throw new IllegalArgumentException("Codigo invalido: " + codigo);
  }
}
