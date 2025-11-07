package com.livraigo.mapper;

import com.livraigo.dto.DeliveryRequestDTO;
import com.livraigo.model.entity.Delivery;
import org.modelmapper.ModelMapper;

public class DeliveryMapper {
    
    private final ModelMapper modelMapper;

    public DeliveryMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    
    public Delivery toEntity(DeliveryRequestDTO dto) {
        return modelMapper.map(dto, Delivery.class);
    }
    
    public DeliveryRequestDTO toDto(Delivery entity) {
        return modelMapper.map(entity, DeliveryRequestDTO.class);
    }
}