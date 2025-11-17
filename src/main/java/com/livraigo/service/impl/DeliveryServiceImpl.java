package com.livraigo.service.impl;

import com.livraigo.dto.DeliveryRequestDTO;
import com.livraigo.mapper.DeliveryMapper;
import com.livraigo.model.entity.Delivery;
import com.livraigo.model.entity.enums.DeliveryStatus;
import com.livraigo.repository.DeliveryRepository;
import com.livraigo.service.interfaces.DeliveryService;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;
import java.util.Optional;
@Service
public class DeliveryServiceImpl implements DeliveryService {
    
    private static final Logger logger = LoggerFactory.getLogger(DeliveryServiceImpl.class);
    
    private final DeliveryRepository deliveryRepository;
    private final DeliveryMapper deliveryMapper;
    
    public DeliveryServiceImpl(DeliveryRepository deliveryRepository, DeliveryMapper deliveryMapper) {
        this.deliveryRepository = deliveryRepository;
        this.deliveryMapper = deliveryMapper;
    }
    
    @Override
    public List<Delivery> findAll() {
        logger.info("Fetching all deliveries");
        return deliveryRepository.findAll();
    }
    
    @Override
    public Delivery findById(Long id) {
        logger.info("Fetching delivery with id: {}", id);
        Optional<Delivery> delivery = deliveryRepository.findById(id);
        return delivery.orElseThrow(() -> new RuntimeException("Delivery not found with id: " + id));
    }
    
    @Override
    public Delivery save(DeliveryRequestDTO deliveryDTO) {
        logger.info("Saving new delivery");
        Delivery delivery = deliveryMapper.toEntity(deliveryDTO);
        return deliveryRepository.save(delivery);
    }
    
    @Override
    public Delivery update(Long id, DeliveryRequestDTO deliveryDTO) {
        logger.info("Updating delivery with id: {}", id);
        if (!deliveryRepository.existsById(id)) {
            throw new RuntimeException("Delivery not found with id: " + id);
        }
        Delivery delivery = deliveryMapper.toEntity(deliveryDTO);
        delivery.setId(id);
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
