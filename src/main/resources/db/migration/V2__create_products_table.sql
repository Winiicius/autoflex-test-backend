CREATE TABLE products (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(50) NOT NULL,
    name VARCHAR(120) NOT NULL,
    price NUMERIC(10,2) NOT NULL
);

CREATE UNIQUE INDEX uk_products_code ON products (code);
CREATE UNIQUE INDEX uk_products_name ON products (name);
