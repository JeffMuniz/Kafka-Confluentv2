package br.com.mac.funcionario.blue.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A VwFuncionarioProdutoEmpresa.
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "VW_FUNCIONARIOPRODUTOEMPRESA")
public class VwFuncionarioProdutoEmpresa implements Serializable {

  private static final long serialVersionUID = -978197010891232430L;

  @EmbeddedId
  private VwFuncionarioProdutoEmpresaPK id;

  @Column(name = "NOME")
  private String nome;

  @Column(name = "CPF")
  private String cpf;

  @Column(name = "VALOR")
  private BigDecimal valor;

  @Column(name = "DATA_CARGA")
  private LocalDateTime dataCarga;

  @Column(name = "STATUS")
  private String status;

}
