-- material input status id column 제거

INSERT INTO production_material_data
(material_code, material_name, material_type, purchase_price, stock_quantity, client_id)
VALUES
    ('PRD-MAT-001', '알루미늄 판', 'METAL', 10000, 10, 1),
    ('PRD-MAT-002', '구리 선', 'METAL', 400000, 1000, 2),
    ('PRD-MAT-003', '유리 패널', 'GLASS', 100000, 150, 3),
    ('PRD-MAT-004', '나무 판자', 'WOOD', 20000, 100, 4),
    ('PRD-MAT-005', '철제 막대', 'METAL', 505000, 77, 5),
    ('PRD-MAT-006', '실리콘 실란트', 'CHEMICAL', 10000, 50000, 6),
    ('PRD-MAT-007', '나일론 로프', 'TEXTILE', 10000, 8000, 7),
    ('PRD-MAT-008', 'PVC 파이프', 'PLASTIC', 10000, 150000, 8),
    ('PRD-MAT-009', '아연 판', 'METAL', 10000, 20, 9),
    ('PRD-MAT-010', '탄소 섬유', 'COMPOSITE', 110000, 40, 10);