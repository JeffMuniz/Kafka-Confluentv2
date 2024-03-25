package br.com.mac.funcionario.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.query.JpaEntityGraph;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface JpaSpecificationExecutorWithProjection<T> extends JpaSpecificationExecutor<T> {

  <R> Optional<R> findOne(Specification<T> spec, Class<R> projectionClass);

  <R> Page<R> findAll(Specification<T> spec, Class<R> projectionClass, Pageable pageable);

  <R> Page<R> findAll(Specification<T> spec, Class<R> projectionType, String namedEntityGraph,
                      EntityGraph.EntityGraphType type, Pageable pageable);

  <R> Page<R> findAll(Specification<T> spec, Class<R> projectionClass,
                      JpaEntityGraph dynamicEntityGraph, Pageable pageable);

  Page<T> findAll(Specification<T> spec,
                  JpaEntityGraph dynamicEntityGraph, Pageable pageable);

  Page<T> findAll(Specification<T> spec, String namedEntityGraph,
                  EntityGraph.EntityGraphType type, Pageable pageable);

  <R> List<R> findAll(Specification<T> spec, Class<R> projectionType, String namedEntityGraph,
                      EntityGraph.EntityGraphType type);

  List<T> findAll(Specification<T> spec,
                  String namedEntityGraph,
                  EntityGraph.EntityGraphType type);

  List<T> findAll(Specification<T> spec,
                  String namedEntityGraph);

  <R> List<R> findAll(Specification<T> spec, Class<R> projectionClass,
                      JpaEntityGraph dynamicEntityGraph);
}
