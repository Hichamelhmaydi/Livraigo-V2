package com.livraigo.repository;

import com.livraigo.model.entity.Delivery;
import com.livraigo.model.entity.enums.DeliveryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    
    List<Delivery> findByStatus(DeliveryStatus status);
    
    List<Delivery> findByTourId(Long tourId);
    
    @Query("SELECT d FROM Delivery d WHERE d.tour IS NULL")
    List<Delivery> findUnassignedDeliveries();
    
    @Query("SELECT SUM(d.weight) FROM Delivery d WHERE d.tour.id = :tourId")
    Double calculateTotalWeightByTour(@Param("tourId") Long tourId);
    
    @Query("SELECT SUM(d.volume) FROM Delivery d WHERE d.tour.id = :tourId")
    Double calculateTotalVolumeByTour(@Param("tourId") Long tourId);
}
