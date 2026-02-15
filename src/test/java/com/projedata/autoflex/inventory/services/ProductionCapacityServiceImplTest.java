package com.projedata.autoflex.inventory.services;

import com.projedata.autoflex.inventory.entities.Product;
import com.projedata.autoflex.inventory.entities.ProductMaterial;
import com.projedata.autoflex.inventory.entities.RawMaterial;
import com.projedata.autoflex.inventory.mappers.ProductionMapper;
import com.projedata.autoflex.inventory.repositories.ProductRepository;
import com.projedata.autoflex.inventory.services.impl.ProductionCapacityServiceImpl;
import com.projedata.autoflex.inventory.dtos.production.ProductionCapacityResponse;
import com.projedata.autoflex.inventory.entities.enums.Unit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductionCapacityServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductionMapper productionMapper;

    @InjectMocks
    private ProductionCapacityServiceImpl service;

    @Test
    void findProductionCapacity_shouldReturnEmpty_whenNoProducts() {
        when(productRepository.findAllWithMaterialsAndRawMaterialsFiltered("", "")).thenReturn(List.of());

        List<ProductionCapacityResponse> result = service.findProductionCapacity();

        assertThat(result).isEmpty();
        verify(productRepository).findAllWithMaterialsAndRawMaterialsFiltered("", "");
        verifyNoInteractions(productionMapper);
    }

    @Test
    void findProductionCapacity_shouldCalculateMaxQuantity_singleMaterial() {
        Product p = product(1L, "P-001", "Product A", bd("50.0"));
        RawMaterial steel = rawMaterial(10L, "RM-010", "Steel", Unit.KG, bd("100"));
        p.getMaterials().add(productMaterial(p, steel, bd("2.5")));

        when(productRepository.findAllWithMaterialsAndRawMaterialsFiltered("", "")).thenReturn(List.of(p));

        when(productionMapper.toResponse(any(Product.class), anyInt()))
                .thenAnswer(inv -> mock(ProductionCapacityResponse.class));

        service.findProductionCapacity();

        ArgumentCaptor<Integer> maxQtyCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(productionMapper).toResponse(eq(p), maxQtyCaptor.capture());

        assertThat(maxQtyCaptor.getValue()).isEqualTo(40);
    }

    @Test
    void findProductionCapacity_shouldUseMinAcrossMaterials_multipleMaterials() {
        // Product requires:
        // Steel: 5 KG, stock 20 => 4
        // Screws: 8 UNIT, stock 31 => 3
        // So max should be min(4,3) = 3
        Product p = product(2L, "P-002", "Product B", bd("300.0"));
        RawMaterial steel = rawMaterial(10L, "RM-010", "Steel", Unit.KG, bd("20"));
        RawMaterial screw = rawMaterial(11L, "RM-011", "Screw", Unit.UNIT, bd("31"));

        p.getMaterials().add(productMaterial(p, steel, bd("5")));
        p.getMaterials().add(productMaterial(p, screw, bd("8")));

        when(productRepository.findAllWithMaterialsAndRawMaterialsFiltered("", "")).thenReturn(List.of(p));
        when(productionMapper.toResponse(any(Product.class), anyInt()))
                .thenAnswer(inv -> mock(ProductionCapacityResponse.class));

        service.findProductionCapacity();

        ArgumentCaptor<Integer> maxQtyCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(productionMapper).toResponse(eq(p), maxQtyCaptor.capture());

        assertThat(maxQtyCaptor.getValue()).isEqualTo(3);
    }

    @Test
    void findProductionCapacity_shouldReturnZero_whenAnyMaterialStockIsZero() {
        Product p = product(3L, "P-003", "Product C", bd("45.0"));
        RawMaterial paint = rawMaterial(12L, "RM-012", "Paint", Unit.L, bd("0"));

        p.getMaterials().add(productMaterial(p, paint, bd("1")));

        when(productRepository.findAllWithMaterialsAndRawMaterialsFiltered("", "")).thenReturn(List.of(p));
        when(productionMapper.toResponse(any(Product.class), anyInt()))
                .thenAnswer(inv -> mock(ProductionCapacityResponse.class));

        service.findProductionCapacity();

        ArgumentCaptor<Integer> maxQtyCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(productionMapper).toResponse(eq(p), maxQtyCaptor.capture());

        assertThat(maxQtyCaptor.getValue()).isZero();
    }

    private static Product product(Long id, String code, String name, BigDecimal price) {
        Product p = new Product();
        p.setId(id);
        p.setCode(code);
        p.setName(name);
        p.setPrice(price);
        return p;
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

    private static ProductMaterial productMaterial(Product product, RawMaterial rm, BigDecimal requiredPerUnit) {
        ProductMaterial pm = new ProductMaterial();
        pm.setProduct(product);
        pm.setRawMaterial(rm);
        pm.setQuantity(requiredPerUnit);
        return pm;
    }

    private static BigDecimal bd(String v) {
        return new BigDecimal(v);
    }
}
