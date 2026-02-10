package com.projedata.autoflex.inventory.mappers;

import com.projedata.autoflex.inventory.dtos.rawMaterial.RawMaterialRequest;
import com.projedata.autoflex.inventory.dtos.rawMaterial.RawMaterialResponse;
import com.projedata.autoflex.inventory.entities.RawMaterial;

public class RawMaterialMapper {

    public RawMaterialResponse toResponse(RawMaterial rawMaterial) {
        if (rawMaterial == null) return null;

        return new RawMaterialResponse(
                rawMaterial.getId(),
                rawMaterial.getCode(),
                rawMaterial.getName(),
                rawMaterial.getUnit(),
                rawMaterial.getStockQuantity()
        );
    }

    public RawMaterial toEntity(RawMaterialRequest request) {
        if (request == null) return null;

        RawMaterial rawMaterial = new RawMaterial();
        rawMaterial.setCode(request.code());
        rawMaterial.setName(request.name());
        rawMaterial.setUnit(request.unit());
        rawMaterial.setStockQuantity(request.stockQuantity());
        return rawMaterial;
    }

    public void updateEntity(RawMaterial rawMaterial, RawMaterialRequest request) {
        rawMaterial.setCode(request.code());
        rawMaterial.setName(request.name());
        rawMaterial.setUnit(request.unit());
        rawMaterial.setStockQuantity(request.stockQuantity());
    }
}
