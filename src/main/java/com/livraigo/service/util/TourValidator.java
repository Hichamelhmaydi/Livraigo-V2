package com.livraigo.service.util;

import com.livraigo.model.entity.Delivery;
import com.livraigo.model.entity.Vehicle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class TourValidator {
    
    private static final Logger logger = LoggerFactory.getLogger(TourValidator.class);
    
    public void validateTourConstraints(Vehicle vehicle, List<Delivery> deliveries) {
        logger.info("Validating tour constraints for vehicle: {}", vehicle.getId());
        
        double totalWeight = deliveries.stream()
                .mapToDouble(Delivery::getWeight)
                .sum();
        
        double totalVolume = deliveries.stream()
                .mapToDouble(Delivery::getVolume)
                .sum();
        
        if (totalWeight > vehicle.getMaxWeight()) {
            throw new RuntimeException(
                String.format("Total weight %.2fkg exceeds vehicle capacity %.2fkg", 
                    totalWeight, vehicle.getMaxWeight())
            );
        }
        
        if (totalVolume > vehicle.getMaxVolume()) {
            throw new RuntimeException(
                String.format("Total volume %.2fm³ exceeds vehicle capacity %.2fm³", 
                    totalVolume, vehicle.getMaxVolume())
            );
        }
        
        if (deliveries.size() > vehicle.getMaxDeliveries()) {
            throw new RuntimeException(
                String.format("Number of deliveries %d exceeds vehicle capacity %d", 
                    deliveries.size(), vehicle.getMaxDeliveries())
            );
        }
        
        logger.info("Tour constraints validated successfully");
    }
}
