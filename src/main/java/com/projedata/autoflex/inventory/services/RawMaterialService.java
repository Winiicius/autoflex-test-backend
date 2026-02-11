package com.projedata.autoflex.inventory.services;

import com.projedata.autoflex.inventory.dtos.rawMaterial.RawMaterialRequest;
import com.projedata.autoflex.inventory.dtos.rawMaterial.RawMaterialResponse;

import java.util.List;

public interface RawMaterialService {

    RawMaterialResponse create(RawMaterialRequest request);
    List<RawMaterialResponse> findAll(String name, String code);
    RawMaterialResponse findById(Long id);
    RawMaterialResponse update(Long id, RawMaterialRequest request);
    void delete(Long id);

}
