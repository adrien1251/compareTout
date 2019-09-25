package com.eltae.compareTout.converter;

import java.util.ArrayList;
import java.util.List;

public abstract class GenericsConverter<Entity, Dto> {
    public abstract Dto entityToDto(Entity entity);
    public abstract Entity dtoToEntity(Dto dto);

    public List<Dto> entityListToDtoList(List<Entity> entityList){
        List<Dto> dtoList = new ArrayList<>();
        for(Entity entity : entityList){
            dtoList.add(entityToDto(entity));
        }
        return dtoList;
    }

    public List<Entity> dtoListToEntityList(List<Dto> dtoList) {
        List<Entity> entityList = new ArrayList<>();
        for(Dto dto : dtoList){
            entityList.add(dtoToEntity(dto));
        }
        return entityList;
    }
}
