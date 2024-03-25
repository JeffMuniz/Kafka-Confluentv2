package br.com.mac.funcionario.service.mapper;

import br.com.mac.funcionario.dto.response.FuncionarioProdutoResponse;
import br.com.mac.funcionario.repository.blue.projection.FuncionarioProdutoProjection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    uses = {ContaFuncionarioProdutoResponseMapper.class})
public interface FuncionarioProdutoResponseMapper
    extends ResponseMapper<FuncionarioProdutoProjection, FuncionarioProdutoResponse> {

  @Override
  @Mapping(source = "funcionarioProdutoFuncionarioId", target = "id")
  @Mapping(source = "funcionarioProdutoContaId", target = "conta.id")
  @Mapping(source = "produtoId", target = "conta.idProduto")
  @Mapping(source = "funcionarioProdutoId", target = "conta.produto.id")
  @Mapping(source = "produtoNome", target = "conta.produto.nome")
  @Mapping(source = "produtoDescricao", target = "conta.produto.descricao")
  FuncionarioProdutoResponse toResponse(FuncionarioProdutoProjection dto);
}
