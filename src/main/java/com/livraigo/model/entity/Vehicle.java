package com.livraigo.model.entity;

import com.livraigo.model.entity.enums.VehicleType;
import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "vehicles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String licensePlate;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleType type;
    
    @Column(nullable = false)
    private Double maxWeight;
    
    @Column(nullable = false)
    private Double maxVolume;
    
    @Column(nullable = false)
    private Integer maxDeliveries;
    
    private Boolean available;
    
    @PrePersist
    @PreUpdate
    public void setVehicleConstraints() {
        switch (this.type) {
            case BIKE:
                this.maxWeight = 50.0;
                this.maxVolume = 0.5;
                this.maxDeliveries = 15;
                break;
            case VAN:
                this.maxWeight = 1000.0;
                this.maxVolume = 8.0;
                this.maxDeliveries = 50;
                break;
            case TRUCK:
                this.maxWeight = 5000.0;
                this.maxVolume = 40.0;
                this.maxDeliveries = 100;
                break;
        }
        if (this.available == null) {
            this.available = true;
        }
    }
    
    // Getters et setters explicites
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
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
    
    public Double getMaxWeight() {
        return maxWeight;
    }
    
    public void setMaxWeight(Double maxWeight) {
        this.maxWeight = maxWeight;
    }
    
    public Double getMaxVolume() {
        return maxVolume;
    }
    
    public void setMaxVolume(Double maxVolume) {
        this.maxVolume = maxVolume;
    }
    
    public Integer getMaxDeliveries() {
        return maxDeliveries;
    }
    
    public void setMaxDeliveries(Integer maxDeliveries) {
        this.maxDeliveries = maxDeliveries;
    }
    
    public Boolean getAvailable() {
        return available;
    }
    
    public void setAvailable(Boolean available) {
        this.available = available;
    }
}