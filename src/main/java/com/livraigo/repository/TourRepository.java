package com.livraigo.repository;

import com.livraigo.model.entity.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface TourRepository extends JpaRepository<Tour, Long> {
    
    List<Tour> findByDate(LocalDate date);
    
    List<Tour> findByVehicleId(Long vehicleId);
    
    @Query("SELECT t FROM Tour t WHERE t.date = :date AND t.vehicle.available = true")
    List<Tour> findToursByDateWithAvailableVehicles(@Param("date") LocalDate date);
}
