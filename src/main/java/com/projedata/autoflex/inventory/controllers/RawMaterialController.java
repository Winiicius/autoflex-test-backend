package com.projedata.autoflex.inventory.controllers;

import com.projedata.autoflex.inventory.dtos.rawMaterial.RawMaterialRequest;
import com.projedata.autoflex.inventory.dtos.rawMaterial.RawMaterialResponse;
import com.projedata.autoflex.inventory.services.RawMaterialService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/raw-materials")
public class RawMaterialController {

    private final RawMaterialService rawMaterialService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RawMaterialResponse create(@RequestBody @Valid RawMaterialRequest request) {
        return rawMaterialService.create(request);
    }

    @GetMapping
    public List<RawMaterialResponse> findAll(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String code
    ) {
        return rawMaterialService.findAll(name, code);
    }

    @GetMapping("/{id}")
    public RawMaterialResponse findById(@PathVariable Long id) {
        return rawMaterialService.findById(id);
    }

    @PutMapping("/{id}")
    public RawMaterialResponse update(@PathVariable Long id, @RequestBody @Valid RawMaterialRequest request) {
        return rawMaterialService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        rawMaterialService.delete(id);
    }
}
