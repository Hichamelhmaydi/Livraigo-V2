package com.livraigo.controller.web;

import com.livraigo.model.entity.Warehouse;
import com.livraigo.service.interfaces.WarehouseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@Controller
@RequestMapping("/web/warehouses")
public class WebWarehouseController {
    
    private final WarehouseService warehouseService;
    
    public WebWarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }
    
    @GetMapping
    public String getAllWarehouses(Model model) {
        List<Warehouse> warehouses = warehouseService.findAll();
        model.addAttribute("warehouses", warehouses);
        return "warehouses/list";
    }
    
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("warehouse", new Warehouse());
        return "warehouses/create";
    }
    
    @PostMapping("/create")
    public String createWarehouse(@ModelAttribute Warehouse warehouse) {
        warehouseService.save(warehouse);
        return "redirect:/web/warehouses";
    }
    
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Warehouse warehouse = warehouseService.findById(id);
        model.addAttribute("warehouse", warehouse);
        return "warehouses/edit";
    }
    
    @PostMapping("/edit/{id}")
    public String updateWarehouse(@PathVariable Long id, @ModelAttribute Warehouse warehouse) {
        warehouseService.update(id, warehouse);
        return "redirect:/web/warehouses";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteWarehouse(@PathVariable Long id) {
        warehouseService.deleteById(id);
        return "redirect:/web/warehouses";
    }
}
