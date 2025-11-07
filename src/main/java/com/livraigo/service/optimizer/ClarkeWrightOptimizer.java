package com.livraigo.service.optimizer;

import com.livraigo.model.entity.Delivery;
import com.livraigo.model.entity.Warehouse;
import com.livraigo.service.util.DistanceCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class ClarkeWrightOptimizer implements TourOptimizer {
    
    private static final Logger logger = LoggerFactory.getLogger(ClarkeWrightOptimizer.class);
    
    private final DistanceCalculator distanceCalculator;
    
    public ClarkeWrightOptimizer(DistanceCalculator distanceCalculator) {
        this.distanceCalculator = distanceCalculator;
    }
    
    @Override
    public List<Delivery> calculateOptimalTour(Warehouse warehouse, List<Delivery> deliveries) {
        logger.info("Calculating optimal tour using Clarke & Wright algorithm");
        
        if (deliveries.isEmpty()) {
            return new ArrayList<>();
        }
        
        if (deliveries.size() == 1) {
            return new ArrayList<>(deliveries);
        }
        
        // Calculate savings
        List<Savings> savingsList = calculateSavings(warehouse, deliveries);
        
        // Sort savings in descending order
        savingsList.sort((s1, s2) -> Double.compare(s2.savings, s1.savings));
        
        // Initialize routes
        List<Route> routes = new ArrayList<>();
        for (Delivery delivery : deliveries) {
            Route route = new Route();
            route.addDelivery(delivery);
            routes.add(route);
        }
        
        // Merge routes based on savings
        for (Savings savings : savingsList) {
            Route route1 = findRouteContaining(routes, savings.delivery1);
            Route route2 = findRouteContaining(routes, savings.delivery2);
            
            if (route1 != null && route2 != null && route1 != route2) {
                if (canMerge(route1, route2)) {
                    mergeRoutes(route1, route2);
                    routes.remove(route2);
                }
            }
        }
        
        // We should have one route left, get the deliveries in order
        if (routes.size() == 1) {
            return routes.get(0).getDeliveries();
        } else {
            logger.warn("Multiple routes found after optimization: {}", routes.size());
            // Fallback: return all deliveries in original order
            return new ArrayList<>(deliveries);
        }
    }
    
    private List<Savings> calculateSavings(Warehouse warehouse, List<Delivery> deliveries) {
        List<Savings> savingsList = new ArrayList<>();
        
        for (int i = 0; i < deliveries.size(); i++) {
            for (int j = i + 1; j < deliveries.size(); j++) {
                Delivery d1 = deliveries.get(i);
                Delivery d2 = deliveries.get(j);
                
                double distanceWarehouseD1 = distanceCalculator.calculateDistance(warehouse, d1);
                double distanceWarehouseD2 = distanceCalculator.calculateDistance(warehouse, d2);
                double distanceD1D2 = distanceCalculator.calculateDistance(d1, d2);
                
                double savings = distanceWarehouseD1 + distanceWarehouseD2 - distanceD1D2;
                savingsList.add(new Savings(d1, d2, savings));
            }
        }
        
        return savingsList;
    }
    
    private Route findRouteContaining(List<Route> routes, Delivery delivery) {
        for (Route route : routes) {
            if (route.contains(delivery)) {
                return route;
            }
        }
        return null;
    }
    
    private boolean canMerge(Route route1, Route route2) {
        return true;
    }
    
    private void mergeRoutes(Route route1, Route route2) {
        for (Delivery delivery : route2.getDeliveries()) {
            route1.addDelivery(delivery);
        }
    }
    
    private static class Savings {
        final Delivery delivery1;
        final Delivery delivery2;
        final double savings;
        
        Savings(Delivery delivery1, Delivery delivery2, double savings) {
            this.delivery1 = delivery1;
            this.delivery2 = delivery2;
            this.savings = savings;
        }
    }
    
    private static class Route {
        private final List<Delivery> deliveries;
        
        Route() {
            this.deliveries = new ArrayList<>();
        }
        
        void addDelivery(Delivery delivery) {
            deliveries.add(delivery);
        }
        
        boolean contains(Delivery delivery) {
            return deliveries.contains(delivery);
        }
        
        List<Delivery> getDeliveries() {
            return new ArrayList<>(deliveries);
        }
    }
}