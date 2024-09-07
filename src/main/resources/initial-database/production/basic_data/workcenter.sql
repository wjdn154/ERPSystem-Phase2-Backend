-- Workcenter 초기 데이터 삽입
INSERT INTO basic_data_workcenter (code, workcenter_type, name, description, is_active, warehouse_id)
SELECT 'WC001', 'ASSEMBLY', '조립 라인 1', 'First assembly line', TRUE, w.id FROM warehouse w WHERE w.warehouse_type = 'FACTORY'
UNION ALL
SELECT 'WC002', 'PRESS', '프레스 작업장 1', 'First press workshop', TRUE, w.id FROM warehouse w WHERE w.warehouse_type = 'FACTORY'
UNION ALL
SELECT 'WC003', 'WELDING', '용접 구역 1', 'First welding area', TRUE, w.id FROM warehouse w WHERE w.warehouse_type = 'FACTORY'
UNION ALL
SELECT 'WC004', 'PAINT', '도장 구역 1', 'First paint area', TRUE, w.id FROM warehouse w WHERE w.warehouse_type = 'FACTORY'
UNION ALL
SELECT 'WC005', 'MACHINING', '가공 작업장 1', 'First machining workshop', TRUE, w.id FROM warehouse w WHERE w.warehouse_type = 'FACTORY'
UNION ALL
SELECT 'WC006', 'QUALITY_INSPECTION', '품질 검사 구역 1', 'First quality inspection area', TRUE, w.id FROM warehouse w WHERE w.warehouse_type = 'FACTORY'
UNION ALL
SELECT 'WC007', 'CASTING', '주조 구역 1', 'First casting area', TRUE, w.id FROM warehouse w WHERE w.warehouse_type = 'FACTORY'
UNION ALL
SELECT 'WC008', 'FORGING', '단조 구역 1', 'First forging area', TRUE, w.id FROM warehouse w WHERE w.warehouse_type = 'FACTORY'
UNION ALL
SELECT 'WC009', 'HEAT_TREATMENT', '열처리 구역 1', 'First heat treatment area', TRUE, w.id FROM warehouse w WHERE w.warehouse_type = 'FACTORY'
UNION ALL
SELECT 'WC010', 'PLASTIC_MOLDING', '플라스틱 성형 구역 1', 'First plastic molding area', TRUE, w.id FROM warehouse w WHERE w.warehouse_type = 'FACTORY';

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


# INSERT INTO basic_data_workcenter (code, workcenter_type, name, description, is_active, warehouse_id)
# SELECT 'WC001', 'ASSEMBLY', '조립 라인 1', 'First assembly line', TRUE, w.id FROM warehouse w WHERE w.warehouse_type = 'FACTORY'
# UNION ALL
# SELECT 'WC002', 'PRESS', '프레스 작업장 1', 'First press workshop', TRUE, w.id FROM warehouse w WHERE w.warehouse_type = 'FACTORY'
# UNION ALL
# SELECT 'WC003', 'WELDING', '용접 구역 1', 'First welding area', TRUE, w.id FROM warehouse w WHERE w.warehouse_type = 'FACTORY'
# UNION ALL
# SELECT 'WC004', 'PAINT', '도장 구역 1', 'First paint area', TRUE, w.id FROM warehouse w WHERE w.warehouse_type = 'FACTORY'
# UNION ALL
# SELECT 'WC005', 'MACHINING', '가공 작업장 1', 'First machining workshop', TRUE, w.id FROM warehouse w WHERE w.warehouse_type = 'FACTORY'
# UNION ALL
# SELECT 'WC006', 'QUALITY_INSPECTION', '품질 검사 구역 1', 'First quality inspection area', TRUE, w.id FROM warehouse w WHERE w.warehouse_type = 'FACTORY'
# UNION ALL
# SELECT 'WC007', 'CASTING', '주조 구역 1', 'First casting area', TRUE, w.id FROM warehouse w WHERE w.warehouse_type = 'FACTORY'
# UNION ALL
# SELECT 'WC008', 'FORGING', '단조 구역 1', 'First forging area', TRUE, w.id FROM warehouse w WHERE w.warehouse_type = 'FACTORY'
# UNION ALL
# SELECT 'WC009', 'HEAT_TREATMENT', '열처리 구역 1', 'First heat treatment area', TRUE, w.id FROM warehouse w WHERE w.warehouse_type = 'FACTORY'
# UNION ALL
# SELECT 'WC010', 'PLASTIC_MOLDING', '플라스틱 성형 구역 1', 'First plastic molding area', TRUE, w.id FROM warehouse w WHERE w.warehouse_type = 'FACTORY';
#
# -- Workcenter에 프로세스 ID 업데이트
# UPDATE basic_data_workcenter
# SET process_id = (SELECT process_id FROM routing_management_process_details WHERE process_code = 'PRC001')
# WHERE code = 'WC001';
#
# UPDATE basic_data_workcenter
# SET process_id = (SELECT process_id FROM routing_management_process_details WHERE process_code = 'PRC002')
# WHERE code = 'WC003';
#
# UPDATE basic_data_workcenter
# SET process_id = (SELECT process_id FROM routing_management_process_details WHERE process_code = 'PRC003')
# WHERE code = 'WC005';


