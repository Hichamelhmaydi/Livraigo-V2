package com.livraigo.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.livraigo.dto.*;
import com.livraigo.model.entity.Delivery;
import com.livraigo.model.entity.Tour;
import com.livraigo.model.entity.Vehicle;
import com.livraigo.model.entity.Warehouse;

@Component
public class TourMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public TourMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.modelMapper.getConfiguration().setAmbiguityIgnored(true);

        configureRequestToEntityMapping();
    }

    private void configureRequestToEntityMapping() {
        TypeMap<TourRequestDTO, Tour> typeMap = this.modelMapper.createTypeMap(TourRequestDTO.class, Tour.class);

        typeMap.addMappings(mapper -> {
            mapper.skip(Tour::setId);
            mapper.map(TourRequestDTO::getName, Tour::setName);
            mapper.map(TourRequestDTO::getDate, Tour::setDate);
            mapper.map(TourRequestDTO::getAlgorithm, Tour::setAlgorithm);

            // Vehicle
            mapper.<Long>map(TourRequestDTO::getVehicleId, (dest, id) -> {
                if (id != null) {
                    Vehicle vehicle = new Vehicle();
                    vehicle.setId(id);
                    dest.setVehicle(vehicle);
                }
            });

            // Warehouse
            mapper.<Long>map(TourRequestDTO::getWarehouseId, (dest, id) -> {
                if (id != null) {
                    Warehouse warehouse = new Warehouse();
                    warehouse.setId(id);
                    dest.setWarehouse(warehouse);
                }
            });

            // Deliveries
            mapper.<List<Long>>map(TourRequestDTO::getDeliveryIds, (dest, ids) -> {
                if (ids != null && !ids.isEmpty()) {
                    List<Delivery> deliveries = new ArrayList<>();
                    for (Long id : ids) {
                        Delivery delivery = new Delivery();
                        delivery.setId(id);
                        deliveries.add(delivery);
                    }
                    dest.setDeliveries(deliveries);
                } else {
                    dest.setDeliveries(new ArrayList<>());
                }
            });
        });
    }

    public Tour toEntity(TourRequestDTO dto) {
        return modelMapper.map(dto, Tour.class);
    }

    public TourResponseDTO toResponseDTO(Tour tour) {
        if (tour == null) {
            return null;
        }
        
        TourResponseDTO responseDTO = new TourResponseDTO();
        responseDTO.setId(tour.getId());
        responseDTO.setName(tour.getName());
        responseDTO.setDate(tour.getDate());
        responseDTO.setAlgorithm(tour.getAlgorithm());
        responseDTO.setTotalDistance(tour.getTotalDistance());
        
        // Mapper vehicle manuellement
        if (tour.getVehicle() != null) {
            responseDTO.setVehicle(toVehicleResponseDTO(tour.getVehicle()));
        }
        
        // Mapper warehouse manuellement
        if (tour.getWarehouse() != null) {
            responseDTO.setWarehouse(toWarehouseResponseDTO(tour.getWarehouse()));
        }
        
        // Mapper deliveries manuellement pour éviter les proxys
        if (tour.getDeliveries() != null) {
            List<DeliveryResponseDTO> deliveryDTOs = tour.getDeliveries().stream()
                .map(this::toDeliveryResponseDTO)
                .collect(Collectors.toList());
            responseDTO.setDeliveries(deliveryDTOs);
        }
        
        return responseDTO;
    }

    private VehicleResponseDTO toVehicleResponseDTO(Vehicle vehicle) {
        if (vehicle == null) {
            return null;
        }
        
        VehicleResponseDTO dto = new VehicleResponseDTO();
        dto.setId(vehicle.getId());
        dto.setType(vehicle.getType()); // VehicleType, pas de conversion en String
        dto.setLicensePlate(vehicle.getLicensePlate());
        dto.setMaxWeight(vehicle.getMaxWeight());
        dto.setMaxVolume(vehicle.getMaxVolume());
        // Ne pas utiliser getStatus() si Vehicle n'a pas ce champ
        return dto;
    }

    private WarehouseResponseDTO toWarehouseResponseDTO(Warehouse warehouse) {
        if (warehouse == null) {
            return null;
        }
        
        WarehouseResponseDTO dto = new WarehouseResponseDTO();
        dto.setId(warehouse.getId());
        dto.setName(warehouse.getName());
        dto.setAddress(warehouse.getAddress());
        dto.setLatitude(warehouse.getLatitude());
        dto.setLongitude(warehouse.getLongitude());
        return dto;
    }

    private DeliveryResponseDTO toDeliveryResponseDTO(Delivery delivery) {
        if (delivery == null) {
            return null;
        }
        
        DeliveryResponseDTO dto = new DeliveryResponseDTO();
        dto.setId(delivery.getId());
        dto.setAddress(delivery.getAddress());
        dto.setLatitude(delivery.getLatitude());
        dto.setLongitude(delivery.getLongitude());
        dto.setOrder(delivery.getOrder());
        dto.setStatus(delivery.getStatus()); 
        return dto;
    }
}