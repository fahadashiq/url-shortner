package com.vi.urlshortner.service.dto.mapper;

import java.util.List;

/**
 * Generic Interface for mapping from DTO to entity and vice versa.
 * @param <D> DTO
 * @param <E> ENTITY
 */
public interface DtoMapper<D, E>
{
  E toEntity(D dto);

  D toDto(E entity);

  List<E> toEntity(List<D> dto);

  List<D> toDto(List<E> entity);
}
