package com.livraigo.service.impl;


import com.livraigo.model.entity.*;
import com.livraigo.repository.DeliveryHistoryRepository;
import com.livraigo.repository.TourRepository;
import com.livraigo.service.interfaces.DeliveryHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.DayOfWeek;
import java.util.List;

@Service
public class DeliveryHistoryServiceImpl implements DeliveryHistoryService {
    
    private static final Logger logger = LoggerFactory.getLogger(DeliveryHistoryServiceImpl.class);
    
    private final DeliveryHistoryRepository deliveryHistoryRepository;
    private final TourRepository tourRepository;
    
    public DeliveryHistoryServiceImpl(DeliveryHistoryRepository deliveryHistoryRepository, 
                                    TourRepository tourRepository) {
        this.deliveryHistoryRepository = deliveryHistoryRepository;
        this.tourRepository = tourRepository;
    }
    
    @Override
    public DeliveryHistory save(DeliveryHistory deliveryHistory) {
        logger.info("Saving delivery history for customer: {}", deliveryHistory.getCustomer().getName());
        return deliveryHistoryRepository.save(deliveryHistory);
    }
    
    @Override
    public List<DeliveryHistory> findByCustomerId(Long customerId) {
        logger.info("Fetching delivery history for customer id: {}", customerId);
        return deliveryHistoryRepository.findByCustomerId(customerId);
    }
    
    @Override
    public List<DeliveryHistory> findByTourId(Long tourId) {
        logger.info("Fetching delivery history for tour id: {}", tourId);
        return deliveryHistoryRepository.findByTourId(tourId);
    }
    
    @Override
    public Page<DeliveryHistory> findDeliveriesByDateRange(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        logger.info("Fetching deliveries between {} and {}", startDate, endDate);
        return deliveryHistoryRepository.findDeliveriesByDateRange(startDate, endDate, pageable);
    }
    
    @Override
    public Page<DeliveryHistory> findDelayedDeliveries(Integer minDelay, Pageable pageable) {
        logger.info("Fetching deliveries with delay > {} minutes", minDelay);
        return deliveryHistoryRepository.findDelayedDeliveries(minDelay, pageable);
    }
    
    @Override
    public List<Object[]> findAverageDelaysByCustomer() {
        logger.info("Fetching average delays by customer");
        return deliveryHistoryRepository.findAverageDelaysByCustomer();
    }
    
    @Override
    @Transactional
    public void createDeliveryHistoryFromCompletedTour(Long tourId) {
        logger.info("Creating delivery history from completed tour id: {}", tourId);
        Tour tour = tourRepository.findById(tourId)
            .orElseThrow(() -> new RuntimeException("Tour not found with id: " + tourId));
        
        if (tour.getDeliveries() != null) {
            for (Delivery delivery : tour.getDeliveries()) {
                DeliveryHistory history = new DeliveryHistory();
                history.setCustomer(delivery.getCustomer());
                history.setTour(tour);
                history.setDeliveryDate(LocalDate.now());
                history.setPlannedTime(LocalDateTime.now()); 
                history.setActualTime(LocalDateTime.now());
                history.setDelay(0); 
                history.setDayOfWeek(DayOfWeek.from(LocalDate.now()).toString());
                
                history.setAddress(delivery.getAddress());
                history.setLatitude(delivery.getLatitude());
                history.setLongitude(delivery.getLongitude());
                history.setWeight(delivery.getWeight());
                history.setVolume(delivery.getVolume());
                
                deliveryHistoryRepository.save(history);
            }
        }
    }
}
