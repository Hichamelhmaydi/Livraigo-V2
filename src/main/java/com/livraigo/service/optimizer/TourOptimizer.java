package com.livraigo.service.optimizer;

import com.livraigo.model.entity.Delivery;
import com.livraigo.model.entity.Warehouse;

import java.util.List;

public interface TourOptimizer {
    List<Delivery> calculateOptimalTour(Warehouse warehouse, List<Delivery> deliveries);
}
