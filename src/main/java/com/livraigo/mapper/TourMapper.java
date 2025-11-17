package com.livraigo.mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.livraigo.dto.TourRequestDTO;
import com.livraigo.model.entity.Delivery;
import com.livraigo.model.entity.Tour;
import com.livraigo.model.entity.Vehicle;
import com.livraigo.model.entity.Warehouse;

@Component
public class TourMapper {

    @Autowired
    private final ModelMapper modelMapper;

    public TourMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        this.modelMapper.getConfiguration().setAmbiguityIgnored(true);

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
}
