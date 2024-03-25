package br.com.mac.funcionario.service.mapper;

import br.com.mac.funcionario.utils.PageUtil;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Contract for a generic dto to response mapper.
 *
 * @param <D> - DTO type parameter.
 * @param <R> - Response type parameter.
 */
public interface ResponseMapper<D, R> {

  default Page<R> toResponse(
      List<D> dtoList, Pageable pageable) {
    return PageUtil.createPageFromList(toResponse(dtoList), pageable);
  }

  List<R> toResponse(List<D> dtoList);

  Set<R> toResponse(Set<D> dtoList);

  R toResponse(D dto);
}
