-- routing_steps 테이블 초기 데이터 삽입
INSERT INTO process_routing_routing_step (process_routing_id, process_id, step_order)
VALUES
-- 루트 A: 3개의 공정
(1, 1, 1),  -- 조립 (step_order 1)
(1, 2, 2),  -- 용접 (step_order 2)
(1, 3, 3),  -- 도장 (step_order 3)

-- 루트 B: 2개의 공정
(2, 4, 1),  -- 가공 (step_order 1)
(2, 5, 2),  -- 열처리 (step_order 2)

-- 루트 C: 3개의 공정
(3, 6, 1),  -- 품질 검사 (step_order 1)
(3, 7, 2),  -- 프레스 (step_order 2)
(3, 8, 3),  -- 단조 (step_order 3)

-- 루트 D: 2개의 공정
(4, 9, 1),  -- 플라스틱 성형 (step_order 1)
(4, 10, 2), -- 주조 (step_order 2)

-- 루트 E: 3개의 공정
(5, 11, 1), -- 세척 (step_order 1)
(5, 12, 2), -- 절단 (step_order 2)
(5, 13, 3), -- 레이저 절단 (step_order 3)

-- 루트 F: 2개의 공정
(6, 14, 1), -- CNC 가공 (step_order 1)
(6, 15, 2), -- 연마 (step_order 2)

-- 루트 G: 3개의 공정
(7, 16, 1), -- 조립 2차 (step_order 1)
(7, 17, 2), -- 패키징 (step_order 2)
(7, 18, 3), -- 압출 성형 (step_order 3)

-- 루트 H: 2개의 공정
(8, 19, 1), -- 프레스 2차 (step_order 1)
(8, 20, 2), -- 경화 (step_order 2)

-- 루트 I: 3개의 공정
(9, 21, 1), -- 사출 성형 (step_order 1)
(9, 22, 2), -- 압축 성형 (step_order 2)
(9, 23, 3), -- 합성 (step_order 3)

-- 루트 J: 2개의 공정
(10, 24, 1), -- 광택 (step_order 1)
(10, 25, 2); -- 코팅 (step_order 2)
