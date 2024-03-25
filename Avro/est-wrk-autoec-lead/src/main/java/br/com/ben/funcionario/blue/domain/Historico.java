package br.com.mac.funcionario.blue.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "HISTORICOATIVACAOINATIVACAO")
public class Historico implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID_HISTORICO")
  private Long idHistorico;

  @Column(name = "CPF")
  private String cpf;

  @Column(name = "CNPJ")
  private String cnpj;

  @Column(name = "NOME_OPERADOR")
  private String nomeOperador;

  @Column(name = "LOGIN_OPERADOR")
  private String loginOperador;

  @Column(name = "ACAO")
  private String acao;

  @Column(name = "MOTIVO")
  private String motivo;

  @JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss")
  @Column(name = "DATA_HORA", columnDefinition = "TIMESTAMP")
  private LocalDateTime dataHora;

  @ManyToOne
  @JoinColumn(name = "id_funcionario")
  private Funcionario funcionario;

}
