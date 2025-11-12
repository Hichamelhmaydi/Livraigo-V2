package com.livraigo.service.interfaces;

import com.livraigo.model.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface CustomerService {
    Customer save(Customer customer);
    Optional<Customer> findById(Long id);
    List<Customer> findAll();
    Page<Customer> findAll(Pageable pageable);
    Page<Customer> searchCustomers(String searchTerm, Pageable pageable);
    void delete(Long id);
    Customer update(Long id, Customer customer);
    List<Customer> findCustomersInArea(Double minLat, Double maxLat, Double minLng, Double maxLng);
}
