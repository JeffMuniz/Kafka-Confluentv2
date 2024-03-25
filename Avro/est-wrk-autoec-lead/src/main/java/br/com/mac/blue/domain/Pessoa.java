package br.com.mac.funcionario.blue.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@EqualsAndHashCode(of = {"idPessoa"})
@Table(name = "PESSOAS")
public class Pessoa implements Serializable {

  @Id
  @Column(name = "ID_PESSOAFISICA")
  private Long idPessoa;

  @Column(name = "DATANASCIMENTO", columnDefinition = "TIMESTAMP")
  private LocalDate dataNascimento;

  @Column(name = "NOME")
  private String nome;

  @Column(name = "CPF")
  private String cpf;

  @Column(name = "SEXO")
  private String sexo;

  @Column(name = "ID_EMISSOR")
  private Long idEmissor;

  @Column(name = "NUMEROIDENTIDADE")
  private String numeroIdentidade;

  @Column(name = "STATUS")
  private String status;

  @OneToMany(mappedBy = "pessoa")
  private Set<Conta> contas = new HashSet<>();

  @OneToMany(mappedBy = "pessoa")
  private Set<Empresa> empresas = new HashSet<>();

  @OneToMany(mappedBy = "pessoa")
  private Set<Endereco> enderecos = new HashSet<>();

  @OneToMany(mappedBy = "pessoa")
  private Set<Funcionario> funcionarios = new HashSet<>();

//  @PrePersist
//  void prePersist() {
//    if (!ObjectUtils.isEmpty(cpf)) {
//      cpf = cpf.trim();
//    }
//    if (!ObjectUtils.isEmpty(numeroIdentidade)) {
//      numeroIdentidade = numeroIdentidade.trim();
//    }
//  }
//
//  @PostLoad
//  void postLoad() {
//    if (!ObjectUtils.isEmpty(cpf)) {
//      cpf = cpf.trim();
//    }
//    if (!ObjectUtils.isEmpty(numeroIdentidade)) {
//      numeroIdentidade = numeroIdentidade.trim();
//    }
//  }
}

