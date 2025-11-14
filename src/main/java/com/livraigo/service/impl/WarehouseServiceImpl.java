package com.livraigo.service.impl;

import com.livraigo.mapper.WarehouseMapper;
import com.livraigo.model.entity.Warehouse;
import com.livraigo.repository.WarehouseRepository;
import com.livraigo.service.interfaces.WarehouseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;
import java.util.Optional;

public class WarehouseServiceImpl implements WarehouseService {
    
    private static final Logger logger = LoggerFactory.getLogger(WarehouseServiceImpl.class);
    
    private final WarehouseRepository warehouseRepository;
    private final WarehouseMapper warehouseMapper;
    
    public WarehouseServiceImpl(WarehouseRepository warehouseRepository, WarehouseMapper warehouseMapper) {
        this.warehouseRepository = warehouseRepository;
        this.warehouseMapper = warehouseMapper;
    }
    
    @Override
    public List<Warehouse> findAll() {
        logger.info("Fetching all warehouses");
        return warehouseRepository.findAll();
    }
    
    @Override
    public Warehouse findById(Long id) {
        logger.info("Fetching warehouse with id: {}", id);
        Optional<Warehouse> warehouse = warehouseRepository.findById(id);
        return warehouse.orElseThrow(() -> new RuntimeException("Warehouse not found with id: " + id));
    }
    
    @Override
    public Warehouse save(Warehouse warehouse) {
        logger.info("Saving new warehouse");
        return warehouseRepository.save(warehouse);
    }
    
    @Override
    public Warehouse update(Long id, Warehouse warehouse) {
        logger.info("Updating warehouse with id: {}", id);
        if (!warehouseRepository.existsById(id)) {
            throw new RuntimeException("Warehouse not found with id: " + id);
        }
        warehouse.setId(id);
        return warehouseRepository.save(warehouse);
    }
    
    @Override
    public void deleteById(Long id) {
        logger.info("Deleting warehouse with id: {}", id);
        try {
            warehouseRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException("Warehouse not found with id: " + id);
        }
    }
}
