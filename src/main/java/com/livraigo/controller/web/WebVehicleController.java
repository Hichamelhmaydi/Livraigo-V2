package com.livraigo.controller.web;

import com.livraigo.dto.VehicleRequestDTO;
import com.livraigo.model.entity.Vehicle;
import com.livraigo.model.entity.enums.VehicleType;
import com.livraigo.service.interfaces.VehicleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@Controller
@RequestMapping("/web/vehicles")
public class WebVehicleController {
    
    private final VehicleService vehicleService;
    
    public WebVehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }
    
    @GetMapping
    public String getAllVehicles(Model model) {
        List<Vehicle> vehicles = vehicleService.findAll();
        model.addAttribute("vehicles", vehicles);
        return "vehicles/list";
    }
    
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("vehicle", new VehicleRequestDTO());
        model.addAttribute("types", VehicleType.values());
        return "vehicles/create";
    }
    
    @PostMapping("/create")
    public String createVehicle(@ModelAttribute VehicleRequestDTO vehicleDTO) {
        vehicleService.save(vehicleDTO);
        return "redirect:/web/vehicles";
    }
    
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Vehicle vehicle = vehicleService.findById(id);
        model.addAttribute("vehicle", vehicle);
        model.addAttribute("types", VehicleType.values());
        return "vehicles/edit";
    }
    
    @PostMapping("/edit/{id}")
    public String updateVehicle(@PathVariable Long id, @ModelAttribute VehicleRequestDTO vehicleDTO) {
        vehicleService.update(id, vehicleDTO);
        return "redirect:/web/vehicles";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteVehicle(@PathVariable Long id) {
        vehicleService.deleteById(id);
        return "redirect:/web/vehicles";
    }
    
    @GetMapping("/available")
    public String getAvailableVehicles(Model model) {
        List<Vehicle> vehicles = vehicleService.findAvailableVehicles();
        model.addAttribute("vehicles", vehicles);
        return "vehicles/available";
    }
}
