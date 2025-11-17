package com.livraigo.service.optimizer;

import com.livraigo.model.entity.Delivery;
import com.livraigo.model.entity.Vehicle;
import com.livraigo.model.entity.Warehouse;
import com.livraigo.model.entity.enums.OptimizationAlgorithm;
import com.livraigo.service.util.DistanceCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class NearestNeighborOptimizer implements TourOptimizer {
    
    private static final Logger logger = LoggerFactory.getLogger(NearestNeighborOptimizer.class);
    
    private final DistanceCalculator distanceCalculator;
    
    public NearestNeighborOptimizer(DistanceCalculator distanceCalculator) {
        this.distanceCalculator = distanceCalculator;
    }
    
    @Override
    public List<Delivery> calculateOptimalTour(List<Delivery> deliveries, Warehouse warehouse, Vehicle vehicle) {
        logger.info("Calculating optimal tour using Nearest Neighbor algorithm");
        
        if (deliveries.isEmpty()) {
            return new ArrayList<>();
        }
        
        List<Delivery> unvisited = new ArrayList<>(deliveries);
        List<Delivery> optimizedRoute = new ArrayList<>();
        
        Delivery currentLocation = findClosestToWarehouse(warehouse, unvisited);
        optimizedRoute.add(currentLocation);
        unvisited.remove(currentLocation);
        
        while (!unvisited.isEmpty()) {
            Delivery nearest = findNearestDelivery(currentLocation, unvisited);
            optimizedRoute.add(nearest);
            unvisited.remove(nearest);
            currentLocation = nearest;
        }
        
        logger.info("Nearest Neighbor optimization completed. Route length: {}", optimizedRoute.size());
        return optimizedRoute;
    }
    
    private Delivery findClosestToWarehouse(Warehouse warehouse, List<Delivery> deliveries) {
        Delivery closest = null;
        double minDistance = Double.MAX_VALUE;
        
        for (Delivery delivery : deliveries) {
            double distance = distanceCalculator.calculateDistance(warehouse, delivery);
            if (distance < minDistance) {
                minDistance = distance;
                closest = delivery;
            }
        }
        
        return closest;
    }
    
    private Delivery findNearestDelivery(Delivery current, List<Delivery> deliveries) {
        Delivery nearest = null;
        double minDistance = Double.MAX_VALUE;
        
        for (Delivery delivery : deliveries) {
            double distance = distanceCalculator.calculateDistance(current, delivery);
            if (distance < minDistance) {
                minDistance = distance;
                nearest = delivery;
            }
        }
        
        return nearest;
    }
    @Override
    public OptimizationAlgorithm getAlgorithm() {
        return OptimizationAlgorithm.NEAREST_NEIGHBOR;
    }

}
