package br.com.mac.funcionario.enums;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public enum MotivoAtivar {

  RECONTRATACAO(1, "Recontratação", "recontratação"),
  ERRO_OPERACIONAL(2, "Erro operacional", "erro operacional"),
  RETORNO_AFASTAMENTO(3, "Retorno afastamento", "retorno afastamento");

  private final Integer codigo;
  private final String descricao;
  private final String textoRequest;

  MotivoAtivar(Integer codigo, String descricao, String textoRequest) {
    this.codigo = codigo;
    this.descricao = descricao;
    this.textoRequest = textoRequest;
  }

  public static String toEnumCodigoDescricao(final String textoMotivo) {
    if (textoMotivo == null) {
      return null;
    }
    for (MotivoAtivar motivoAtivacao : MotivoAtivar.values()) {
      if (textoMotivo.equalsIgnoreCase(motivoAtivacao.getTextoRequest())) {
        return motivoAtivacao.codigo + " - " + motivoAtivacao.getDescricao();
      }
    }
    throw new IllegalArgumentException("Motivo invalido: " + textoMotivo);
  }

  public static MotivoAtivar toEnum(final String textoMotivo) {
    if (textoMotivo == null) {
      return null;
    }
    for (MotivoAtivar motivoAtivacao : MotivoAtivar.values()) {
      if (textoMotivo.equalsIgnoreCase(motivoAtivacao.getTextoRequest())) {
        return motivoAtivacao;
      }
    }
    throw new IllegalArgumentException("Motivo invalido: " + textoMotivo);
  }

  public static MotivoAtivar toEnum(final Integer codigo) {
    if (codigo == null) {
      return null;
    }
    for (MotivoAtivar motivoAtivacao : MotivoAtivar.values()) {
      if (codigo.equals(motivoAtivacao.getCodigo())) {
        return motivoAtivacao;
      }
    }
    throw new IllegalArgumentException("Codigo invalido: " + codigo);
  }
}
