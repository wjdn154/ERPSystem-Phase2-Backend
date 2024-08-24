INSERT INTO routing_management_process_details (process_id, process_code, process_name, is_process_outsourced, process_duration, process_cost, process_defect_rate, process_description, process_is_used)
VALUES
(1, 'PRC001', '조립', FALSE, 2.5, 500000, 0.02, 'Assembly of parts and modules. 부품 및 모듈 조립.', TRUE),
(2, 'PRC002', '용접', FALSE, 3.0, 800000, 0.03, 'Welding of metal components. 금속 부품 용접.', TRUE),
(3, 'PRC003', '도장', TRUE, 4.0, 1200000, 0.01, 'Painting and corrosion protection. 도장 및 방청.', TRUE),
(4, 'PRC004', '가공', FALSE, 5.0, 1500000, 0.015, 'Precision machining of metal parts. 금속 부품 정밀 가공.', TRUE),
(5, 'PRC005', '열처리', TRUE, 7.0, 2000000, 0.005, 'Heat treatment to harden metal. 금속 경화를 위한 열처리.', TRUE),
(6, 'PRC006', '품질 검사', FALSE, 1.5, 300000, 0.001, 'Quality inspection and validation. 품질 검사 및 검증.', TRUE),
(7, 'PRC007', '프레스', FALSE, 2.0, 700000, 0.02, 'Pressing of metal sheets. 금속 판재 성형.', TRUE),
(8, 'PRC008', '단조', FALSE, 4.5, 1100000, 0.025, 'Forging of metal components. 금속 부품 단조.', TRUE),
(9, 'PRC009', '플라스틱 성형', TRUE, 3.5, 900000, 0.02, 'Molding of plastic parts. 플라스틱 부품 성형.', TRUE),
(10, 'PRC010', '주조', FALSE, 6.0, 1800000, 0.015, 'Casting of metal parts. 금속 부품 주조.', TRUE);
