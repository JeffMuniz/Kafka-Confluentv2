package br.com.mac.funcionario.repository.blue;

import br.com.mac.funcionario.blue.domain.PessoaFisica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PessoaFisicaRepository extends JpaRepository<PessoaFisica, Long> {

    @Query(value = " select id_pessoafisica,       "
            + "        id_emissor,            "
            + "        nome,                  "
            + "        cpf,                   "
            + "        datanascimento,        "
            + "        sexo,                  "
            + "        numeroidentidade,      "
            + "        orgaoidentidade,       "
            + "        estadoidentidade,      "
            + "        dataemissaoidentidade, "
            + "        cic,                   "
            + "        statuscic,             "
            + "        flagalteracaocic,      "
            + "        flagdeficientevisual,  "
            + "        tipopessoa,            "
            + "        statuspf               "
            + "   from PessoasFisicas         "
            + " where Id_PessoaFisica = :id   ",
            nativeQuery = true)
    Optional<PessoaFisica> procurarPessoPorIdPessoaFisica(final Long id);

}
