package com.livraigo.dto;

import lombok.Data;
import java.util.List;

import com.livraigo.model.entity.enums.OptimizationAlgorithm;

@Data
public class OptimizationRequestDTO {
    private Long warehouseId;
    private List<Long> deliveryIds;
    private OptimizationAlgorithm algorithm;
}
