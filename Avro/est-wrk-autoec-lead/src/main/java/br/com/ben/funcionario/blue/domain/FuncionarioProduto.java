package br.com.mac.funcionario.blue.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@NamedEntityGraph(
    name = "funcionario-produto-com-funcionario-conta",
    attributeNodes = {
        @NamedAttributeNode(value = "funcionario", subgraph = "funcionario-com-pessoa-empresa-endereco"),
        @NamedAttributeNode(value = "conta", subgraph = "conta-com-produto-pessoa")
    },
    subgraphs = {
        @NamedSubgraph(name = "funcionario-com-pessoa-empresa-endereco",
            attributeNodes = {
                @NamedAttributeNode(value = "pessoa"),
                @NamedAttributeNode(value = "empresa", subgraph = "empresa-com-pessoa"),
                @NamedAttributeNode(value = "endereco", subgraph = "endereco-com-pessoa"),
            }),
        @NamedSubgraph(name = "empresa-com-pessoa",
            attributeNodes = {
                @NamedAttributeNode("pessoa")
            }),
        @NamedSubgraph(name = "endereco-com-pessoa",
            attributeNodes = {
                @NamedAttributeNode("pessoa")
            }),
        @NamedSubgraph(name = "conta-com-produto-pessoa",
            attributeNodes = {
                @NamedAttributeNode(value = "produto"),
                @NamedAttributeNode(value = "pessoa")
            }),
    }
)

@Entity
@Data
@EqualsAndHashCode(of = {"id"})
@Table(name = "FUNCIONARIOSPRODUTOS")
public class FuncionarioProduto implements Serializable {

  private static final long serialVersionUID = 5883932088029863721L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID_FUNCIONARIOSPRODUTOS")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ID_FUNCIONARIO", referencedColumnName = "ID_FUNCIONARIO")
  private Funcionario funcionario;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ID_CONTA", referencedColumnName = "ID_CONTA")
  private Conta conta;
//
//  @OneToMany(mappedBy = "funcionarioProduto")
//  private Set<EmpresaCargaDetalheProduto> empresaCargaDetalheProdutos = new HashSet<>();
//
//  @OneToMany(mappedBy = "id.funcionarioProduto")
//  private Set<VwFuncionarioProdutoEmpresa> vwFuncionarioProdutoEmpresas = new HashSet<>();
}
