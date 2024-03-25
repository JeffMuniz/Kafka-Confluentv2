package br.com.mac.funcionario.blue.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Table(name = "PRODUTOS")
public class Produto implements Serializable {

  /**
   * Atributo serialVersionUID
   */
  private static final long serialVersionUID = -1145947252772990529L;

  /**
   * Atributo id
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID_PRODUTO")
  private Long id;

  /**
   * Atributo nome
   */
  @Column(name = "NOME")
  private String nome;

  /**
   * Atributo descricao
   */
  @Column(name = "DESCRICAO")
  private String descricao;

  /**
   * Atributo idBandeira
   */
  @Column(name = "ID_BANDEIRA")
  private Long idBandeira;

  @OneToMany(mappedBy = "produto")
  private Set<Conta> contas = new HashSet<>();

  @OneToMany(mappedBy = "produto")
  private Set<ArquivoCarga> arquivoCargas = new HashSet<>();

  @OneToMany(mappedBy = "id.produto")
  private Set<VwFuncionarioProdutoEmpresa> vwFuncionarioProdutoEmpresas = new HashSet<>();
}
