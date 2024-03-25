package br.com.mac.funcionario.domain.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErroInfo implements Serializable {

  private LocalDateTime timestamp;

  private Integer code;

  private String exception;

  private List<String> messages;

  private String path;

}
