package br.com.mac.funcionario.service.mapper;

import br.com.mac.funcionario.blue.domain.Pessoa;
import br.com.mac.funcionario.dto.response.PessoaResponse;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.util.StringUtils;

@Mapper(
    componentModel = "spring",
    uses = {})
public interface PessoaResponseMapper extends ResponseMapper<Pessoa, PessoaResponse> {

  @AfterMapping
  default void updateResult(@MappingTarget PessoaResponse pessoaResponse) {
    pessoaResponse.setCpf(StringUtils.trimWhitespace(pessoaResponse.getCpf()));
    pessoaResponse.setNome(StringUtils.trimWhitespace(pessoaResponse.getNome()));
    pessoaResponse
        .setNumeroIdentidade(StringUtils.trimWhitespace(pessoaResponse.getNumeroIdentidade()));
  }
}
