-- mrp_material_input_status 테이블에 초기 데이터 삽입
INSERT INTO mrp_material_input_status
    (name, date_time, material_data_id, production_order_id, process_id, workcenter_id, equipment_id, quantity_consumed, unit_of_measure, remarks)
VALUES
    ('2024-10-04-PRD-MAT-001-공정1', '2024-10-04 08:00:00', 1, 1, 1, 1, 1, 100, 'kg', '첫 번째 자재 투입 현황'),
    ('2024-10-05-PRD-MAT-002-공정2', '2024-10-05 08:00:00', 2, 2, 2, 2, NULL, 200, 'm', '구리 선 투입 현황'),
    ('2024-10-06-PRD-MAT-003-공정3', '2024-10-06 09:00:00', 3, 3, 3, 3, 2, 50, 'kg', '유리 패널 투입 현황'),
    ('2024-10-07-PRD-MAT-004-공정4', '2024-10-07 10:00:00', 4, 4, 4, 4, NULL, 70, 'm', '나무 판자 투입 현황'),
    ('2024-10-08-PRD-MAT-005-공정5', '2024-10-08 11:00:00', 5, 5, 5, 5, 3, 77, 'pcs', '철제 막대 투입 현황');