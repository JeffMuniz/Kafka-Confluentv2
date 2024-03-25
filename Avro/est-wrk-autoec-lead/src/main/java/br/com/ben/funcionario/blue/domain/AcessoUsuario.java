package br.com.mac.funcionario.blue.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "ACESSOUSUARIOS")
public class AcessoUsuario implements Serializable {

  @Id
  @Column(name = "ID_USUARIO")
  private Long idUsuario;

  @Column(name = "CPF")
  private String cpf;

  @Column(name = "LOGIN")
  private String login;
}
