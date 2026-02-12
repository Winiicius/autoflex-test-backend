package com.projedata.autoflex.inventory.repositories;

import com.projedata.autoflex.inventory.entities.Product;
import com.projedata.autoflex.inventory.entities.RawMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
    where (:name is null or lower(p.name) like lower(concat('%', :name, '%')))
      and (:code is null or lower(p.code) like lower(concat('%', :code, '%')))
    """)
    List<Product> findAllWithMaterialsAndRawMaterialsFiltered(@Param("name") String name,
                                                              @Param("code") String code);
}
