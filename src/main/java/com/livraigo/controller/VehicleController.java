package com.livraigo.controller;

import com.livraigo.dto.VehicleRequestDTO;
import com.livraigo.model.entity.Vehicle;
import com.livraigo.model.entity.enums.VehicleType;
import com.livraigo.service.interfaces.VehicleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private static final Logger logger = LoggerFactory.getLogger(VehicleController.class);

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping
    public ResponseEntity<List<Vehicle>> getAllVehicles() {
        logger.info("GET /api/vehicles");
        List<Vehicle> vehicles = vehicleService.findAll();
        return ResponseEntity.ok(vehicles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable Long id) {
        logger.info("GET /api/vehicles/{}", id);
        Vehicle vehicle = vehicleService.findById(id);
        return ResponseEntity.ok(vehicle);
    }

    @PostMapping
    public ResponseEntity<Vehicle> createVehicle(@Valid @RequestBody VehicleRequestDTO vehicleDTO) {
        logger.info("POST /api/vehicles");
        Vehicle vehicle = vehicleService.save(vehicleDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(vehicle);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vehicle> updateVehicle(@PathVariable Long id,
            @Valid @RequestBody VehicleRequestDTO vehicleDTO) {
        logger.info("PUT /api/vehicles/{}", id);
        Vehicle vehicle = vehicleService.update(id, vehicleDTO);
        return ResponseEntity.ok(vehicle);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        logger.info("DELETE /api/vehicles/{}", id);
        vehicleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Vehicle>> getVehiclesByType(@PathVariable VehicleType type) {
        logger.info("GET /api/vehicles/type/{}", type);
        List<Vehicle> vehicles = vehicleService.findByType(type);
        return ResponseEntity.ok(vehicles);
    }

    @GetMapping("/available")
    public ResponseEntity<List<Vehicle>> getAvailableVehicles() {
        logger.info("GET /api/vehicles/available");
        List<Vehicle> vehicles = vehicleService.findAvailableVehicles();
        return ResponseEntity.ok(vehicles);
    }
}