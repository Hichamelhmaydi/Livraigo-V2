package com.livraigo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.livraigo.dto.TourRequestDTO;
import com.livraigo.dto.TourResponseDTO;
import com.livraigo.model.entity.Delivery;
import com.livraigo.model.entity.Tour;
import com.livraigo.service.interfaces.TourService;

import jakarta.validation.Valid;

@RestController
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
    public ResponseEntity<TourResponseDTO> getTourById(@PathVariable Long id) {
        logger.info("GET /api/tours/{}", id);
        TourResponseDTO tour = tourService.findById(id); // Now returns DTO
        return ResponseEntity.ok(tour);
    }

    @PostMapping
    public ResponseEntity<?> createTour(@Valid @RequestBody TourRequestDTO tourDTO) {
        logger.info("POST /api/tours");
        try {
            TourResponseDTO tourResponse = tourService.save(tourDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(tourResponse);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid algorithm in tour creation: {}", e.getMessage());
            return ResponseEntity.badRequest().body(
                new ErrorResponse("ALGORITHM_ERROR", e.getMessage())
            );
        } catch (Exception e) {
            logger.error("Error creating tour: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ErrorResponse("TOUR_CREATION_ERROR", e.getMessage())
            );
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTour(@PathVariable Long id, @Valid @RequestBody TourRequestDTO tourDTO) {
        logger.info("PUT /api/tours/{}", id);
        try {
            TourResponseDTO tour = tourService.update(id, tourDTO); // Now returns DTO
            return ResponseEntity.ok(tour);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid algorithm in tour update: {}", e.getMessage());
            return ResponseEntity.badRequest().body(
                new ErrorResponse("ALGORITHM_ERROR", e.getMessage())
            );
        } catch (Exception e) {
            logger.error("Error updating tour: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ErrorResponse("TOUR_UPDATE_ERROR", e.getMessage())
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTour(@PathVariable Long id) {
        logger.info("DELETE /api/tours/{}", id);
        tourService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/optimized")
    public ResponseEntity<List<Delivery>> getOptimizedTour(@PathVariable Long id) {
        logger.info("GET /api/tours/{}/optimized", id);
        List<Delivery> optimizedTour = tourService.getOptimizedTour(id);
        return ResponseEntity.ok(optimizedTour);
    }

    @GetMapping("/{id}/distance")
    public ResponseEntity<Double> getTotalDistance(@PathVariable Long id) {
        logger.info("GET /api/tours/{}/distance", id);
        Double distance = tourService.getTotalDistance(id);
        return ResponseEntity.ok(distance);
    }

    @PostMapping("/{id}/optimize")
    public ResponseEntity<TourResponseDTO> optimizeTour(@PathVariable Long id) {
        logger.info("POST /api/tours/{}/optimize", id);
        TourResponseDTO optimizedTour = tourService.optimizeTour(id); // Now returns DTO
        return ResponseEntity.ok(optimizedTour);
    }

    public static class ErrorResponse {
        private String error;
        private String message;

        public ErrorResponse(String error, String message) {
            this.error = error;
            this.message = message;
        }

        public String getError() { return error; }
        public void setError(String error) { this.error = error; }

        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }
}