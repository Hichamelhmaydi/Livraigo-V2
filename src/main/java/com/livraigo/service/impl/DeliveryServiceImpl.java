package com.livraigo.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.livraigo.dto.DeliveryRequestDTO;
import com.livraigo.model.entity.Customer;
import com.livraigo.model.entity.Delivery;
import com.livraigo.model.entity.Tour;
import com.livraigo.model.entity.enums.DeliveryStatus;
import com.livraigo.repository.CustomerRepository;
import com.livraigo.repository.DeliveryRepository;
import com.livraigo.repository.TourRepository;
import com.livraigo.service.interfaces.DeliveryService;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    private static final Logger logger = LoggerFactory.getLogger(DeliveryServiceImpl.class);

    private final DeliveryRepository deliveryRepository;
    private final CustomerRepository customerRepository;
    private final TourRepository tourRepository;

    public DeliveryServiceImpl(DeliveryRepository deliveryRepository,
                               CustomerRepository customerRepository,
                               TourRepository tourRepository) {
        this.deliveryRepository = deliveryRepository;
        this.customerRepository = customerRepository;
        this.tourRepository = tourRepository;
    }

    @Override
    @Transactional
    public Delivery save(DeliveryRequestDTO deliveryDTO) {
        logger.info("=== START: Saving new delivery ===");
        logger.info("CustomerId from DTO: {}", deliveryDTO.getCustomerId());

        // 1. Validation obligatoire
        if (deliveryDTO.getCustomerId() == null) {
            throw new RuntimeException("CustomerId is required");
        }

        // 2. Vérifier que le client existe
        Customer customer = customerRepository.findById(deliveryDTO.getCustomerId())
                .orElseThrow(() -> new RuntimeException(
                        "Customer not found with id: " + deliveryDTO.getCustomerId()));
        
        logger.info("Found customer: {} (ID: {})", customer.getName(), customer.getId());

        // 3. Créer la livraison MANUELLEMENT
        Delivery delivery = new Delivery();
        delivery.setAddress(deliveryDTO.getAddress());
        delivery.setLatitude(deliveryDTO.getLatitude());
        delivery.setLongitude(deliveryDTO.getLongitude());
        delivery.setWeight(deliveryDTO.getWeight());
        delivery.setVolume(deliveryDTO.getVolume());
        delivery.setPreferredTimeStart(deliveryDTO.getPreferredTimeStart());
        delivery.setPreferredTimeEnd(deliveryDTO.getPreferredTimeEnd());
        delivery.setStatus(deliveryDTO.getStatus());
        delivery.setOrder(deliveryDTO.getOrder());
        
        // 4. DÉFINIR LE CUSTOMER - C'EST LA CLÉ !
        delivery.setCustomer(customer);

        // 5. Gestion optionnelle de la tour
        if (deliveryDTO.getTourId() != null) {
            Tour tour = tourRepository.findById(deliveryDTO.getTourId())
                    .orElseThrow(() -> new RuntimeException(
                            "Tour not found with id: " + deliveryDTO.getTourId()));
            delivery.setTour(tour);
        }

        // 6. VÉRIFICATION FINALE AVANT SAUVEGARDE
        if (delivery.getCustomer() == null) {
            throw new RuntimeException("Customer is NULL in delivery entity before save!");
        }
        
        logger.info("Delivery customer ID before save: {}", delivery.getCustomer().getId());

        // 7. Sauvegarde
        Delivery savedDelivery = deliveryRepository.save(delivery);
        
        logger.info("=== SUCCESS: Delivery saved with ID: {} ===", savedDelivery.getId());
        return savedDelivery;
    }

    @Override
    public List<Delivery> findAll() {
        logger.info("Fetching all deliveries");
        return deliveryRepository.findAll();
    }

    @Override
    public Delivery findById(Long id) {
        logger.info("Fetching delivery with id: {}", id);
        return deliveryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Delivery not found with id: " + id));
    }

    @Override
    @Transactional
    public Delivery update(Long id, DeliveryRequestDTO deliveryDTO) {
        logger.info("Updating delivery with id: {}", id);
        Delivery delivery = deliveryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Delivery not found with id: " + id));

        delivery.setAddress(deliveryDTO.getAddress());
        delivery.setLatitude(deliveryDTO.getLatitude());
        delivery.setLongitude(deliveryDTO.getLongitude());
        delivery.setWeight(deliveryDTO.getWeight());
        delivery.setVolume(deliveryDTO.getVolume());
        delivery.setPreferredTimeStart(deliveryDTO.getPreferredTimeStart());
        delivery.setPreferredTimeEnd(deliveryDTO.getPreferredTimeEnd());
        delivery.setStatus(deliveryDTO.getStatus());
        delivery.setOrder(deliveryDTO.getOrder());

        if (deliveryDTO.getCustomerId() != null) {
            Customer customer = customerRepository.findById(deliveryDTO.getCustomerId())
                    .orElseThrow(() -> new RuntimeException(
                            "Customer not found with id: " + deliveryDTO.getCustomerId()));
            delivery.setCustomer(customer);
        }

        if (deliveryDTO.getTourId() != null) {
            Tour tour = tourRepository.findById(deliveryDTO.getTourId())
                    .orElseThrow(() -> new RuntimeException(
                            "Tour not found with id: " + deliveryDTO.getTourId()));
            delivery.setTour(tour);
        }

        return deliveryRepository.save(delivery);
    }

    @Override
    public void deleteById(Long id) {
        logger.info("Deleting delivery with id: {}", id);
        try {
            deliveryRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException("Delivery not found with id: " + id);
        }
    }

    @Override
    public List<Delivery> findByStatus(DeliveryStatus status) {
        logger.info("Fetching deliveries with status: {}", status);
        return deliveryRepository.findByStatus(status);
    }

    @Override
    public List<Delivery> findUnassignedDeliveries() {
        logger.info("Fetching unassigned deliveries");
        return deliveryRepository.findUnassignedDeliveries();
    }
}