package com.projedata.autoflex.inventory.services.impl;

import com.projedata.autoflex.inventory.dtos.production.ProductionCapacityResponse;
import com.projedata.autoflex.inventory.entities.Product;
import com.projedata.autoflex.inventory.entities.ProductMaterial;
import com.projedata.autoflex.inventory.mappers.ProductionMapper;
import com.projedata.autoflex.inventory.repositories.ProductRepository;
import com.projedata.autoflex.inventory.services.ProductionCapacityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Comparator;
import java.math.RoundingMode;
import java.util.List;

@AllArgsConstructor
@Service
public class ProductionCapacityServiceImpl implements ProductionCapacityService {

    private final ProductRepository productRepository;
    private final ProductionMapper productionMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ProductionCapacityResponse> findProductionCapacity() {

        List<Product> products = productRepository.findAllWithMaterialsAndRawMaterials();

        products.sort(Comparator.comparing(Product::getPrice, Comparator.nullsLast(Comparator.naturalOrder())).reversed());

        return products.stream()
                .map(p -> {
                    int maxQuantity = calculateMaxQuantity(p);
                    return productionMapper.toResponse(p, maxQuantity);
                })
                .toList();
    }

    private int calculateMaxQuantity(Product product) {
        List<ProductMaterial> materials = product.getMaterials();

        if (materials == null || materials.isEmpty()) {
            return 0;
        }

        BigDecimal minPossible = null;

        for (ProductMaterial productMaterial : materials) {
            if (productMaterial.getRawMaterial() == null
                    || productMaterial.getRawMaterial().getStockQuantity() == null
                    || productMaterial.getQuantity() == null) {
                return 0;
            }

            BigDecimal stock = productMaterial.getRawMaterial().getStockQuantity();
            BigDecimal requiredPerUnit = productMaterial.getQuantity();

            if (stock.compareTo(BigDecimal.ZERO) <= 0) {
                return 0;
            }
            if (requiredPerUnit.compareTo(BigDecimal.ZERO) <= 0) {
                return 0;
            }

            BigDecimal possibleWithThisMaterial = stock.divide(requiredPerUnit, 0, RoundingMode.FLOOR);

            if (minPossible == null || possibleWithThisMaterial.compareTo(minPossible) < 0) {
                minPossible = possibleWithThisMaterial;
            }

            if (minPossible.compareTo(BigDecimal.ZERO) == 0) {
                return 0;
            }
        }

        if (minPossible.compareTo(BigDecimal.valueOf(Integer.MAX_VALUE)) > 0) {
            return Integer.MAX_VALUE;
        }

        return minPossible.intValue();
    }
}
