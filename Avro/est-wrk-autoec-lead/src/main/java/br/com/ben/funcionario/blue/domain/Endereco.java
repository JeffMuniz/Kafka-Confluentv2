package br.com.mac.funcionario.blue.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(of = {"id"})
@Table(name = "ENDERECOS")
public class Endereco implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "ID_ENDERECO")
  private Long id;

  @Column(name = "NOMELOGRADOURO")
  private String logradouro;

  @Column(name = "NUMEROENDERECO")
  private String numero;

  @Column(name = "COMPLEMENTOENDERECO")
  private String complemento;

  @Column(name = "BAIRRO")
  private String bairro;

  @Column(name = "CEP")
  private String cep;

  @Column(name = "CIDADE")
  private String cidade;

  @Column(name = "UF")
  private String uf;

  @Column(name = "REFERENCIALOCALIZACAO")
  private String referencia;

  @Column(name = "DATAINCLUSAO")
  private ZonedDateTime dataInclusao;

  @Column(name = "DATAULTIMAATUALIZACAO")
  private ZonedDateTime dataAtualizacao;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ID_PESSOAFISICA", referencedColumnName = "ID_PESSOAFISICA")
  private Pessoa pessoa;

  @OneToMany(mappedBy = "endereco")
  private Set<Funcionario> funcionarios = new HashSet<>();

//  @PostLoad
//  public void trataCampos() {
//    if (StringUtils.isNotEmpty(this.logradouro)) {
//      this.logradouro = this.logradouro.trim();
//    }
//
//    if (StringUtils.isNotEmpty(this.complemento)) {
//      this.complemento = this.complemento.trim();
//    }
//
//    if (StringUtils.isNotEmpty(this.cep)) {
//      this.cep = this.cep.trim();
//    }
//
//    if (StringUtils.isNotEmpty(this.bairro)) {
//      this.bairro = this.bairro.trim();
//    }
//
//    if (StringUtils.isNotEmpty(this.cidade)) {
//      this.cidade = this.cidade.trim();
//    }
//  }

}
