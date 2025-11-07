package com.livraigo.mapper;

import com.livraigo.model.entity.Warehouse;
import org.modelmapper.ModelMapper;

public class WarehouseMapper {
    
    private final ModelMapper modelMapper;

    public WarehouseMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    
    public Warehouse toEntity(Warehouse entity) {
        return modelMapper.map(entity, Warehouse.class);
    }
}