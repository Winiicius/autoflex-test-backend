package com.projedata.autoflex.inventory.dtos.production;

import com.projedata.autoflex.inventory.entities.enums.Unit;

import java.math.BigDecimal;
import java.util.List;

public record ProductionCapacityResponse(
        Long productId,
        String productCode,
        String productName,
        BigDecimal unitPrice,
        Integer maxQuantity,
        BigDecimal totalValue,
        List<MaterialUsageResponse> materials
) {

    public record MaterialUsageResponse(
            Long rawMaterialId,
            String rawMaterialCode,
            String rawMaterialName,
            Unit unit,
            BigDecimal requiredPerUnit,
            BigDecimal stockQuantity
    ) {}
}