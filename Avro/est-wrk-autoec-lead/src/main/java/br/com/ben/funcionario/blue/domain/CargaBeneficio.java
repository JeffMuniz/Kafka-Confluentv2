package br.com.mac.funcionario.blue.domain;

import java.io.Serializable;
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
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CARGASmacEFICIOS")
public class Cargamaceficio implements Serializable {

  private static final long serialVersionUID = -523717925277303703L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID_CARGAmacEFICIO")
  private Long id;

  @NotNull
  @Column(name = "STATUS", nullable = false)
  private Long status;

  @Size(max = 100)
  @Column(name = "NOMEARQUIVO", length = 100)
  private String nomeArquivo;

  @Size(max = 10)
  @Column(name = "ORIGEM", length = 10)
  private String origem;

  @Column(name = "id_usuario")
  private Long idUsuario;

  @NotNull
  @Column(name = "DATAPROCESSAMENTO", nullable = false)
  private LocalDateTime dataProcessamento;

  @NotNull
  @Column(name = "DATAIMPORTACAO", nullable = false)
  private LocalDateTime dataImportacao;

  @NotNull
  @Column(name = "DATAAGENDAMENTO", nullable = false)
  private LocalDateTime dataAgendamento;

  @Column(name = "DATAPROCESSAMENTOCARGA")
  private LocalDateTime dataProcessamentoCarga;

  @Column(name = "DATACANCELAMENTO")
  private LocalDateTime dataCancelamento;

  @ManyToOne
  @JoinColumns(@JoinColumn(name = "ID_ARQUIVOCARGASTD"))
  private ArquivoCarga arquivoCarga;

  @ManyToOne
  @JoinColumns(@JoinColumn(name = "ID_EMPRESA"))
  private Empresa empresa;

  @OneToMany(mappedBy = "cargamaceficio")
  private Set<EmpresaCarga> empresaCargas = new HashSet<>();
}
