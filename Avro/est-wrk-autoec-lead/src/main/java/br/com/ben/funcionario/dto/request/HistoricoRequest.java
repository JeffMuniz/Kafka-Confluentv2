package br.com.mac.funcionario.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HistoricoRequest implements Serializable {

  private static final long serialVersionUID = 1L;
  //    private ModuloHistorico modulo;
//    private OrigemHistorico origem;
//    private SubModuloHistorico subModulo;
  private String descricaoSubModulo;
  private String chaveModulo;
  private String chaveSubModulo;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  private LocalDateTime data;

  private String login;
  private String nome;
  private boolean processamentoSincrono;
  private Object valorAnterior;
  private Object valorPosterior;
}
