package br.com.mac.estautoecapi.repository;

import br.com.mac.estautoecapi.bean.PessoaFisicaResponse;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaFisicaQuestionarioRepository extends MongoRepository<PessoaFisicaResponse, String> {

    @Query(value = "{'cpf': ?0}")
    PessoaFisicaResponse findByCpf(String cpf);

}
