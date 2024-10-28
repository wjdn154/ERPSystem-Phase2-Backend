-- mps_id = 1 작업지시 10건
INSERT INTO common_scheduling_production_order
(id, name, mps_id, remarks, confirmed, closed, start_date_time, end_date_time, production_quantity, process_id, workcenter_id, actual_start_date_time, actual_end_date_time, actual_production_quantity, workers, actual_workers)
VALUES
-- 1. 주간 엔진 오일 필터 프레스 작업
-- (1, '주간 엔진 오일 필터 프레스 작업', 1, '엔진 오일 필터의 첫 번째 프레스 작업', TRUE, TRUE,
--  '2024-10-23 08:00:00', '2024-10-23 16:00:00', 250, 3, 1,
--  '2024-10-23 08:15:00', '2024-10-23 15:50:00', 240, 4, 3),
(1, '주간 브레이크 패드 도장 작업 - 시험', 1, '시험 도장 - 브레이크 패드', TRUE, FALSE, '2024-10-23 09:00:00', '2024-10-23 13:00:00', 250, 3, 1, NULL, NULL, NULL, 4, NULL),

-- 2. 야간 엔진 오일 필터 조정 작업
(2, '야간 엔진 오일 필터 조정 작업', 1, '필터 조정 및 세부 점검 작업', TRUE, TRUE,
 '2024-10-23 16:00:00', '2024-10-23 23:50:00', 180, 4, 2,
 '2024-10-23 16:20:00', '2024-10-23 23:30:00', 170, 5, 4),

-- 3. 주간 엔진 오일 필터 검수 작업
(3, '주간 엔진 오일 필터 검수 작업', 1, '양품 검수 및 불량 검출 작업', TRUE, TRUE,
 '2024-10-23 08:00:00', '2024-10-23 12:00:00', 300, 5, 3,
 '2024-10-23 08:10:00', '2024-10-23 11:50:00', 290, 3, 2),

-- 4. 야간 엔진 오일 필터 마무리 작업
(4, '야간 엔진 오일 필터 마무리 작업', 1, '최종 마무리 및 포장 준비', TRUE, TRUE,
 '2024-10-23 18:00:00', '2024-10-23 23:55:00', 200, 6, 4,
 '2024-10-23 18:10:00', '2024-10-23 23:50:00', 190, 6, 5),

-- 5. 주간 엔진 오일 필터 조립 - 테스트
(5, '주간 엔진 오일 필터 조립 - 테스트', 1, '테스트 조립 작업', TRUE, TRUE,
 '2024-10-23 08:00:00', '2024-10-23 15:30:00', 100, 2, 2,
 '2024-10-23 08:15:00', '2024-10-23 15:00:00', 95, 3, 2),

-- 6. 야간 엔진 오일 필터 품질 검사 - 특수
(6, '야간 엔진 오일 필터 품질 검사 - 특수', 1, '특수 필터 품질 검사', TRUE, TRUE,
 '2024-10-23 18:00:00', '2024-10-23 19:50:00', 150, 7, 5,
 '2024-10-23 18:10:00', '2024-10-23 19:45:00', 145, 4, 3),

-- 7. 주간 엔진 오일 필터 마킹 작업
(7, '주간 엔진 오일 필터 마킹 작업', 1, '필터에 로고 및 시리얼 번호 마킹', TRUE, TRUE,
 '2024-10-23 09:00:00', '2024-10-23 13:00:00', 120, 3, 3,
 '2024-10-23 09:15:00', '2024-10-23 12:50:00', 115, 2, 2),

-- 8. 야간 엔진 오일 필터 열처리 - 긴급
(8, '야간 엔진 오일 필터 열처리 - 긴급', 1, '긴급 열처리 작업', TRUE, TRUE,
 '2024-10-23 18:00:00', '2024-10-23 23:40:00', 180, 9, 6,
 '2024-10-23 18:20:00', '2024-10-23 23:35:00', 175, 5, 5),

-- 9. 주간 엔진 오일 필터 포장 작업
(9, '주간 엔진 오일 필터 포장 작업', 1, '필터 포장 및 출고 준비 작업', TRUE, TRUE,
 '2024-10-23 08:00:00', '2024-10-23 12:00:00', 200, 5, 4,
 '2024-10-23 08:30:00', '2024-10-23 11:50:00', 190, 3, 2),

-- 10. 야간 엔진 오일 필터 세척 작업
(10, '야간 엔진 오일 필터 세척 작업', 1, '필터 세척 및 건조 작업', TRUE, TRUE,
 '2024-10-23 16:00:00', '2024-10-23 23:30:00', 220, 8, 5,
 '2024-10-23 16:10:00', '2024-10-23 23:20:00', 210, 6, 4),

-- 확정, 마감 FALSE
(11, '주간 엔진 오일 필터 도장 작업 - 샘플', 1, '샘플 생산 - 엔진 오일 필터', FALSE, FALSE, '2024-10-23 12:30:00', '2024-10-23 16:30:00', 210, 5, 5, NULL, NULL, NULL, 3, NULL),
(12, '야간 엔진 오일 필터 서스펜션 작업', 1, '서스펜션 조정 작업 - 엔진 오일 필터', FALSE, FALSE, '2024-10-23 17:00:00', '2024-10-23 23:00:00', 230, 8, 8, NULL, NULL, NULL, 6, NULL),
(13, '주간 엔진 오일 필터 조립 - 특수 작업', 1, '시험 생산 - 엔진 오일 필터 조립', FALSE, FALSE, '2024-10-23 08:00:00', '2024-10-23 12:00:00', 200, 2, 2, NULL, NULL, NULL, 4, NULL),

