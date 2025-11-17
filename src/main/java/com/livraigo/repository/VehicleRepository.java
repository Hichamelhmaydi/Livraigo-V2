package com.livraigo.repository;

import com.livraigo.model.entity.Vehicle;
import com.livraigo.model.entity.enums.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    
    List<Vehicle> findByType(VehicleType type);
    
    List<Vehicle> findByAvailable(Boolean available);
    
    @Query("SELECT v FROM Vehicle v WHERE v.available = true AND v.maxWeight >= :requiredWeight AND v.maxVolume >= :requiredVolume")
    List<Vehicle> findSuitableVehicles(@Param("requiredWeight") Double requiredWeight, 
                                      @Param("requiredVolume") Double requiredVolume);
}
