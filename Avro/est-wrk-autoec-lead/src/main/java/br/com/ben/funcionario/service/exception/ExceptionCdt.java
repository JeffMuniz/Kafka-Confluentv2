package br.com.mac.funcionario.service.exception;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@Setter
public class ExceptionCdt extends RuntimeException implements Serializable {
  /**
   *
   */
  private static final long serialVersionUID = 1L;

  private String message;
  private Throwable throwable;
  private HttpStatus status;

  public ExceptionCdt(HttpStatus status, Throwable cause) {
    super(cause);
    this.status = status;
  }

  public ExceptionCdt(HttpStatus status, String message) {
    super(message);
    this.message = message;
    this.status = status;
  }

  public static void checkThrow(final boolean expression,
                                final ExceptionsMessagesCdtEnum exceptionsMessagesCdtEnum) {
    if (expression) {
      exceptionsMessagesCdtEnum.raise();
    }
  }

  public static void checkThrow(final boolean expression,
                                final ExceptionsMessagesCdtEnum exceptionsMessagesCdtEnum,
                                final String message) {
    if (expression) {
      exceptionsMessagesCdtEnum.raise(message);
    }
  }
}
