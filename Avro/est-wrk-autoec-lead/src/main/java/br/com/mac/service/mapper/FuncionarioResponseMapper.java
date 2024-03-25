package br.com.mac.funcionario.service.mapper;

import br.com.mac.funcionario.blue.domain.FuncionarioVM;
import br.com.mac.funcionario.dto.response.FuncionarioResponse;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.util.StringUtils;

@Mapper(
    componentModel = "spring",
    uses = {EnderecoResponseMapper.class, PessoaResponseMapper.class})
public interface FuncionarioResponseMapper
    extends ResponseMapper<FuncionarioVM, FuncionarioResponse> {

  @Override
  @Mapping(source = "empresaCnpj", target = "cnpj")
  @Mapping(source = "funcionarioId", target = "id")
  @Mapping(source = "empresaId", target = "idEmpresa")
  @Mapping(source = "funcionarioEmpresaSetorId", target = "idSetor")
  @Mapping(source = "funcionarioMatricula", target = "matricula")
  @Mapping(source = "funcionarioStatus", target = "status")
  @Mapping(source = "pessoaId", target = "pessoa.idPessoa")
  @Mapping(source = "pessoaCpf", target = "pessoa.cpf")
  @Mapping(source = "pessoaEmissorId", target = "pessoa.idEmissor")
  @Mapping(source = "pessoaDataNascimento", target = "pessoa.dataNascimento")
  @Mapping(source = "pessoaSexo", target = "pessoa.sexo")
  @Mapping(source = "pessoaNumeroIdentidade", target = "pessoa.numeroIdentidade")
  @Mapping(source = "pessoaNome", target = "pessoa.nome")
  @Mapping(source = "enderecoBairro", target = "endereco.bairro")
  @Mapping(source = "enderecoCep", target = "endereco.cep")
  @Mapping(source = "enderecoCidade", target = "endereco.cidade")
  @Mapping(source = "enderecoUf", target = "endereco.uf")
  @Mapping(source = "enderecoComplemento", target = "endereco.complemento")
  @Mapping(source = "enderecoLogradouro", target = "endereco.logradouro")
  @Mapping(source = "enderecoNumero", target = "endereco.numero")
  FuncionarioResponse toResponse(FuncionarioVM dto);

  @AfterMapping
  default void updateResult(
      @MappingTarget FuncionarioResponse.FuncionarioResponseBuilder funcionarioResponse) {
    var build = funcionarioResponse.build();
    funcionarioResponse.matricula(StringUtils.trimWhitespace(build.getMatricula()));
    funcionarioResponse.cnpj(StringUtils.trimWhitespace(build.getCnpj()));
    var enderecoResponse = build.getEndereco();
    enderecoResponse.setCidade(StringUtils.trimWhitespace(enderecoResponse.getCidade()));
    enderecoResponse.setBairro(StringUtils.trimWhitespace(enderecoResponse.getBairro()));
    enderecoResponse.setComplemento(StringUtils.trimWhitespace(enderecoResponse.getComplemento()));
    enderecoResponse.setLogradouro(StringUtils.trimWhitespace(enderecoResponse.getLogradouro()));
    funcionarioResponse.endereco(enderecoResponse);
    var pessoaResponse = build.getPessoa();
    pessoaResponse.setNumeroIdentidade(
        StringUtils.trimWhitespace(pessoaResponse.getNumeroIdentidade()));
    pessoaResponse.setCpf(StringUtils.trimWhitespace(pessoaResponse.getCpf()));
    funcionarioResponse.pessoa(pessoaResponse);
  }
}
