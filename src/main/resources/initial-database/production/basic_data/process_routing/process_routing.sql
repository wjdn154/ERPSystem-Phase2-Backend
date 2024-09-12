INSERT INTO process_routing
(id, process_routing_code, name, description, is_standard, is_active)
VALUES
(1, 'ROUT001', '루트 A', '제품 A의 제조 과정', true, true),
(2, 'ROUT002', '루트 B', '제품 B의 제조 과정', false, true),
(3, 'ROUT003', '루트 C', '제품 C의 제조 과정', true, false),
(4, 'ROUT004', '루트 D', '제품 D의 제조 과정', false, true),
(5, 'ROUT005', '루트 E', '제품 E의 제조 과정', true, true),
(6, 'ROUT006', '루트 F', '제품 F의 제조 과정', false, true),
(7, 'ROUT007', '루트 G', '제품 G의 제조 과정', true, false),
(8, 'ROUT008', '루트 H', '제품 H의 제조 과정', false, true),
(9, 'ROUT009', '루트 I', '제품 I의 제조 과정', true, true),
(10, 'ROUT010', '루트 J', '제품 J의 제조 과정', false, false);

-- processDetailsl, routingStep 함께 확인