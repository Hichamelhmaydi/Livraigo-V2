package com.livraigo.repository;


import com.livraigo.model.entity.DeliveryHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DeliveryHistoryRepository extends JpaRepository<DeliveryHistory, Long> {
    
    List<DeliveryHistory> findByCustomerId(Long customerId);
    List<DeliveryHistory> findByTourId(Long tourId);
    
    @Query("SELECT dh FROM DeliveryHistory dh WHERE dh.deliveryDate BETWEEN :startDate AND :endDate")
    Page<DeliveryHistory> findDeliveriesByDateRange(@Param("startDate") LocalDate startDate,@Param("endDate") LocalDate endDate,Pageable pageable);
    
    @Query("SELECT dh FROM DeliveryHistory dh WHERE dh.delay > :minDelay")
    Page<DeliveryHistory> findDelayedDeliveries(@Param("minDelay") Integer minDelay, Pageable pageable);
    
    @Query("SELECT dh.customer.id, AVG(dh.delay) FROM DeliveryHistory dh GROUP BY dh.customer.id HAVING AVG(dh.delay) > 0")
    List<Object[]> findAverageDelaysByCustomer();
}