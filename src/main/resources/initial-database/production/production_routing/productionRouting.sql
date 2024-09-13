INSERT INTO routing_management_production_routing
(id, company_id, production_routing_code, name, description, is_standard, is_active)
VALUES
(1, 1, 'ROUT001', '루트 A', '제품 A의 제조 과정', true, true),
(2, 1, 'ROUT002', '루트 B', '제품 B의 제조 과정', false, true),
(3, 1, 'ROUT003', '루트 C', '제품 C의 제조 과정', true, false),
(4, 1, 'ROUT004', '루트 D', '제품 D의 제조 과정', false, true),
(5, 1, 'ROUT005', '루트 E', '제품 E의 제조 과정', true, true),
(6, 1, 'ROUT006', '루트 F', '제품 F의 제조 과정', false, true),
(7, 1, 'ROUT007', '루트 G', '제품 G의 제조 과정', true, false),
(8, 1, 'ROUT008', '루트 H', '제품 H의 제조 과정', false, true),
(9, 1, 'ROUT009', '루트 I', '제품 I의 제조 과정', true, true),
(10, 1, 'ROUT010', '루트 J', '제품 J의 제조 과정', false, false);

-- processDetailsl, routingStep 함께 확인