package com.livraigo.model.entity;

import com.livraigo.model.entity.enums.DeliveryStatus;
import lombok.*;
import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "deliveries")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Delivery {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String address;
    
    @Column(nullable = false)
    private Double latitude;
    
    @Column(nullable = false)
    private Double longitude;
    
    @Column(nullable = false)
    private Double weight;
    
    @Column(nullable = false)
    private Double volume;
    
    private LocalTime preferredTimeStart;
    
    private LocalTime preferredTimeEnd;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliveryStatus status;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_id")
    private Tour tour;
    
    @Column(name = "delivery_order")
    private Integer order;
    
    // Getters et setters explicites
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public Double getLatitude() {
        return latitude;
    }
    
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    
    public Double getLongitude() {
        return longitude;
    }
    
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    
    public Double getWeight() {
        return weight;
    }
    
    public void setWeight(Double weight) {
        this.weight = weight;
    }
    
    public Double getVolume() {
        return volume;
    }
    
    public void setVolume(Double volume) {
        this.volume = volume;
    }
    
    public LocalTime getPreferredTimeStart() {
        return preferredTimeStart;
    }
    
    public void setPreferredTimeStart(LocalTime preferredTimeStart) {
        this.preferredTimeStart = preferredTimeStart;
    }
    
    public LocalTime getPreferredTimeEnd() {
        return preferredTimeEnd;
    }
    
    public void setPreferredTimeEnd(LocalTime preferredTimeEnd) {
        this.preferredTimeEnd = preferredTimeEnd;
    }
    
    public DeliveryStatus getStatus() {
        return status;
    }
    
    public void setStatus(DeliveryStatus status) {
        this.status = status;
    }
    
    public Tour getTour() {
        return tour;
    }
    
    public void setTour(Tour tour) {
        this.tour = tour;
    }
    
    public Integer getOrder() {
        return order;
    }
    
    public void setOrder(Integer order) {
        this.order = order;
    }
}