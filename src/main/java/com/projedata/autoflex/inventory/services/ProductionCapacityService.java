package com.projedata.autoflex.inventory.services;

import com.projedata.autoflex.inventory.dtos.production.ProductionCapacityResponse;

import java.util.List;

public interface ProductionCapacityService {

    List<ProductionCapacityResponse> findProductionCapacity();

}