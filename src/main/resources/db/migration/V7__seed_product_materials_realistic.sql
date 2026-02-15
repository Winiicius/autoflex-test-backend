INSERT INTO product_materials (product_id, raw_material_id, quantity)
SELECT p.id, rm.id, x.qty
FROM (VALUES
('P-010','RM-010',22.00),
('P-010','RM-011',10.00),
('P-010','RM-019',1.20),
('P-010','RM-020',60.00),
('P-010','RM-021',16.00),
('P-010','RM-022',16.00),
('P-010','RM-024',1.00)
) AS x(p_code, rm_code, qty)
JOIN products p ON p.code = x.p_code
JOIN raw_materials rm ON rm.code = x.rm_code
ON CONFLICT DO NOTHING;

INSERT INTO product_materials (product_id, raw_material_id, quantity)
SELECT p.id, rm.id, x.qty
FROM (VALUES
('P-011','RM-010',14.00),
('P-011','RM-011',6.00),
('P-011','RM-019',0.80),
('P-011','RM-020',40.00),
('P-011','RM-021',10.00),
('P-011','RM-022',10.00)
) AS x(p_code, rm_code, qty)
JOIN products p ON p.code = x.p_code
JOIN raw_materials rm ON rm.code = x.rm_code
ON CONFLICT DO NOTHING;

INSERT INTO product_materials (product_id, raw_material_id, quantity)
SELECT p.id, rm.id, x.qty
FROM (VALUES
('P-012','RM-014',2.40),
('P-012','RM-020',2.00),
('P-012','RM-024',1.00)
) AS x(p_code, rm_code, qty)
JOIN products p ON p.code = x.p_code
JOIN raw_materials rm ON rm.code = x.rm_code
ON CONFLICT DO NOTHING;

INSERT INTO product_materials (product_id, raw_material_id, quantity)
SELECT p.id, rm.id, x.qty
FROM (VALUES
('P-013','RM-015',18.00),
('P-013','RM-016',4.00),
('P-013','RM-023',0.60),
('P-013','RM-020',24.00),
('P-013','RM-021',8.00),
('P-013','RM-022',8.00),
('P-013','RM-024',1.00)
) AS x(p_code, rm_code, qty)
JOIN products p ON p.code = x.p_code
JOIN raw_materials rm ON rm.code = x.rm_code
ON CONFLICT DO NOTHING;

INSERT INTO product_materials (product_id, raw_material_id, quantity)
SELECT p.id, rm.id, x.qty
FROM (VALUES
('P-014','RM-017',1.60),
('P-014','RM-011',1.50),
('P-014','RM-020',12.00),
('P-014','RM-021',4.00),
('P-014','RM-022',4.00)
) AS x(p_code, rm_code, qty)
JOIN products p ON p.code = x.p_code
JOIN raw_materials rm ON rm.code = x.rm_code
ON CONFLICT DO NOTHING;

INSERT INTO product_materials (product_id, raw_material_id, quantity)
SELECT p.id, rm.id, x.qty
FROM (VALUES
('P-015','RM-018',0.80),
('P-015','RM-020',6.00),
('P-015','RM-024',1.00)
) AS x(p_code, rm_code, qty)
JOIN products p ON p.code = x.p_code
JOIN raw_materials rm ON rm.code = x.rm_code
ON CONFLICT DO NOTHING;

INSERT INTO product_materials (product_id, raw_material_id, quantity)
SELECT p.id, rm.id, x.qty
FROM (VALUES
('P-016','RM-013',1.80),
('P-016','RM-012',0.70),
('P-016','RM-020',10.00),
('P-016','RM-024',1.00)
) AS x(p_code, rm_code, qty)
JOIN products p ON p.code = x.p_code
JOIN raw_materials rm ON rm.code = x.rm_code
ON CONFLICT DO NOTHING;

INSERT INTO product_materials (product_id, raw_material_id, quantity)
SELECT p.id, rm.id, x.qty
FROM (VALUES
('P-017','RM-015',6.00),
('P-017','RM-020',14.00),
('P-017','RM-021',4.00),
('P-017','RM-022',4.00)
) AS x(p_code, rm_code, qty)
JOIN products p ON p.code = x.p_code
JOIN raw_materials rm ON rm.code = x.rm_code
ON CONFLICT DO NOTHING;

INSERT INTO product_materials (product_id, raw_material_id, quantity)
SELECT p.id, rm.id, x.qty
FROM (VALUES
('P-018','RM-010',6.00),
('P-018','RM-011',4.00),
('P-018','RM-019',0.90),
('P-018','RM-020',18.00)
) AS x(p_code, rm_code, qty)
JOIN products p ON p.code = x.p_code
JOIN raw_materials rm ON rm.code = x.rm_code
ON CONFLICT DO NOTHING;

INSERT INTO product_materials (product_id, raw_material_id, quantity)
SELECT p.id, rm.id, x.qty
FROM (VALUES
('P-019','RM-015',12.00),
('P-019','RM-016',6.00),
('P-019','RM-023',0.80),
('P-019','RM-020',30.00),
('P-019','RM-021',8.00),
('P-019','RM-022',8.00)
) AS x(p_code, rm_code, qty)
JOIN products p ON p.code = x.p_code
JOIN raw_materials rm ON rm.code = x.rm_code
ON CONFLICT DO NOTHING;

INSERT INTO product_materials (product_id, raw_material_id, quantity)
SELECT p.id, rm.id, x.qty
FROM (VALUES
('P-020','RM-024',1.00)
) AS x(p_code, rm_code, qty)
JOIN products p ON p.code = x.p_code
JOIN raw_materials rm ON rm.code = x.rm_code
ON CONFLICT DO NOTHING;

INSERT INTO product_materials (product_id, raw_material_id, quantity)
SELECT p.id, rm.id, x.qty
FROM (VALUES
('P-021','RM-011',2.00),
('P-021','RM-020',8.00),
('P-021','RM-021',4.00),
('P-021','RM-022',4.00)
) AS x(p_code, rm_code, qty)
JOIN products p ON p.code = x.p_code
JOIN raw_materials rm ON rm.code = x.rm_code
ON CONFLICT DO NOTHING;