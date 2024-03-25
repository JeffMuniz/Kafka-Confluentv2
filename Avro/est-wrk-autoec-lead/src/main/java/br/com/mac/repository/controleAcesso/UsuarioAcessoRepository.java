package br.com.mac.funcionario.repository.controleAcesso;

import br.com.mac.funcionario.controleAcesso.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioAcessoRepository extends JpaRepository<Usuario, Long> {

}
