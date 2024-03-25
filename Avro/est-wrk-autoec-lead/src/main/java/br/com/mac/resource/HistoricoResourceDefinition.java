package br.com.mac.funcionario.resource;

import static br.com.mac.funcionario.exception.GlobalExceptionHandler.MENSAGEM_GLOBAL_204;
import static br.com.mac.funcionario.exception.GlobalExceptionHandler.MENSAGEM_GLOBAL_400;
import static br.com.mac.funcionario.exception.GlobalExceptionHandler.MENSAGEM_GLOBAL_401;
import static br.com.mac.funcionario.exception.GlobalExceptionHandler.MENSAGEM_GLOBAL_500;
import static br.com.mac.funcionario.utils.Constantes.RESOURCE_HISTORICO_CONSULTAR_POR_CNPJ_NOTES;
import static br.com.mac.funcionario.utils.Constantes.RESOURCE_HISTORICO_CONSULTAR_POR_CNPJ_VALUE;
import static br.com.mac.funcionario.utils.Constantes.RESOURCE_HISTORICO_CONSULTAR_POR_CPF_NOTES;
import static br.com.mac.funcionario.utils.Constantes.RESOURCE_HISTORICO_CONSULTAR_POR_CPF_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


import br.com.mac.funcionario.blue.domain.Historico;
import br.com.mac.funcionario.domain.response.ErroInfo;
import br.com.mac.funcionario.dto.request.RequestHistoricoDTO;
import br.com.mac.funcionario.utils.Constantes;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@Api(value = Constantes.PATH_HISTORICO, produces = APPLICATION_JSON_VALUE, tags = {
    Constantes.TAG_HISTORICO})
public interface HistoricoResourceDefinition {

  @ApiOperation(value = RESOURCE_HISTORICO_CONSULTAR_POR_CPF_VALUE, notes = RESOURCE_HISTORICO_CONSULTAR_POR_CPF_NOTES, response = Historico.class)
  @ApiResponses({
      @ApiResponse(code = 204, message = MENSAGEM_GLOBAL_204, response = ErroInfo.class),
      @ApiResponse(code = 400, message = MENSAGEM_GLOBAL_400, response = ErroInfo.class),
      @ApiResponse(code = 401, message = MENSAGEM_GLOBAL_401, response = ErroInfo.class),
      @ApiResponse(code = 500, message = MENSAGEM_GLOBAL_500, response = ErroInfo.class)
  })
  ResponseEntity<RequestHistoricoDTO> consultarHistoricoVinculoCpf(
      @Valid @PathVariable("cpf") String cpf);

  @ApiOperation(value = RESOURCE_HISTORICO_CONSULTAR_POR_CNPJ_VALUE, notes = RESOURCE_HISTORICO_CONSULTAR_POR_CNPJ_NOTES, response = Historico.class)
  @ApiResponses({
      @ApiResponse(code = 204, message = MENSAGEM_GLOBAL_204, response = ErroInfo.class),
      @ApiResponse(code = 400, message = MENSAGEM_GLOBAL_400, response = ErroInfo.class),
      @ApiResponse(code = 401, message = MENSAGEM_GLOBAL_401, response = ErroInfo.class),
      @ApiResponse(code = 500, message = MENSAGEM_GLOBAL_500, response = ErroInfo.class)
  })
  ResponseEntity<RequestHistoricoDTO> consultarHistoricoVinculoCnpj(
      @Valid @PathVariable("cpf") String cnpj);
}
