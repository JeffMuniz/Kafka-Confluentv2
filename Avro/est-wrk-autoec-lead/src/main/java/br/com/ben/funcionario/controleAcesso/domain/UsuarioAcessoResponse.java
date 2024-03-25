
package br.com.mac.funcionario.controleAcesso.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UsuarioAcessoResponse implements Serializable {

     private static final long serialVersionUID = 1L;

     @ApiModelProperty(value = "Identificador do usuário", example = "1", position=1)
     private Long id;
     
     @ApiModelProperty(value = "cpf", notes = "CPF do usuário", example = "12345678910",  position=4)
     private String cpf;

     @ApiModelProperty(value = "nome", notes = "Nome do usuário", example = "Maria da Paz",  position=6)
     private String nome;

     @ApiModelProperty(value = "login", notes = "Login do usuário", example = "12345678910",  position=7)
     private String login;

     @ApiModelProperty(notes = "Status do usuário", required = false, example = "1",  position=8)
     private Integer status;

}
