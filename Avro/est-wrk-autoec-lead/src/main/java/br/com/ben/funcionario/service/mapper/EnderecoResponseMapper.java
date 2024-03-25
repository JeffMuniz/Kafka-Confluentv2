package br.com.mac.funcionario.service.mapper;

import br.com.mac.funcionario.blue.domain.Endereco;
import br.com.mac.funcionario.dto.response.EnderecoResponse;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.util.StringUtils;

@Mapper(
    componentModel = "spring",
    uses = {})
public interface EnderecoResponseMapper extends ResponseMapper<Endereco, EnderecoResponse> {

  @AfterMapping
  default void updateResult(@MappingTarget EnderecoResponse enderecoResponse) {
    enderecoResponse.setBairro(StringUtils.trimWhitespace(enderecoResponse.getBairro()));
    enderecoResponse.setComplemento(StringUtils.trimWhitespace(enderecoResponse.getComplemento()));
    enderecoResponse.setCep(StringUtils.trimWhitespace(enderecoResponse.getCep()));
    enderecoResponse.setCidade(StringUtils.trimWhitespace(enderecoResponse.getCidade()));
    enderecoResponse.setNumero(StringUtils.trimWhitespace(enderecoResponse.getNumero()));
    enderecoResponse.setUf(StringUtils.trimWhitespace(enderecoResponse.getUf()));
    enderecoResponse.setLogradouro(StringUtils.trimWhitespace(enderecoResponse.getLogradouro()));
  }
}
