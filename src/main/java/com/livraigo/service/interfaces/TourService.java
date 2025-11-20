package com.livraigo.service.interfaces;

import com.livraigo.dto.TourRequestDTO;
import com.livraigo.dto.TourResponseDTO;
import com.livraigo.model.entity.Delivery;
import com.livraigo.model.entity.Tour;

import java.util.List;

public interface TourService {
    
    List<Tour> findAll();
    TourResponseDTO findById(Long id); // Change to DTO
    TourResponseDTO save(TourRequestDTO tourDTO);
    TourResponseDTO update(Long id, TourRequestDTO tourDTO); // Change to DTO
    void deleteById(Long id);
    List<Delivery> getOptimizedTour(Long tourId);
    Double getTotalDistance(Long tourId);
    TourResponseDTO optimizeTour(Long tourId); // Change to DTO
}