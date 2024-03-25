package br.com.mac.funcionario.blue.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "PessoasFisicas")
public class PessoaFisica implements Serializable {

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

  @Column(name = "statuspf")
  private String status;

//  @OneToMany(mappedBy = "pessoaFisica")
//  private Set<Funcionario> funcionarios = new HashSet<>();
}

