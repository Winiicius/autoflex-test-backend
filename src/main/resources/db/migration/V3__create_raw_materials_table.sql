CREATE TABLE raw_materials (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(50) NOT NULL,
    name VARCHAR(120) NOT NULL,
    unit VARCHAR(20) NOT NULL,
    stock_quantity NUMERIC(10,2) NOT NULL
);

CREATE UNIQUE INDEX uk_raw_materials_code ON raw_materials (code);
CREATE UNIQUE INDEX uk_raw_materials_name ON raw_materials (name);
