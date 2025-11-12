package com.livraigo.repository;


import com.livraigo.model.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    
    // Méthodes dérivées
    Page<Customer> findByNameContainingIgnoreCase(String name, Pageable pageable);
    List<Customer> findByAddressContainingIgnoreCase(String address);
    
    // Requêtes personnalisées
    @Query("SELECT c FROM Customer c WHERE " +
           "LOWER(c.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(c.address) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<Customer> searchCustomers(@Param("searchTerm") String searchTerm, Pageable pageable);
    
    @Query("SELECT c FROM Customer c WHERE c.latitude BETWEEN :minLat AND :maxLat " +
           "AND c.longitude BETWEEN :minLng AND :maxLng")
    List<Customer> findCustomersInArea(@Param("minLat") Double minLat,@Param("maxLat") Double maxLat,@Param("minLng") Double minLng,@Param("maxLng") Double maxLng);
}
