package com.projedata.autoflex.inventory.dtos.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductMaterialDTO(

        @NotNull
        Long rawMaterialId,

        @NotNull
        @Positive
        BigDecimal quantity
) {}