package com.livraigo.service.optimizer;

import com.livraigo.model.entity.Delivery;
import com.livraigo.model.entity.Vehicle;
import com.livraigo.model.entity.Warehouse;
import com.livraigo.model.entity.enums.OptimizationAlgorithm;
import com.livraigo.repository.DeliveryHistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Component
public class AiOptimizer implements TourOptimizer {
    
    private static final Logger logger = LoggerFactory.getLogger(AiOptimizer.class);
    
    private final ChatClient chatClient;
    private final DeliveryHistoryRepository deliveryHistoryRepository;
    
    // Rendez les dépendances optionnelles
    @Autowired(required = false)
    public AiOptimizer(ChatClient chatClient, DeliveryHistoryRepository deliveryHistoryRepository) {
        this.chatClient = chatClient;
        this.deliveryHistoryRepository = deliveryHistoryRepository;
        
        if (chatClient == null) {
            logger.warn("ChatClient not available - AiOptimizer will use fallback optimization");
        } else {
            logger.info("AiOptimizer initialized with ChatClient");
        }
    }
    
    // Constructeur par défaut sans dépendances
    public AiOptimizer() {
        this.chatClient = null;
        this.deliveryHistoryRepository = null;
        logger.warn("AiOptimizer initialized without dependencies - using fallback optimization");
    }
    
    @Override
    public List<Delivery> calculateOptimalTour(List<Delivery> deliveries, Warehouse warehouse, Vehicle vehicle) {
        // Si ChatClient n'est pas disponible, utilisez une méthode de repli
        if (chatClient == null) {
            return fallbackOptimization(deliveries, warehouse, vehicle);
        }
        
        try {
            logger.info("Starting AI optimization for {} deliveries", deliveries.size());
            
            List<Object[]> historicalData = deliveryHistoryRepository != null ? 
                deliveryHistoryRepository.findAverageDelaysByCustomer() : List.of();
            
            String prompt = buildOptimizationPrompt(deliveries, warehouse, vehicle, historicalData);
            
            String aiResponse = chatClient.prompt()
                .user(prompt)
                .call()
                .content();
            
            logger.info("AI optimization completed successfully");
            
            return parseAIResponse(aiResponse, deliveries);
            
        } catch (Exception e) {
            logger.error("Error during AI optimization, using fallback order", e);
            return fallbackOptimization(deliveries, warehouse, vehicle);
        }
    }
    
    private List<Delivery> fallbackOptimization(List<Delivery> deliveries, Warehouse warehouse, Vehicle vehicle) {
        logger.info("Using fallback optimization for {} deliveries", deliveries.size());
        // Logique de repli simple - retourne l'ordre original
        // Vous pourriez implémenter une logique simple basée sur la distance
        return deliveries;
    }
    
    private String buildOptimizationPrompt(List<Delivery> deliveries, Warehouse warehouse, 
                                         Vehicle vehicle, List<Object[]> historicalData) {
        
        Map<String, Object> optimizationData = new HashMap<>();
        optimizationData.put("deliveries", deliveries);
        optimizationData.put("warehouse", warehouse);
        optimizationData.put("vehicle", vehicle);
        optimizationData.put("historical_delays", historicalData);
        optimizationData.put("current_time", java.time.LocalDateTime.now().toString());
        
        return """
            En tant qu'expert en optimisation logistique, analysez les données de livraison suivantes 
            et proposez un ordre optimal pour la tournée.
            
            CONTEXTE MÉTIER :
            - Entreprise de livraison avec contraintes de temps et de capacité
            - Objectif : minimiser la distance totale et les retards
            - Prise en compte des créneaux horaires préférés des clients
            - Analyse des patterns historiques de retard
            
            DONNÉES D'ENTRÉE :
            %s
            
            CONTRAINTES À RESPECTER :
            1. Capacité du véhicule : %s kg, %s m³ (type: %s)
            2. Heure de départ : 06:00
            3. Heure de fin maximale : 22:00
            4. Créneaux horaires préférés des clients
            5. Minimiser la distance totale
            
            Veuillez fournir une réponse au format JSON avec l'ordre optimisé des livraisons.
            """.formatted(
                optimizationData.toString(),
                vehicle.getMaxWeight(),
                vehicle.getMaxVolume(),
                vehicle.getType()
            );
    }
    
    private List<Delivery> parseAIResponse(String aiResponse, List<Delivery> originalDeliveries) {
        logger.info("AI response received: {}", aiResponse);
        // Pour l'instant, retourne l'ordre original
        // Implémentez la logique de parsing de la réponse AI ici
        return originalDeliveries;
    }
    
    @Override
    public OptimizationAlgorithm getAlgorithm() {
        return OptimizationAlgorithm.AI_OPTIMIZER;
    }
}