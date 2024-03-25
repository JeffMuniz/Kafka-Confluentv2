package br.com.mac.funcionario.blue.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.time.ZonedDateTime;
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
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NamedEntityGraph(
    name = "funcionario-com-pessoa-endereco-empresa-funcionario-produtos",
    attributeNodes = {
        @NamedAttributeNode(value = "pessoa"),
        @NamedAttributeNode(value = "endereco"),
        @NamedAttributeNode(value = "empresa"),
    }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@EqualsAndHashCode(of = {"idFuncionario"})
@Table(name = "FUNCIONARIOS")
public class Funcionario implements Serializable {

  private static final long serialVersionUID = -9130200726694089977L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID_FUNCIONARIO")
  private Long idFuncionario;

  @Transient
  @Column(name = "CPF")
  private String cpf;

  @Transient
  @Column(name = "CNPJ")
  private String cnpj;

  @Transient
  @Column(name = "NOME")
  private String nome;

  @Column(name = "STATUS")
  private Long status;

  @Column(name = "MATRICULA")
  private String matricula;

  @Transient
  @Column(name = "MOTIVO")
  private String motivo;

  @Transient
  @JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss")
  @Column(name = "DATA_HORA", columnDefinition = "TIMESTAMP")
  private ZonedDateTime dataHora;

  @JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss")
  @Column(name = "DataCadastro", columnDefinition = "TIMESTAMP")
  private ZonedDateTime dataCadastro;

  @JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss")
  @Column(name = "StatusData", columnDefinition = "TIMESTAMP")
  private ZonedDateTime dataStatus;

  @Column(name = "ID_EmpresaSetor")
  private Long idEmpresaSetor;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumns(@JoinColumn(name = "ID_PESSOAFISICA"))
  private Pessoa pessoa;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ID_ENDERECOENTREGA", referencedColumnName = "ID_ENDERECO")
  private Endereco endereco;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumns(@JoinColumn(name = "ID_EMPRESA"))
  private Empresa empresa;

  @OneToMany(mappedBy = "funcionario")
  private Set<FuncionarioProduto> funcionarioProdutos = new HashSet<>();

  @OneToMany(mappedBy = "funcionario")
  private Set<EmpresaCargaDetalhe> empresaCargaDetalhes = new HashSet<>();

  @OneToMany(mappedBy = "id.funcionario")
  private Set<VwFuncionarioProdutoEmpresa> vwFuncionarioProdutoEmpresas = new HashSet<>();
}
