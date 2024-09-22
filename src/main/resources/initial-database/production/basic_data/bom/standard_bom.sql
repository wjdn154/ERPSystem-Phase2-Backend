-- StandardBom 테이블 초기 데이터 삽입 (상위/하위 관계 설정)
INSERT INTO bom_standard_bom (id, code, version, created_date, remarks, loss_rate, outsourcing_type, start_date, expired_date, is_active, parent_bom_id, parent_product_id)
VALUES
-- 제품 A는 부품 F, G, O를 자식으로 가짐
(1, 'BOM001', 1.0, NOW(), '제품 A에 대한 전체 외주 처리', 0.05, 'FULL_OUTSOURCING', '2024-01-01', '2024-12-31', true, NULL, 1),  -- 상위 BOM: 제품 A
(2, 'BOM002', 1.0, NOW(), '제품 A에 대한 부품 F 연결', 0.02, 'PARTIAL_OUTSOURCING', '2024-01-01', '2024-12-31', true, 1, 6),  -- 제품 A -> 부품 F
(3, 'BOM003', 1.0, NOW(), '제품 A에 대한 부품 G 연결', 0.02, 'PARTIAL_OUTSOURCING', '2024-01-01', '2024-12-31', true, 1, 7),  -- 제품 A -> 부품 G
(4, 'BOM004', 1.0, NOW(), '제품 A에 대한 부품 O 연결', 0.02, 'PARTIAL_OUTSOURCING', '2024-01-01', '2024-12-31', true, 1, 15),  -- 제품 A -> 부품 O

-- 제품 B는 부품 H, I, N을 자식으로 가짐
(5, 'BOM005', 1.0, NOW(), '제품 B에 대한 부품 H 연결', 0.03, 'SUBCONTRACTING', '2024-01-01', '2024-12-31', true, NULL, 2),  -- 상위 BOM: 제품 B
(6, 'BOM006', 1.0, NOW(), '제품 B에 대한 부품 I 연결', 0.04, 'SUBCONTRACTING', '2024-01-01', '2024-12-31', true, 5, 8),  -- 제품 B -> 부품 H
(7, 'BOM007', 1.0, NOW(), '제품 B에 대한 부품 N 연결', 0.05, 'JOINT_PRODUCTION', '2024-01-01', '2024-12-31', true, 5, 14),  -- 제품 B -> 부품 N

-- 제품 C는 부품 J와 O를 자식으로 가짐
(8, 'BOM008', 1.0, NOW(), '제품 C에 대한 부품 J 연결', 0.06, 'JOINT_PRODUCTION', '2024-01-01', '2024-12-31', true, NULL, 3),  -- 상위 BOM: 제품 C
(9, 'BOM009', 1.0, NOW(), '제품 C에 대한 부품 O 연결', 0.03, 'QUALITY_INSPECTION_OUTSOURCING', '2024-01-01', '2024-12-31', true, 8, 15),  -- 제품 C -> 부품 O

-- 제품 D는 부품 F, G를 자식으로 가짐
(10, 'BOM010', 1.0, NOW(), '제품 D에 대한 부품 F 연결', 0.01, 'QUALITY_INSPECTION_OUTSOURCING', '2024-01-01', '2024-12-31', true, NULL, 4),  -- 상위 BOM: 제품 D
(11, 'BOM011', 1.0, NOW(), '제품 D에 대한 부품 G 연결', 0.07, 'JOINT_PRODUCTION', '2024-01-01', '2024-12-31', true, 10, 7),  -- 제품 D -> 부품 G

-- 제품 E는 부품 H, I, O를 자식으로 가짐
(12, 'BOM012', 1.0, NOW(), '제품 E에 대한 부품 H 연결', 0.02, 'QUALITY_INSPECTION_OUTSOURCING', '2024-01-01', '2024-12-31', true, NULL, 5),  -- 상위 BOM: 제품 E
(13, 'BOM013', 1.0, NOW(), '제품 E에 대한 부품 I 연결', 0.01, 'JOINT_PRODUCTION', '2024-01-01', '2024-12-31', true, 12, 9),  -- 제품 E -> 부품 I
(14, 'BOM014', 1.0, NOW(), '제품 E에 대한 부품 O 연결', 0.01, 'JOINT_PRODUCTION', '2024-01-01', '2024-12-31', true, 12, 15),

-- 제품 K는 부품 F, J를 자식으로 가짐
(15, 'BOM015', 1.0, NOW(), '제품 K에 대한 부품 F 연결', 0.05, 'FULL_OUTSOURCING', '2024-01-01', '2024-12-31', true, NULL, 11),  -- 상위 BOM: 제품 K
(16, 'BOM016', 1.0, NOW(), '제품 K에 대한 부품 J 연결', 0.02, 'PARTIAL_OUTSOURCING', '2024-01-01', '2024-12-31', true, 15, 10),

-- 제품 L은 부품 F, G를 자식으로 가짐
(17, 'BOM017', 1.0, NOW(), '제품 L에 대한 부품 F 연결', 0.03, 'PARTIAL_OUTSOURCING', '2024-01-01', '2024-12-31', true, NULL, 12),  -- 상위 BOM: 제품 L (부품 F 연결)
(18, 'BOM018', 1.0, NOW(), '제품 L에 대한 부품 G 연결', 0.02, 'JOINT_PRODUCTION', '2024-01-01', '2024-12-31', true, 17, 7),  -- 상위 BOM이 17인 제품 L에 부품 G 연결

-- 제품 M은 부품 N을 자식으로 가짐
(19, 'BOM019', 1.0, NOW(), '제품 M에 대한 부품 N 연결', 0.04, 'QUALITY_INSPECTION_OUTSOURCING', '2024-01-01', '2024-12-31', true, NULL, 13);  -- 상위 BOM: 제품 M (부품 N 연결)
