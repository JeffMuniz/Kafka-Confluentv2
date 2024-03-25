package br.com.mac.funcionario.blue.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ARQUIVOSCARGASDETALHES")
public class ArquivoCargaDetalhe implements Serializable {

  private static final long serialVersionUID = -5555231495014214409L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID_DETALHEARQUIVO")
  private Long id;

  @Size(max = 14)
  @Column(name = "CNPJ", length = 14)
  private String cnpj;

  @Size(max = 50)
  @Column(name = "CODUNIDADE", length = 50)
  private String codUnidade;

  @Size(max = 100)
  @Column(name = "MATRICULA", length = 100)
  private String matricula;

  @Size(max = 100)
  @Column(name = "NOME", length = 100)
  private String nome;

  @Size(max = 11)
  @Column(name = "CPF", length = 11)
  private String cpf;

  @Size(max = 10)
  @Column(name = "DATANASCIMENTO", length = 10)
  private String dataNascimento;

  @Size(max = 50)
  @Column(name = "PRODUTO", length = 50)
  private String produto;

  @Size(max = 10)
  @Column(name = "VALOR", length = 10)
  private String valormaceficio;

  @Size(max = 10)
  @Column(name = "DATACREDITO", length = 10)
  private String dataCredito;

  @Size(max = 100)
  @Column(name = "LOGRADOURO", length = 100)
  private String logradouro;

  @Size(max = 100)
  @Column(name = "ENDERECO", length = 100)
  private String endereco;

  @Size(max = 30)
  @Column(name = "COMPLEMENTO", length = 30)
  private String complemento;

  @Size(max = 10)
  @Column(name = "NUMERO", length = 10)
  private String numero;

  @Size(max = 100)
  @Column(name = "BAIRRO", length = 100)
  private String bairro;

  @Size(max = 100)
  @Column(name = "CIDADE", length = 100)
  private String cidade;

  @Size(max = 2)
  @Column(name = "UF", length = 2)
  private String uf;

  @Size(max = 9)
  @Column(name = "CEP", length = 9)
  private String cep;

  @NotNull
  @Column(name = "statusDetalhe", nullable = false)
  private Long statusDetalhe;

  @Size(max = 100)
  @Column(name = "MOTIVO", length = 100)
  private String motivoErro;

  @Column(name = "DATAPROCESSAMENTO")
  private LocalDateTime dataProcessamento;

  @Column(name = "DATAAGENDAMENTOCARGA")
  private LocalDate dataAgendamentoCarga;

  @NotNull
  @Column(name = "NUMEROLINHAARQUIVO", nullable = false)
  private Long numeroLinhaArquivo;

  @ManyToOne(optional = false)
  @JoinColumns(@JoinColumn(name = "ID_ARQUIVOCARGASTD"))
  @NotNull
  private ArquivoCarga arquivoCarga;
}
