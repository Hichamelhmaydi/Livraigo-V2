package com.livraigo.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.livraigo.repository.DeliveryHistoryRepository;
import com.livraigo.service.optimizer.AiOptimizer;
import com.livraigo.service.optimizer.ClarkeWrightOptimizer;
import com.livraigo.service.optimizer.NearestNeighborOptimizer;
import com.livraigo.service.optimizer.TourOptimizer;
import com.livraigo.service.util.DistanceCalculator;

@Configuration
public class AppConfig {

    @Bean
    public NearestNeighborOptimizer nearestNeighborOptimizer(DistanceCalculator distanceCalculator) {
        return new NearestNeighborOptimizer(distanceCalculator);
    }

    @Bean
    @ConditionalOnProperty(name = "app.optimization.algorithm", havingValue = "CLARKE_WRIGHT")
    @Primary
    public TourOptimizer clarkeWrightOptimizer(DistanceCalculator distanceCalculator) {
        return new ClarkeWrightOptimizer(distanceCalculator);
    }

    @Bean
    @ConditionalOnProperty(name = "app.optimization.algorithm", havingValue = "AI_OPTIMIZER")
    @Primary
    public AiOptimizer aiOptimizer(ChatClient chatClient, DeliveryHistoryRepository deliveryHistoryRepository) {
        return new AiOptimizer(chatClient, deliveryHistoryRepository);
    }

}