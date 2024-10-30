

INSERT INTO equipment_data
(cost, install_date, purchase_date, factory_id, workcenter_id, profile_picture, equipment_name, equipment_num, manufacturer, model_name, equipment_type, operation_status, k_wh)
VALUES
    (5500000, '2022-02-02', '2022-02-03', 1, 1, 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/f1fcbe51-5d04-4e1d-b532-bb3a573faf57_4축 MCT.png', '자동 조립 라인', 'PRD-EM-001', '삼성', 'ASLINE-100', 'ASSEMBLY', 'BEFORE_OPERATION', 150),
    (7500000, '2022-02-02', '2022-02-04', 2, 2, 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/c4a5e1c9-60bc-4deb-ab8c-de19ac0987e6_5축 MCT.png', '5축 CNC 기계', 'PRD-EM1-002', 'LG', '5AXIS-CNC', 'ASSEMBLY', 'BEFORE_OPERATION', 200),
    (800000, '2022-02-02', '2022-02-04', 1, 7, 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/f5343304-61fe-47b7-b7d0-fc76bfd6866d_열분해설비.jpg', 'X-Ray 검사기', 'PRD-EM1-003', 'YG', 'XRAY-X', 'ASSEMBLY', 'BEFORE_OPERATION', 100),
    (6000000, '2022-02-02', '2022-02-04', 1, 6, 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/fc8067c1-eb4c-4fe4-a053-99641076e37e_엑스레이 장비.png', '자동화 조립 로봇', 'PRD-EM1-004', '현대', 'ROBO-AS1', 'ASSEMBLY', 'BEFORE_OPERATION', 180),
    (900000, '2022-02-02', '2022-02-04', 2, 14, 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/51ac8d58-f9c9-4a14-9bf2-d09439d5e0b5_고속 포장기.png', '고속 포장기', 'PRD-EM1-005', '삼성', 'PK-FAST', 'ASSEMBLY', 'BEFORE_OPERATION', 120),
    (3000000, '2022-02-02', '2022-02-04', 1, 15, 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/4b8749d6-f7ab-46d6-bd8d-371ff70c7ecd_수직 방향 전환 선반2.png', '자율 물류 로봇', 'PRD-EM1-006', '삼성', 'LOGI-AUTO', 'ASSEMBLY', 'REPAIRING', 130),
    (1500000, '2022-03-02', '2021-02-04', 1, 1, 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/9e4d0b51-fc30-48a0-8ed4-a8bf3a601403_소스탱크.png', '소스탱크', 'PRD-EM1-007', 'LG', 'TANK-SOURCE', 'ASSEMBLY', 'REPAIRING', 160),
    (1500000, '2022-03-02', '2021-02-04', 1, 1, 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/8b6683c7-fff9-464e-89eb-246190b5f71c_소스탱크2.png', '소스탱크', 'PRD-EM1-008', 'LG', 'TANK-SOURCE2', 'ASSEMBLY', 'REPAIRING', 110),
    (1500000, '2022-03-02', '2021-02-04', 1, 1, 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/a62db646-0752-4e82-9db7-3e165613fd77_숙성탱크.png', '숙성탱크', 'PRD-EM1-009', '현대자동차', 'TANK-AGING', 'ASSEMBLY', 'REPAIRING', 140),
    (1500000, '2022-03-02', '2021-02-04', 1, 1, 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/2c61c3fe-5809-4a06-ab4e-e79f5e4130bb_5축 MCT2.png', '포장 설비', 'PRD-EM1-010', '삼성', 'PACK-MACHINE', 'ASSEMBLY', 'REPAIRING', 150),
    (1500000, '2022-03-02', '2021-02-04', 1, 1, 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/7501d330-672e-4709-bbdd-e5b37d3eb846_열분해설비.jpg', '열분해 기계', 'PRD-EM2-001', '삼성', 'THERM-DEC', 'ASSEMBLY', 'REPAIRING', 125),
    (1500000, '2022-04-02', '2021-04-04', 1, 1, 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/52c4dd09-e95c-49cf-a456-4bbe326b6781_물 제트.png', '물 제트 기계', 'PRD-EM2-002', '삼성', 'WATER-JET', 'ASSEMBLY', 'REPAIRING', 135);

# -- WH002 작업장 설비 (조립, 가공)
# INSERT INTO equipment_data
# (cost, install_date, purchase_date, company_id, factory_id, workcenter_id, equipment_img, equipment_name, equipment_num, manufacturer, model_name, equipment_type, operation_status)
# VALUES
#     (5500000, '2023-03-01', '2023-02-15', 1, 2, 1, NULL, '자동 조립 라인', 'AS-1001', 'ASSEMBLY_CORP', 'ASLINE-100', 'ASSEMBLY', 'OPERATING'),
#     (7500000, '2023-04-10', '2023-04-01', 2, 2, 2, NULL, '5축 CNC 기계', 'MC-2001', 'CNC_MASTER', '5AXIS-CNC', 'MACHINING', 'OPERATING');
#
# -- WH003 작업장 설비 (검사, 조립)
# INSERT INTO equipment_data
# (cost, install_date, purchase_date, company_id, factory_id, workcenter_id, equipment_img, equipment_name, equipment_num, manufacturer, model_name, equipment_type, operation_status)
# VALUES
#     (800000, '2021-12-15', '2021-12-01', 1, 3, 7, NULL, 'X-Ray 검사기', 'IN-7001', 'INSPECTTECH', 'XRAY-X', 'INSPECTION', 'OPERATING'),
#     (6000000, '2023-05-01', '2023-04-15', 2, 3, 6, NULL, '자동화 조립 로봇', 'AS-6001', 'ROBO_ASSEMBLY', 'ROBO-AS1', 'ASSEMBLY', 'OPERATING');
#
# -- WH004 작업장 설비 (포장, 물류)
# INSERT INTO equipment_data
# (cost, install_date, purchase_date, company_id, factory_id, workcenter_id, equipment_img, equipment_name, equipment_num, manufacturer, model_name, equipment_type, operation_status)
# VALUES
#     (900000, '2023-01-05', '2023-01-01', 1, 4, 14, NULL, '고속 포장기', 'PK-9001', 'PACKMATIC', 'PK-FAST', 'PACKAGING', 'OPERATING'),
#     (3000000, '2022-03-10', '2022-03-01', 2, 4, 15, NULL, '자율 물류 로봇', 'LG-15001', 'LOGI_AI', 'LOGI-AUTO', 'LOGISTICS', 'OPERATING');
