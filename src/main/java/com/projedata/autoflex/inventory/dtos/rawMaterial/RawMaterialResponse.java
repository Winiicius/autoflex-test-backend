package com.projedata.autoflex.inventory.dtos.rawMaterial;

import com.projedata.autoflex.inventory.entities.enums.Unit;

import java.math.BigDecimal;

public record RawMaterialResponse(
        Long id,
        String code,
        String name,
        Unit unit,
        BigDecimal stockQuantity
) {}