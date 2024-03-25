package br.com.mac.funcionario.resource;

import static br.com.mac.funcionario.utils.Constantes.PATH_HISTORICO;


import br.com.mac.funcionario.dto.request.RequestHistoricoDTO;
import br.com.mac.funcionario.service.HistoricoService;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(PATH_HISTORICO)
public class HistoricoResource implements HistoricoResourceDefinition {

  private final HistoricoService historicoService;

  public HistoricoResource(HistoricoService historicoService) {
    this.historicoService = historicoService;
  }

  @Override
  @GetMapping("/cpf/{cpf}")
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<RequestHistoricoDTO> consultarHistoricoVinculoCpf(
      @Valid @PathVariable("cpf") final String cpf) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(historicoService.consultarHistoricoVinculoCpf(cpf));
  }


  @Override
  @GetMapping("/cnpj/{cnpj}")
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<RequestHistoricoDTO> consultarHistoricoVinculoCnpj(
      @Valid @PathVariable("cnpj") final String cnpj) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(historicoService.consultarHistoricoVinculoCnpj(cnpj));
  }
}
