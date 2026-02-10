package com.projedata.autoflex.inventory.dtos.product;

import com.projedata.autoflex.inventory.entities.enums.Unit;

import java.math.BigDecimal;
import java.util.List;

public record ProductResponse(
        Long id,
        String code,
        String name,
        BigDecimal price,
        List<ProductMaterialResponse> materials
) {

    public record ProductMaterialResponse(
            Long rawMaterialId,
            String rawMaterialName,
            Unit unit,
            BigDecimal quantity
    ) {}
}