package br.com.mac.funcionario.service;

import br.com.mac.funcionario.blue.domain.Funcionario;
import br.com.mac.funcionario.blue.domain.Historico;
import br.com.mac.funcionario.dto.HistoricoLogDTO;
import br.com.mac.funcionario.service.exception.BadRequestCdt;
import br.com.mac.funcionario.service.exception.ExceptionCdt;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class HistoricoLogService {

  public static final String PATH_HISTORICOS = "/historicos";
  private static final Logger logger = LoggerFactory.getLogger(FuncionarioAtivarService.class);
  @Value("${app.historico.host}")
  protected String URI;

  @Autowired
  ObjectMapper objectMapper;

  public void criarLogFuncionario(List<Funcionario> funcionarios) {

    List<HistoricoLogDTO> historicoLogDTOS = new ArrayList<>();
    for (Funcionario funcionario : funcionarios) {
      HistoricoLogDTO logDTO = new HistoricoLogDTO();
      logDTO.setModulo("RH");
      logDTO.setChaveModulo("");
//            logDTO.setChaveSubModulo();
      logDTO.setData(LocalDateTime.now());
      logDTO.setDescricaoSubModulo("Ativar / Inativar funcionário para recebimento de macfícios");
      logDTO.setLogin(funcionario.getCpf());
      logDTO.setOrigem("PORTAL_RH");
      logDTO.setSubModulo(
          funcionario.getMotivo() != null ? funcionario.getMotivo() : ""
      );
      logDTO.setValorAnterior(funcionario);
      logDTO.setValorPosterior("");
      historicoLogDTOS.add(logDTO);
    }

    this.criarHistorico(historicoLogDTOS);
  }

  public void criarHistorico(List<HistoricoLogDTO> historicosRequest) {
    RestTemplate restTemplate = new RestTemplate();
    UriComponents uriComponents =
        UriComponentsBuilder.fromUriString(URI).path(PATH_HISTORICOS).build();


    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    ObjectNode objectNode = objectMapper.createObjectNode();

    objectNode.set("historicos", objectMapper.valueToTree(historicosRequest));
    objectNode.put("processamentoSincrono", false);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<String> entity = new HttpEntity<String>(objectNode.toString(), headers);

    try {
      ResponseEntity<String> responseEntity =
          restTemplate.postForEntity(uriComponents.toUriString(), entity, String.class);

      if (responseEntity.getStatusCode() != HttpStatus.CREATED) {
        logger.error("Erro ao criar histórico: " + objectNode.toString() + " - " +
            responseEntity.getStatusCode());
        throw new ExceptionCdt(
            responseEntity.getStatusCode(), "Erro ao criar histórico: " + objectNode.toString());
      }
    } catch (HttpClientErrorException e) {
      logger.error("Erro ao criar histórico: criarLogHistorico - " + e.getMessage());
      throw new BadRequestCdt(objectNode.toString());
    }
  }

  public void criarLogHistorico(List<Historico> historicos) {
    RestTemplate restTemplate = new RestTemplate();
    UriComponents uriComponents =
        UriComponentsBuilder.fromUriString(URI).path(PATH_HISTORICOS).build();


    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    ObjectNode objectNode = objectMapper.createObjectNode();

//        List<Historico> historicos = Arrays.asList(historico);
    objectNode.set("historicos", objectMapper.valueToTree(historicos));

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    System.out.println(objectNode.toString());

    HttpEntity<String> entity = new HttpEntity<String>(objectNode.toString(), headers);

    try {
      ResponseEntity<String> responseEntity =
          restTemplate.postForEntity(uriComponents.toUriString(), entity, String.class);

      if (responseEntity.getStatusCode() != HttpStatus.CREATED) {
        logger.error("Erro ao criar histórico: " + objectNode.toString() + " - " +
            responseEntity.getStatusCode());
        throw new ExceptionCdt(
            responseEntity.getStatusCode(), "Erro ao criar histórico: " + objectNode.toString());
      }
    } catch (HttpClientErrorException e) {
      logger.error("Erro ao criar histórico: criarLogHistorico - " + e.getMessage());
      throw new BadRequestCdt(objectNode.toString());
    }
  }
}

