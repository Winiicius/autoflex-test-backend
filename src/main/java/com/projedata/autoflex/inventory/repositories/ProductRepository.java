package com.projedata.autoflex.inventory.repositories;

import com.projedata.autoflex.inventory.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByCode(String code);

    boolean existsByCode(String code);

    @Query("""
        select distinct p
        from Product p
        left join fetch p.materials pm
        left join fetch pm.rawMaterial rm
    """)
    List<Product> findAllWithMaterialsAndRawMaterials();
}
