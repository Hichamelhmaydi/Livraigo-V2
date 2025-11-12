package com.livraigo.model.entity;

import lombok.Data;
import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String address;
    
    @Column(nullable = false)
    private Double latitude;
    
    @Column(nullable = false)
    private Double longitude;
    
    @Column(name = "preferred_time_slot")
    private String preferredTimeSlot; 
    
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Delivery> deliveries;
}