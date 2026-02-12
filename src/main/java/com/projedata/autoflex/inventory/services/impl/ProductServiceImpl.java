package com.projedata.autoflex.inventory.services.impl;

import com.projedata.autoflex.inventory.dtos.product.ProductMaterialDTO;
import com.projedata.autoflex.inventory.dtos.product.ProductRequest;
import com.projedata.autoflex.inventory.dtos.product.ProductResponse;
import com.projedata.autoflex.inventory.entities.Product;
import com.projedata.autoflex.inventory.entities.RawMaterial;
import com.projedata.autoflex.inventory.exceptions.BusinessRuleException;
import com.projedata.autoflex.inventory.exceptions.ConflictException;
import com.projedata.autoflex.inventory.exceptions.ResourceNotFoundException;
import com.projedata.autoflex.inventory.mappers.ProductMapper;
import com.projedata.autoflex.inventory.repositories.ProductRepository;
import com.projedata.autoflex.inventory.repositories.RawMaterialRepository;
import com.projedata.autoflex.inventory.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private ProductMapper productMapper;
    private RawMaterialRepository rawMaterialRepository;

    @Override
    @Transactional
    public ProductResponse create(ProductRequest request) {

        if (request.materials() == null || request.materials().isEmpty()) {
            throw new BusinessRuleException("Product must have at least one raw material");
        }

        if (productRepository.existsByCode(request.code())) {
            throw new ConflictException("Product code already exists: " + request.code());
        }

        Map<Long, RawMaterial> rawMaterialsById = loadRawMaterials(request);

        var productEntity = productMapper.toEntity(request, rawMaterialsById);

        var saved = productRepository.save(productEntity);

        return productMapper.toResponse(saved);
    }

    private Map<Long, RawMaterial> loadRawMaterials(ProductRequest request) {

        if (request.materials() == null || request.materials().isEmpty()) {
            return Map.of();
        }

        List<Long> ids = request.materials().stream()
                .map(ProductMaterialDTO::rawMaterialId)
                .distinct()
                .toList();

        List<RawMaterial> rawMaterials = rawMaterialRepository.findAllById(ids);

        if (rawMaterials.size() != ids.size()) {
            throw new ResourceNotFoundException("One or more raw materials were not found");
        }

        return rawMaterials.stream()
                .collect(Collectors.toMap(RawMaterial::getId, rm -> rm));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> findAll(String name, String code) {

        String normalizedName = (name != null) ? name.trim() : "";
        String normalizedCode = (code != null) ? code.trim() : "";

        return productRepository
                .findAllWithMaterialsAndRawMaterialsFiltered(normalizedName, normalizedCode)
                .stream()
                .map(productMapper::toResponse)
                .toList();
    }

    @Override
    public ProductResponse findById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("RProduct not found: " + id));
        return productMapper.toResponse(product);
    }

    @Override
    @Transactional
    public ProductResponse update(Long id, ProductRequest request) {

        if (request.materials() == null || request.materials().isEmpty()) {
            throw new BusinessRuleException("Product must have at least one raw material");
        }

        var product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("RProduct not found: " + id));

        if (!product.getCode().equals(request.code()) && productRepository.existsByCode(request.code())) {
            throw new ConflictException("Product code already exists: " + request.code());
        }

        Map<Long, RawMaterial> rawMaterialsById = loadRawMaterials(request);

        productMapper.updateEntity(product, request, rawMaterialsById);

        return productMapper.toResponse(product);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));

        productRepository.delete(product);
    }
}
