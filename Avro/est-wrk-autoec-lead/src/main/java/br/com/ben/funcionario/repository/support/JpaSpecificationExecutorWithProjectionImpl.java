package br.com.mac.funcionario.repository.support;

import br.com.mac.funcionario.repository.JpaSpecificationExecutorWithProjection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.query.Jpa21Utils;
import org.springframework.data.jpa.repository.query.JpaEntityGraph;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.QueryHints;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public class JpaSpecificationExecutorWithProjectionImpl<T, ID extends Serializable>
    extends SimpleJpaRepository<T, ID> implements JpaSpecificationExecutorWithProjection<T> {

  private final EntityManager entityManager;

  private final ProjectionFactory projectionFactory = new SpelAwareProxyProjectionFactory();

  private final JpaEntityInformation<T, ?> entityInformation;

  public JpaSpecificationExecutorWithProjectionImpl(JpaEntityInformation<T, ?> entityInformation,
                                                    EntityManager entityManager) {
    super(entityInformation, entityManager);
    this.entityManager = entityManager;
    this.entityInformation = entityInformation;
  }


  @Override
  public <R> Optional<R> findOne(Specification<T> spec, Class<R> projectionType) {
    TypedQuery<T> query = getQuery(spec, Sort.unsorted());
    try {
      T result = query.getSingleResult();
      return Optional.of(projectionFactory.createProjection(projectionType, result));
    } catch (NoResultException e) {
      return Optional.empty();
    }
  }

  @Override
  public <R> Page<R> findAll(Specification<T> spec, Class<R> projectionType, Pageable pageable) {
    TypedQuery<T> query = getQuery(spec, pageable);
    return readPageWithProjection(spec, projectionType, pageable, query);
  }

  @Override
  public <R> Page<R> findAll(Specification<T> spec, Class<R> projectionType,
                             String namedEntityGraph,
                             org.springframework.data.jpa.repository.EntityGraph.EntityGraphType type,
                             Pageable pageable) {
    EntityGraph<?> entityGraph = this.entityManager.getEntityGraph(namedEntityGraph);
    if (entityGraph == null) {
      throw new IllegalArgumentException("Not found named entity graph -> " + namedEntityGraph);
    }
    TypedQuery<T> query = getQuery(spec, pageable);
    query.setHint(type.getKey(), entityGraph);
    return readPageWithProjection(spec, projectionType, pageable, query);
  }

  @Override
  public <R> Page<R> findAll(Specification<T> spec, Class<R> projectionType,
                             JpaEntityGraph dynamicEntityGraph, Pageable pageable) {
    TypedQuery<T> query = getQuery(spec, pageable);
    QueryHints queryHints = Jpa21Utils
            .getFetchGraphHint(this.entityManager, dynamicEntityGraph, getDomainClass());
    applyEntityGraphQueryHints(query, queryHints);
    return readPageWithProjection(spec, projectionType, pageable, query);
  }

  @Override
  public Page<T> findAll(Specification<T> spec, JpaEntityGraph dynamicEntityGraph,
                         Pageable pageable) {
    TypedQuery<T> query = getQuery(spec, pageable);
    QueryHints queryHints = Jpa21Utils
            .getFetchGraphHint(this.entityManager, dynamicEntityGraph, getDomainClass());
    applyEntityGraphQueryHints(query, queryHints);
    return readPage(spec, pageable, query);
  }

  @Override
  public Page<T> findAll(Specification<T> spec,
                         String namedEntityGraph,
                         org.springframework.data.jpa.repository.EntityGraph.EntityGraphType type,
                         Pageable pageable) {
    EntityGraph<?> entityGraph = this.entityManager.getEntityGraph(namedEntityGraph);
    if (entityGraph == null) {
      throw new IllegalArgumentException("Not found named entity graph -> " + namedEntityGraph);
    }
    TypedQuery<T> query = getQuery(spec, pageable);
    query.setHint(type.getKey(), entityGraph);
    return readPage(spec, pageable, query);
  }

  @Override
  public <R> List<R> findAll(Specification<T> spec, Class<R> projectionType,
                             String namedEntityGraph,
                             org.springframework.data.jpa.repository.EntityGraph.EntityGraphType type) {
    EntityGraph<?> entityGraph = this.entityManager.getEntityGraph(namedEntityGraph);
    if (entityGraph == null) {
      throw new IllegalArgumentException("Not found named entity graph -> " + namedEntityGraph);
    }
    TypedQuery<T> query = getQuery(spec, Pageable.unpaged());
    query.setHint(type.getKey(), entityGraph);
    return query.getResultList()
        .stream()
        .map(item -> projectionFactory.createProjection(projectionType, item))
        .collect(Collectors.toList());
  }

  @Override
  public List<T> findAll(Specification<T> spec,
                             String namedEntityGraph,
                             org.springframework.data.jpa.repository.EntityGraph.EntityGraphType type) {
    EntityGraph<?> entityGraph = this.entityManager.getEntityGraph(namedEntityGraph);
    if (entityGraph == null) {
      throw new IllegalArgumentException("Not found named entity graph -> " + namedEntityGraph);
    }
    TypedQuery<T> query = getQuery(spec, Pageable.unpaged());
    query.setHint(type.getKey(), entityGraph);
    query.setLockMode(LockModeType.NONE);
    return query.getResultList();
  }

  @Override
  public List<T> findAll(Specification<T> spec,
                             String namedEntityGraph) {
    return findAll(spec, namedEntityGraph, org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.FETCH);
  }

  @Override
  public <R> List<R> findAll(Specification<T> spec, Class<R> projectionType,
                             JpaEntityGraph dynamicEntityGraph) {
    TypedQuery<T> query = getQuery(spec, Pageable.unpaged());
    QueryHints queryHints = Jpa21Utils
            .getFetchGraphHint(this.entityManager, dynamicEntityGraph, getDomainClass());
    applyEntityGraphQueryHints(query, queryHints);
    return query.getResultList()
        .stream()
        .map(item -> projectionFactory.createProjection(projectionType, item))
        .collect(Collectors.toList());
  }

  private void applyEntityGraphQueryHints(Query query, QueryHints hints) {
    hints.forEach(query::setHint);
  }

  private <R> Page<R> readPageWithProjection(Specification<T> spec, Class<R> projectionType,
                                             Pageable pageable, TypedQuery<T> query) {
    return readPage(spec, pageable, query)
        .map(item -> projectionFactory.createProjection(projectionType, item));
  }

  private Page<T> readPage(Specification<T> spec,
                           Pageable pageable, TypedQuery<T> query) {
    if (log.isDebugEnabled()) {
      query.getHints()
          .forEach((key, value) -> log.info("apply query hints -> {} : {}", key, value));
    }
    return pageable.isUnpaged() ? new PageImpl<>(query.getResultList()) :
        readPage(query, getDomainClass(), pageable, spec);
  }
}
