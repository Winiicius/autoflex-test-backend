package com.projedata.autoflex.inventory.mappers;

import com.projedata.autoflex.inventory.dtos.product.ProductMaterialDTO;
import com.projedata.autoflex.inventory.dtos.product.ProductRequest;
import com.projedata.autoflex.inventory.dtos.product.ProductResponse;
import com.projedata.autoflex.inventory.entities.Product;
import com.projedata.autoflex.inventory.entities.ProductMaterial;
import com.projedata.autoflex.inventory.entities.RawMaterial;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class ProductMapper {

    public ProductResponse toResponse(Product product) {
        if (product == null) return null;

        return new ProductResponse(
                product.getId(),
                product.getCode(),
                product.getName(),
                product.getPrice(),
                toProductMaterialResponse(product.getMaterials())
        );
    }

    private List<ProductResponse.ProductMaterialResponse> toProductMaterialResponse(List<ProductMaterial> materials) {
        if (materials == null || materials.isEmpty()) return List.of();

        List<ProductResponse.ProductMaterialResponse> list = new ArrayList<>();
        for (ProductMaterial productMaterial : materials) {
            RawMaterial rawMaterial = productMaterial.getRawMaterial();

            list.add(new ProductResponse.ProductMaterialResponse(
                    rawMaterial != null ? rawMaterial.getId() : null,
                    rawMaterial != null ? rawMaterial.getName() : null,
                    rawMaterial != null ? rawMaterial.getUnit() : null,
                    productMaterial.getQuantity()
            ));
        }
        return list;
    }

    public Product toEntity(ProductRequest request, Map<Long, RawMaterial> rawMaterialsById) {
        if (request == null) return null;

        Product product = new Product();
        product.setCode(request.code());
        product.setName(request.name());
        product.setPrice(request.price());

        List<ProductMaterial> materials = toProductMaterials(request.materials(), product, rawMaterialsById);
        product.setMaterials(materials);

        return product;
    }

    /**
     * Update an existing Product entity from request.
     * This implementation REPLACES the materials list.
     */
    public void updateEntity(Product product, ProductRequest request, Map<Long, RawMaterial> rawMaterialsById) {
        product.setCode(request.code());
        product.setName(request.name());
        product.setPrice(request.price());
        product.getMaterials().clear();
        product.getMaterials().addAll(toProductMaterials(request.materials(), product, rawMaterialsById));
    }

    private List<ProductMaterial> toProductMaterials(
            List<ProductMaterialDTO> dtos,
            Product product,
            Map<Long, RawMaterial> rawMaterialsById
    ) {
        if (dtos == null || dtos.isEmpty()) return new ArrayList<>();

        List<ProductMaterial> list = new ArrayList<>();
        for (ProductMaterialDTO dto : dtos) {
            RawMaterial rawMaterial = rawMaterialsById.get(dto.rawMaterialId());
            ProductMaterial productMaterial = new ProductMaterial();
            productMaterial.setProduct(product);
            productMaterial.setRawMaterial(rawMaterial);
            productMaterial.setQuantity(dto.quantity());

            list.add(productMaterial);
        }
        return list;
    }

    public ProductRequest toRequestDTO(Product product) {
        if (product == null) return null;

        return new ProductRequest(
                product.getCode(),
                product.getName(),
                product.getPrice(),
                toProductMaterialDTO(product.getMaterials())
        );
    }

    private List<ProductMaterialDTO> toProductMaterialDTO(List<ProductMaterial> materials) {
        if (materials == null || materials.isEmpty()) return List.of();

        List<ProductMaterialDTO> list = new ArrayList<>();
        for (ProductMaterial pm : materials) {
            list.add(new ProductMaterialDTO(
                    pm.getRawMaterial().getId(),
                    pm.getQuantity(),
                    pm.getRawMaterial().getUnit()
            ));
        }
        return list;
    }
}
