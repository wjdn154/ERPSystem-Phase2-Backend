-- Workcenter 초기 데이터 (warehouse_type이 FACTORY인 공장들만 포함)
INSERT INTO basic_data_workcenter (id, company_id, process_id, warehouse_id, code, description, name, workcenter_type, is_active)
VALUES
-- WH002에 속한 작업장들 (FACTORY)
(1, 1, 1, 2, 'WC001', '프레스 작업장', '프레스 공장 WH002', 'PRESS', TRUE),
(2, 1, 2, 2, 'WC002', '조립 작업장', '조립 공장 WH002', 'ASSEMBLY', TRUE),
(3, 1, 3, 2, 'WC003', '주조 작업장', '주조 공장 WH002', 'CASTING', TRUE),
(4, 2, 4, 2, 'WC004', '용접 작업장', '용접 공장 WH002', 'WELDING', TRUE),
(5, 2, 5, 2, 'WC005', '도장 작업장', '도장 공장 WH002', 'PAINT', TRUE),

-- WH003에 속한 작업장들 (FACTORY)
(6, 1, 6, 3, 'WC006', '플라스틱 성형 작업장', '플라스틱 공장 WH003', 'PLASTIC_MOLDING', TRUE),
(7, 1, 7, 3, 'WC007', '품질 검사 작업장', '품질 검사 공장 WH003', 'QUALITY_INSPECTION', TRUE),
(8, 1, 8, 3, 'WC008', '단조 작업장', '단조 공장 WH003', 'FORGING', TRUE),
(9, 2, 9, 3, 'WC009', '열처리 작업장', '열처리 공장 WH003', 'HEAT_TREATMENT', TRUE),
(10, 2, 10, 3, 'WC010', '기계 가공 작업장', '기계 가공 공장 WH003', 'MACHINING', TRUE),

-- WH004에 속한 작업장들 (FACTORY)
(11, 1, 11, 4, 'WC011', '프레스 작업장', '프레스 공장 WH004', 'PRESS', TRUE),
(12, 1, 12, 4, 'WC012', '조립 작업장', '조립 공장 WH004', 'ASSEMBLY', TRUE),
(13, 2, 13, 4, 'WC013', '주조 작업장', '주조 공장 WH004', 'CASTING', TRUE),
(14, 2, 14, 4, 'WC014', '용접 작업장', '용접 공장 WH004', 'WELDING', TRUE),
(15, 1, 15, 4, 'WC015', '도장 작업장', '도장 공장 WH004', 'PAINT', TRUE),
(16, 1, 16, 4, 'WC016', '플라스틱 성형 작업장', '플라스틱 공장 WH004', 'PLASTIC_MOLDING', TRUE),
(17, 2, 17, 4, 'WC017', '품질 검사 작업장', '품질 검사 공장 WH004', 'QUALITY_INSPECTION', TRUE),
(18, 2, 18, 4, 'WC018', '단조 작업장', '단조 공장 WH004', 'FORGING', TRUE),
(19, 2, 19, 4, 'WC019', '열처리 작업장', '열처리 공장 WH004', 'HEAT_TREATMENT', TRUE),
(20, 2, 20, 4, 'WC020', '기계 가공 작업장', '기계 가공 공장 WH004', 'MACHINING', TRUE);

-- Workcenter에 프로세스 ID 업데이트 (생산 공정과 작업장 연결)
UPDATE basic_data_workcenter
SET process_id = (SELECT process_id FROM routing_management_process_details WHERE process_code = 'PRC001')
WHERE code = 'WC001';

UPDATE basic_data_workcenter
SET process_id = (SELECT process_id FROM routing_management_process_details WHERE process_code = 'PRC002')
WHERE code = 'WC002';

UPDATE basic_data_workcenter
SET process_id = (SELECT process_id FROM routing_management_process_details WHERE process_code = 'PRC003')
WHERE code = 'WC003';

UPDATE basic_data_workcenter
SET process_id = (SELECT process_id FROM routing_management_process_details WHERE process_code = 'PRC004')
WHERE code = 'WC004';

UPDATE basic_data_workcenter
SET process_id = (SELECT process_id FROM routing_management_process_details WHERE process_code = 'PRC005')
WHERE code = 'WC005';

UPDATE basic_data_workcenter
SET process_id = (SELECT process_id FROM routing_management_process_details WHERE process_code = 'PRC006')
WHERE code = 'WC006';

UPDATE basic_data_workcenter
SET process_id = (SELECT process_id FROM routing_management_process_details WHERE process_code = 'PRC007')
WHERE code = 'WC007';

UPDATE basic_data_workcenter
SET process_id = (SELECT process_id FROM routing_management_process_details WHERE process_code = 'PRC008')
WHERE code = 'WC008';

UPDATE basic_data_workcenter
SET process_id = (SELECT process_id FROM routing_management_process_details WHERE process_code = 'PRC009')
WHERE code = 'WC009';

UPDATE basic_data_workcenter
SET process_id = (SELECT process_id FROM routing_management_process_details WHERE process_code = 'PRC010')
WHERE code = 'WC010';