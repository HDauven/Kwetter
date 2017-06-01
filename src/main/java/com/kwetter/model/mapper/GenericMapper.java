package com.kwetter.model.mapper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by hein on 5/19/17.
 */
public abstract class GenericMapper<ENTITY, DTO> {

    public abstract DTO to (ENTITY entity);

    public List<DTO> to(List<ENTITY> froms) {
        return froms.stream().map(this::to).collect(Collectors.toList());
    }
}
