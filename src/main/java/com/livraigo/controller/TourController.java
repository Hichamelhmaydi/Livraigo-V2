package com.livraigo.controller;

import com.livraigo.dto.TourRequestDTO;
import com.livraigo.model.entity.Delivery;
import com.livraigo.model.entity.Tour;
import com.livraigo.service.interfaces.TourService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

//@RestController
@RequestMapping("/api/tours")
public class TourController {
    
    private static final Logger logger = LoggerFactory.getLogger(TourController.class);
    
    private final TourService tourService;
    
    public TourController(TourService tourService) {
        this.tourService = tourService;
    }
    
    @GetMapping
    public ResponseEntity<List<Tour>> getAllTours() {
        logger.info("GET /api/tours");
        List<Tour> tours = tourService.findAll();
        return ResponseEntity.ok(tours);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Tour> getTourById(@PathVariable Long id) {
        logger.info("GET /api/tours/{}", id);
        Tour tour = tourService.findById(id);
        return ResponseEntity.ok(tour);
    }
    
    @PostMapping
    public ResponseEntity<Tour> createTour(@Valid @RequestBody TourRequestDTO tourDTO) {
        logger.info("POST /api/tours");
        Tour tour = tourService.save(tourDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(tour);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Tour> updateTour(@PathVariable Long id, 
                                          @Valid @RequestBody TourRequestDTO tourDTO) {
        logger.info("PUT /api/tours/{}", id);
        Tour tour = tourService.update(id, tourDTO);
        return ResponseEntity.ok(tour);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTour(@PathVariable Long id) {
        logger.info("DELETE /api/tours/{}", id);
        tourService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/{id}/optimized-route")
    public ResponseEntity<List<Delivery>> getOptimizedTour(@PathVariable Long id) {
        logger.info("GET /api/tours/{}/optimized-route", id);
        List<Delivery> deliveries = tourService.getOptimizedTour(id);
        return ResponseEntity.ok(deliveries);
    }
    
    @GetMapping("/{id}/total-distance")
    public ResponseEntity<Double> getTotalDistance(@PathVariable Long id) {
        logger.info("GET /api/tours/{}/total-distance", id);
        Double distance = tourService.getTotalDistance(id);
        return ResponseEntity.ok(distance);
    }
    
    @PostMapping("/{id}/optimize")
    public ResponseEntity<Tour> optimizeTour(@PathVariable Long id) {
        logger.info("POST /api/tours/{}/optimize", id);
        Tour tour = tourService.optimizeTour(id);
        return ResponseEntity.ok(tour);
    }
}