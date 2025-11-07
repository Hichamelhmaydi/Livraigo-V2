package com.livraigo.controller;

import com.livraigo.model.entity.Warehouse;
import com.livraigo.service.interfaces.WarehouseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

//@RestController
@RequestMapping("/api/warehouses")
public class WarehouseController {
    
    private static final Logger logger = LoggerFactory.getLogger(WarehouseController.class);
    
    private final WarehouseService warehouseService;
    
    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }
    
    @GetMapping
    public ResponseEntity<List<Warehouse>> getAllWarehouses() {
        logger.info("GET /api/warehouses");
        List<Warehouse> warehouses = warehouseService.findAll();
        return ResponseEntity.ok(warehouses);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Warehouse> getWarehouseById(@PathVariable Long id) {
        logger.info("GET /api/warehouses/{}", id);
        Warehouse warehouse = warehouseService.findById(id);
        return ResponseEntity.ok(warehouse);
    }
    
    @PostMapping
    public ResponseEntity<Warehouse> createWarehouse(@Valid @RequestBody Warehouse warehouse) {
        logger.info("POST /api/warehouses");
        Warehouse savedWarehouse = warehouseService.save(warehouse);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedWarehouse);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Warehouse> updateWarehouse(@PathVariable Long id, 
                                                    @Valid @RequestBody Warehouse warehouse) {
        logger.info("PUT /api/warehouses/{}", id);
        Warehouse updatedWarehouse = warehouseService.update(id, warehouse);
        return ResponseEntity.ok(updatedWarehouse);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWarehouse(@PathVariable Long id) {
        logger.info("DELETE /api/warehouses/{}", id);
        warehouseService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}