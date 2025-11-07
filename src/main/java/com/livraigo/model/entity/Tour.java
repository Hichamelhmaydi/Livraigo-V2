package com.livraigo.model.entity;

import com.livraigo.model.entity.enums.OptimizationAlgorithm;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tours")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tour {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    @NotNull(message = "La date de la tournée est obligatoire")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OptimizationAlgorithm algorithm;
    
    private Double totalDistance;
    
    @OneToMany(mappedBy = "tour", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("order ASC")
    @Builder.Default
    private List<Delivery> deliveries = new ArrayList<>();
    
    // Getters et setters explicites
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
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
    
    public Vehicle getVehicle() {
        return vehicle;
    }
    
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
    
    public Warehouse getWarehouse() {
        return warehouse;
    }
    
    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }
    
    public OptimizationAlgorithm getAlgorithm() {
        return algorithm;
    }
    
    public void setAlgorithm(OptimizationAlgorithm algorithm) {
        this.algorithm = algorithm;
    }
    
    public Double getTotalDistance() {
        return totalDistance;
    }
    
    public void setTotalDistance(Double totalDistance) {
        this.totalDistance = totalDistance;
    }
    
    public List<Delivery> getDeliveries() {
        return deliveries;
    }
    
    public void setDeliveries(List<Delivery> deliveries) {
        this.deliveries = deliveries;
    }
}