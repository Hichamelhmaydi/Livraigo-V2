package com.livraigo.mapper;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.livraigo.dto.DeliveryRequestDTO;
import com.livraigo.model.entity.Delivery;

@Component
public class DeliveryMapper {

    private final ModelMapper modelMapper;

    public DeliveryMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        
        // Configuration pour ignorer les relations lors du mapping
        this.modelMapper.getConfiguration()
            .setSkipNullEnabled(true)
            .setPropertyCondition(Conditions.isNotNull());
    }

    public Delivery toEntity(DeliveryRequestDTO dto) {
        Delivery delivery = new Delivery();
        
        // Mapping manuel des champs simples
        delivery.setAddress(dto.getAddress());
        delivery.setLatitude(dto.getLatitude());
        delivery.setLongitude(dto.getLongitude());
        delivery.setWeight(dto.getWeight());
        delivery.setVolume(dto.getVolume());
        delivery.setPreferredTimeStart(dto.getPreferredTimeStart());
        delivery.setPreferredTimeEnd(dto.getPreferredTimeEnd());
        delivery.setStatus(dto.getStatus());
        delivery.setOrder(dto.getOrder());
        
        // Les relations (customer, tour) seront gérées dans le service
        return delivery;
    }

    public DeliveryRequestDTO toDto(Delivery entity) {
        DeliveryRequestDTO dto = new DeliveryRequestDTO();
        
        dto.setAddress(entity.getAddress());
        dto.setLatitude(entity.getLatitude());
        dto.setLongitude(entity.getLongitude());
        dto.setWeight(entity.getWeight());
        dto.setVolume(entity.getVolume());
        dto.setPreferredTimeStart(entity.getPreferredTimeStart());
        dto.setPreferredTimeEnd(entity.getPreferredTimeEnd());
        dto.setStatus(entity.getStatus());
        dto.setOrder(entity.getOrder());

        if (entity.getCustomer() != null) {
            dto.setCustomerId(entity.getCustomer().getId());
        }
        if (entity.getTour() != null) {
            dto.setTourId(entity.getTour().getId());
        }

        return dto;
    }
}