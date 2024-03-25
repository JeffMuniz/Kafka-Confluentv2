
package br.com.mac.funcionario.controleAcesso.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel(description = "Request para alterar dados de usuário no Pier")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class UsuarioAcessoRequest implements Serializable {

     /**
      * 
      */
     private static final long serialVersionUID = 1L;

     @ApiModelProperty(notes = "Login do usuário para acesso", required = false)
     private String login;

}
