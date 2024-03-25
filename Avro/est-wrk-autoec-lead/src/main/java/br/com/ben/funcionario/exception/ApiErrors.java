package br.com.mac.funcionario.exception;

import br.com.mac.funcionario.service.exception.BusinnesException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiErrors {

  private List<String> errors;

  public ApiErrors(BindingResult bindingResult) {
    this.errors = new ArrayList<>();
    bindingResult.getAllErrors().forEach(error -> this.errors.add(error.getDefaumacessage()));
  }

  public ApiErrors(BusinnesException ex) {
    this.errors = Arrays.asList(ex.getMessage());
  }
}
