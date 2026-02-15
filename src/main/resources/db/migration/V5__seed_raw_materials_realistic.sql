INSERT INTO raw_materials (code, name, unit, stock_quantity) VALUES
('RM-010','Steel Sheet 1mm','KG',420.00),
('RM-011','Steel Tube 20mm','KG',180.00),
('RM-012','Aluminum Profile','KG',95.00),
('RM-013','ABS Plastic Pellets','KG',260.00),
('RM-014','Polypropylene Pellets','KG',0.00),
('RM-015','Wood Plank Pine','KG',140.00),
('RM-016','Plywood 18mm','M2',60.00),
('RM-017','Tempered Glass 6mm','M2',25.00),
('RM-018','Acrylic Sheet 3mm','M2',10.00),
('RM-019','Powder Paint Black','KG',12.00),
('RM-020','Screws M4','UNIT',900.00),
('RM-021','Bolts M6','UNIT',250.00),
('RM-022','Nuts M6','UNIT',220.00),
('RM-023','Wood Glue','KG',3.50),
('RM-024','Cardboard Box Medium','UNIT',0.00)
ON CONFLICT DO NOTHING;