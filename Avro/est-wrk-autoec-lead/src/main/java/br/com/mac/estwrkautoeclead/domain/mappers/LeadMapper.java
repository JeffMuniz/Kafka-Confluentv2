package br.com.machina.estwrkautoeclead.domain.mappers;

import br.com.machina.estwrkautoeclead.domain.DadosCredenciamento;
import br.com.machina.estwrkautoeclead.domain.DadosLead;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LeadMapper {

    @Mapping(target = "nome", source = "proprietario.nome")
    @Mapping(target = "cpf", source = "proprietario.cpf")
    @Mapping(target = "email", source = "proprietario.email")
    @Mapping(target = "telefone", source = "proprietario.telefone")
    @Mapping(target = "razaoSocial", source = "estabelecimento.razaoSocial")
    @Mapping(target = "cnpj", source = "estabelecimento.cnpj")
    @Mapping(target = "nomeFantasia", source = "estabelecimento.nomeFantasia")
    DadosLead from(final DadosCredenciamento dadosCredenciamento);
}
