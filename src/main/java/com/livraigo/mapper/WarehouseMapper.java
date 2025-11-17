package com.livraigo.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.livraigo.model.entity.Warehouse;
@Component
public class WarehouseMapper {
    @Autowired
    private final ModelMapper modelMapper;

    public WarehouseMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    
    public Warehouse toEntity(Warehouse entity) {
        return modelMapper.map(entity, Warehouse.class);
    }
}
