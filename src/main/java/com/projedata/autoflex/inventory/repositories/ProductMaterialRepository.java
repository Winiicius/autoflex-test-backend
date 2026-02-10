package com.projedata.autoflex.inventory.repositories;

import com.projedata.autoflex.inventory.entities.ProductMaterial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductMaterialRepository extends JpaRepository<ProductMaterial, Long> {

    List<ProductMaterial> findByProductId(Long productId);

    Optional<ProductMaterial> findByProductIdAndRawMaterialId(Long productId, Long rawMaterialId);

    boolean existsByProductIdAndRawMaterialId(Long productId, Long rawMaterialId);

    void deleteByProductId(Long productId);
}
