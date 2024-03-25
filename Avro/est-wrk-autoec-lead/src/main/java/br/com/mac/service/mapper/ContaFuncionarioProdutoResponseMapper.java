package br.com.mac.funcionario.service.mapper;

import br.com.mac.funcionario.blue.domain.Conta;
import br.com.mac.funcionario.dto.response.ContaFuncionarioProdutoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    uses = {})
public interface ContaFuncionarioProdutoResponseMapper
    extends ResponseMapper<Conta, ContaFuncionarioProdutoResponse> {

  @Override
  @Mapping(source = "produto.id", target = "idProduto")
  ContaFuncionarioProdutoResponse toResponse(Conta dto);
}
