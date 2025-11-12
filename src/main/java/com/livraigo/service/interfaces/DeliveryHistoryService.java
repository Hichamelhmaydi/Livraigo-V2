package com.livraigo.service.interfaces;

import com.livraigo.model.entity.DeliveryHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.List;

public interface DeliveryHistoryService {
    DeliveryHistory save(DeliveryHistory deliveryHistory);
    List<DeliveryHistory> findByCustomerId(Long customerId);
    List<DeliveryHistory> findByTourId(Long tourId);
    Page<DeliveryHistory> findDeliveriesByDateRange(LocalDate startDate, LocalDate endDate, Pageable pageable);
    Page<DeliveryHistory> findDelayedDeliveries(Integer minDelay, Pageable pageable);
    List<Object[]> findAverageDelaysByCustomer();
    void createDeliveryHistoryFromCompletedTour(Long tourId);
}
