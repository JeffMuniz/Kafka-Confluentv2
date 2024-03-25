package br.com.mac.funcionario.repository.blue;

import br.com.mac.funcionario.blue.domain.Funcionario;
import br.com.mac.funcionario.repository.JpaSpecificationExecutorWithProjection;
import br.com.mac.funcionario.repository.blue.projection.FuncionarioProdutoProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FuncionarioRepository
    extends JpaRepository<Funcionario, Long>,
        JpaSpecificationExecutor<Funcionario>,
        JpaSpecificationExecutorWithProjection<Funcionario> {

  @Query(
      value =
          "select distinct funcionari2_.Id_Funcionario as funcionarioProdutoFuncionarioId, "
              + "funcionari2_.Id_Conta as funcionarioProdutoContaId, "
              + "funcionari2_.Id_FuncionariosProdutos as funcionarioProdutoId, "
              + "produto11_.DESCRICAO as produtoDescricao, "
              + "produto11_.ID_BANDEIRA as produtoBandeiraId, "
              + "produto11_.ID_PRODUTO as produtoId, "
              + "produto11_.NOME as produtoNome "
              + "from Funcionarios funcionari0_ "
              + "inner join Enderecos endereco1_ (nolock) on funcionari0_.Id_EnderecoEntrega = endereco1_.Id_Endereco "
              + "inner join FuncionariosProdutos funcionari2_ (nolock) "
              + "on funcionari0_.Id_Funcionario = funcionari2_.Id_Funcionario "
              + "inner join Contas conta3_ (nolock) on funcionari2_.Id_Conta = conta3_.Id_Conta "
              + "inner join Pessoas pessoa4_ (nolock) on funcionari0_.Id_PessoaFisica = pessoa4_.Id_PessoaFisica "
              + "inner join Empresas empresa5_ (nolock) on funcionari0_.Id_Empresa = empresa5_.Id_Empresa "
              + "inner join EMPRESASCARGASDETALHES empresacar6_ (nolock) "
              + "on funcionari0_.Id_Funcionario = empresacar6_.Id_Funcionario "
              + "inner join EMPRESASCARGAS empresacar7_ (nolock) on empresacar6_.Id_EmpresaCarga = empresacar7_.ID_EMPRESACARGA "
              + "inner join CARGASmacEFICIOS cargamacef8_ (nolock) "
              + "on empresacar7_.Id_Cargamaceficio = cargamacef8_.ID_CARGAmacEFICIO "
              + "inner join ARQUIVOCARGASTD arquivocar9_ (nolock) "
              + "on cargamacef8_.ID_ARQUIVOCARGASTD = arquivocar9_.ID_ARQUIVOCARGASTD "
              + "inner join Pessoas pessoa10_ (nolock) on empresa5_.Id_Pessoa = pessoa10_.Id_PessoaFisica "
              + "inner join PRODUTOS produto11_ (nolock) on conta3_.Id_Produto = produto11_.ID_PRODUTO "
              + "where funcionari0_.Id_Funcionario = :idFuncionario",
      nativeQuery = true)
  List<FuncionarioProdutoProjection> findAllFuncionarioProduto(@Param("idFuncionario") Long idFuncionario);
}
