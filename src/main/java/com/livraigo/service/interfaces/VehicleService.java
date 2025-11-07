package com.livraigo.service.interfaces;

import com.livraigo.dto.VehicleRequestDTO;
import com.livraigo.model.entity.Vehicle;
import com.livraigo.model.entity.enums.VehicleType;

import java.util.List;

public interface VehicleService {
    List<Vehicle> findAll();
    Vehicle findById(Long id);
    Vehicle save(VehicleRequestDTO vehicleDTO);
    Vehicle update(Long id, VehicleRequestDTO vehicleDTO);
    void deleteById(Long id);
    List<Vehicle> findByType(VehicleType type);
    List<Vehicle> findAvailableVehicles();
}