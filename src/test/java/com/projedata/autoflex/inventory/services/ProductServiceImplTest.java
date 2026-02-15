package com.projedata.autoflex.inventory.services;

import com.projedata.autoflex.inventory.dtos.product.ProductMaterialDTO;
import com.projedata.autoflex.inventory.dtos.product.ProductRequest;
import com.projedata.autoflex.inventory.dtos.product.ProductResponse;
import com.projedata.autoflex.inventory.entities.Product;
import com.projedata.autoflex.inventory.entities.ProductMaterial;
import com.projedata.autoflex.inventory.entities.RawMaterial;
import com.projedata.autoflex.inventory.entities.enums.Unit;
import com.projedata.autoflex.inventory.exceptions.BusinessRuleException;
import com.projedata.autoflex.inventory.exceptions.ConflictException;
import com.projedata.autoflex.inventory.exceptions.ResourceNotFoundException;
import com.projedata.autoflex.inventory.mappers.ProductMapper;
import com.projedata.autoflex.inventory.repositories.ProductRepository;
import com.projedata.autoflex.inventory.repositories.RawMaterialRepository;
import com.projedata.autoflex.inventory.services.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock private ProductRepository productRepository;
    @Mock private RawMaterialRepository rawMaterialRepository;
    @Mock private ProductMapper productMapper;

    @InjectMocks private ProductServiceImpl service;

    @Test
    void create_shouldThrow_whenMaterialsIsNullOrEmpty() {
        ProductRequest reqNull = new ProductRequest("P-001", "Product A", bd("10"), null);
        ProductRequest reqEmpty = new ProductRequest("P-001", "Product A", bd("10"), List.of());

        assertThatThrownBy(() -> service.create(reqNull))
                .isInstanceOf(BusinessRuleException.class)
                .hasMessageContaining("at least one");

        assertThatThrownBy(() -> service.create(reqEmpty))
                .isInstanceOf(BusinessRuleException.class)
                .hasMessageContaining("at least one");

        verifyNoInteractions(productRepository, rawMaterialRepository, productMapper);
    }

    @Test
    void create_shouldThrowConflict_whenProductCodeAlreadyExists() {
        ProductRequest req = new ProductRequest(
                "P-001", "Product A", bd("10"),
                List.of(new ProductMaterialDTO(1L, bd("2")))
        );

        when(productRepository.existsByCode("P-001")).thenReturn(true);

        assertThatThrownBy(() -> service.create(req))
                .isInstanceOf(ConflictException.class)
                .hasMessageContaining("Product code already exists");

        verify(productRepository).existsByCode("P-001");
        verifyNoMoreInteractions(productRepository);
        verifyNoInteractions(rawMaterialRepository, productMapper);
    }

    @Test
    void create_shouldThrowNotFound_whenAnyRawMaterialDoesNotExist() {
        ProductRequest req = new ProductRequest(
                "P-001", "Product A", bd("10"),
                List.of(
                        new ProductMaterialDTO(1L, bd("2")),
                        new ProductMaterialDTO(99L, bd("1"))
                )
        );

        when(productRepository.existsByCode("P-001")).thenReturn(false);

        RawMaterial rm1 = rawMaterial(1L, "RM-001", "Steel", Unit.KG, bd("100"));

        when(rawMaterialRepository.findAllById(argThat(iter ->
                containsExactlyInAnyOrder(iter, 1L, 99L)
        ))).thenReturn(List.of(rm1));

        assertThatThrownBy(() -> service.create(req))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("raw materials");

        verify(productRepository).existsByCode("P-001");
        verify(rawMaterialRepository).findAllById(any());
        verifyNoInteractions(productMapper);
        verify(productRepository, never()).save(any());
    }

    @Test
    void create_shouldSaveAndReturnResponse_whenValid() {
        ProductRequest req = new ProductRequest(
                "P-001", "Product A", bd("10"),
                List.of(
                        new ProductMaterialDTO(1L, bd("2.5")),
                        new ProductMaterialDTO(2L, bd("1"))
                )
        );

        when(productRepository.existsByCode("P-001")).thenReturn(false);

        RawMaterial rm1 = rawMaterial(1L, "RM-001", "Steel", Unit.KG, bd("100"));
        RawMaterial rm2 = rawMaterial(2L, "RM-002", "Screw", Unit.UNIT, bd("50"));

        when(rawMaterialRepository.findAllById(argThat(iter ->
                containsExactlyInAnyOrder(iter, 1L, 2L)
        ))).thenReturn(List.of(rm1, rm2));

        Product entityToSave = new Product();
        when(productMapper.toEntity(eq(req), anyMap())).thenReturn(entityToSave);

        Product saved = product(10L, "P-001", "Product A", bd("10"));
        when(productRepository.save(entityToSave)).thenReturn(saved);

        ProductResponse expected = mock(ProductResponse.class);
        when(productMapper.toResponse(saved)).thenReturn(expected);

        ProductResponse result = service.create(req);

        assertThat(result).isSameAs(expected);

        verify(productRepository).existsByCode("P-001");

        ArgumentCaptor<Iterable<Long>> idsCaptor = ArgumentCaptor.forClass(Iterable.class);
        verify(rawMaterialRepository).findAllById(idsCaptor.capture());
        assertThat(toList(idsCaptor.getValue())).containsExactlyInAnyOrder(1L, 2L);

        verify(productMapper).toEntity(eq(req), argThat(map ->
                map.size() == 2 && map.containsKey(1L) && map.containsKey(2L)
        ));

        verify(productRepository).save(entityToSave);
        verify(productMapper).toResponse(saved);
    }

    @Test
    void update_shouldThrow_whenMaterialsEmpty() {
        ProductRequest req = new ProductRequest("P-001", "Product A", bd("10"), List.of());

        assertThatThrownBy(() -> service.update(1L, req))
                .isInstanceOf(BusinessRuleException.class)
                .hasMessageContaining("at least one");

        verifyNoInteractions(productRepository, rawMaterialRepository, productMapper);
    }

    @Test
    void update_shouldThrowNotFound_whenProductDoesNotExist() {
        ProductRequest req = new ProductRequest(
                "P-001", "Product A", bd("10"),
                List.of(new ProductMaterialDTO(1L, bd("1")))
        );

        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(1L, req))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(productRepository).findById(1L);
        verifyNoMoreInteractions(productRepository);
        verifyNoInteractions(rawMaterialRepository, productMapper);
    }

    @Test
    void update_shouldThrowConflict_whenChangingCodeToExistingOne() {
        Product existing = product(1L, "P-OLD", "Old", bd("10"));

        ProductRequest req = new ProductRequest(
                "P-NEW", "New", bd("20"),
                List.of(new ProductMaterialDTO(1L, bd("1")))
        );

        when(productRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(productRepository.existsByCode("P-NEW")).thenReturn(true);

        assertThatThrownBy(() -> service.update(1L, req))
                .isInstanceOf(ConflictException.class)
                .hasMessageContaining("Product code already exists");

        verify(productRepository).findById(1L);
        verify(productRepository).existsByCode("P-NEW");
        verifyNoInteractions(rawMaterialRepository, productMapper);
    }

    @Test
    void update_shouldReplaceMaterialsAndFlush_whenValid() {

        Product existing = product(1L, "P-001", "Product A", bd("10"));

        RawMaterial oldRm = rawMaterial(3L, "RM-003", "Old", Unit.UNIT, bd("10"));
        existing.getMaterials().add(productMaterial(existing, oldRm, bd("2")));

        ProductRequest req = new ProductRequest(
                "P-001", "Product A Updated", bd("99"),
                List.of(
                        new ProductMaterialDTO(1L, bd("2")),
                        new ProductMaterialDTO(2L, bd("5"))
                )
        );

        when(productRepository.findById(1L)).thenReturn(Optional.of(existing));

        RawMaterial rm1 = rawMaterial(1L, "RM-001", "Steel", Unit.KG, bd("100"));
        RawMaterial rm2 = rawMaterial(2L, "RM-002", "Screw", Unit.UNIT, bd("50"));

        when(rawMaterialRepository.findAllById(argThat(iter ->
                containsExactlyInAnyOrder(iter, 1L, 2L)
        ))).thenReturn(List.of(rm1, rm2));

        List<ProductMaterial> newMaterials = List.of(
                productMaterial(existing, rm1, bd("2")),
                productMaterial(existing, rm2, bd("5"))
        );

        when(productMapper.toProductMaterials(eq(req.materials()), eq(existing), anyMap()))
                .thenReturn(newMaterials);

        ProductResponse expected = mock(ProductResponse.class);
        when(productMapper.toResponse(existing)).thenReturn(expected);

        ProductResponse result = service.update(1L, req);

        assertThat(result).isSameAs(expected);

        verify(productRepository).findById(1L);

        ArgumentCaptor<Iterable<Long>> idsCaptor = ArgumentCaptor.forClass(Iterable.class);
        verify(rawMaterialRepository).findAllById(idsCaptor.capture());
        assertThat(toList(idsCaptor.getValue())).containsExactlyInAnyOrder(1L, 2L);

        verify(productRepository).flush();

        verify(productMapper).toProductMaterials(eq(req.materials()), eq(existing), anyMap());
        verify(productMapper).toResponse(existing);

        assertThat(existing.getName()).isEqualTo("Product A Updated");
        assertThat(existing.getPrice()).isEqualByComparingTo(bd("99"));
        assertThat(existing.getMaterials()).hasSize(2);
    }

    @Test
    void delete_shouldThrowNotFound_whenProductDoesNotExist() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.delete(1L))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(productRepository).findById(1L);
        verify(productRepository, never()).delete(any());
    }

    @Test
    void delete_shouldDelete_whenExists() {
        Product existing = product(1L, "P-001", "Product A", bd("10"));
        when(productRepository.findById(1L)).thenReturn(Optional.of(existing));

        service.delete(1L);

        verify(productRepository).findById(1L);
        verify(productRepository).delete(existing);
    }

    private static BigDecimal bd(String v) {
        return new BigDecimal(v);
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

    private static ProductMaterial productMaterial(Product product, RawMaterial rm, BigDecimal qty) {
        ProductMaterial pm = new ProductMaterial();
        pm.setProduct(product);
        pm.setRawMaterial(rm);
        pm.setQuantity(qty);
        return pm;
    }

    private static boolean containsExactlyInAnyOrder(Iterable<Long> iterable, Long... expected) {
        List<Long> actual = toList(iterable);
        List<Long> exp = Arrays.asList(expected);
        return actual.size() == exp.size() && actual.containsAll(exp) && exp.containsAll(actual);
    }

    private static List<Long> toList(Iterable<Long> iterable) {
        List<Long> list = new ArrayList<>();
        if (iterable != null) iterable.forEach(list::add);
        return list;
    }
}
