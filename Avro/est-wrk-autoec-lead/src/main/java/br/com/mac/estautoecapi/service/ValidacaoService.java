package br.com.mac.estautoecapi.service;

import br.com.mac.customexception.dto.ExceptionsMessagesEnum;
import br.com.mac.customexception.exception.BadRequestCustom;
import br.com.mac.estautoecapi.bean.PessoaFisicaResponse;
import br.com.mac.estautoecapi.bean.ValidaPessoaFisicaRequest;
import br.com.mac.estautoecapi.bean.credenciamento.DadosCredenciamento;
import br.com.mac.estautoecapi.bean.credenciamento.Estabelecimento;
import br.com.mac.estautoecapi.repository.DadosCredenciamentoRepository;
import br.com.mac.estautoecapi.repository.PessoaFisicaQuestionarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static br.com.mac.customexception.exception.ExceptionCustom.checkThrow;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class ValidacaoService {

    private final FakeList fakeList;

    private final PessoaFisicaQuestionarioRepository pessoaFisicaQuestionarioRepository;

    private final DadosCredenciamentoRepository dadosCredenciamentoRepository;

    private final SocioService socioService;

    private final MotorPoliticaService service;

    public PessoaFisicaResponse consultarPessoaFisicaValidacao(final String cnpj, final String cpf) throws BadRequestCustom {
        checkThrow(!socioService.isSocio(cnpj, cpf), ExceptionsMessagesEnum.GLOBAL_BAD_REQUEST);

        PessoaFisicaResponse fisicaResponse = pessoaFisicaQuestionarioRepository.findByCpf(cpf);
        if (nonNull(fisicaResponse))
            return fisicaResponse;

        fisicaResponse = service.consultarPessoaFisica(cpf);

        fisicaResponse.setNomes(fakeList.buildFakeListNomes(fisicaResponse.getSexo(), fisicaResponse.getNome()));
        fisicaResponse.setNomesMae(fakeList.buildFakeListNomesMae(fisicaResponse.getNomeMae()));
        fisicaResponse.setDatasNascimento(fakeList.buildFakeListDataNascimento(fisicaResponse.getDataNascimento()));

        pessoaFisicaQuestionarioRepository.save(fisicaResponse);
        fisicaResponse.ocultarValores();

        return fisicaResponse;
    }

    public void validarPessoa(String cnpj, ValidaPessoaFisicaRequest pessoaRequest) {
        if (isPessoaFisicaValida(pessoaRequest) && socioService.isSocio(cnpj, pessoaRequest.getCpf())) {
            String generatedId = criaIdDadosCredenciamento(cnpj, pessoaRequest.getCpf());
            Estabelecimento estabelecimento = Estabelecimento.builder().cnpj(cnpj).build();

            dadosCredenciamentoRepository.save(DadosCredenciamento.builder().estabelecimento(estabelecimento).dataCriacao(LocalDate.now()).id(generatedId).build());
            return;
        }
        throw new BadRequestCustom("Erro na validação");
    }

    private Boolean isPessoaFisicaValida(ValidaPessoaFisicaRequest pessoaRequest) {
        PessoaFisicaResponse pessoa = pessoaFisicaQuestionarioRepository.findByCpf(pessoaRequest.getCpf());
        if (isNull(pessoa)) {
            return false;
        } else if (!isNull(pessoaRequest.getNome())) {
            return pessoaRequest.getNome().equals(pessoa.getNome());
        } else if (!isNull(pessoaRequest.getNomeMae())) {
            return pessoaRequest.getNomeMae().equals(pessoa.getNomeMae());
        } else if (!isNull(pessoaRequest.getDataNascimento())) {
            return pessoaRequest.getDataNascimento().equals(pessoa.getDataNascimento());
        }
        return false;
    }

    private String criaIdDadosCredenciamento(String cnpj, String cpf) {
        return cnpj + ":" + cpf;
    }
}