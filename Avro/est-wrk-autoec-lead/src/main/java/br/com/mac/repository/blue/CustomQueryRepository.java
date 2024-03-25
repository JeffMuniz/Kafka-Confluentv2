package br.com.mac.funcionario.repository.blue;

import br.com.mac.funcionario.dto.FuncionarioDTO;

import java.util.LinkedHashMap;

public interface CustomQueryRepository {

    String getCNPJByIdFuncionario(final Long idFuncionario);

    LinkedHashMap<String, FuncionarioDTO> getFuncionarioDTOByCpfAndCnpj(final StringBuilder cpf, final String cnpj,
                                                                        LinkedHashMap<String, FuncionarioDTO> funcionarios);
}
