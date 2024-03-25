package br.com.mac.funcionario.blue.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Table(name = "EMPRESASCARGASDETALHESPRODUTOS")
public class EmpresaCargaDetalheProduto implements Serializable {

  private static final long serialVersionUID = 3981245420531698021L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID_EMPRESACARGADETALHEPRODUTO")
  private Long id;

  @Column(name = "STATUS")
  private Long status;

  @Column(name = "FLAGNOVOCARTAO")
  private Integer flagNovoCartao;

  @NotNull
  @Column(name = "VALOR", precision = 21, scale = 2, nullable = false)
  private BigDecimal valor;

  @Column(name = "STATUSDATA")
  private LocalDateTime statusData;

  @ManyToOne(optional = false)
  @JoinColumns(@JoinColumn(name = "ID_EMPRESACARGADETALHE"))
  @NotNull
  private EmpresaCargaDetalhe empresaCargaDetalhe;

  @ManyToOne
  @JoinColumns(
      @JoinColumn(name = "ID_FUNCIONARIOSPRODUTOS", referencedColumnName = "ID_FUNCIONARIO"))
  private FuncionarioProduto funcionarioProduto;
}
