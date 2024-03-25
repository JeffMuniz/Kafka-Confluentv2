package br.com.mac.funcionario.blue.domain;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@EqualsAndHashCode(of = {"idEmpresa"})
@Table(name = "EMPRESAS")
public class Empresa {

  @Id
  @Column(name = "ID_EMPRESA")
  private Long idEmpresa;

  @Column(name = "NOMEFANTASIA")
  private String nomeFantasia;

  @Column(name = "ID_GRUPOEMPRESA")
  private Long idGrupoEmpresa;

  @ManyToOne
  @JoinColumns({@JoinColumn(name = "ID_PESSOA", referencedColumnName = "ID_PESSOAFISICA")})
  private Pessoa pessoa;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "EmpresasContas",
      joinColumns = @JoinColumn(name = "Id_Empresa"),
      inverseJoinColumns = @JoinColumn(name = "Id_Conta"))
  private Set<Conta> contas = new HashSet<>();

  @OneToMany(mappedBy = "empresa")
  private Set<Cargamaceficio> cargamaceficios = new HashSet<>();

  @OneToMany(mappedBy = "empresa")
  private Set<EmpresaCarga> empresaCargas = new HashSet<>();

  @OneToMany(mappedBy = "empresa")
  private Set<Funcionario> funcionarios = new HashSet<>();

  @OneToMany(mappedBy = "id.empresa")
  private Set<VwFuncionarioProdutoEmpresa> vwFuncionarioProdutoEmpresas = new HashSet<>();
}
