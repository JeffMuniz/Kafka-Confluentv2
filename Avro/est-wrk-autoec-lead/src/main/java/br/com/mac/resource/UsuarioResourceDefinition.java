package br.com.mac.funcionario.resource;

import static br.com.mac.funcionario.exception.GlobalExceptionHandler.MENSAGEM_GLOBAL_400;
import static br.com.mac.funcionario.exception.GlobalExceptionHandler.MENSAGEM_GLOBAL_404;
import static br.com.mac.funcionario.exception.GlobalExceptionHandler.MENSAGEM_GLOBAL_412;
import static br.com.mac.funcionario.exception.GlobalExceptionHandler.MENSAGEM_GLOBAL_500;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


import br.com.mac.funcionario.controleAcesso.domain.UsuarioAcessoResponse;
import br.com.mac.funcionario.domain.response.ErroInfo;
import br.com.mac.funcionario.utils.Constantes;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@Api(value = Constantes.PATH_USUARIO, produces = APPLICATION_JSON_VALUE, tags = {"Usuário"})
public interface UsuarioResourceDefinition {

  @ApiOperation(value = "Obtem usuário acesso", notes = "Obtem usuário acesso", response = UsuarioAcessoResponse.class)
  @ApiResponses({@ApiResponse(code = 400, message = MENSAGEM_GLOBAL_400, response = ErroInfo.class),
      @ApiResponse(code = 404, message = MENSAGEM_GLOBAL_404, response = ErroInfo.class),
      @ApiResponse(code = 412, message = MENSAGEM_GLOBAL_412, response = ErroInfo.class),
      @ApiResponse(code = 500, message = MENSAGEM_GLOBAL_500, response = ErroInfo.class)
  })
  ResponseEntity<?> getUsuarioAcesso(@PathVariable(required = true) Long id);


}
