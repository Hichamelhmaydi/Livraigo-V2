package com.livraigo.controller;


import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.livraigo.model.entity.DeliveryHistory;
import com.livraigo.service.interfaces.DeliveryHistoryService;

@RestController
@RequestMapping("/api/delivery-history")
public class DeliveryHistoryController {
    
    private static final Logger logger = LoggerFactory.getLogger(DeliveryHistoryController.class);
    
    private final DeliveryHistoryService deliveryHistoryService;
    
    public DeliveryHistoryController(DeliveryHistoryService deliveryHistoryService) {
        this.deliveryHistoryService = deliveryHistoryService;
    }
    
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<DeliveryHistory>> getHistoryByCustomer(@PathVariable Long customerId) {
        logger.info("Fetching delivery history for customer id: {}", customerId);
        List<DeliveryHistory> history = deliveryHistoryService.findByCustomerId(customerId);
        return ResponseEntity.ok(history);
    }
    
    @GetMapping("/tour/{tourId}")
    public ResponseEntity<List<DeliveryHistory>> getHistoryByTour(@PathVariable Long tourId) {
        logger.info("Fetching delivery history for tour id: {}", tourId);
        List<DeliveryHistory> history = deliveryHistoryService.findByTourId(tourId);
        return ResponseEntity.ok(history);
    }
    
    @GetMapping("/date-range")
    public ResponseEntity<Page<DeliveryHistory>> getDeliveriesByDateRange(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        logger.info("Fetching deliveries between {} and {}", startDate, endDate);
        Pageable pageable = PageRequest.of(page, size);
        Page<DeliveryHistory> deliveries = deliveryHistoryService
            .findDeliveriesByDateRange(startDate, endDate, pageable);
        return ResponseEntity.ok(deliveries);
    }
    
    @GetMapping("/delayed")
    public ResponseEntity<Page<DeliveryHistory>> getDelayedDeliveries(
            @RequestParam(defaultValue = "15") Integer minDelay,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        logger.info("Fetching deliveries with delay > {} minutes", minDelay);
        Pageable pageable = PageRequest.of(page, size);
        Page<DeliveryHistory> deliveries = deliveryHistoryService
            .findDelayedDeliveries(minDelay, pageable);
        return ResponseEntity.ok(deliveries);
    }
    
    @PostMapping("/from-tour/{tourId}")
    public ResponseEntity<Void> createHistoryFromTour(@PathVariable Long tourId) {
        logger.info("Creating delivery history from tour id: {}", tourId);
        deliveryHistoryService.createDeliveryHistoryFromCompletedTour(tourId);
        return ResponseEntity.ok().build();
    }
}
