package com.projedata.autoflex.inventory.mappers;

import com.projedata.autoflex.inventory.dtos.production.ProductionCapacityResponse;
import com.projedata.autoflex.inventory.entities.Product;
import com.projedata.autoflex.inventory.entities.ProductMaterial;
import com.projedata.autoflex.inventory.entities.RawMaterial;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductionMapper {

    public ProductionCapacityResponse toResponse(Product product, int maxQuantity) {
        if (product == null) return null;

        BigDecimal totalValue = product.getPrice()
                .multiply(BigDecimal.valueOf(maxQuantity));

        return new ProductionCapacityResponse(
                product.getId(),
                product.getCode(),
                product.getName(),
                product.getPrice(),
                maxQuantity,
                totalValue,
                toMaterialUsageResponses(product.getMaterials())
        );
    }

    public List<ProductionCapacityResponse> toResponseList(List<Product> products, List<Integer> maxQuantities) {
        if (products == null || products.isEmpty()) return List.of();
        if (maxQuantities == null || products.size() != maxQuantities.size()) {
            throw new IllegalArgumentException("products and maxQuantities must have the same size");
        }

        List<ProductionCapacityResponse> responses = new ArrayList<>();
        for (int i = 0; i < products.size(); i++) {
            responses.add(toResponse(products.get(i), maxQuantities.get(i)));
        }
        return responses;
    }

    private List<ProductionCapacityResponse.MaterialUsageResponse> toMaterialUsageResponses(List<ProductMaterial> materials) {
        if (materials == null || materials.isEmpty()) return List.of();

        List<ProductionCapacityResponse.MaterialUsageResponse> list = new ArrayList<>();
        for (ProductMaterial productMaterial : materials) {
            RawMaterial rawMaterial = productMaterial.getRawMaterial();

            list.add(new ProductionCapacityResponse.MaterialUsageResponse(
                    rawMaterial.getId(),
                    rawMaterial.getCode(),
                    rawMaterial.getName(),
                    rawMaterial.getUnit(),
                    productMaterial.getQuantity(),          // requiredPerUnit
                    rawMaterial.getStockQuantity()      // current stock
            ));
        }
        return list;
    }
}
