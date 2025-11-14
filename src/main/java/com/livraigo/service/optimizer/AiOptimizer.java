package com.livraigo.service.optimizer;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.livraigo.model.entity.Delivery;
import com.livraigo.model.entity.Vehicle;
import com.livraigo.model.entity.Warehouse;
import com.livraigo.model.entity.enums.OptimizationAlgorithm;
import com.livraigo.repository.DeliveryHistoryRepository;
import com.livraigo.service.interfaces.TourOptimizer;

@Component
@ConditionalOnProperty(name = "app.optimization.algorithm", havingValue = "AI_OPTIMIZER")
public class AiOptimizer implements TourOptimizer {
    
    private static final Logger logger = LoggerFactory.getLogger(AiOptimizer.class);
    
    private final ChatClient chatClient;
    private final DeliveryHistoryRepository deliveryHistoryRepository;
    
    public AiOptimizer(ChatClient chatClient, DeliveryHistoryRepository deliveryHistoryRepository) {
        this.chatClient = chatClient;
        this.deliveryHistoryRepository = deliveryHistoryRepository;
    }
    
    @Override
    public List<Delivery> calculateOptimalTour(Warehouse warehouse, List<Delivery> deliveries) {
        try {
            logger.info("Starting AI optimization for {} deliveries", deliveries.size());

            List<Object[]> historicalData = deliveryHistoryRepository.findAverageDelaysByCustomer();

            String prompt = buildOptimizationPrompt(deliveries, warehouse, null, historicalData);
            
            String aiResponse = chatClient.prompt()
                .user(prompt)
                .call()
                .content();
            
            logger.info("AI optimization completed successfully");
            
            return parseAIResponse(aiResponse, deliveries);
            
        } catch (Exception e) {
            logger.error("Error during AI optimization, using default order", e);
            return deliveries;
        }
    }
    
    private String buildOptimizationPrompt(List<Delivery> deliveries, Warehouse warehouse,  Vehicle vehicle, List<Object[]> historicalData) {
        
        Map<String, Object> optimizationData = new HashMap<>();
        optimizationData.put("deliveries", deliveries);
        optimizationData.put("warehouse", warehouse);
        optimizationData.put("vehicle", vehicle);
        optimizationData.put("historical_delays", historicalData);
        optimizationData.put("current_time", java.time.LocalDateTime.now().toString());
        
        String vehicleMaxWeight = vehicle != null ? String.valueOf(vehicle.getMaxWeight()) : "unknown";
        String vehicleType = vehicle != null ? String.valueOf(vehicle.getType()) : "unknown";

        return """
            En tant qu'expert en optimisation logistique, analysez les données de livraison suivantes 
            et proposez un ordre optimal pour la tournée.
            
            CONTEXTE MÉTIER :
            - Entreprise de livraison avec contraintes de temps et de capacité
            - Objectif : minimiser la distance totale et les retards
            - Prise en compte des créneaux horaires préférés des clients
            - Analyse des patterns historiques de retard
            
            DONNÉES D'ENTRÉE (format JSON) :
            %s
            
            DONNÉES HISTORIQUES DES RETARDS :
            %s
            
            CONTRAINTES À RESPECTER :
            1. Capacité du véhicule : %s (type: %s)
            2. Heure de départ : 06:00
            3. Heure de fin maximale : 22:00
            4. Créneaux horaires préférés des clients
            5. Minimiser la distance totale
            6. Éviter les zones à fort historique de retard
            
            FORMAT DE SORTIE ATTENDU (JSON) :
            {
                "optimized_route": [
                    {"delivery_id": 1, "customer_name": "Client A", "estimated_arrival": "09:30"},
                    ...
                ],
                "recommendations": {
                    "total_distance_km": 45.5,
                    "estimated_duration_min": 320,
                    "risk_zones": ["Zone A", "Zone B"],
                    "optimization_justification": "Explication des choix d'optimisation...",
                    "predicted_delays": {"Client X": 15, "Client Y": 0}
                }
            }
            
            CRITÈRES D'OPTIMISATION PRIORITAIRES :
            1. Respect des créneaux horaires clients
            2. Éviter les zones à fort historique de retard
            3. Regroupement géographique
            4. Minimisation de la distance totale
            5. Équilibrage de la charge de travail
            
            Veuillez fournir une réponse au format JSON strict.
            """.formatted(
                optimizationData.toString(),
                historicalData.toString(),
                vehicleMaxWeight,
                vehicleType
            );
    }
    
    private List<Delivery> parseAIResponse(String aiResponse, List<Delivery> originalDeliveries) {
        logger.info("AI response received: {}", aiResponse);

        return originalDeliveries;
    }
    
    @Override
    public OptimizationAlgorithm getAlgorithm() {
        return OptimizationAlgorithm.AI_OPTIMIZER;
    }
}
