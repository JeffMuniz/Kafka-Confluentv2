package br.com.mac.funcionario.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageFuncionarioResponse {

  @JsonProperty("content")
  @Valid
  private List<FuncionarioResponse> content;

  @JsonProperty("first")
  private Boolean first;

  @JsonProperty("hasContent")
  private Boolean hasContent;

  @JsonProperty("last")
  private Boolean last;

  @JsonProperty("nextPage")
  private Integer nextPage;

  @JsonProperty("number")
  private Integer number;

  @JsonProperty("numberOfElements")
  private Integer numberOfElements;

  @JsonProperty("previousPage")
  private Integer previousPage;

  @JsonProperty("size")
  private Integer size;

  @JsonProperty("totalElements")
  private Long totalElements;

  @JsonProperty("totalPages")
  private Integer totalPages;
}
