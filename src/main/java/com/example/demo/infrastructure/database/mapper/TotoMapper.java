package com.example.demo.infrastructure.database.mapper;

import com.example.demo.domain.model.Toto;
import com.example.demo.infrastructure.database.entity.TotoEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TotoMapper {

    TotoEntity toEntity(Toto toto);

    Toto toDomain(TotoEntity totoEntity);
}
