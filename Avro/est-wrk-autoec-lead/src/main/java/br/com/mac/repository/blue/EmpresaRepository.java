package br.com.mac.funcionario.repository.blue;

import br.com.mac.funcionario.dto.IEmpresaDTO;
import br.com.mac.funcionario.blue.domain.Empresa;
import br.com.mac.funcionario.repository.JpaSpecificationExecutorWithProjection;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmpresaRepository extends JpaRepository<Empresa, Long>,
    JpaSpecificationExecutorWithProjection<Empresa> {

  @Query("SELECT emp.pessoa.cpf AS cnpj FROM Empresa emp where emp.pessoa.cpf=:cnpj")
  Optional<IEmpresaDTO> findIEmpresaDTOByCnpj(@Param("cnpj") final String cnpj);

}
