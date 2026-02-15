package com.projedata.autoflex.inventory.services;

import com.projedata.autoflex.inventory.dtos.rawMaterial.RawMaterialRequest;
import com.projedata.autoflex.inventory.dtos.rawMaterial.RawMaterialResponse;
import com.projedata.autoflex.inventory.entities.RawMaterial;
import com.projedata.autoflex.inventory.entities.enums.Unit;
import com.projedata.autoflex.inventory.exceptions.ConflictException;
import com.projedata.autoflex.inventory.exceptions.ResourceNotFoundException;
import com.projedata.autoflex.inventory.mappers.RawMaterialMapper;
import com.projedata.autoflex.inventory.repositories.RawMaterialRepository;
import com.projedata.autoflex.inventory.services.impl.RawMaterialServiceimpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RawMaterialServiceImplTest {

    @Mock private RawMaterialRepository rawMaterialRepository;
    @Mock private RawMaterialMapper rawMaterialMapper;

    @InjectMocks private RawMaterialServiceimpl service;

    @Test
    void create_shouldSaveAndReturnResponse_whenValid() {
        RawMaterialRequest request = new RawMaterialRequest("RM-001", "Steel", Unit.KG, bd("100"));

        when(rawMaterialRepository.existsByCode("RM-001")).thenReturn(false);

        RawMaterial entity = new RawMaterial();
        when(rawMaterialMapper.toEntity(request)).thenReturn(entity);

        RawMaterial saved = rawMaterial(1L, "RM-001", "Steel", Unit.KG, bd("100"));
        when(rawMaterialRepository.save(entity)).thenReturn(saved);

        RawMaterialResponse expected = new RawMaterialResponse(1L, "RM-001", "Steel", Unit.KG, bd("100"));
        when(rawMaterialMapper.toResponse(saved)).thenReturn(expected);

        RawMaterialResponse result = service.create(request);

        assertThat(result).isEqualTo(expected);

        verify(rawMaterialRepository).existsByCode("RM-001");
        verify(rawMaterialMapper).toEntity(request);
        verify(rawMaterialRepository).save(entity);
        verify(rawMaterialMapper).toResponse(saved);
    }

    @Test
    void create_shouldThrowConflict_whenCodeAlreadyExists() {
        RawMaterialRequest request = new RawMaterialRequest("RM-001", "Steel", Unit.KG, bd("100"));

        when(rawMaterialRepository.existsByCode("RM-001")).thenReturn(true);

        assertThatThrownBy(() -> service.create(request))
                .isInstanceOf(ConflictException.class);

        verify(rawMaterialRepository).existsByCode("RM-001");
        verify(rawMaterialRepository, never()).save(any());
        verifyNoInteractions(rawMaterialMapper);
    }

    @Test
    void findAll_shouldUseFindAll_whenNoFilters() {
        RawMaterial entity = rawMaterial(1L, "RM-001", "Steel", Unit.KG, bd("100"));
        when(rawMaterialRepository.findAll()).thenReturn(List.of(entity));

        RawMaterialResponse response = new RawMaterialResponse(1L, "RM-001", "Steel", Unit.KG, bd("100"));
        when(rawMaterialMapper.toResponse(entity)).thenReturn(response);

        List<RawMaterialResponse> result = service.findAll(null, null);

        assertThat(result).containsExactly(response);

        verify(rawMaterialRepository).findAll();
        verify(rawMaterialRepository, never()).findByNameContainingIgnoreCase(anyString());
        verify(rawMaterialRepository, never()).findByCodeContainingIgnoreCase(anyString());
        verify(rawMaterialRepository, never()).findByNameContainingIgnoreCaseAndCodeContainingIgnoreCase(anyString(), anyString());
    }

    @Test
    void findAll_shouldUseFindByName_whenOnlyNameProvided() {
        RawMaterial entity = rawMaterial(1L, "RM-001", "Steel", Unit.KG, bd("100"));
        when(rawMaterialRepository.findByNameContainingIgnoreCase("ste")).thenReturn(List.of(entity));

        RawMaterialResponse response = new RawMaterialResponse(1L, "RM-001", "Steel", Unit.KG, bd("100"));
        when(rawMaterialMapper.toResponse(entity)).thenReturn(response);

        List<RawMaterialResponse> result = service.findAll("ste", null);

        assertThat(result).containsExactly(response);

        verify(rawMaterialRepository).findByNameContainingIgnoreCase("ste");
        verify(rawMaterialRepository, never()).findAll();
        verify(rawMaterialRepository, never()).findByCodeContainingIgnoreCase(anyString());
        verify(rawMaterialRepository, never()).findByNameContainingIgnoreCaseAndCodeContainingIgnoreCase(anyString(), anyString());
    }

    @Test
    void findAll_shouldUseFindByCode_whenOnlyCodeProvided() {
        RawMaterial entity = rawMaterial(1L, "RM-001", "Steel", Unit.KG, bd("100"));
        when(rawMaterialRepository.findByCodeContainingIgnoreCase("001")).thenReturn(List.of(entity));

        RawMaterialResponse response = new RawMaterialResponse(1L, "RM-001", "Steel", Unit.KG, bd("100"));
        when(rawMaterialMapper.toResponse(entity)).thenReturn(response);

        List<RawMaterialResponse> result = service.findAll(null, "001");

        assertThat(result).containsExactly(response);

        verify(rawMaterialRepository).findByCodeContainingIgnoreCase("001");
        verify(rawMaterialRepository, never()).findAll();
        verify(rawMaterialRepository, never()).findByNameContainingIgnoreCase(anyString());
        verify(rawMaterialRepository, never()).findByNameContainingIgnoreCaseAndCodeContainingIgnoreCase(anyString(), anyString());
    }

    @Test
    void findAll_shouldUseFindByNameAndCode_whenBothProvided() {
        RawMaterial entity = rawMaterial(1L, "RM-001", "Steel", Unit.KG, bd("100"));
        when(rawMaterialRepository.findByNameContainingIgnoreCaseAndCodeContainingIgnoreCase("ste", "001"))
                .thenReturn(List.of(entity));

        RawMaterialResponse response = new RawMaterialResponse(1L, "RM-001", "Steel", Unit.KG, bd("100"));
        when(rawMaterialMapper.toResponse(entity)).thenReturn(response);

        List<RawMaterialResponse> result = service.findAll("ste", "001");

        assertThat(result).containsExactly(response);

        verify(rawMaterialRepository).findByNameContainingIgnoreCaseAndCodeContainingIgnoreCase("ste", "001");
        verify(rawMaterialRepository, never()).findAll();
        verify(rawMaterialRepository, never()).findByNameContainingIgnoreCase(anyString());
        verify(rawMaterialRepository, never()).findByCodeContainingIgnoreCase(anyString());
    }

    @Test
    void findAll_shouldReturnEmpty_whenRepositoryReturnsEmpty() {
        when(rawMaterialRepository.findAll()).thenReturn(List.of());

        List<RawMaterialResponse> result = service.findAll(null, null);

        assertThat(result).isEmpty();
        verify(rawMaterialRepository).findAll();
        verifyNoInteractions(rawMaterialMapper);
    }

    @Test
    void findById_shouldReturnResponse_whenExists() {
        RawMaterial entity = rawMaterial(1L, "RM-001", "Steel", Unit.KG, bd("100"));
        when(rawMaterialRepository.findById(1L)).thenReturn(Optional.of(entity));

        RawMaterialResponse response = new RawMaterialResponse(1L, "RM-001", "Steel", Unit.KG, bd("100"));
        when(rawMaterialMapper.toResponse(entity)).thenReturn(response);

        RawMaterialResponse result = service.findById(1L);

        assertThat(result).isEqualTo(response);
        verify(rawMaterialRepository).findById(1L);
        verify(rawMaterialMapper).toResponse(entity);
    }

    @Test
    void findById_shouldThrowNotFound_whenDoesNotExist() {
        when(rawMaterialRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(1L))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(rawMaterialRepository).findById(1L);
        verifyNoInteractions(rawMaterialMapper);
    }

    @Test
    void update_shouldUpdateAndReturnResponse_whenValid() {
        RawMaterial existing = rawMaterial(1L, "RM-001", "Steel", Unit.KG, bd("100"));

        RawMaterialRequest request = new RawMaterialRequest(
                "RM-001",
                "Steel Updated",
                Unit.KG,
                bd("200")
        );

        when(rawMaterialRepository.findById(1L)).thenReturn(Optional.of(existing));

        doAnswer(inv -> {
            RawMaterial target = inv.getArgument(0);
            RawMaterialRequest req = inv.getArgument(1);
            target.setCode(req.code());
            target.setName(req.name());
            target.setUnit(req.unit());
            target.setStockQuantity(req.stockQuantity());
            return null;
        }).when(rawMaterialMapper).updateEntity(existing, request);

        RawMaterialResponse expected = new RawMaterialResponse(1L, "RM-001", "Steel Updated", Unit.KG, bd("200"));
        when(rawMaterialMapper.toResponse(existing)).thenReturn(expected);

        RawMaterialResponse result = service.update(1L, request);

        assertThat(result).isEqualTo(expected);
        assertThat(existing.getName()).isEqualTo("Steel Updated");
        assertThat(existing.getStockQuantity()).isEqualByComparingTo("200");

        verify(rawMaterialRepository).findById(1L);
        verify(rawMaterialMapper).updateEntity(existing, request);
        verify(rawMaterialMapper).toResponse(existing);
    }

    @Test
    void update_shouldThrowNotFound_whenDoesNotExist() {
        when(rawMaterialRepository.findById(1L)).thenReturn(Optional.empty());

        RawMaterialRequest request = new RawMaterialRequest("RM-001", "Steel", Unit.KG, bd("100"));

        assertThatThrownBy(() -> service.update(1L, request))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(rawMaterialRepository).findById(1L);
        verifyNoInteractions(rawMaterialMapper);
    }


    @Test
    void delete_shouldThrowNotFound_whenDoesNotExist() {
        when(rawMaterialRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.delete(1L))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(rawMaterialRepository).findById(1L);
        verify(rawMaterialRepository, never()).delete(any());
    }

    @Test
    void delete_shouldDelete_whenExists() {
        RawMaterial existing = rawMaterial(1L, "RM-001", "Steel", Unit.KG, bd("100"));
        when(rawMaterialRepository.findById(1L)).thenReturn(Optional.of(existing));

        service.delete(1L);

        verify(rawMaterialRepository).findById(1L);
        verify(rawMaterialRepository).delete(existing);
    }

    private static RawMaterial rawMaterial(Long id, String code, String name, Unit unit, BigDecimal stock) {
        RawMaterial rm = new RawMaterial();
        rm.setId(id);
        rm.setCode(code);
        rm.setName(name);
        rm.setUnit(unit);
        rm.setStockQuantity(stock);
        return rm;
    }

    private static BigDecimal bd(String v) {
        return new BigDecimal(v);
    }
}
