package br.com.mac.funcionario.controleAcesso.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "USUARIOS")
public class Usuario implements Serializable {

     private static final long serialVersionUID = -9130200726694089977L;

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Column(name = "ID_USUARIO")
     private Long id;

     @Column(name = "NOME")
     private String nome;

     @Column(name = "LOGIN")
     private String login;

     @Column(name = "ID_EMISSOR")
     private Integer idEmissor;

     @CPF
     @Column(name = "CPF")
     private String cpf;

     @Email
     @Column(name = "EMAIL")
     private String email;

     @Column(name = "SENHA")
     private byte[] senha;

     @Column(name = "STATUS")
     private Integer status;

     @Column(name = "BLOQUEARACESSO")
     private Boolean bloquearAcesso;

     @Column(name = "TENTATIVASINCORRETAS")
     private Integer tentativasIncorretas;

     @JsonSerialize(using = LocalDateTimeSerializer.class)
     @JsonDeserialize(using = LocalDateTimeDeserializer.class)
     @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
     @Column(name = "DATAULTIMAATUALIZACAO")
     private LocalDateTime dataUltimaAtualizacao;

     @Column(name = "PodeAssociarQQEmissor")
     private Boolean podeAssociarQQEmissor;

     @JsonSerialize(using = LocalDateTimeSerializer.class)
     @JsonDeserialize(using = LocalDateTimeDeserializer.class)
     @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
     @Column(name = "DataValidade")
     private LocalDateTime dataValidade;

     @Column(name = "Ativo")
     private Boolean ativo;

     @Column(name = "flagFiltrarOrigemComercial")
     private Boolean flagFiltrarOrigemComercial;

     @Column(name = "flagAcessaCadBinsChave")
     private Boolean flagAcessaCadBinsChave;

     @Column(name = "Hash")
     private byte[] hash;

     @Column(name = "FlagSenhaEmissor")
     private Boolean flagSenhaEmissor;

     @Column(name = "EmailAdministrador")
     private String emailAdministrador;

     @JsonSerialize(using = LocalDateTimeSerializer.class)
     @JsonDeserialize(using = LocalDateTimeDeserializer.class)
     @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
     @Column(name = "DataHora_UltLogin")
     private LocalDateTime dataHoraUltimoLogin;

     @Column(name = "Id_UsuarioPai")
     private Long idUsuarioPai;

     @Column(name = "Id_TipoUsuario")
     private Long idTipoUsuario;

     @Column(name = "FlagFiltrarPessoaJuridica")
     private Boolean flagFiltrarPessoaJuridica;

     @Column(name = "Tema")
     private String tema;

     @Column(name = "Id_Cargo")
     private Long idCargo;

     @Column(name = "PrimeiroAcesso")
     private Boolean primeiroAcesso;

     @Column(name = "Id_Plataforma")
     private Long idPlataforma;

     @Column(name = "Id_UsuarioResponsavelUltAlteracao")
     private Long idUsuarioResponsavelUltAlteracao;

     @CNPJ
     @Column(name = "CNPJ")
     private String cnpj;

}
