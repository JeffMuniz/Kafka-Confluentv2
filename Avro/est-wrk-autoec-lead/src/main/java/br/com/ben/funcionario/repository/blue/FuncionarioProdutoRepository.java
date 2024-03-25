package br.com.mac.funcionario.repository.blue;

import br.com.mac.funcionario.blue.domain.FuncionarioProduto;
import br.com.mac.funcionario.repository.JpaSpecificationExecutorWithProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.List;

@Repository
public interface FuncionarioProdutoRepository
    extends JpaRepository<FuncionarioProduto, Long>,
        JpaSpecificationExecutorWithProjection<FuncionarioProduto> {
}
