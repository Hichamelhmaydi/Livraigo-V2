package com.livraigo.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.livraigo.dto.DeliveryRequestDTO;
import com.livraigo.model.entity.Delivery;
import com.livraigo.model.entity.enums.DeliveryStatus;
import com.livraigo.service.interfaces.DeliveryService;

@RestController
@RequestMapping("/api/deliveries")
public class DeliveryController {
    
    private static final Logger logger = LoggerFactory.getLogger(DeliveryController.class);
    
    private final DeliveryService deliveryService;
    
    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }
    
    @GetMapping
    public ResponseEntity<List<Delivery>> getAllDeliveries() {
        logger.info("GET /api/deliveries");
        List<Delivery> deliveries = deliveryService.findAll();
        return ResponseEntity.ok(deliveries);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Delivery> getDeliveryById(@PathVariable Long id) {
        logger.info("GET /api/deliveries/{}", id);
        Delivery delivery = deliveryService.findById(id);
        return ResponseEntity.ok(delivery);
    }
    @PostMapping
    public ResponseEntity<Delivery> createDelivery(@Valid @RequestBody DeliveryRequestDTO deliveryDTO) {
        logger.info("POST /api/deliveries");
        Delivery delivery = deliveryService.save(deliveryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(delivery);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Delivery> updateDelivery(@PathVariable Long id, 
                                                  @Valid @RequestBody DeliveryRequestDTO deliveryDTO) {
        logger.info("PUT /api/deliveries/{}", id);
        Delivery delivery = deliveryService.update(id, deliveryDTO);
        return ResponseEntity.ok(delivery);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDelivery(@PathVariable Long id) {
        logger.info("DELETE /api/deliveries/{}", id);
        deliveryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Delivery>> getDeliveriesByStatus(@PathVariable DeliveryStatus status) {
        logger.info("GET /api/deliveries/status/{}", status);
        List<Delivery> deliveries = deliveryService.findByStatus(status);
        return ResponseEntity.ok(deliveries);
    }
    
    @GetMapping("/unassigned")
    public ResponseEntity<List<Delivery>> getUnassignedDeliveries() {
        logger.info("GET /api/deliveries/unassigned");
        List<Delivery> deliveries = deliveryService.findUnassignedDeliveries();
        return ResponseEntity.ok(deliveries);
    }
}
