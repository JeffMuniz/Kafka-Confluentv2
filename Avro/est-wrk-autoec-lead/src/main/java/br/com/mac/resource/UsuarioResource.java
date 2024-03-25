package br.com.mac.funcionario.resource;

import static br.com.mac.funcionario.utils.Constantes.PATH_USUARIO;
import static org.springframework.http.HttpStatus.OK;


import br.com.mac.funcionario.controleAcesso.domain.UsuarioAcessoResponse;
import br.com.mac.funcionario.service.UsuarioAcessoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = PATH_USUARIO)
public class UsuarioResource implements UsuarioResourceDefinition {

  private final UsuarioAcessoService usuarioAcessoService;

  public UsuarioResource(UsuarioAcessoService usuarioAcessoService) {
    this.usuarioAcessoService = usuarioAcessoService;
  }

  @Override
  @ResponseStatus(value = OK)
  @GetMapping(value = "/{id}")
  public ResponseEntity<UsuarioAcessoResponse> getUsuarioAcesso(
      @PathVariable(required = true) Long id) {
    return ResponseEntity.ok(this.usuarioAcessoService.getUsuarioAcesso(id));
  }
}
