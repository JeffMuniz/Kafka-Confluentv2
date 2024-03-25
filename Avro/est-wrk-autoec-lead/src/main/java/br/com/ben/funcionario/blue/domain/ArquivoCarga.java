package br.com.mac.funcionario.blue.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ARQUIVOCARGASTD")
public class ArquivoCarga implements Serializable {

  private static final long serialVersionUID = -365088118189475319L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID_ARQUIVOCARGASTD")
  private Long id;

  @Column(name = "VALORESPERADO", precision = 21, scale = 2)
  private BigDecimal valor;

  @NotNull
  @Column(name = "STATUS")
  private Long status;

  @Column(name = "DATASTATUS")
  private LocalDateTime dataStatus;

  @Column(name = "DATAPROCESSAMENTO")
  private LocalDateTime dataProcessamento;

  @Column(name = "DATAIMPORTACAO")
  private LocalDateTime dataImportacao;

  @Column(name = "DATAAGENDAMENTO")
  private LocalDateTime dataAgendamento;

  @Size(max = 100)
  @Column(name = "NOMEARQUIVO", length = 100)
  private String nome;

  @Column(name = "JOB_ID")
  private Long jobId;

  @Column(name = "ID_USUARIOREGISTRO")
  private Long idUsuario;

  @Size(max = 20)
  @Column(name = "ORIGEM", length = 20)
  private String origem;

  @Column(name = "MOTIVOERRO")
  private String motivoErro;

  @Column(name = "FLAGPEDIDOCENTRALIZADO", columnDefinition = "BIT")
  private Boolean flagPedidoCentralizado;

  @Column(name = "FLAGFATURAMENTOCENTRALIZADO", columnDefinition = "BIT")
  private Boolean flagFaturamentoCentralizado;

  @Column(name = "DATACANCELAMENTOPEDIDO")
  private LocalDateTime dataCancelamentoPedido;

  @Size(max = 3)
  @Column(name = "TIPOPAGAMENTOPEDIDO", length = 3)
  private String tipoPagamentoPedido;

  @Column(name = "STATUSPAGAMENTO")
  private Long statusPagamento;

  @Size(max = 50)
  @Column(name = "UUID", length = 50)
  private String uuid;

  @ManyToOne
  @JoinColumns(@JoinColumn(name = "ID_PRODUTO"))
  private Produto produto;

  @OneToMany(mappedBy = "arquivoCarga")
  private Set<ArquivoCargaDetalhe> arquivoCargaDetalhes = new HashSet<>();

  @OneToMany(mappedBy = "arquivoCarga")
  private Set<Cargamaceficio> cargamaceficios = new HashSet<>();
}
