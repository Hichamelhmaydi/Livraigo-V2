package com.livraigo.dto;

import com.livraigo.model.entity.enums.DeliveryStatus;
import lombok.Data;

@Data
public class DeliveryResponseDTO {
    private Long id;
    private String address;
    private Double latitude;
    private Double longitude;
    private Integer order;
    private DeliveryStatus status; 
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }
    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
    public Integer getOrder() { return order; }
    public void setOrder(Integer order) { this.order = order; }
    public DeliveryStatus getStatus() { return status; }
    public void setStatus(DeliveryStatus status) { this.status = status; }
}