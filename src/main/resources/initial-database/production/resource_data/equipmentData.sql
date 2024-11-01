INSERT INTO equipment_data
(cost, install_date, purchase_date, factory_id, workcenter_id, profile_picture, equipment_name, equipment_num, manufacturer, model_name, equipment_type, operation_status, k_wh)
VALUES
-- 프론트 범퍼 조립 작업장 (workcenter_id: 31)
    (5500000, '2022-02-02', '2022-02-03', 2, 31, 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/f1fcbe51-5d04-4e1d-b532-bb3a573faf57_조립라인.png', '프론트 범퍼 조립 라인', 'PRD-EM-031', '현대', 'FRNT-BMP-LN', 'ASSEMBLY', 'OPERATING', 150),
-- 휠 부품 프레스 작업장 (workcenter_id: 14)
    (6000000, '2022-02-02', '2022-02-04', 12, 14, 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/9b6d28af-1494-4d45-b42f-b315e5aa88cf_프레스라인.png', '휠 부품 프레스 기계', 'PRD-EM-014', 'LG', 'WHL-PRS-MCH', 'MACHINING', 'OPERATING', 180),
-- 연료 필터 CNC 가공 작업장 (workcenter_id: 27)
    (7000000, '2022-02-02', '2022-02-04', 19, 27, 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/75350b64-8ecf-4d7b-8122-a7a99919b795_CNC가공기.png', '연료 필터 CNC 가공기', 'PRD-EM-028', 'Mazak', 'FUEL-FLTR-CNC', 'MACHINING', 'OPERATING', 200),

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


# INSERT INTO equipment_data (equipment_num, equipment_name, equipment_type, manufacturer, model_name, purchase_date, install_date, operation_status, cost, workcenter_id, factory_id, profile_picture, k_wh)
# VALUES
# ('EQ001', '차체 조립 로봇', 'ASSEMBLY', 'ABB Robotics', 'IRB 6700', '2019-02-15', '2019-03-01', 'OPERATING', 30000000, 1, 2, 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/a342e53f-e238-4583-8ae1-849a5b8f618f_차체조립로봇_1.jpg', 15),
# ('EQ003', '도장 시스템', 'ASSEMBLY', 'Dürr', 'EcoBell3', '2021-05-20', '2021-06-01', 'OPERATING', 35000000, 3, 4, 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/535a81dd-590b-4b2b-8a95-48001a01256e_도장시스템_1.jpg', 10),
# ('EQ004', '정밀 가공기', 'MACHINING', 'Mazak', 'Integrex i-400', '2020-08-15', '2020-09-01', 'OPERATING', 40000000, 4, 5, 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/2a31c35c-c069-4359-a6b8-e9a67b91174a_크랙및구조불량검사장비_1.jpg', 25),
# ('EQ005', '열처리로', 'MACHINING', 'Seco Warwick', 'Vector 30', '2018-03-10', '2018-04-01', 'OPERATING', 50000000, 5, 9, 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/4d50e06d-a508-49f7-bf87-f1c7444b2c64_진공열처리.png', 30),
# ('EQ006', '크랙 및 구조 불량 검사 장비', 'INSPECTION', 'Mitutoyo', 'QV Apex', '2021-04-01', '2021-04-15', 'OPERATING', 15000000, 6, 6, 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/27846342-fe87-43b6-ad5f-3d0abe21f8da_크랙및구조불량검사장비_1.jpg', 5),
# ('EQ007', '프레스 기계', 'ASSEMBLY', 'AIDA', 'NC1-1100', '2019-06-20', '2019-07-01', 'OPERATING', 60000000, 7, 12, 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/9b6d28af-1494-4d45-b42f-b315e5aa88cf_프레스.png', 50),
# ('EQ008', '단조 프레스', 'MACHINING', 'SMS Group', 'MP 2500', '2020-11-15', '2020-12-01', 'OPERATING', 50000000, 8, 16, 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/ca8be0a4-522a-4703-84f0-2a5bf54fdfe8_단조프레스.jpg', 40),
# ('EQ009', '압출 성형기', 'MACHINING', 'KraussMaffei', 'ZE 65', '2019-09-10', '2019-09-25', 'OPERATING', 45000000, 9, 15, 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/a3930836-19db-4a82-84f4-71bdef16b713_압출성형기.jpg', 45),
# ('EQ010', 'CNC 머시닝 센터', 'MACHINING', 'DMG Mori', 'DMU 50', '2018-12-01', '2018-12-20', 'OPERATING', 65000000, 10, 19, 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/75350b64-8ecf-4d7b-8122-a7a99919b795_CNC머시닝센터 (2).jpg', 55);


# ('EQ011', '전기 테스트 시스템', 'INSPECTION', 'Keysight', 'TestPro 9000', '2021-02-15', '2021-03-01', 'OPERATING', 20000000, 11, 6, 'http://wooyang-tech.co.kr/wooimg/21_11.gif', 12),
# ('EQ012', '배터리 조립기', 'ASSEMBLY', 'Panasonic', 'BA-300', '2020-09-20', '2020-10-05', 'OPERATING', 30000000, 12, 2, 'http://wooyang-tech.co.kr/wooimg/21_12.gif', 18),
# ('EQ013', '자동 나사 조립기', 'ASSEMBLY', 'Bosch', 'ScrewMaster 200', '2019-11-10', '2019-11-25', 'OPERATING', 10000000, 13, 2, 'http://wooyang-tech.co.kr/wooimg/21_13.gif', 5);

# -- 14. 고온 소성로: 고온 소성로
# ('EQ014', '고온 소성로', 'MACHINING', 'Lindberg', 'HTF55667C', '2020-03-20', '2020-04-01', 'OPERATING', 60000000, 14, 9, 'http://wooyang-tech.co.kr/wooimg/21_14.gif', 60),
#
# -- 15. 레이저 커터: 레이저 커터
# ('EQ015', '레이저 커터', 'MACHINING', 'Trumpf', 'TruLaser 1030', '2019-02-15', '2019-03-01', 'OPERATING', 45000000, 15, 12, 'http://wooyang-tech.co.kr/wooimg/21_15.gif', 25),
#
# -- 16. 물류 공정: 물류 컨베이어
# ('EQ016', '물류 컨베이어', 'LOGISTICS', 'Siemens', 'ConveyMax 2000', '2021-01-05', '2021-02-01', 'OPERATING', 20000000, 16, 15, 'http://wooyang-tech.co.kr/wooimg/21_16.gif', 20),
#
# -- 17. 포장 공정: 포장 로봇
# ('EQ017', '포장 로봇', 'PACKAGING', 'Fanuc', 'R-2000iC', '2020-05-10', '2020-05-25', 'OPERATING', 22000000, 17, 18, 'http://wooyang-tech.co.kr/wooimg/21_17.gif', 10),
#
# -- 18. 주조 공정: 주조 로봇
# ('EQ018', '주조 로봇', 'MACHINING', 'KUKA', 'KR QUANTEC', '2019-03-15', '2019-04-01', 'OPERATING', 55000000, 18, 10, 'http://wooyang-tech.co.kr/wooimg/21_18.gif', 45),
#
# -- 19. 냉각 시스템: 냉각 시스템
# ('EQ019', '냉각 시스템', 'MACHINING', 'York', 'CoolerX 150', '2018-06-15', '2018-07-01', 'OPERATING', 40000000, 19, 9, 'http://wooyang-tech.co.kr/wooimg/21_19.gif', 30),
#
# -- 20. 광택 공정: 광택기
# ('EQ020', '광택기', 'MACHINING', 'Flex', 'Polisher 1300', '2020-11-05', '2020-12-01', 'OPERATING', 15000000, 20, 19, 'http://wooyang-tech.co.kr/wooimg/21_20.gif', 8),
#
# -- 21. 초음파 세척기: 초음파 세척기
# ('EQ021', '초음파 세척기', 'INSPECTION', 'Branson', 'UC-6000', '2019-08-25', '2019-09-10', 'OPERATING', 20000000, 21, 18, 'http://wooyang-tech.co.kr/wooimg/21_21.gif', 15),
#
# -- 22. 압축 성형 공정: 압축 성형기
# ('EQ022', '압축 성형기', 'MACHINING', 'Sumitomo', 'CM-450', '2021-03-20', '2021-04-10', 'OPERATING', 50000000, 22, 15, 'http://wooyang-tech.co.kr/wooimg/21_22.gif', 40),
#
# -- 23. 물류 공정: 자동 적재기
# ('EQ023', '자동 적재기', 'LOGISTICS', 'Mitsubishi', 'Loader A1', '2020-10-15', '2020-11-01', 'OPERATING', 30000000, 23, 15, 'http://wooyang-tech.co.kr/wooimg/21_23.gif', 20),
#
# -- 24. 프레스 공정: 전동 모노레일
# ('EQ024', '프레스 용 전동 모노레일', 'LOGISTICS', 'AIDA', 'MonoRail X1', '2019-12-05', '2020-01-01', 'OPERATING', 50000000, 24, 12, 'http://wooyang-tech.co.kr/wooimg/21_24.gif', 55),
#
# -- 25. 용접 공정: 자동 용접 장비
# ('EQ025', '자동 용접 장비', 'ASSEMBLY', 'Lincoln Electric', 'AutoWeld 500', '2018-11-15', '2018-12-01', 'OPERATING', 35000000, 25, 3, 'http://wooyang-tech.co.kr/wooimg/21_25.gif', 22),
#
# -- 26. 전기 제어 패널: 전기 제어 패널
# ('EQ026', '전기 제어 패널', 'ASSEMBLY', 'Schneider', 'ControlPANEL 200', '2019-02-10', '2019-02-25', 'OPERATING', 12000000, 26, 2, 'http://wooyang-tech.co.kr/wooimg/21_26.gif', 10),
#
# -- 27. 세척 공정: 고압 세척기
# ('EQ027', '고압 세척기', 'INSPECTION', 'Kärcher', 'HighWash 900', '2020-06-01', '2020-06-20', 'OPERATING', 18000000, 27, 6, 'http://wooyang-tech.co.kr/wooimg/21_27.gif', 18),
#
# -- 28. 압출 공정: 압출기
# ('EQ028', '압출기', 'MACHINING', 'Davis-Standard', 'SuperEX 2500', '2021-04-15', '2021-05-05', 'OPERATING', 55000000, 28, 15, 'http://wooyang-tech.co.kr/wooimg/21_28.gif', 48),
#
# -- 29. 물류 공정: 물류용 리프트
# ('EQ029', '물류용 리프트', 'LOGISTICS', 'Thyssenkrupp', 'LiftMaster L500', '2019-05-20', '2019-06-05', 'OPERATING', 32000000, 29, 15, 'http://wooyang-tech.co.kr/wooimg/21_29.gif', 25),
#
# -- 30. 용접 공정: 고주파 용접기
# ('EQ030', '고주파 용접기', 'ASSEMBLY', 'Rofin', 'WeldMaster 800', '2020-03-10', '2020-03-25', 'OPERATING', 33000000, 30, 2, 'http://wooyang-tech.co.kr/wooimg/21_30.gif', 28),
# -- 31. 조립 공정: 서브프레임 조립 로봇
# ('EQ031', '서브프레임 조립 로봇', 'ASSEMBLY', 'KUKA', 'KR 800 PA', '2020-07-12', '2020-08-01', 'OPERATING', 32000000, 31, 2, 'http://wooyang-tech.co.kr/wooimg/22_01.gif', 25),
# -- 32. 가공 공정: 실린더 가공기
# ('EQ034', '실린더 가공기', 'MACHINING', 'Mazak', 'Variaxis C-600', '2020-02-20', '2020-03-05', 'OPERATING', 38000000, 34, 4, 'http://wooyang-tech.co.kr/wooimg/22_04.gif', 35),
# -- 33. 품질 검사 공정: 차체 품질 검사기
# ('EQ036', '차체 품질 검사기', 'INSPECTION', 'Mitutoyo', 'STRATO-Apex 574', '2018-10-10', '2018-11-01', 'OPERATING', 20000000, 36, 6, 'http://wooyang-tech.co.kr/wooimg/22_06.gif', 10),
# -- 34. CNC 가공 공정: 밸브 CNC 가공기
# ('EQ040', '밸브 CNC 가공기', 'MACHINING', 'DMG Mori', 'DMU 80 P duoBLOCK', '2018-09-12', '2018-10-01', 'OPERATING', 60000000, 40, 19, 'http://wooyang-tech.co.kr/wooimg/22_10.gif', 50),
# -- 35. 검사 공정: 변속기 검사 장비
# ('EQ041', '변속기 검사 장비', 'INSPECTION', 'Zeiss', 'PRISMO verity', '2021-01-05', '2021-01-25', 'OPERATING', 19000000, 41, 6, 'http://wooyang-tech.co.kr/wooimg/22_11.gif', 9),
# -- 36. 물류 공정: 서브프레임 이송 컨베이어
# ('EQ043', '서브프레임 이송 컨베이어', 'LOGISTICS', 'Siemens', 'ConveyAll X2', '2019-06-15', '2019-07-01', 'OPERATING', 30000000, 43, 15, 'http://wooyang-tech.co.kr/wooimg/22_13.gif', 19),
# -- 37. 물류 공정: 엔진 블록 이송 리프트
# ('EQ044', '엔진 블록 이송 리프트', 'LOGISTICS', 'Thyssenkrupp', 'LiftMaster Z400', '2018-12-10', '2019-01-01', 'OPERATING', 32000000, 44, 15, 'http://wooyang-tech.co.kr/wooimg/22_14.gif', 21),
# -- 38. 조립 공정: 후방 서스펜션 조립 로봇
# ('EQ045', '후방 서스펜션 조립 로봇', 'ASSEMBLY', 'KUKA', 'KR 500 FORTEC', '2021-02-11', '2021-03-01', 'OPERATING', 36000000, 45, 2, 'http://wooyang-tech.co.kr/wooimg/22_15.gif', 26),
# -- 39. 품질 검사 공정: 엔진 품질 검사 장비
# ('EQ066', '엔진 품질 검사 장비', 'INSPECTION', 'Zeiss', 'CONTURA G2', '2018-08-15', '2018-09-01', 'OPERATING', 22000000, 6, 6, 'http://example.com/eq_066.jpg', 8),
# -- 40. CNC 가공 공정: 실린더 블록 CNC 가공기
# ('EQ070', '실린더 블록 CNC 가공기', 'MACHINING', 'DMG Mori', 'DMU 90 P duoBLOCK', '2018-06-15', '2018-07-01', 'OPERATING', 62000000, 10, 19, 'http://example.com/eq_070.jpg', 55),
# -- 41. 검사 공정: 브레이크 디스크 검사 장비
# ('EQ071', '브레이크 디스크 검사 장비', 'INSPECTION', 'Mitutoyo', 'FORMTRACER Avant', '2021-03-05', '2021-03-25', 'OPERATING', 18000000, 11, 6, 'http://example.com/eq_071.jpg', 7),
# -- 42. 포장 공정: 엔진 하우징 포장 로봇
# ('EQ072', '엔진 하우징 포장 로봇', 'PACKAGING', 'Yaskawa', 'MPL500 II', '2020-12-20', '2021-01-10', 'OPERATING', 29000000, 12, 18, 'http://example.com/eq_072.jpg', 14),
# -- 43. 물류 공정: 차체 이동 컨베이어
# ('EQ073', '차체 이동 컨베이어', 'LOGISTICS', 'Siemens', 'VarioFlow X', '2019-09-15', '2019-10-01', 'OPERATING', 32000000, 13, 15, 'http://example.com/eq_073.jpg', 18),
# -- 44. 물류 공정: 부품 리프트 장치
# ('EQ074', '부품 리프트 장치', 'LOGISTICS', 'Thyssenkrupp', 'LiftMaster S800', '2018-11-12', '2018-12-01', 'OPERATING', 35000000, 14, 15, 'http://example.com/eq_074.jpg', 20),
# -- 45. 조립 공정: 리어 서스펜션 조립 로봇
# ('EQ075', '리어 서스펜션 조립 로봇', 'ASSEMBLY', 'KUKA', 'KR 470 FORTEC', '2021-05-18', '2021-06-05', 'OPERATING', 37000000, 15, 2, 'http://example.com/eq_075.jpg', 24);