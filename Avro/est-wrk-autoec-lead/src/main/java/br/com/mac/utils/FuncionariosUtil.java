package br.com.mac.funcionario.utils;

import br.com.mac.funcionario.blue.domain.Funcionario;
import br.com.mac.funcionario.dto.FuncionarioDTO;
import br.com.mac.funcionario.enums.MotivoInativar;
import java.util.ArrayList;
import java.util.List;

public class FuncionariosUtil {

  public static void listDtoToListEntity(List<FuncionarioDTO> funcionarioDTOList,
                                         List<Funcionario> funcionarioList) {
    List<Funcionario> retorno = new ArrayList<>();
    for (FuncionarioDTO funcionarioDTO : funcionarioDTOList) {
      for (Funcionario funcionario : funcionarioList) {
        if (funcionarioDTO.getIdFuncionario().equals(funcionario.getIdFuncionario())) {
          funcionario.setMotivo(MotivoInativar.toEnum(funcionarioDTO.getMotivo()).getDescricao());
          funcionario.setCnpj(funcionarioDTO.getCnpj());
          funcionario.setCpf(funcionarioDTO.getCpf());
          funcionario.setNome(funcionarioDTO.getNome());
          retorno.add(funcionario);
          break;
        }
      }
    }
  }

}
