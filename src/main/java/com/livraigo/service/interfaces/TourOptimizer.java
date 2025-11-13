package com.livraigo.service.interfaces;

import java.util.List;

import com.livraigo.model.entity.Delivery;
import com.livraigo.model.entity.Warehouse;
import com.livraigo.model.entity.enums.OptimizationAlgorithm;

public interface  TourOptimizer {
    public OptimizationAlgorithm getAlgorithm();
    public List<Delivery> calculateOptimalTour(Warehouse warehouse, List<Delivery> deliveries);
}
