package com.livraigo.dto;

import com.livraigo.model.entity.enums.VehicleType;
import lombok.Data;

@Data
public class VehicleRequestDTO {
    private String licensePlate;
    private VehicleType type;
    private Boolean available;
    
    // Getters et setters explicites
    public String getLicensePlate() {
        return licensePlate;
    }
    
    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }
    
    public VehicleType getType() {
        return type;
    }
    
    public void setType(VehicleType type) {
        this.type = type;
    }
    
    public Boolean getAvailable() {
        return available;
    }
    
    public void setAvailable(Boolean available) {
        this.available = available;
    }
}