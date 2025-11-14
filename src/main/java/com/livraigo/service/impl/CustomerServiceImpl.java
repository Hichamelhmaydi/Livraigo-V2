package com.livraigo.service.impl;


import com.livraigo.model.entity.Customer;
import com.livraigo.repository.CustomerRepository;
import com.livraigo.service.interfaces.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    
    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
    
    private final CustomerRepository customerRepository;
    
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    
    @Override
    public Customer save(Customer customer) {
        logger.info("Saving new customer: {}", customer.getName());
        return customerRepository.save(customer);
    }
    
    @Override
    public Optional<Customer> findById(Long id) {
        logger.info("Fetching customer with id: {}", id);
        return customerRepository.findById(id);
    }
    
    @Override
    public List<Customer> findAll() {
        logger.info("Fetching all customers");
        return customerRepository.findAll();
    }
    
    @Override
    public Page<Customer> findAll(Pageable pageable) {
        logger.info("Fetching customers with pagination");
        return customerRepository.findAll(pageable);
    }
    
    @Override
    public Page<Customer> searchCustomers(String searchTerm, Pageable pageable) {
        logger.info("Searching customers with term: {}", searchTerm);
        return customerRepository.searchCustomers(searchTerm, pageable);
    }
    
    @Override
    public void delete(Long id) {
        logger.info("Deleting customer with id: {}", id);
        try {
            customerRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException("Customer not found with id: " + id);
        }
    }
    
    @Override
    public Customer update(Long id, Customer customer) {
        logger.info("Updating customer with id: {}", id);
        Customer existingCustomer = customerRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
        
        existingCustomer.setName(customer.getName());
        existingCustomer.setAddress(customer.getAddress());
        existingCustomer.setLatitude(customer.getLatitude());
        existingCustomer.setLongitude(customer.getLongitude());
        existingCustomer.setPreferredTimeSlot(customer.getPreferredTimeSlot());
        
        return customerRepository.save(existingCustomer);
    }
    
    @Override
    public List<Customer> findCustomersInArea(Double minLat, Double maxLat, Double minLng, Double maxLng) {
        logger.info("Finding customers in area: lat[{}, {}], lng[{}, {}]", minLat, maxLat, minLng, maxLng);
        return customerRepository.findCustomersInArea(minLat, maxLat, minLng, maxLng);
    }
}
