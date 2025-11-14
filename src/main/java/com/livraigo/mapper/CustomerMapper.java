package com.livraigo.mapper;


import org.springframework.stereotype.Component;

import com.livraigo.dto.CustomerRequestDTO;
import com.livraigo.model.entity.Customer;

@Component
public class CustomerMapper {
    
    public Customer toEntity(CustomerRequestDTO dto) {
        Customer customer = new Customer();
        customer.setName(dto.getName());
        customer.setAddress(dto.getAddress());
        customer.setLatitude(dto.getLatitude());
        customer.setLongitude(dto.getLongitude());
        customer.setPreferredTimeSlot(dto.getPreferredTimeSlot());
        return customer;
    }
    
    public CustomerRequestDTO toDto(Customer customer) {
        CustomerRequestDTO dto = new CustomerRequestDTO();
        dto.setName(customer.getName());
        dto.setAddress(customer.getAddress());
        dto.setLatitude(customer.getLatitude());
        dto.setLongitude(customer.getLongitude());
        dto.setPreferredTimeSlot(customer.getPreferredTimeSlot());
        return dto;
    }
}
