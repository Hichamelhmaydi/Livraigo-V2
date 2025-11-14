package com.livraigo.controller.web;

import com.livraigo.dto.DeliveryRequestDTO;
import com.livraigo.model.entity.Delivery;
import com.livraigo.model.entity.enums.DeliveryStatus;
import com.livraigo.service.interfaces.DeliveryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@Controller
@RequestMapping("/web/deliveries")
public class WebDeliveryController {
    
    private final DeliveryService deliveryService;
    
    public WebDeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }
    
    @GetMapping
    public String getAllDeliveries(Model model) {
        List<Delivery> deliveries = deliveryService.findAll();
        model.addAttribute("deliveries", deliveries);
        return "deliveries/list";
    }
    
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("delivery", new DeliveryRequestDTO());
        model.addAttribute("statuses", DeliveryStatus.values());
        return "deliveries/create";
    }
    
    @PostMapping("/create")
    public String createDelivery(@ModelAttribute DeliveryRequestDTO deliveryDTO) {
        deliveryService.save(deliveryDTO);
        return "redirect:/web/deliveries";
    }
    
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Delivery delivery = deliveryService.findById(id);
        model.addAttribute("delivery", delivery);
        model.addAttribute("statuses", DeliveryStatus.values());
        return "deliveries/edit";
    }
    
    @PostMapping("/edit/{id}")
    public String updateDelivery(@PathVariable Long id, @ModelAttribute DeliveryRequestDTO deliveryDTO) {
        deliveryService.update(id, deliveryDTO);
        return "redirect:/web/deliveries";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteDelivery(@PathVariable Long id) {
        deliveryService.deleteById(id);
        return "redirect:/web/deliveries";
    }
    
    @GetMapping("/unassigned")
    public String getUnassignedDeliveries(Model model) {
        List<Delivery> deliveries = deliveryService.findUnassignedDeliveries();
        model.addAttribute("deliveries", deliveries);
        return "deliveries/unassigned";
    }
}
