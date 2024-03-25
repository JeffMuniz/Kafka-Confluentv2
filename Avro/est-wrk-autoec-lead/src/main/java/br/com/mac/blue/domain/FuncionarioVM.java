package br.com.mac.funcionario.blue.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.ZonedDateTime;

@Data
@Entity
public class FuncionarioVM {
  @Id
  @Column(name = "funcionarioId")
  private Long funcionarioId;

  @Column(name = "pessoaId")
  private Long pessoaId;

  @Column(name = "empresaId")
  private Long empresaId;

  @Column(name = "empresaCnpj")
  private String empresaCnpj;

  @Column(name = "funcionarioDataCadastro")
  private ZonedDateTime funcionarioDataCadastro;

  @Column(name = "funcionarioDataStatus")
  private ZonedDateTime funcionarioDataStatus;

  @Column(name = "funcionarioEmpresaSetorId")
  private Long funcionarioEmpresaSetorId;

  @Column(name = "funcionarioMatricula")
  private String funcionarioMatricula;

  @Column(name = "funcionarioStatus")
  private Long funcionarioStatus;

  @Column(name = "pessoaCpf")
  private String pessoaCpf;

  @Column(name = "pessoaDataNascimento")
  private LocalDate pessoaDataNascimento;

  @Column(name = "pessoaEmissorId")
  private Long pessoaEmissorId;

  @Column(name = "pessoaNome")
  private String pessoaNome;

  @Column(name = "pessoaNumeroIdentidade")
  private String pessoaNumeroIdentidade;

  @Column(name = "pessoaSexo")
  private String pessoaSexo;

  @Column(name = "enderecoBairro")
  private String enderecoBairro;

  @Column(name = "enderecoCep")
  private String enderecoCep;

  @Column(name = "enderecoCidade")
  private String enderecoCidade;

  @Column(name = "enderecoComplemento")
  private String enderecoComplemento;

  @Column(name = "enderecoLogradouro")
  private String enderecoLogradouro;

  @Column(name = "enderecoNumero")
  private String enderecoNumero;

  @Column(name = "enderecoUf")
  private String enderecoUf;
}
