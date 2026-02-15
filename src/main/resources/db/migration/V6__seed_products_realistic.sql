INSERT INTO products (code, name, price) VALUES
('P-010','Metal Workbench 120cm',899.90),
('P-011','Metal Shelf 5 Levels',349.90),
('P-012','Plastic Storage Bin 60L',79.90),
('P-013','Wooden Desk 120cm',699.90),
('P-014','Glass Door Panel',459.90),
('P-015','Acrylic Display Stand',129.90),
('P-016','Toolbox Pro 40cm',219.90),
('P-017','Dining Chair Basic',189.90),
('P-018','Industrial Side Table',249.90),
('P-019','Wall Mounted Cabinet',529.90),
('P-020','Packaging Kit Standard',14.90),
('P-021','Metal Frame Small',99.90)
ON CONFLICT DO NOTHING;