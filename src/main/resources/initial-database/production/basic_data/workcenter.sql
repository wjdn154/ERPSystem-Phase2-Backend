-- Workcenter 초기 데이터 (warehouse_type이 FACTORY인 작업장들만 포함)
INSERT INTO basic_data_workcenter (id, process_id, warehouse_id, code, description, name, workcenter_type, is_active)
VALUES
-- WH002에 속한 작업장들 (FACTORY)
(1, 1, 2, 'WC001', '금속을 변형시키는 작업', '프레스 작업장', 'PRESS', TRUE),
(2, 2, 2, 'WC002', '부품을 조립하는 작업', '조립 작업장', 'ASSEMBLY', TRUE),
(3, 3, 2, 'WC003', '금속을 녹여 주형에 붓는 작업', '주조 작업장', 'CASTING', TRUE),
(4, 4, 2, 'WC004', '금속 부품을 용접하는 작업', '용접 작업장', 'WELDING', TRUE),
(5, 5, 2, 'WC005', '부품에 도장 처리를 하는 작업', '도장 작업장', 'PAINT', TRUE),

-- WH003에 속한 작업장들 (FACTORY)
(6, 6, 3, 'WC006', '플라스틱 성형 작업을 진행하는 작업', '플라스틱 성형 작업장', 'PLASTIC_MOLDING', TRUE),
(7, 7, 3, 'WC007', '제품의 품질을 검사하는 작업', '품질 검사 작업장', 'QUALITY_INSPECTION', TRUE),
(8, 8, 3, 'WC008', '금속을 단조로 변형시키는 작업', '단조 작업장', 'FORGING', TRUE),
(9, 9, 3, 'WC009', '금속을 고온으로 가열하는 작업', '열처리 작업장', 'HEAT_TREATMENT', TRUE),
(10, 10, 3, 'WC010', '정밀한 기계 가공을 수행하는 작업', '기계 가공 작업장', 'MACHINING', TRUE),

-- WH004에 속한 작업장들 (FACTORY)
(11, 11, 4, 'WC011', '금속을 변형시키는 작업', '프레스 작업장', 'PRESS', TRUE),
(12, 12, 4, 'WC012', '부품을 조립하는 작업', '조립 작업장', 'ASSEMBLY', TRUE),
(13, 13, 4, 'WC013', '금속을 녹여 주형에 붓는 작업', '주조 작업장', 'CASTING', TRUE),
(14, 14, 4, 'WC014', '금속 부품을 용접하는 작업', '용접 작업장', 'WELDING', TRUE),
(15, 15, 4, 'WC015', '부품에 도장 처리를 하는 작업', '도장 작업장', 'PAINT', TRUE),
(16, 16, 4, 'WC016', '플라스틱 성형 작업을 진행하는 작업', '플라스틱 성형 작업장', 'PLASTIC_MOLDING', TRUE),
(17, 17, 4, 'WC017', '제품의 품질을 검사하는 작업', '품질 검사 작업장', 'QUALITY_INSPECTION', TRUE),
(18, 18, 4, 'WC018', '금속을 단조로 변형시키는 작업', '단조 작업장', 'FORGING', TRUE),
(19, 19, 4, 'WC019', '금속을 고온으로 가열하는 작업', '열처리 작업장', 'HEAT_TREATMENT', TRUE),
(20, 20, 4, 'WC020', '정밀한 기계 가공을 수행하는 작업', '기계 가공 작업장', 'MACHINING', TRUE);

-- Workcenter에 프로세스 ID 업데이트 (생산 공정과 작업장 연결)
UPDATE basic_data_workcenter
SET process_id = (SELECT process_id FROM process_routing_process_details WHERE process_code = 'PRC001')
WHERE code = 'WC001';

UPDATE basic_data_workcenter
SET process_id = (SELECT process_id FROM process_routing_process_details WHERE process_code = 'PRC002')
WHERE code = 'WC002';

UPDATE basic_data_workcenter
SET process_id = (SELECT process_id FROM process_routing_process_details WHERE process_code = 'PRC003')
WHERE code = 'WC003';

UPDATE basic_data_workcenter
SET process_id = (SELECT process_id FROM process_routing_process_details WHERE process_code = 'PRC004')
WHERE code = 'WC004';

UPDATE basic_data_workcenter
SET process_id = (SELECT process_id FROM process_routing_process_details WHERE process_code = 'PRC005')
WHERE code = 'WC005';

UPDATE basic_data_workcenter
SET process_id = (SELECT process_id FROM process_routing_process_details WHERE process_code = 'PRC006')
WHERE code = 'WC006';

UPDATE basic_data_workcenter
SET process_id = (SELECT process_id FROM process_routing_process_details WHERE process_code = 'PRC007')
WHERE code = 'WC007';

UPDATE basic_data_workcenter
SET process_id = (SELECT process_id FROM process_routing_process_details WHERE process_code = 'PRC008')
WHERE code = 'WC008';

UPDATE basic_data_workcenter
SET process_id = (SELECT process_id FROM process_routing_process_details WHERE process_code = 'PRC009')
WHERE code = 'WC009';

UPDATE basic_data_workcenter
SET process_id = (SELECT process_id FROM process_routing_process_details WHERE process_code = 'PRC010')
WHERE code = 'WC010';