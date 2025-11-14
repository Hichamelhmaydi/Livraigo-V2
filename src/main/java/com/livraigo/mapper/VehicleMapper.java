package com.livraigo.mapper;

import com.livraigo.dto.VehicleRequestDTO;
import com.livraigo.model.entity.Vehicle;
import org.modelmapper.ModelMapper;

public class VehicleMapper {
    
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
