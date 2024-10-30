

INSERT INTO equipment_data
(cost, install_date, purchase_date, factory_id, workcenter_id, profile_picture, equipment_name, equipment_num, manufacturer, model_name, equipment_type, operation_status, k_wh)

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
-- 1. 조립 공정: 차체 조립 로봇

INSERT INTO equipment_data (equipment_num, equipment_name, equipment_type, manufacturer, model_name, purchase_date, install_date, operation_status, cost, workcenter_id, factory_id, equipment_img, k_wh, company_id)
VALUES
('EQ001', '차체 조립 로봇', 'ASSEMBLY', 'ABB Robotics', 'IRB 6700', '2019-02-15', '2019-03-01', 'OPERATING', 30000000, 1, 2, 'http://wooyang-tech.co.kr/wooimg/21_01.gif', 15, 1),

-- 2. 용접 공정: 용접 로봇
('EQ002', '용접 로봇', 'ASSEMBLY', 'KUKA', 'KR 1000 titan', '2020-01-10', '2020-01-25', 'OPERATING', 25000000, 2, 3, 'http://wooyang-tech.co.kr/wooimg/21_02.gif', 20, 1),

-- 3. 도장 공정: 도장 시스템
('EQ003', '도장 시스템', 'ASSEMBLY', 'Dürr', 'EcoBell3', '2021-05-20', '2021-06-01', 'OPERATING', 35000000, 3, 4, 'http://wooyang-tech.co.kr/wooimg/21_03.gif', 10, 1),

-- 4. 정밀 가공 공정: 정밀 가공기
('EQ004', '정밀 가공기', 'MACHINING', 'Mazak', 'Integrex i-400', '2020-08-15', '2020-09-01', 'OPERATING', 40000000, 4, 5, 'http://wooyang-tech.co.kr/wooimg/21_04.gif', 25, 1),

-- 5. 열처리 공정: 열처리로
('EQ005', '열처리로', 'MACHINING', 'Seco Warwick', 'Vector 30', '2018-03-10', '2018-04-01', 'OPERATING', 50000000, 5, 9, 'http://wooyang-tech.co.kr/wooimg/21_05.gif', 30, 1),

-- 6. 품질 검사 공정: 품질 검사 장비
('EQ006', '품질 검사 장비', 'INSPECTION', 'Mitutoyo', 'QV Apex', '2021-04-01', '2021-04-15', 'OPERATING', 15000000, 6, 6, 'http://wooyang-tech.co.kr/wooimg/21_06.gif', 5, 1),

-- 7. 프레스 공정: 프레스 기계
('EQ007', '프레스 기계', 'ASSEMBLY', 'AIDA', 'NC1-1100', '2019-06-20', '2019-07-01', 'OPERATING', 60000000, 7, 12, 'http://wooyang-tech.co.kr/wooimg/21_07.gif', 50, 1),

-- 8. 단조 공정: 단조 프레스
('EQ008', '단조 프레스', 'MACHINING', 'SMS Group', 'MP 2500', '2020-11-15', '2020-12-01', 'OPERATING', 50000000, 8, 16, 'http://wooyang-tech.co.kr/wooimg/21_08.gif', 40, 1),

-- 9. 압출 성형 공정: 압출 성형기
('EQ009', '압출 성형기', 'MACHINING', 'KraussMaffei', 'ZE 65', '2019-09-10', '2019-09-25', 'OPERATING', 45000000, 9, 15, 'http://wooyang-tech.co.kr/wooimg/21_09.gif', 45, 1),

-- 10. CNC 가공 공정: CNC 머시닝 센터
('EQ010', 'CNC 머시닝 센터', 'MACHINING', 'DMG Mori', 'DMU 50', '2018-12-01', '2018-12-20', 'OPERATING', 65000000, 10, 19, 'http://wooyang-tech.co.kr/wooimg/21_10.gif', 55, 1),

-- 11. 전기 테스트 시스템: 전기 테스트 시스템
('EQ011', '전기 테스트 시스템', 'INSPECTION', 'Keysight', 'TestPro 9000', '2021-02-15', '2021-03-01', 'OPERATING', 20000000, 11, 6, 'http://wooyang-tech.co.kr/wooimg/21_11.gif', 12, 1),

-- 12. 배터리 조립기: 배터리 조립기
('EQ012', '배터리 조립기', 'ASSEMBLY', 'Panasonic', 'BA-300', '2020-09-20', '2020-10-05', 'OPERATING', 30000000, 12, 2, 'http://wooyang-tech.co.kr/wooimg/21_12.gif', 18, 1),

