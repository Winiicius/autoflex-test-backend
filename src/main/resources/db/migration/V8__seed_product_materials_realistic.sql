INSERT INTO product_materials (product_id, raw_material_id, quantity)
SELECT p.id, rm.id, x.qty
FROM (VALUES
  ('P-010','RM-010',22.00),
  ('P-010','RM-011',10.00),
  ('P-010','RM-017',1.20),
  ('P-010','RM-018',0.30),
  ('P-010','RM-023',60.00),
  ('P-010','RM-024',16.00),
  ('P-010','RM-025',16.00),
  ('P-010','RM-026',4.00),
  ('P-010','RM-022',1.00),
  ('P-010','RM-021',1.00)
) AS x(p_code, rm_code, qty)
JOIN products p ON p.code = x.p_code
JOIN raw_materials rm ON rm.code = x.rm_code;

INSERT INTO product_materials (product_id, raw_material_id, quantity)
SELECT p.id, rm.id, x.qty
FROM (VALUES
  ('P-011','RM-010',14.00),
  ('P-011','RM-011',6.00),
  ('P-011','RM-017',0.80),
  ('P-011','RM-018',0.20),
  ('P-011','RM-023',40.00),
  ('P-011','RM-024',10.00),
  ('P-011','RM-025',10.00),
  ('P-011','RM-026',4.00)
) AS x(p_code, rm_code, qty)
JOIN products p ON p.code = x.p_code
JOIN raw_materials rm ON rm.code = x.rm_code;

INSERT INTO product_materials (product_id, raw_material_id, quantity)
SELECT p.id, rm.id, x.qty
FROM (VALUES
  ('P-012','RM-014',2.40),
  ('P-012','RM-023',2.00),
  ('P-012','RM-022',1.00),
  ('P-012','RM-021',1.00)
) AS x(p_code, rm_code, qty)
JOIN products p ON p.code = x.p_code
JOIN raw_materials rm ON rm.code = x.rm_code;

INSERT INTO product_materials (product_id, raw_material_id, quantity)
SELECT p.id, rm.id, x.qty
FROM (VALUES
  ('P-013','RM-015',18.00),
  ('P-013','RM-016',0.60),
  ('P-013','RM-023',24.00),
  ('P-013','RM-024',8.00),
  ('P-013','RM-025',8.00),
  ('P-013','RM-026',4.00),
  ('P-013','RM-022',1.00),
  ('P-013','RM-021',1.00)
) AS x(p_code, rm_code, qty)
JOIN products p ON p.code = x.p_code
JOIN raw_materials rm ON rm.code = x.rm_code;

INSERT INTO product_materials (product_id, raw_material_id, quantity)
SELECT p.id, rm.id, x.qty
FROM (VALUES
  ('P-014','RM-010',6.00),
  ('P-014','RM-011',4.00),
  ('P-014','RM-017',0.90),
  ('P-014','RM-018',0.15),
  ('P-014','RM-020',120.00),
  ('P-014','RM-023',18.00)
) AS x(p_code, rm_code, qty)
JOIN products p ON p.code = x.p_code
JOIN raw_materials rm ON rm.code = x.rm_code;

INSERT INTO product_materials (product_id, raw_material_id, quantity)
SELECT p.id, rm.id, x.qty
FROM (VALUES
  ('P-015','RM-013',1.80),
  ('P-015','RM-012',0.70),
  ('P-015','RM-020',80.00),
  ('P-015','RM-023',10.00),
  ('P-015','RM-022',1.00),
  ('P-015','RM-021',1.00)
) AS x(p_code, rm_code, qty)
JOIN products p ON p.code = x.p_code
JOIN raw_materials rm ON rm.code = x.rm_code;

INSERT INTO product_materials (product_id, raw_material_id, quantity)
SELECT p.id, rm.id, x.qty
FROM (VALUES
  ('P-016','RM-015',6.00),
  ('P-016','RM-023',14.00),
  ('P-016','RM-024',4.00),
  ('P-016','RM-025',4.00),
  ('P-016','RM-026',4.00)
) AS x(p_code, rm_code, qty)
JOIN products p ON p.code = x.p_code
JOIN raw_materials rm ON rm.code = x.rm_code;

INSERT INTO product_materials (product_id, raw_material_id, quantity)
SELECT p.id, rm.id, x.qty
FROM (VALUES
  ('P-017','RM-015',12.00),
  ('P-017','RM-016',0.80),
  ('P-017','RM-023',30.00),
  ('P-017','RM-024',8.00),
  ('P-017','RM-025',8.00),
  ('P-017','RM-026',4.00),
  ('P-017','RM-021',1.00),
  ('P-017','RM-022',1.00)
) AS x(p_code, rm_code, qty)
JOIN products p ON p.code = x.p_code
JOIN raw_materials rm ON rm.code = x.rm_code;

INSERT INTO product_materials (product_id, raw_material_id, quantity)
SELECT p.id, rm.id, x.qty
FROM (VALUES
  ('P-018','RM-011',2.00),
  ('P-018','RM-023',8.00),
  ('P-018','RM-024',4.00),
  ('P-018','RM-025',4.00),
  ('P-018','RM-017',0.20)
) AS x(p_code, rm_code, qty)
JOIN products p ON p.code = x.p_code
JOIN raw_materials rm ON rm.code = x.rm_code;

INSERT INTO product_materials (product_id, raw_material_id, quantity)
SELECT p.id, rm.id, x.qty
FROM (VALUES
  ('P-019','RM-027',600.00),
  ('P-019','RM-028',120.00),
  ('P-019','RM-029',0.20),
  ('P-019','RM-021',1.00)
) AS x(p_code, rm_code, qty)
JOIN products p ON p.code = x.p_code
JOIN raw_materials rm ON rm.code = x.rm_code;

INSERT INTO product_materials (product_id, raw_material_id, quantity)
SELECT p.id, rm.id, x.qty
FROM (VALUES
  ('P-020','RM-029',0.50),
  ('P-020','RM-020',150.00),
  ('P-020','RM-021',1.00)
) AS x(p_code, rm_code, qty)
JOIN products p ON p.code = x.p_code
JOIN raw_materials rm ON rm.code = x.rm_code;

INSERT INTO product_materials (product_id, raw_material_id, quantity)
SELECT p.id, rm.id, x.qty
FROM (VALUES
  ('P-021','RM-022',1.00),
  ('P-021','RM-021',1.00)
) AS x(p_code, rm_code, qty)
JOIN products p ON p.code = x.p_code
JOIN raw_materials rm ON rm.code = x.rm_code;
