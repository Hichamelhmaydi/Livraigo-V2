package com.livraigo.dto;

import com.livraigo.model.entity.enums.OptimizationAlgorithm;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Data
public class TourRequestDTO {
    private String name;
    @NotNull(message = "La date de la tournée est obligatoire")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private Long vehicleId;
    private Long warehouseId;
    private OptimizationAlgorithm algorithm;
    private List<Long> deliveryIds;
    
    // Getters et setters explicites pour s'assurer qu'ils existent
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public Long getVehicleId() {
        return vehicleId;
    }
    
    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }
    
    public Long getWarehouseId() {
        return warehouseId;
    }
    
    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }
    
    public OptimizationAlgorithm getAlgorithm() {
        return algorithm;
    }
    
    public void setAlgorithm(OptimizationAlgorithm algorithm) {
        this.algorithm = algorithm;
    }
    
    public List<Long> getDeliveryIds() {
        return deliveryIds;
    }
    
    public void setDeliveryIds(List<Long> deliveryIds) {
        this.deliveryIds = deliveryIds;
    }
}