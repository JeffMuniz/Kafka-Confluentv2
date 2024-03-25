package br.com.mac.funcionario.repository.blue;

import br.com.mac.funcionario.dto.FuncionarioDTO;
import br.com.mac.funcionario.enums.Acao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Repository
@Transactional(value = "transactionManagerBlue")
public class CustomQueryRepositoryImpl implements CustomQueryRepository {

    private static Logger logger = LoggerFactory.getLogger(CustomQueryRepositoryImpl.class);

    @PersistenceContext(unitName = "blue", name = "")
    private EntityManager entityManager;

    @Override
    public String getCNPJByIdFuncionario(final Long idFuncionario) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("SELECT pe.cpf as cnpj ");
        stringBuilder.append(" FROM  empresas emp");
        stringBuilder.append("           INNER JOIN  pessoas pe on (pe.id_pessoafisica=emp.id_pessoa)  ");
        stringBuilder.append("           INNER JOIN  funcionarios f on (f.id_empresa=emp.id_empresa)  ");
        stringBuilder.append("           INNER JOIN  pessoas ps on (ps.id_pessoafisica=f.id_pessoafisica)  ");
        stringBuilder.append("WHERE f.id_funcionario = "+ idFuncionario);

        return (String) this.entityManager.createNativeQuery(stringBuilder.toString()).getSingleResult();
    }

    @Override
    @Transactional(transactionManager = "transactionManagerBlue",
                   noRollbackFor=Exception.class)
    public LinkedHashMap<String, FuncionarioDTO> getFuncionarioDTOByCpfAndCnpj(final StringBuilder cpf, final String cnpj,
                                                              LinkedHashMap<String, FuncionarioDTO> funcionarios) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("SELECT ");
        stringBuilder.append("       f.id_funcionario as id, ");
        stringBuilder.append("       f.status as status, ");
        stringBuilder.append("       ps.cpf as cpf, ");
        stringBuilder.append("       pe.cpf as cnpj, ");
        stringBuilder.append("       ps.nome as nome, ");
        stringBuilder.append("       emp.nomeFantasia ");
        stringBuilder.append(" FROM  empresas emp");
        stringBuilder.append("           INNER JOIN  pessoas pe on (pe.id_pessoafisica=emp.id_pessoa)  ");
        stringBuilder.append("           INNER JOIN  funcionarios f on (f.id_empresa=emp.id_empresa)  ");
        stringBuilder.append("           INNER JOIN  pessoas ps on (ps.id_pessoafisica=f.id_pessoafisica)  ");
        stringBuilder.append("WHERE ps.cpf in ("+ cpf.substring(1).toString()+")");
        stringBuilder.append("  AND pe.cpf = '"+ cnpj+"'");

        logger.info(stringBuilder.toString());

        List<Object[]> result = null;
        try{
            result = this.entityManager.createNativeQuery(stringBuilder.toString()).getResultList();

            for(Object[] obj : result) {
                funcionarios.replace((String) obj[2], FuncionarioDTO.builder()
                                                    .cpf((String) obj[2])
                                                    .cnpj((String) obj[3])
                                                    .status(Long.valueOf((Short) obj[1]))
                                                    .nome((String) obj[4])
                                                    .nomeEmpresa((String) obj[5])
                                                    .idFuncionario(Long.valueOf(obj[0].toString()))
                                                    .build());
            }

            return funcionarios;
        } catch (EmptyResultDataAccessException | NoResultException e) {
            logger.error("Erro ao consultar status do funcionário - " + e.getMessage());
        } finally {
            if(result == null)
            logger.error("Erro ao consultar status do funcionário - CPF: "
                    + cpf
                    + " - " + Acao.NAO_ENCONTRADO.getDescricao());
        }
        return funcionarios;
    }

}
