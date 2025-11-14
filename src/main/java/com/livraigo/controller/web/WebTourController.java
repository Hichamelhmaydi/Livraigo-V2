package com.livraigo.controller.web;

import com.livraigo.dto.TourRequestDTO;
import com.livraigo.model.entity.Delivery;
import com.livraigo.model.entity.Tour;
import com.livraigo.model.entity.Vehicle;
import com.livraigo.model.entity.Warehouse;
import com.livraigo.model.entity.enums.OptimizationAlgorithm;
import com.livraigo.service.interfaces.DeliveryService;
import com.livraigo.service.interfaces.TourService;
import com.livraigo.service.interfaces.VehicleService;
import com.livraigo.service.interfaces.WarehouseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@Controller
@RequestMapping("/web/tours")
public class WebTourController {
    
    private final TourService tourService;
    private final VehicleService vehicleService;
    private final WarehouseService warehouseService;
    private final DeliveryService deliveryService;
    
    public WebTourController(TourService tourService, 
                           VehicleService vehicleService, 
                           WarehouseService warehouseService,
                           DeliveryService deliveryService) {
        this.tourService = tourService;
        this.vehicleService = vehicleService;
        this.warehouseService = warehouseService;
        this.deliveryService = deliveryService;
    }
    
    @GetMapping
    public String getAllTours(Model model) {
        List<Tour> tours = tourService.findAll();
        model.addAttribute("tours", tours);
        return "tours/list";
    }
    
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        List<Vehicle> vehicles = vehicleService.findAvailableVehicles();
        List<Warehouse> warehouses = warehouseService.findAll();
        List<Delivery> unassignedDeliveries = deliveryService.findUnassignedDeliveries();
        
        model.addAttribute("tour", new TourRequestDTO());
        model.addAttribute("vehicles", vehicles);
        model.addAttribute("warehouses", warehouses);
        model.addAttribute("unassignedDeliveries", unassignedDeliveries);
        model.addAttribute("algorithms", OptimizationAlgorithm.values());
        
        return "tours/create";
    }
    
    @PostMapping("/create")
    public String createTour(@ModelAttribute TourRequestDTO tourDTO) {
        tourService.save(tourDTO);
        return "redirect:/web/tours";
    }
    
    @GetMapping("/{id}")
    public String getTourDetails(@PathVariable Long id, Model model) {
        Tour tour = tourService.findById(id);
        List<Delivery> optimizedRoute = tourService.getOptimizedTour(id);
        Double totalDistance = tourService.getTotalDistance(id);
        
        model.addAttribute("tour", tour);
        model.addAttribute("optimizedRoute", optimizedRoute);
        model.addAttribute("totalDistance", totalDistance);
        
        return "tours/details";
    }
    
    @GetMapping("/{id}/optimize")
    public String optimizeTour(@PathVariable Long id) {
        tourService.optimizeTour(id);
        return "redirect:/web/tours/" + id;
    }
    
    @GetMapping("/delete/{id}")
    public String deleteTour(@PathVariable Long id) {
        tourService.deleteById(id);
        return "redirect:/web/tours";
    }
}
