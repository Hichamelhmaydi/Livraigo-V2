package com.livraigo.model.entity;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "delivery_history")
public class DeliveryHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    
    @ManyToOne
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
    
    private String address;
    private Double latitude;
    private Double longitude;
    private Double weight;
    private Double volume;
}