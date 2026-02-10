package com.projedata.autoflex.inventory.dtos.product;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;

public record ProductRequest(

        @NotBlank
        @Size(max = 50)
        String code,

        @NotBlank
        @Size(max = 150)
        String name,

        @NotNull
        @Positive
        BigDecimal price,

        @Valid
        @NotEmpty
        List<ProductMaterialDTO> materials
) {}