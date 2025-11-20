package com.livraigo.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.livraigo.dto.TourRequestDTO;
import com.livraigo.dto.TourResponseDTO;
import com.livraigo.mapper.TourMapper;
import com.livraigo.model.entity.Delivery;
import com.livraigo.model.entity.Tour;
import com.livraigo.model.entity.Vehicle;
import com.livraigo.model.entity.Warehouse;
import com.livraigo.model.entity.enums.OptimizationAlgorithm;
import com.livraigo.repository.DeliveryRepository;
import com.livraigo.repository.TourRepository;
import com.livraigo.repository.VehicleRepository;
import com.livraigo.repository.WarehouseRepository;
import com.livraigo.service.interfaces.TourService;
import com.livraigo.service.optimizer.AiOptimizer;
import com.livraigo.service.optimizer.ClarkeWrightOptimizer;
import com.livraigo.service.optimizer.NearestNeighborOptimizer;
import com.livraigo.service.optimizer.TourOptimizer;
import com.livraigo.service.util.TourValidator;

@Service
public class TourServiceImpl implements TourService {
    
    private static final Logger logger = LoggerFactory.getLogger(TourServiceImpl.class);
    
    private final TourRepository tourRepository;
    private final DeliveryRepository deliveryRepository;
    private final VehicleRepository vehicleRepository;
    private final WarehouseRepository warehouseRepository;
    private final NearestNeighborOptimizer nearestNeighborOptimizer;
    private final ClarkeWrightOptimizer clarkeWrightOptimizer;
    private final AiOptimizer aiOptimizer;
    private final TourValidator tourValidator;
    private final TourMapper tourMapper;
    
    public TourServiceImpl(TourRepository tourRepository,
                          DeliveryRepository deliveryRepository,
                          VehicleRepository vehicleRepository,
                          WarehouseRepository warehouseRepository,
                          NearestNeighborOptimizer nearestNeighborOptimizer,
                          ClarkeWrightOptimizer clarkeWrightOptimizer,
                          AiOptimizer aiOptimizer,
                          TourValidator tourValidator,
                          TourMapper tourMapper) {
        this.tourRepository = tourRepository;
        this.deliveryRepository = deliveryRepository;
        this.vehicleRepository = vehicleRepository;
        this.warehouseRepository = warehouseRepository;
        this.nearestNeighborOptimizer = nearestNeighborOptimizer;
        this.clarkeWrightOptimizer = clarkeWrightOptimizer;
        this.aiOptimizer = aiOptimizer;
        this.tourValidator = tourValidator;
        this.tourMapper = tourMapper;
    }
    
    @Override
    public List<Tour> findAll() {
        logger.info("Fetching all tours");
        return tourRepository.findAll();
    }
    
    @Override
    public TourResponseDTO findById(Long id) {
        logger.info("Fetching tour with id: {}", id);
        Optional<Tour> tour = tourRepository.findById(id);
        return tour.map(tourMapper::toResponseDTO)
                  .orElseThrow(() -> new RuntimeException("Tour not found with id: " + id));
    }
    
    @Override
    public TourResponseDTO save(TourRequestDTO tourDTO) {
        logger.info("Saving new tour");

        Vehicle vehicle = vehicleRepository.findById(tourDTO.getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
        Warehouse warehouse = warehouseRepository.findById(tourDTO.getWarehouseId())
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));

        List<Delivery> deliveries = tourDTO.getDeliveryIds().stream()
                .map(id -> deliveryRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Delivery not found with id: " + id)))
                .collect(Collectors.toList());

        tourValidator.validateTourConstraints(vehicle, deliveries);

        Tour tour = tourMapper.toEntity(tourDTO);

        tour.setVehicle(vehicle);
        tour.setWarehouse(warehouse);
        tour.setDeliveries(deliveries);

        tour = optimizeTour(tour);

        Tour savedTour = tourRepository.save(tour);
        return tourMapper.toResponseDTO(savedTour);
    }

    @Override
    public TourResponseDTO update(Long id, TourRequestDTO tourDTO) {
        logger.info("Updating tour with id: {}", id);
        if (!tourRepository.existsById(id)) {
            throw new RuntimeException("Tour not found with id: " + id);
        }
        
        Tour tour = tourMapper.toEntity(tourDTO);
        tour.setId(id);
        Tour updatedTour = tourRepository.save(tour);
        return tourMapper.toResponseDTO(updatedTour); // Return DTO
    }
    
    @Override
    public void deleteById(Long id) {
        logger.info("Deleting tour with id: {}", id);
        try {
            tourRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException("Tour not found with id: " + id);
        }
    }
    
    @Override
    public List<Delivery> getOptimizedTour(Long tourId) {
        logger.info("Getting optimized tour for tour id: {}", tourId);
        Tour tour = findEntityById(tourId);
        return tour.getDeliveries();
    }
    
    @Override
    public Double getTotalDistance(Long tourId) {
        logger.info("Calculating total distance for tour id: {}", tourId);
        Tour tour = findEntityById(tourId);
        return tour.getTotalDistance();
    }
    
    @Override
    public TourResponseDTO optimizeTour(Long tourId) {
        logger.info("Optimizing tour with id: {}", tourId);
        Tour tour = findEntityById(tourId);
        Tour optimizedTour = optimizeTour(tour);
        return tourMapper.toResponseDTO(optimizedTour); // Return DTO
    }
    
    // Méthode privée pour récupérer l'entité (utilisée en interne seulement)
    private Tour findEntityById(Long id) {
        Optional<Tour> tour = tourRepository.findById(id);
        return tour.orElseThrow(() -> new RuntimeException("Tour not found with id: " + id));
    }
    
    private Tour optimizeTour(Tour tour) {
        TourOptimizer optimizer = getOptimizer(tour.getAlgorithm());

        List<Delivery> optimizedDeliveries = optimizer.calculateOptimalTour(
            tour.getDeliveries(),  
            tour.getWarehouse(),    
            tour.getVehicle()      
        );

        for (int i = 0; i < optimizedDeliveries.size(); i++) {
            optimizedDeliveries.get(i).setOrder(i + 1);
            optimizedDeliveries.get(i).setTour(tour);
        }
        
        tour.setDeliveries(optimizedDeliveries);
        tour.setTotalDistance(calculateTotalDistance(tour));
        
        return tour;
    }
    
    private TourOptimizer getOptimizer(OptimizationAlgorithm algorithm) {
        switch (algorithm) {
            case NEAREST_NEIGHBOR:
                return nearestNeighborOptimizer;
            case CLARKE_WRIGHT:
                return clarkeWrightOptimizer;
            case AI_OPTIMIZER:
                if (aiOptimizer == null) {
                    throw new IllegalArgumentException("AI Optimizer is not available.");
                }
                return aiOptimizer;
            default:
                throw new IllegalArgumentException("Unknown optimization algorithm: " + algorithm);
        }
    }
    
    private Double calculateTotalDistance(Tour tour) {
        return 100.0;
    }
}