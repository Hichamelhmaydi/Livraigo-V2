package com.livraigo.service.interfaces;

import com.livraigo.model.entity.Warehouse;

import java.util.List;

public interface WarehouseService {
    List<Warehouse> findAll();
    Warehouse findById(Long id);
    Warehouse save(Warehouse warehouse);
    Warehouse update(Long id, Warehouse warehouse);
    void deleteById(Long id);
}