package com.livraigo.service.util;

import com.livraigo.model.entity.Delivery;
import com.livraigo.model.entity.Warehouse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DistanceCalculator {
    
    private static final Logger logger = LoggerFactory.getLogger(DistanceCalculator.class);
    private static final double EARTH_RADIUS_KM = 6371.0;
    
    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        logger.debug("Calculating distance between ({}, {}) and ({}, {})", lat1, lon1, lat2, lon2);
        
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                  Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                  Math.sin(dLon / 2) * Math.sin(dLon / 2);
        
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        
        return EARTH_RADIUS_KM * c;
    }
    
    public double calculateDistance(Warehouse warehouse, Delivery delivery) {
        return calculateDistance(
            warehouse.getLatitude(),
            warehouse.getLongitude(),
            delivery.getLatitude(),
            delivery.getLongitude()
        );
    }
    
    public double calculateDistance(Delivery delivery1, Delivery delivery2) {
        return calculateDistance(
            delivery1.getLatitude(),
            delivery1.getLongitude(),
            delivery2.getLatitude(),
            delivery2.getLongitude()
        );
    }
}
