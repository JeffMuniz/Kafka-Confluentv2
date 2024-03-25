package br.com.mac.funcionario.repository.blue;

import br.com.mac.funcionario.blue.domain.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

}
