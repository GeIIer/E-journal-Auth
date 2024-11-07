package org.example.service_auth.mappers;

import java.util.List;

public interface CommonMapper<DTO, E> {
    DTO toDto(E entity);
    List<DTO> toDtoList(List<E> entityList);
    E toEntity(DTO dto);
    List<E> toEntityList(List<DTO> dtoList);
}