-- mps_id=2인 작업지시
-- 1. 주간 브레이크 패드 조립 작업
(14, '주간 브레이크 패드 조립 작업', 2, '브레이크 패드의 조립 공정', TRUE, TRUE,
 '2024-10-23 08:00:00', '2024-10-23 16:00:00', 300, 3, 1,
 '2024-10-23 08:10:00', '2024-10-23 15:50:00', 290, 5, 3),

-- 2. 야간 브레이크 패드 용접 작업
(15, '야간 브레이크 패드 용접 작업', 2, '긴급 용접 작업 - 브레이크 패드', TRUE, TRUE,
 '2024-10-23 16:00:00', '2024-10-23 23:45:00', 200, 4, 2,
 '2024-10-23 16:20:00', '2024-10-23 23:40:00', 195, 6, 5),

-- 3. 주간 브레이크 패드 열처리 작업
(16, '주간 브레이크 패드 열처리 작업', 2, '브레이크 패드 열처리 공정', TRUE, TRUE,
 '2024-10-23 09:00:00', '2024-10-23 15:00:00', 250, 5, 3,
 '2024-10-23 09:15:00', '2024-10-23 14:50:00', 240, 4, 3),

-- 4. 야간 브레이크 패드 검수 작업
(17, '야간 브레이크 패드 검수 작업', 2, '불량 검출 및 양품 확인 작업', TRUE, TRUE,
 '2024-10-23 18:00:00', '2024-10-23 23:30:00', 180, 6, 4,
 '2024-10-23 18:10:00', '2024-10-23 23:20:00', 175, 5, 4),

-- 5. 주간 브레이크 패드 도장 작업
(18, '주간 브레이크 패드 도장 작업', 2, '브레이크 패드 도장 공정', TRUE, TRUE,
 '2024-10-23 08:00:00', '2024-10-23 12:00:00', 150, 7, 3,
 '2024-10-23 08:15:00', '2024-10-23 11:50:00', 145, 3, 2),

-- 6. 야간 브레이크 패드 포장 작업
(19, '야간 브레이크 패드 포장 작업', 2, '포장 및 출고 준비 작업', TRUE, TRUE,
 '2024-10-23 18:00:00', '2024-10-23 22:00:00', 100, 8, 4,
 '2024-10-23 18:20:00', '2024-10-23 21:50:00', 98, 4, 3),

-- 7. 주간 브레이크 패드 검사 작업
(20, '주간 브레이크 패드 검사 작업', 2, '품질 검사 및 불량 처리', TRUE, TRUE,
 '2024-10-23 10:00:00', '2024-10-23 12:30:00', 200, 9, 2,
 '2024-10-23 10:10:00', '2024-10-23 12:20:00', 190, 3, 2),

-- 8. 야간 브레이크 패드 성형 작업
(21, '야간 브레이크 패드 성형 작업', 2, '성형 및 마무리 작업', TRUE, TRUE,
 '2024-10-23 16:00:00', '2024-10-23 22:30:00', 170, 3, 2,
 '2024-10-23 16:15:00', '2024-10-23 22:20:00', 165, 4, 3),

-- 9. 주간 브레이크 패드 조정 작업
(22, '주간 브레이크 패드 조정 작업', 2, '브레이크 패드 정밀 조정', TRUE, TRUE,
 '2024-10-23 08:00:00', '2024-10-23 11:30:00', 180, 4, 1,
 '2024-10-23 08:10:00', '2024-10-23 11:20:00', 175, 3, 2),

-- 10. 야간 브레이크 패드 테스트 작업
(23, '야간 브레이크 패드 테스트 작업', 2, '테스트 작업 및 결과 분석', TRUE, TRUE,
 '2024-10-23 19:00:00', '2024-10-23 23:50:00', 220, 5, 2,
 '2024-10-23 19:10:00', '2024-10-23 23:45:00', 215, 5, 4),

-- 확정, 마감 FALSE
(24, '주간 브레이크 패드 도장 작업 - 시험', 2, '시험 도장 - 브레이크 패드', FALSE, FALSE, '2024-10-23 09:00:00', '2024-10-23 13:00:00', 90, 5, 4, NULL, NULL, NULL, 3, NULL),
(25, '주간 브레이크 패드 조립 - 특수 작업', 2, '시험 생산 - 브레이크 패드 조립', FALSE, FALSE, '2024-10-23 08:00:00', '2024-10-23 12:00:00', 100, 3, 2, NULL, NULL, NULL, 3, NULL),
(26, '주간 브레이크 패드 도장 작업 - 샘플', 2, '샘플 도장 작업 - 브레이크 패드', FALSE, FALSE, '2024-10-23 12:30:00', '2024-10-23 16:00:00', 110, 5, 4, NULL, NULL, NULL, 4, NULL),
(27, '야간 브레이크 패드 서스펜션 작업', 2, '서스펜션 조정 작업 - 브레이크 패드', FALSE, FALSE, '2024-10-23 17:00:00', '2024-10-23 23:00:00', 130, 7, 5, NULL, NULL, NULL, 5, NULL);
