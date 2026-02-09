CREATE TABLE product_materials (
    id BIGSERIAL PRIMARY KEY,
    product_id BIGINT NOT NULL,
    raw_material_id BIGINT NOT NULL,
    quantity NUMERIC(10,2) NOT NULL,

    CONSTRAINT fk_product_materials_product
        FOREIGN KEY (product_id)
        REFERENCES products (id)
        ON DELETE CASCADE,

    CONSTRAINT fk_product_materials_raw_material
        FOREIGN KEY (raw_material_id)
        REFERENCES raw_materials (id)
        ON DELETE RESTRICT
);

CREATE UNIQUE INDEX uk_product_materials_product_raw
ON product_materials (product_id, raw_material_id);
