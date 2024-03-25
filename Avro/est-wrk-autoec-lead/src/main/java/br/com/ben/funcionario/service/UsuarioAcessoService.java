package br.com.mac.funcionario.service;

import br.com.mac.funcionario.controleAcesso.domain.Usuario;
import br.com.mac.funcionario.controleAcesso.domain.UsuarioAcessoResponse;
import br.com.mac.funcionario.exception.UsuarioNaoEncontradoException;
import br.com.mac.funcionario.repository.controleAcesso.UsuarioAcessoRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class UsuarioAcessoService {

  private final UsuarioAcessoRepository usuarioAcessoRepository;

  public UsuarioAcessoService(UsuarioAcessoRepository usuarioAcessoRepository) {
    this.usuarioAcessoRepository = usuarioAcessoRepository;
  }

  public UsuarioAcessoResponse getUsuarioAcesso(final Long id) {
    Optional<Usuario> usuarioOptional = usuarioAcessoRepository.findById(id);
    Usuario usuario = usuarioOptional.orElseThrow(() -> new UsuarioNaoEncontradoException(
        "Usuario não encontrado! Id: " + id + Usuario.class.getName()
    ));

    return UsuarioAcessoResponse.builder()
        .cpf(usuario.getCpf())
        .id(usuario.getId())
        .login(usuario.getLogin())
        .nome(usuario.getNome())
        .status(usuario.getStatus())
        .build();
  }
}
