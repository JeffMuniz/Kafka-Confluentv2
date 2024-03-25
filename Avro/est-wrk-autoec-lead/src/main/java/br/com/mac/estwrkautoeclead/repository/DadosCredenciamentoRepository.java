package br.com.machina.estwrkautoeclead.repository;

import br.com.machina.estwrkautoeclead.domain.DadosCredenciamento;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DadosCredenciamentoRepository extends MongoRepository<DadosCredenciamento, String> {

    List<DadosCredenciamento> findDadosCredenciamentoByStatusCredenciamento(String status);
}
