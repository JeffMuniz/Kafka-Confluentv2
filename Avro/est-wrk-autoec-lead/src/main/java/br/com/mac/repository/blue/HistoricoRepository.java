package br.com.mac.funcionario.repository.blue;

import br.com.mac.funcionario.blue.domain.Historico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoricoRepository extends JpaRepository<Historico, Long> {

    List<Historico> findByCpf(final String cpf);

    List<Historico> findByCnpj(final String cnpj);

    List<Historico> findByCpfAndCnpj(final String cpf, final String cnpj);
}
