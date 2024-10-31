INSERT INTO equipment_data
(cost, install_date, purchase_date, factory_id, workcenter_id, profile_picture, equipment_name, equipment_num, manufacturer, model_name, equipment_type, operation_status, k_wh)
VALUES
-- 프론트 범퍼 조립 작업장 (workcenter_id: 31)
    (5500000, '2022-02-02', '2022-02-03', 2, 31, 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/f1fcbe51-5d04-4e1d-b532-bb3a573faf57_조립라인.png', '프론트 범퍼 조립 라인', 'PRD-EM-031', '현대', 'FRNT-BMP-LN', 'ASSEMBLY', 'OPERATING', 150),
-- 휠 부품 프레스 작업장 (workcenter_id: 14)
    (6000000, '2022-02-02', '2022-02-04', 12, 14, 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/9b6d28af-1494-4d45-b42f-b315e5aa88cf_프레스라인.png', '휠 부품 프레스 기계', 'PRD-EM-014', 'LG', 'WHL-PRS-MCH', 'MACHINING', 'OPERATING', 180),
-- 연료 필터 CNC 가공 작업장 (workcenter_id: 28)
    (7000000, '2022-02-02', '2022-02-04', 19, 28, 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/75350b64-8ecf-4d7b-8122-a7a99919b795_CNC가공기.png', '연료 필터 CNC 가공기', 'PRD-EM-028', 'Mazak', 'FUEL-FLTR-CNC', 'MACHINING', 'OPERATING', 200),

    (5500000, '2022-02-02', '2022-02-03', 1, 1, 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/f1fcbe51-5d04-4e1d-b532-bb3a573faf57_4축 MCT.png', '자동 조립 라인', 'PRD-EM-001', '삼성', 'ASLINE-100', 'ASSEMBLY', 'BEFORE_OPERATION', 150),
    (7500000, '2022-02-02', '2022-02-04', 2, 2, 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/c4a5e1c9-60bc-4deb-ab8c-de19ac0987e6_5축 MCT.png', '5축 CNC 기계', 'PRD-EM1-002', 'LG', '5AXIS-CNC', 'ASSEMBLY', 'BEFORE_OPERATION', 200),
    (800000, '2022-02-02', '2022-02-04', 1, 3, 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/f5343304-61fe-47b7-b7d0-fc76bfd6866d_열분해설비.jpg', 'X-Ray 검사기', 'PRD-EM1-003', 'YG', 'XRAY-X', 'ASSEMBLY', 'BEFORE_OPERATION', 100),
    (6000000, '2022-02-02', '2022-02-04', 1, 4, 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/fc8067c1-eb4c-4fe4-a053-99641076e37e_엑스레이 장비.png', '자동화 조립 로봇', 'PRD-EM1-004', '현대', 'ROBO-AS1', 'ASSEMBLY', 'BEFORE_OPERATION', 180),
    (900000, '2022-02-02', '2022-02-04', 2, 5, 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/51ac8d58-f9c9-4a14-9bf2-d09439d5e0b5_고속 포장기.png', '고속 포장기', 'PRD-EM1-005', '삼성', 'PK-FAST', 'ASSEMBLY', 'BEFORE_OPERATION', 120),
    (3000000, '2022-02-02', '2022-02-04', 1, 6, 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/4b8749d6-f7ab-46d6-bd8d-371ff70c7ecd_수직 방향 전환 선반2.png', '자율 물류 로봇', 'PRD-EM1-006', '삼성', 'LOGI-AUTO', 'ASSEMBLY', 'REPAIRING', 130),
    (1500000, '2022-03-02', '2021-02-04', 1, 7, 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/9e4d0b51-fc30-48a0-8ed4-a8bf3a601403_소스탱크.png', '소스탱크', 'PRD-EM1-007', 'LG', 'TANK-SOURCE', 'ASSEMBLY', 'REPAIRING', 160),
    (1500000, '2022-03-02', '2021-02-04', 1, 8, 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/8b6683c7-fff9-464e-89eb-246190b5f71c_소스탱크2.png', '소스탱크', 'PRD-EM1-008', 'LG', 'TANK-SOURCE2', 'ASSEMBLY', 'REPAIRING', 110),
    (1500000, '2022-03-02', '2021-02-04', 1, 9, 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/a62db646-0752-4e82-9db7-3e165613fd77_숙성탱크.png', '숙성탱크', 'PRD-EM1-009', '현대자동차', 'TANK-AGING', 'ASSEMBLY', 'REPAIRING', 140),
    (1500000, '2022-03-02', '2021-02-04', 1, 10, 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/2c61c3fe-5809-4a06-ab4e-e79f5e4130bb_5축 MCT2.png', '포장 설비', 'PRD-EM1-010', '삼성', 'PACK-MACHINE', 'ASSEMBLY', 'REPAIRING', 150),
    (1500000, '2022-03-02', '2021-02-04', 1, 11, 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/7501d330-672e-4709-bbdd-e5b37d3eb846_열분해설비.jpg', '열분해 기계', 'PRD-EM2-001', '삼성', 'THERM-DEC', 'ASSEMBLY', 'REPAIRING', 125),
    (1500000, '2022-04-02', '2021-04-04', 1, 12, 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/52c4dd09-e95c-49cf-a456-4bbe326b6781_물 제트.png', '물 제트 기계', 'PRD-EM2-002', '삼성', 'WATER-JET', 'ASSEMBLY', 'REPAIRING', 135),
    (30000000, '2019-03-01', '2019-02-15', 2, 13, 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/a342e53f-e238-4583-8ae1-849a5b8f618f_차체조립로봇_1.jpg', '차체 조립 로봇', 'PRD-EM3-001', 'ABB Robotics', 'IRB 6700', 'ASSEMBLY', 'OPERATING', 15),
    (35000000, '2021-06-01', '2021-05-20', 4, 14, 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/535a81dd-590b-4b2b-8a95-48001a01256e_도장시스템_1.jpg', '도장 시스템', 'PRD-EM3-002', 'Dürr', 'EcoBell3', 'ASSEMBLY', 'OPERATING', 10),
    (40000000, '2020-09-01', '2020-08-15', 5, 15, 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/2a31c35c-c069-4359-a6b8-e9a67b91174a_크랙및구조불량검사장비_1.jpg', '정밀 가공기', 'PRD-EM3-003', 'Mazak', 'Integrex i-400', 'MACHINING', 'OPERATING', 25),
    (50000000, '2018-04-01', '2018-03-10', 9, 16, 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/4d50e06d-a508-49f7-bf87-f1c7444b2c64_진공열처리.png', '열처리로', 'PRD-EM3-Q004', 'Seco Warwick', 'Vector 30', 'MACHINING', 'OPERATING', 30),
    (15000000, '2021-04-15', '2021-04-01', 6, 17, 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/27846342-fe87-43b6-ad5f-3d0abe21f8da_크랙및구조불량검사장비_1.jpg', '크랙 및 구조 불량 검사 장비', 'PRD-EM4-001', 'Mitutoyo', 'QV Apex', 'INSPECTION', 'OPERATING', 5),
    (60000000, '2019-07-01', '2019-06-20', 12, 18, 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/9b6d28af-1494-4d45-b42f-b315e5aa88cf_프레스.png', '프레스 기계', 'PRD-EM4-002', 'AIDA', 'NC1-1100', 'ASSEMBLY', 'OPERATING', 50),
    (50000000, '2020-12-01', '2020-11-15', 16, 19, 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/ca8be0a4-522a-4703-84f0-2a5bf54fdfe8_단조프레스.jpg', '단조 프레스', 'PRD-EM4-003', 'SMS Group', 'MP 2500', 'MACHINING', 'OPERATING', 40),
    (45000000, '2019-09-25', '2019-09-10', 15, 20, 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/a3930836-19db-4a82-84f4-71bdef16b713_압출성형기.jpg', '압출 성형기', 'PRD-EM5-001', 'KraussMaffei', 'ZE 65', 'MACHINING', 'OPERATING', 45),
    (65000000, '2018-12-20', '2018-12-01', 19, 21, 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/75350b64-8ecf-4d7b-8122-a7a99919b795_CNC머시닝센터 (2).jpg', 'CNC 머시닝 센터', 'PRD-EM5-002', 'DMG Mori', 'DMU 50', 'MACHINING', 'OPERATING', 55);
