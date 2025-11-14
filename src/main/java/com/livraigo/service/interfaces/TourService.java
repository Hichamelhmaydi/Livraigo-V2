package com.livraigo.service.interfaces;

import com.livraigo.dto.TourRequestDTO;
import com.livraigo.model.entity.Delivery;
import com.livraigo.model.entity.Tour;

import java.util.List;

public interface TourService {
    List<Tour> findAll();
    Tour findById(Long id);
    Tour save(TourRequestDTO tourDTO);
    Tour update(Long id, TourRequestDTO tourDTO);
    void deleteById(Long id);
    List<Delivery> getOptimizedTour(Long tourId);
    Double getTotalDistance(Long tourId);
    Tour optimizeTour(Long tourId);
}
