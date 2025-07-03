package com.jobintechtracking.app.mappers;

public abstract class AbstractEntityDtoModelMapper<E,D> {
    public abstract D convertToDTO(E entity);
    public abstract E convertToEntity(D dto);
}
