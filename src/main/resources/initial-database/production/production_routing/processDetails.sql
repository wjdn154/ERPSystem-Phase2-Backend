# INSERT INTO routing_management_process_details (process_id, process_code, process_name, is_process_outsourced, process_duration, process_cost, process_defect_rate, process_description, process_is_used)
# VALUES
# (1, 'PRC001', '조립', FALSE, 2.5, 500000, 0.02, 'Assembly of parts and modules. 부품 및 모듈 조립.', TRUE),
# (2, 'PRC002', '용접', FALSE, 3.0, 800000, 0.03, 'Welding of metal components. 금속 부품 용접.', TRUE),
# (3, 'PRC003', '도장', TRUE, 4.0, 1200000, 0.01, 'Painting and corrosion protection. 도장 및 방청.', TRUE),
# (4, 'PRC004', '가공', FALSE, 5.0, 1500000, 0.015, 'Precision machining of metal parts. 금속 부품 정밀 가공.', TRUE),
# (5, 'PRC005', '열처리', TRUE, 7.0, 2000000, 0.005, 'Heat treatment to harden metal. 금속 경화를 위한 열처리.', TRUE),
# (6, 'PRC006', '품질 검사', FALSE, 1.5, 300000, 0.001, 'Quality inspection and validation. 품질 검사 및 검증.', TRUE),
# (7, 'PRC007', '프레스', FALSE, 2.0, 700000, 0.02, 'Pressing of metal sheets. 금속 판재 성형.', TRUE),
# (8, 'PRC008', '단조', FALSE, 4.5, 1100000, 0.025, 'Forging of metal components. 금속 부품 단조.', TRUE),
# (9, 'PRC009', '플라스틱 성형', TRUE, 3.5, 900000, 0.02, 'Molding of plastic parts. 플라스틱 부품 성형.', TRUE),
# (10, 'PRC010', '주조', FALSE, 6.0, 1800000, 0.015, 'Casting of metal parts. 금속 부품 주조.', TRUE);


-- routing_management_process_details 초기 데이터 삽입
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
    (10, 'PRC010', '주조', FALSE, 6.0, 1800000, 0.015, 'Casting of metal parts. 금속 부품 주조.', TRUE),
    (11, 'PRC011', '세척', FALSE, 1.0, 200000, 0.005, 'Cleaning of parts. 부품 세척.', TRUE),
    (12, 'PRC012', '절단', FALSE, 2.0, 400000, 0.01, 'Cutting of metal sheets. 금속 판재 절단.', TRUE),
    (13, 'PRC013', '레이저 절단', TRUE, 3.5, 1000000, 0.005, 'Laser cutting for precision parts. 정밀 부품 레이저 절단.', TRUE),
    (14, 'PRC014', 'CNC 가공', FALSE, 4.5, 1400000, 0.015, 'CNC machining of metal parts. 금속 부품 CNC 가공.', TRUE),
    (15, 'PRC015', '연마', FALSE, 1.5, 300000, 0.02, 'Polishing of metal surfaces. 금속 표면 연마.', TRUE),
    (16, 'PRC016', '조립 2차', FALSE, 2.0, 600000, 0.02, 'Second assembly process. 2차 조립 공정.', TRUE),
    (17, 'PRC017', '패키징', FALSE, 1.5, 200000, 0.005, 'Packaging of finished products. 완제품 패키징.', TRUE),
    (18, 'PRC018', '압출 성형', TRUE, 3.0, 800000, 0.02, 'Extrusion molding process. 압출 성형 공정.', TRUE),
    (19, 'PRC019', '프레스 2차', FALSE, 2.0, 750000, 0.02, 'Second press process. 2차 프레스 공정.', TRUE),
    (20, 'PRC020', '경화', TRUE, 5.0, 1800000, 0.01, 'Hardening of materials. 재료 경화 공정.', TRUE),
    (21, 'PRC021', '사출 성형', TRUE, 4.0, 1200000, 0.015, 'Injection molding process. 사출 성형 공정.', TRUE),
    (22, 'PRC022', '압축 성형', TRUE, 3.5, 950000, 0.02, 'Compression molding process. 압축 성형 공정.', TRUE),
    (23, 'PRC023', '합성', FALSE, 6.0, 2000000, 0.03, 'Material synthesis process. 재료 합성 공정.', TRUE),
    (24, 'PRC024', '광택', FALSE, 2.0, 500000, 0.01, 'Surface polishing and finishing. 표면 광택 공정.', TRUE),
    (25, 'PRC025', '코팅', TRUE, 3.0, 1000000, 0.01, 'Surface coating for protection. 보호를 위한 표면 코팅.', TRUE),
    (26, 'PRC026', '조립 3차', FALSE, 2.0, 650000, 0.02, 'Third assembly process. 3차 조립 공정.', FALSE),
    (27, 'PRC027', '고온 처리', TRUE, 5.5, 2000000, 0.005, 'High temperature treatment. 고온 처리 공정.', TRUE),
    (28, 'PRC028', '냉각', FALSE, 1.0, 150000, 0.01, 'Cooling process for finished products. 완제품 냉각 공정.', TRUE),
    (29, 'PRC029', '플라스틱 재활용', TRUE, 4.0, 900000, 0.02, 'Plastic recycling process. 플라스틱 재활용 공정.', TRUE),
    (30, 'PRC030', '알루미늄 주조', FALSE, 5.5, 1600000, 0.015, 'Aluminum casting process. 알루미늄 주조 공정.', TRUE);
