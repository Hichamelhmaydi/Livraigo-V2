package com.livraigo.dto;

import lombok.Data;

@Data
public class CustomerRequestDTO {
    private String name;
    private String address;
    private Double latitude;
    private Double longitude;
    private String preferredTimeSlot;
}
