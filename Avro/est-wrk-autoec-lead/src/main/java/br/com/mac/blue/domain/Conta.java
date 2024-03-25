package br.com.mac.funcionario.blue.domain;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Table(name = "CONTAS")
public class Conta implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 5768220055765101419L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID_CONTA")
  private Long id;

  @Column(name = "ID_PESSOA")
  private Integer idPessoa;

  @ManyToOne(optional = false)
  @JoinColumns(@JoinColumn(name = "Id_Pessoa", referencedColumnName = "ID_PESSOAFISICA"))
  @NotNull
  private Pessoa pessoa;

  @ManyToOne(optional = false)
  @JoinColumns(@JoinColumn(name = "Id_Produto"))
  @NotNull
  private Produto produto;

  @OneToMany(mappedBy = "conta")
  private Set<FuncionarioProduto> funcionarioProdutos = new HashSet<>();

  @ManyToMany(mappedBy = "contas")
  private Set<Empresa> empresas = new HashSet<>();
}
