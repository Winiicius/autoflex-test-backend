package com.projedata.autoflex.inventory.services.impl;

import com.projedata.autoflex.inventory.dtos.rawMaterial.RawMaterialRequest;
import com.projedata.autoflex.inventory.dtos.rawMaterial.RawMaterialResponse;
import com.projedata.autoflex.inventory.entities.RawMaterial;
import com.projedata.autoflex.inventory.exceptions.ConflictException;
import com.projedata.autoflex.inventory.exceptions.ResourceNotFoundException;
import com.projedata.autoflex.inventory.mappers.RawMaterialMapper;
import com.projedata.autoflex.inventory.repositories.RawMaterialRepository;
import com.projedata.autoflex.inventory.services.RawMaterialService;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Service
public class RawMaterialServiceimpl implements RawMaterialService {

    private final RawMaterialRepository rawMaterialRepository;
    private final RawMaterialMapper rawMaterialMapper;

    @Transactional
    public RawMaterialResponse create(RawMaterialRequest request) {
        if (rawMaterialRepository.existsByCode(request.code()))
            throw new ConflictException("Raw material code already exists: " + request.code());

        RawMaterial entity = rawMaterialMapper.toEntity(request);
        RawMaterial saved = rawMaterialRepository.save(entity);

        return rawMaterialMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<RawMaterialResponse> findAll(String name, String code) {
        List<RawMaterial> result;

        boolean hasName = name != null && !name.isBlank();
        boolean hasCode = code != null && !code.isBlank();

        if (hasName && hasCode) {
            result = rawMaterialRepository.findByNameContainingIgnoreCaseAndCodeContainingIgnoreCase(name, code);
        } else if (hasName) {
            result = rawMaterialRepository.findByNameContainingIgnoreCase(name);
        } else if (hasCode) {
            result = rawMaterialRepository.findByCodeContainingIgnoreCase(code);
        } else {
            result = rawMaterialRepository.findAll();
        }

        return result.stream()
                .map(rawMaterialMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public RawMaterialResponse findById(Long id) {
        RawMaterial entity = rawMaterialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Raw material not found: " + id));

        return rawMaterialMapper.toResponse(entity);
    }

    @Transactional
    public RawMaterialResponse update(Long id, RawMaterialRequest request) {
        RawMaterial entity = rawMaterialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Raw material not found: " + id));

        if (!Objects.equals(entity.getCode(), request.code())
                && rawMaterialRepository.existsByCode(request.code())) {
            throw new ConflictException("Raw material code already exists: " + request.code());
        }

        rawMaterialMapper.updateEntity(entity, request);

        return rawMaterialMapper.toResponse(entity);
    }

    @Transactional
    public void delete(Long id) {
        RawMaterial entity = rawMaterialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Raw material not found: " + id));

        rawMaterialRepository.delete(entity);
    }
}
