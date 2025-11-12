package com.livraigo.model.entity;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalDate;

@Entity
@Table(name = "delivery_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryHistory {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_id", nullable = false)
    private Tour tour;
    
    @Column(name = "delivery_date")
    private LocalDate deliveryDate;
    
    @Column(name = "planned_time")
    private LocalDateTime plannedTime;
    
    @Column(name = "actual_time")
    private LocalDateTime actualTime;
    
    private Integer delay; 
    
    @Column(name = "day_of_week")
    private String dayOfWeek;
    
    // Informations de la livraison
    private String address;
    private Double latitude;
    private Double longitude;
    private Double weight;
    private Double volume;
    
    // Getters et setters explicites
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Customer getCustomer() {
        return customer;
    }
    
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    public Tour getTour() {
        return tour;
    }
    
    public void setTour(Tour tour) {
        this.tour = tour;
    }
    
    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }
    
    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
    
    public LocalDateTime getPlannedTime() {
        return plannedTime;
    }
    
    public void setPlannedTime(LocalDateTime plannedTime) {
        this.plannedTime = plannedTime;
    }
    
    public LocalDateTime getActualTime() {
        return actualTime;
    }
    
    public void setActualTime(LocalDateTime actualTime) {
        this.actualTime = actualTime;
    }
    
    public Integer getDelay() {
        return delay;
    }
    
    public void setDelay(Integer delay) {
        this.delay = delay;
    }
    
    public String getDayOfWeek() {
        return dayOfWeek;
    }
    
    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
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
}