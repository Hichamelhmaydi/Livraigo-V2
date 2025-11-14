package com.livraigo.service.impl;

import com.livraigo.dto.VehicleRequestDTO;
import com.livraigo.mapper.VehicleMapper;
import com.livraigo.model.entity.Vehicle;
import com.livraigo.model.entity.enums.VehicleType;
import com.livraigo.repository.VehicleRepository;
import com.livraigo.service.interfaces.VehicleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;
import java.util.Optional;

public class VehicleServiceImpl implements VehicleService {
    
    private static final Logger logger = LoggerFactory.getLogger(VehicleServiceImpl.class);
    
    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;
    
    public VehicleServiceImpl(VehicleRepository vehicleRepository, VehicleMapper vehicleMapper) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleMapper = vehicleMapper;
    }
    
    @Override
    public List<Vehicle> findAll() {
        logger.info("Fetching all vehicles");
        return vehicleRepository.findAll();
    }
    
    @Override
    public Vehicle findById(Long id) {
        logger.info("Fetching vehicle with id: {}", id);
        Optional<Vehicle> vehicle = vehicleRepository.findById(id);
        return vehicle.orElseThrow(() -> new RuntimeException("Vehicle not found with id: " + id));
    }
    
    @Override
    public Vehicle save(VehicleRequestDTO vehicleDTO) {
        logger.info("Saving new vehicle");
        Vehicle vehicle = vehicleMapper.toEntity(vehicleDTO);
        return vehicleRepository.save(vehicle);
    }
    
    @Override
    public Vehicle update(Long id, VehicleRequestDTO vehicleDTO) {
        logger.info("Updating vehicle with id: {}", id);
        if (!vehicleRepository.existsById(id)) {
            throw new RuntimeException("Vehicle not found with id: " + id);
        }
        Vehicle vehicle = vehicleMapper.toEntity(vehicleDTO);
        vehicle.setId(id);
        return vehicleRepository.save(vehicle);
    }
    
    @Override
    public void deleteById(Long id) {
        logger.info("Deleting vehicle with id: {}", id);
        try {
            vehicleRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException("Vehicle not found with id: " + id);
        }
    }
    
    @Override
    public List<Vehicle> findByType(VehicleType type) {
        logger.info("Fetching vehicles of type: {}", type);
        return vehicleRepository.findByType(type);
    }
    
    @Override
    public List<Vehicle> findAvailableVehicles() {
        logger.info("Fetching available vehicles");
        return vehicleRepository.findByAvailable(true);
    }
}
