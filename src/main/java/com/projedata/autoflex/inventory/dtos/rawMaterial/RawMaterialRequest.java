package com.projedata.autoflex.inventory.dtos.rawMaterial;

import com.projedata.autoflex.inventory.entities.enums.Unit;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record RawMaterialRequest(
        @NotBlank
        @Size(max = 50)
        String code,

        @NotBlank
        @Size(max = 150)
        String name,

        @NotNull
        Unit unit,

        @NotNull
        @PositiveOrZero
        BigDecimal stockQuantity
) {}