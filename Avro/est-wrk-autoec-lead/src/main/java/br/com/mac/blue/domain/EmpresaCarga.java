package br.com.mac.funcionario.blue.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@Table(name = "EMPRESASCARGAS")
public class EmpresaCarga implements Serializable {

  private static final long serialVersionUID = -4830837726202214301L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID_EMPRESACARGA")
  private Long id;

  @NotNull
  @Column(name = "VALOR", precision = 21, scale = 2, nullable = false)
  private BigDecimal valor;

  @Column(name = "QTDENOVOSCARTOES")
  private Long quantidadeNovosCartoes;

  @ManyToOne
  @JoinColumns(@JoinColumn(name = "ID_CARGAmacEFICIO"))
  private Cargamaceficio cargamaceficio;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumns(@JoinColumn(name = "ID_EMPRESA"))
  private Empresa empresa;

  @OneToMany(mappedBy = "empresaCarga")
  private Set<EmpresaCargaDetalhe> empresaCargaDetalhes = new HashSet<>();

  @OneToMany(mappedBy = "id.empresaCarga")
  private Set<VwFuncionarioProdutoEmpresa> vwFuncionarioProdutoEmpresas = new HashSet<>();
}
