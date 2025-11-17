package com.livraigo.service.optimizer;

import com.livraigo.model.entity.Delivery;
import com.livraigo.model.entity.Vehicle;
import com.livraigo.model.entity.Warehouse;
import com.livraigo.model.entity.enums.OptimizationAlgorithm;

import java.util.List;


public interface TourOptimizer {
   List<Delivery> calculateOptimalTour(List<Delivery> deliveries, Warehouse warehouse, Vehicle vehicle);
    OptimizationAlgorithm getAlgorithm();
}
