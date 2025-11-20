package com.livraigo.dto;

import com.livraigo.model.entity.enums.OptimizationAlgorithm;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class TourResponseDTO {
    private Long id;
    private String name;
    private LocalDate date;
    private VehicleResponseDTO vehicle;
    private WarehouseResponseDTO warehouse;
    private OptimizationAlgorithm algorithm;
    private Double totalDistance;
    private List<DeliveryResponseDTO> deliveries;
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public VehicleResponseDTO getVehicle() { return vehicle; }
    public void setVehicle(VehicleResponseDTO vehicle) { this.vehicle = vehicle; }
    public WarehouseResponseDTO getWarehouse() { return warehouse; }
    public void setWarehouse(WarehouseResponseDTO warehouse) { this.warehouse = warehouse; }
    public OptimizationAlgorithm getAlgorithm() { return algorithm; }
    public void setAlgorithm(OptimizationAlgorithm algorithm) { this.algorithm = algorithm; }
    public Double getTotalDistance() { return totalDistance; }
    public void setTotalDistance(Double totalDistance) { this.totalDistance = totalDistance; }
    public List<DeliveryResponseDTO> getDeliveries() { return deliveries; }
    public void setDeliveries(List<DeliveryResponseDTO> deliveries) { this.deliveries = deliveries; }
}