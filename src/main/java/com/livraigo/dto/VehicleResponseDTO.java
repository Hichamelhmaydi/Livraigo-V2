package com.livraigo.dto;

import com.livraigo.model.entity.enums.VehicleType;
import lombok.Data;

@Data
public class VehicleResponseDTO {
    private Long id;
    private VehicleType type; // Utilisez VehicleType au lieu de String
    private String licensePlate;
    private Double maxWeight;
    private Double maxVolume;
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public VehicleType getType() { return type; }
    public void setType(VehicleType type) { this.type = type; }
    public String getLicensePlate() { return licensePlate; }
    public void setLicensePlate(String licensePlate) { this.licensePlate = licensePlate; }
    public Double getMaxWeight() { return maxWeight; }
    public void setMaxWeight(Double maxWeight) { this.maxWeight = maxWeight; }
    public Double getMaxVolume() { return maxVolume; }
    public void setMaxVolume(Double maxVolume) { this.maxVolume = maxVolume; }
}