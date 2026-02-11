package com.projedata.autoflex.inventory.controllers;

import com.projedata.autoflex.inventory.dtos.production.ProductionCapacityResponse;
import com.projedata.autoflex.inventory.services.ProductionCapacityService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/production")
public class ProductionController {

    private final ProductionCapacityService productionCapacityService;

    @GetMapping
    public List<ProductionCapacityResponse> findProductionCapacity() {
        return productionCapacityService.findProductionCapacity();
    }
}