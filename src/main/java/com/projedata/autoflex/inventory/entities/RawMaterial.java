package com.projedata.autoflex.inventory.entities;

import com.projedata.autoflex.inventory.entities.enums.Unit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "raw_materials")
public class RawMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Unit unit;

    @Column(nullable = false)
    private float stockQuantity;

    @OneToMany(mappedBy = "rawMaterial")
    private List<ProductMaterial> products = new ArrayList<>();

}
