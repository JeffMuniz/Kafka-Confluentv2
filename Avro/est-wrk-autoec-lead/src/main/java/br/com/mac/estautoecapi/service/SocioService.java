package br.com.mac.estautoecapi.service;

import br.com.mac.estautoecapi.bean.PoliticaResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static java.util.Objects.requireNonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class SocioService {

    private final MotorPoliticaService service;

    public boolean isSocio(String cnpj, String cpf) {
        PoliticaResponse pessoaJuridica = service.consultarPessoaJuridica(cnpj);
        if(pessoaJuridica != null)
            return pessoaJuridica.getSocios().stream().anyMatch(s -> requireNonNull(cpf).equals(s.getCpf()));
        return false;
    }

}