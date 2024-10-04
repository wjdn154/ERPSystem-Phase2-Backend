INSERT INTO common_scheduling_production_order
(name, plan_of_make_to_order_id, plan_of_make_to_stock_id, remarks, confirmed, start_date_time, end_date_time)
VALUES
    ('조립 작업 지시 1 - 엔진 조립', NULL, NULL, '엔진 부품을 조립하는 작업 지시. PRC001 (조립) 공정 수행', FALSE, '2024-10-01 08:00:00', '2024-10-01 17:00:00'),
    ('용접 작업 지시 2 - 차체 프레임 용접', NULL, NULL, '차체 프레임을 용접하는 작업 지시. PRC002 (용접) 공정 수행', FALSE, '2024-10-02 08:00:00', '2024-10-02 17:00:00'),
    ('도장 작업 지시 3 - 도어 패널 도장', NULL, NULL, '도어 패널의 표면을 도장하는 작업 지시. PRC003 (도장) 공정 수행', FALSE, '2024-10-03 09:00:00', '2024-10-03 13:00:00'),
    ('가공 작업 지시 4 - 기어 가공', NULL, NULL, '기어 부품을 정밀 가공하는 작업 지시. PRC004 (가공) 공정 수행', FALSE, '2024-10-04 08:00:00', '2024-10-04 17:00:00'),
    ('열처리 작업 지시 5 - 샤프트 열처리', NULL, NULL, '샤프트 부품의 강도 향상을 위한 열처리 작업 지시. PRC005 (열처리) 공정 수행', FALSE, '2024-01-05 08:00:00', '2024-01-05 17:00:00'),
    ('품질 검사 작업 지시 6 - 브레이크 시스템 검사', NULL, NULL, '브레이크 시스템의 품질을 검사하는 작업 지시. PRC006 (품질 검사) 공정 수행', FALSE, '2024-01-12 08:00:00', '2024-01-12 17:00:00'),
    ('프레스 작업 지시 7 - 휠 프레스 성형', NULL, NULL, '자동차 휠을 프레스 성형하는 작업 지시. PRC007 (프레스) 공정 수행', FALSE, '2024-01-18 08:00:00', '2024-01-18 17:00:00'),
    ('단조 작업 지시 8 - 클러치 플레이트 단조', NULL, NULL, '클러치 플레이트를 단조하는 작업 지시. PRC008 (단조) 공정 수행', FALSE, '2024-02-08 08:00:00', '2024-02-08 17:00:00'),
    ('플라스틱 성형 작업 지시 9 - 대시보드 성형', NULL, NULL, '대시보드를 플라스틱 성형하는 작업 지시. PRC009 (플라스틱 성형) 공정 수행', FALSE, '2024-02-13 08:00:00', '2024-02-13 17:00:00'),
    ('주조 작업 지시 10 - 엔진 블록 주조', NULL, NULL, '엔진 블록을 금속 주조하는 작업 지시. PRC010 (주조) 공정 수행', FALSE, DATE_ADD(LAST_DAY('2024-03-01'), INTERVAL -RAND()*30 DAY), ADDTIME(DATE_ADD(LAST_DAY('2024-03-01'), INTERVAL -RAND()*30 DAY), '09:00:00')),
    ('세척 작업 지시 11 - 기어 박스 부품 세척', NULL, NULL, '기어 박스 부품을 세척하는 작업 지시. PRC011 (세척) 공정 수행', FALSE, DATE_ADD(LAST_DAY('2024-04-01'), INTERVAL -RAND()*30 DAY), ADDTIME(DATE_ADD(LAST_DAY('2024-04-01'), INTERVAL -RAND()*30 DAY), '09:00:00')),
    ('절단 작업 지시 12 - 철판 절단', NULL, NULL, '철판을 절단하는 작업 지시. PRC012 (절단) 공정 수행', FALSE, DATE_ADD(LAST_DAY('2024-05-01'), INTERVAL -RAND()*30 DAY), ADDTIME(DATE_ADD(LAST_DAY('2024-05-01'), INTERVAL -RAND()*30 DAY), '09:00:00')),
    ('레이저 절단 작업 지시 13 - 정밀 부품 레이저 절단', NULL, NULL, '정밀 부품을 레이저로 절단하는 작업 지시. PRC013 (레이저 절단) 공정 수행', FALSE, DATE_ADD(LAST_DAY('2024-06-01'), INTERVAL -RAND()*30 DAY), ADDTIME(DATE_ADD(LAST_DAY('2024-06-01'), INTERVAL -RAND()*30 DAY), '09:00:00')),
    ('CNC 가공 작업 지시 14 - 금속 부품 CNC 가공', NULL, NULL, '금속 부품을 CNC 가공하는 작업 지시. PRC014 (CNC 가공) 공정 수행', FALSE, DATE_ADD(LAST_DAY('2024-07-01'), INTERVAL -RAND()*30 DAY), ADDTIME(DATE_ADD(LAST_DAY('2024-07-01'), INTERVAL -RAND()*30 DAY), '09:00:00')),
    ('연마 작업 지시 15 - 금속 표면 연마', NULL, NULL, '금속 표면을 연마하는 작업 지시. PRC015 (연마) 공정 수행', FALSE, DATE_ADD(LAST_DAY('2024-08-01'), INTERVAL -RAND()*30 DAY), ADDTIME(DATE_ADD(LAST_DAY('2024-08-01'), INTERVAL -RAND()*30 DAY), '09:00:00')),
    ('조립 2차 작업 지시 16 - 엔진 부품 2차 조립', NULL, NULL, '엔진 부품의 2차 조립을 수행하는 작업 지시. PRC016 (조립 2차) 공정 수행', FALSE, DATE_ADD(LAST_DAY('2024-09-01'), INTERVAL -RAND()*30 DAY), ADDTIME(DATE_ADD(LAST_DAY('2024-09-01'), INTERVAL -RAND()*30 DAY), '09:00:00')),
    ('패키징 작업 지시 17 - 완제품 패키징', NULL, NULL, '완제품을 패키징하는 작업 지시. PRC017 (패키징) 공정 수행', FALSE, DATE_ADD(LAST_DAY('2024-10-01'), INTERVAL -RAND()*30 DAY), ADDTIME(DATE_ADD(LAST_DAY('2024-10-01'), INTERVAL -RAND()*30 DAY), '09:00:00')),
    ('압출 성형 작업 지시 18 - 플라스틱 압출 성형', NULL, NULL, '플라스틱 부품의 압출 성형을 수행하는 작업 지시. PRC018 (압출 성형) 공정 수행', FALSE, DATE_ADD(LAST_DAY('2024-11-01'), INTERVAL -RAND()*30 DAY), ADDTIME(DATE_ADD(LAST_DAY('2024-11-01'), INTERVAL -RAND()*30 DAY), '09:00:00')),
    ('프레스 2차 작업 지시 19 - 금속 프레스 2차 작업', NULL, NULL, '금속 프레스 2차 작업을 수행하는 작업 지시. PRC019 (프레스 2차) 공정 수행', FALSE, DATE_ADD(LAST_DAY('2024-12-01'), INTERVAL -RAND()*30 DAY), ADDTIME(DATE_ADD(LAST_DAY('2024-12-01'), INTERVAL -RAND()*30 DAY), '09:00:00')),
    ('경화 작업 지시 20 - 재료 경화 작업', NULL, NULL, '재료 경화를 위한 작업 지시. PRC020 (경화) 공정 수행', FALSE, DATE_ADD(LAST_DAY('2024-12-01'), INTERVAL -RAND()*30 DAY), ADDTIME(DATE_ADD(LAST_DAY('2024-12-01'), INTERVAL -RAND()*30 DAY), '09:00:00'));