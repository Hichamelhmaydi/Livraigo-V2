package com.livraigo.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.livraigo.dto.VehicleRequestDTO;
import com.livraigo.model.entity.Vehicle;
@Component
public class VehicleMapper {
    @Autowired
    private final ModelMapper modelMapper;

    public VehicleMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    
    public Vehicle toEntity(VehicleRequestDTO dto) {
        return modelMapper.map(dto, Vehicle.class);
    }
    
    public VehicleRequestDTO toDto(Vehicle entity) {
        return modelMapper.map(entity, VehicleRequestDTO.class);
    }
}
