package com.projedata.autoflex.inventory.services;

import com.projedata.autoflex.inventory.dtos.product.ProductRequest;
import com.projedata.autoflex.inventory.dtos.product.ProductResponse;

import java.util.List;

public interface ProductService {

    ProductResponse create(ProductRequest request);

    List<ProductResponse> findAll(String name, String code);

    ProductResponse findById(Long id);

    ProductResponse update(Long id, ProductRequest request);

    void delete(Long id);
}