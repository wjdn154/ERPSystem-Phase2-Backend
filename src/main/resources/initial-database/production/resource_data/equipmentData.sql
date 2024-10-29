INSERT INTO equipment_data (equipment_num, equipment_name, equipment_type, manufacturer, model_name, purchase_date, install_date, operation_status, cost, workcenter_id, factory_id, equipment_img, k_wh, company_id)
VALUES
-- 1. 조립 공정: 차체 조립 로봇
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

-- 26. 전기 제어 패널: 전기 제어 패널
('EQ026', '전기 제어 패널', 'ASSEMBLY', 'Schneider', 'ControlPANEL 200', '2019-02-10', '2019-02-25', 'OPERATING', 12000000, 26, 2, 'http://wooyang-tech.co.kr/wooimg/21_26.gif', 10, 1),

-- 27. 세척 공정: 고압 세척기
('EQ027', '고압 세척기', 'INSPECTION', 'Kärcher', 'HighWash 900', '2020-06-01', '2020-06-20', 'OPERATING', 18000000, 27, 6, 'http://wooyang-tech.co.kr/wooimg/21_27.gif', 18, 1),

-- 28. 압출 공정: 압출기
('EQ028', '압출기', 'MACHINING', 'Davis-Standard', 'SuperEX 2500', '2021-04-15', '2021-05-05', 'OPERATING', 55000000, 28, 15, 'http://wooyang-tech.co.kr/wooimg/21_28.gif', 48, 1),

-- 29. 물류 공정: 물류용 리프트
('EQ029', '물류용 리프트', 'LOGISTICS', 'Thyssenkrupp', 'LiftMaster L500', '2019-05-20', '2019-06-05', 'OPERATING', 32000000, 29, 15, 'http://wooyang-tech.co.kr/wooimg/21_29.gif', 25, 1),

-- 30. 용접 공정: 고주파 용접기
('EQ030', '고주파 용접기', 'ASSEMBLY', 'Rofin', 'WeldMaster 800', '2020-03-10', '2020-03-25', 'OPERATING', 33000000, 30, 2, 'http://wooyang-tech.co.kr/wooimg/21_30.gif', 28, 1),
-- 31. 조립 공정: 서브프레임 조립 로봇
('EQ031', '서브프레임 조립 로봇', 'ASSEMBLY', 'KUKA', 'KR 800 PA', '2020-07-12', '2020-08-01', 'OPERATING', 32000000, 31, 2, 'http://wooyang-tech.co.kr/wooimg/22_01.gif', 25, 1),
-- 32. 가공 공정: 실린더 가공기
('EQ034', '실린더 가공기', 'MACHINING', 'Mazak', 'Variaxis C-600', '2020-02-20', '2020-03-05', 'OPERATING', 38000000, 34, 4, 'http://wooyang-tech.co.kr/wooimg/22_04.gif', 35, 1),
-- 33. 품질 검사 공정: 차체 품질 검사기
('EQ036', '차체 품질 검사기', 'INSPECTION', 'Mitutoyo', 'STRATO-Apex 574', '2018-10-10', '2018-11-01', 'OPERATING', 20000000, 36, 6, 'http://wooyang-tech.co.kr/wooimg/22_06.gif', 10, 1),
-- 34. CNC 가공 공정: 밸브 CNC 가공기
('EQ040', '밸브 CNC 가공기', 'MACHINING', 'DMG Mori', 'DMU 80 P duoBLOCK', '2018-09-12', '2018-10-01', 'OPERATING', 60000000, 40, 19, 'http://wooyang-tech.co.kr/wooimg/22_10.gif', 50, 1),
-- 35. 검사 공정: 변속기 검사 장비
('EQ041', '변속기 검사 장비', 'INSPECTION', 'Zeiss', 'PRISMO verity', '2021-01-05', '2021-01-25', 'OPERATING', 19000000, 41, 6, 'http://wooyang-tech.co.kr/wooimg/22_11.gif', 9, 1),
-- 36. 물류 공정: 서브프레임 이송 컨베이어
('EQ043', '서브프레임 이송 컨베이어', 'LOGISTICS', 'Siemens', 'ConveyAll X2', '2019-06-15', '2019-07-01', 'OPERATING', 30000000, 43, 15, 'http://wooyang-tech.co.kr/wooimg/22_13.gif', 19, 1),
-- 37. 물류 공정: 엔진 블록 이송 리프트
('EQ044', '엔진 블록 이송 리프트', 'LOGISTICS', 'Thyssenkrupp', 'LiftMaster Z400', '2018-12-10', '2019-01-01', 'OPERATING', 32000000, 44, 15, 'http://wooyang-tech.co.kr/wooimg/22_14.gif', 21, 1),
-- 38. 조립 공정: 후방 서스펜션 조립 로봇
('EQ045', '후방 서스펜션 조립 로봇', 'ASSEMBLY', 'KUKA', 'KR 500 FORTEC', '2021-02-11', '2021-03-01', 'OPERATING', 36000000, 45, 2, 'http://wooyang-tech.co.kr/wooimg/22_15.gif', 26, 1),
-- 39. 품질 검사 공정: 엔진 품질 검사 장비
('EQ066', '엔진 품질 검사 장비', 'INSPECTION', 'Zeiss', 'CONTURA G2', '2018-08-15', '2018-09-01', 'OPERATING', 22000000, 6, 6, 'http://example.com/eq_066.jpg', 8, 1),
-- 40. CNC 가공 공정: 실린더 블록 CNC 가공기
('EQ070', '실린더 블록 CNC 가공기', 'MACHINING', 'DMG Mori', 'DMU 90 P duoBLOCK', '2018-06-15', '2018-07-01', 'OPERATING', 62000000, 10, 19, 'http://example.com/eq_070.jpg', 55, 1),
-- 41. 검사 공정: 브레이크 디스크 검사 장비
('EQ071', '브레이크 디스크 검사 장비', 'INSPECTION', 'Mitutoyo', 'FORMTRACER Avant', '2021-03-05', '2021-03-25', 'OPERATING', 18000000, 11, 6, 'http://example.com/eq_071.jpg', 7, 1),
-- 42. 포장 공정: 엔진 하우징 포장 로봇
('EQ072', '엔진 하우징 포장 로봇', 'PACKAGING', 'Yaskawa', 'MPL500 II', '2020-12-20', '2021-01-10', 'OPERATING', 29000000, 12, 18, 'http://example.com/eq_072.jpg', 14, 1),
-- 43. 물류 공정: 차체 이동 컨베이어
('EQ073', '차체 이동 컨베이어', 'LOGISTICS', 'Siemens', 'VarioFlow X', '2019-09-15', '2019-10-01', 'OPERATING', 32000000, 13, 15, 'http://example.com/eq_073.jpg', 18, 1),
-- 44. 물류 공정: 부품 리프트 장치
('EQ074', '부품 리프트 장치', 'LOGISTICS', 'Thyssenkrupp', 'LiftMaster S800', '2018-11-12', '2018-12-01', 'OPERATING', 35000000, 14, 15, 'http://example.com/eq_074.jpg', 20, 1),
-- 45. 조립 공정: 리어 서스펜션 조립 로봇
('EQ075', '리어 서스펜션 조립 로봇', 'ASSEMBLY', 'KUKA', 'KR 470 FORTEC', '2021-05-18', '2021-06-05', 'OPERATING', 37000000, 15, 2, 'http://example.com/eq_075.jpg', 24, 1);