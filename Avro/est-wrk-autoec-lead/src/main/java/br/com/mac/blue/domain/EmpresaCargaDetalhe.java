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
@Table(name = "EMPRESASCARGASDETALHES")
public class EmpresaCargaDetalhe implements Serializable {

  private static final long serialVersionUID = -1842534150819179759L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID_EMPRESACARGADETALHE")
  private Long id;

  @Column(name = "STATUS")
  private Long status;

  @Column(name = "VALORTOTAL", precision = 21, scale = 2)
  private BigDecimal valorTotal;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumns(@JoinColumn(name = "ID_EMPRESACARGA"))
  private EmpresaCarga empresaCarga;

  @ManyToOne(optional = false)
  @JoinColumns(@JoinColumn(name = "ID_FUNCIONARIO"))
  @NotNull
  private Funcionario funcionario;

  @OneToMany(mappedBy = "empresaCargaDetalhe")
  private Set<EmpresaCargaDetalheProduto> empresaCargaDetalheProdutos = new HashSet<>();
}