-- 13. 자동 나사 조립기: 자동 나사 조립기
('EQ013', '자동 나사 조립기', 'ASSEMBLY', 'Bosch', 'ScrewMaster 200', '2019-11-10', '2019-11-25', 'OPERATING', 10000000, 13, 2, 'http://wooyang-tech.co.kr/wooimg/21_13.gif', 5, 1),

-- 14. 고온 소성로: 고온 소성로
('EQ014', '고온 소성로', 'MACHINING', 'Lindberg', 'HTF55667C', '2020-03-20', '2020-04-01', 'OPERATING', 60000000, 14, 9, 'http://wooyang-tech.co.kr/wooimg/21_14.gif', 60, 1),

-- 15. 레이저 커터: 레이저 커터
('EQ015', '레이저 커터', 'MACHINING', 'Trumpf', 'TruLaser 1030', '2019-02-15', '2019-03-01', 'OPERATING', 45000000, 15, 12, 'http://wooyang-tech.co.kr/wooimg/21_15.gif', 25, 1),

-- 16. 물류 공정: 물류 컨베이어
('EQ016', '물류 컨베이어', 'LOGISTICS', 'Siemens', 'ConveyMax 2000', '2021-01-05', '2021-02-01', 'OPERATING', 20000000, 16, 15, 'http://wooyang-tech.co.kr/wooimg/21_16.gif', 20, 1),

-- 17. 포장 공정: 포장 로봇
('EQ017', '포장 로봇', 'PACKAGING', 'Fanuc', 'R-2000iC', '2020-05-10', '2020-05-25', 'OPERATING', 22000000, 17, 18, 'http://wooyang-tech.co.kr/wooimg/21_17.gif', 10, 1),

-- 18. 주조 공정: 주조 로봇
('EQ018', '주조 로봇', 'MACHINING', 'KUKA', 'KR QUANTEC', '2019-03-15', '2019-04-01', 'OPERATING', 55000000, 18, 10, 'http://wooyang-tech.co.kr/wooimg/21_18.gif', 45, 1),

-- 19. 냉각 시스템: 냉각 시스템
('EQ019', '냉각 시스템', 'MACHINING', 'York', 'CoolerX 150', '2018-06-15', '2018-07-01', 'OPERATING', 40000000, 19, 9, 'http://wooyang-tech.co.kr/wooimg/21_19.gif', 30, 1),

-- 20. 광택 공정: 광택기
('EQ020', '광택기', 'MACHINING', 'Flex', 'Polisher 1300', '2020-11-05', '2020-12-01', 'OPERATING', 15000000, 20, 19, 'http://wooyang-tech.co.kr/wooimg/21_20.gif', 8, 1),

-- 21. 초음파 세척기: 초음파 세척기
('EQ021', '초음파 세척기', 'INSPECTION', 'Branson', 'UC-6000', '2019-08-25', '2019-09-10', 'OPERATING', 20000000, 21, 18, 'http://wooyang-tech.co.kr/wooimg/21_21.gif', 15, 1),

-- 22. 압축 성형 공정: 압축 성형기
('EQ022', '압축 성형기', 'MACHINING', 'Sumitomo', 'CM-450', '2021-03-20', '2021-04-10', 'OPERATING', 50000000, 22, 15, 'http://wooyang-tech.co.kr/wooimg/21_22.gif', 40, 1),

-- 23. 물류 공정: 자동 적재기
('EQ023', '자동 적재기', 'LOGISTICS', 'Mitsubishi', 'Loader A1', '2020-10-15', '2020-11-01', 'OPERATING', 30000000, 23, 15, 'http://wooyang-tech.co.kr/wooimg/21_23.gif', 20, 1),

-- 24. 프레스 공정: 전동 모노레일
('EQ024', '프레스 용 전동 모노레일', 'LOGISTICS', 'AIDA', 'MonoRail X1', '2019-12-05', '2020-01-01', 'OPERATING', 50000000, 24, 12, 'http://wooyang-tech.co.kr/wooimg/21_24.gif', 55, 1),

-- 25. 용접 공정: 자동 용접 장비
('EQ025', '자동 용접 장비', 'ASSEMBLY', 'Lincoln Electric', 'AutoWeld 500', '2018-11-15', '2018-12-01', 'OPERATING', 35000000, 25, 3, 'http://wooyang-tech.co.kr/wooimg/21_25.gif', 22, 1),
