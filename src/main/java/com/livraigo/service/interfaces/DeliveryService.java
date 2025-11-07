package com.livraigo.service.interfaces;

import com.livraigo.dto.DeliveryRequestDTO;
import com.livraigo.model.entity.Delivery;
import com.livraigo.model.entity.enums.DeliveryStatus;

import java.util.List;

public interface DeliveryService {
    List<Delivery> findAll();
    Delivery findById(Long id);
    Delivery save(DeliveryRequestDTO deliveryDTO);
    Delivery update(Long id, DeliveryRequestDTO deliveryDTO);
    void deleteById(Long id);
    List<Delivery> findByStatus(DeliveryStatus status);
    List<Delivery> findUnassignedDeliveries();
}