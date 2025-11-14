package com.livraigo.dto;

import com.livraigo.model.entity.enums.DeliveryStatus;
import lombok.Data;
import java.time.LocalTime;

@Data
public class DeliveryRequestDTO {
    private String address;
    private Double latitude;
    private Double longitude;
    private Double weight;
    private Double volume;
    private LocalTime preferredTimeStart;
    private LocalTime preferredTimeEnd;
    private DeliveryStatus status;
    private Long tourId;
    private Integer order;
    
    // Getters et setters explicites
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
    
    public Long getTourId() {
        return tourId;
    }
    
    public void setTourId(Long tourId) {
        this.tourId = tourId;
    }
    
    public Integer getOrder() {
        return order;
    }
    
    public void setOrder(Integer order) {
        this.order = order;
    }
}
