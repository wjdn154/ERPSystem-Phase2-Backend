-- 작업자 배정 테이블에 데이터 삽입


# INSERT INTO common_scheduling_worker_assignment (workcenter_id, worker_id, assignment_date, shift_type_id, production_order_id)
# VALUES
#     -- 현재 날짜로 배정
#     (1, 1, CURRENT_DATE, 1, 1),  -- 작업장 1에 직원 1 배정
#     (2, 2, CURRENT_DATE, 2, 2);  -- 작업장 2에 직원 2 배정
#     (3, 3, CURRENT_DATE, 3, 3);  -- 작업장 3에 직원 3 배정

#
#     -- 2024년 10월
#     (1, 1, '2024-10-05', 1, 4),
#     (2, 2, '2024-10-12', 2, 5),
#     (3, 3, '2024-10-18', 1, 6),
#     (1, 1, '2024-10-08', 2, 7),
#     (2, 2, '2024-10-13', 1, 8),
#     (3, 3, '2024-10-20', 2, 9);

#     -- 랜덤 날짜 생성
#     (1, 1, DATE_ADD(LAST_DAY('2024-01-01'), INTERVAL -RAND()*30 DAY), 1, 10),
#     (1, 1, DATE_ADD(LAST_DAY('2024-01-01'), INTERVAL -RAND()*30 DAY), 1, 10),
#     (1, 1, DATE_ADD(LAST_DAY('2024-01-01'), INTERVAL -RAND()*30 DAY), 1, 10),
#     (1, 1, DATE_ADD(LAST_DAY('2024-01-01'), INTERVAL -RAND()*30 DAY), 1, 10),
#     (1, 1, DATE_ADD(LAST_DAY('2024-01-01'), INTERVAL -RAND()*30 DAY), 1, 10),
#     (1, 1, DATE_ADD(LAST_DAY('2024-01-01'), INTERVAL -RAND()*30 DAY), 1, 10),
#     (1, 1, DATE_ADD(LAST_DAY('2024-01-01'), INTERVAL -RAND()*30 DAY), 1, 10),
#
#     (2, 2, DATE_ADD(LAST_DAY('2024-02-01'), INTERVAL -RAND()*30 DAY), 1, 11),
#     (2, 2, DATE_ADD(LAST_DAY('2024-02-01'), INTERVAL -RAND()*30 DAY), 1, 11),
#     (2, 2, DATE_ADD(LAST_DAY('2024-02-01'), INTERVAL -RAND()*30 DAY), 1, 11),
#     (2, 2, DATE_ADD(LAST_DAY('2024-02-01'), INTERVAL -RAND()*30 DAY), 1, 11),
#     (2, 2, DATE_ADD(LAST_DAY('2024-02-01'), INTERVAL -RAND()*30 DAY), 1, 11),
#     (2, 2, DATE_ADD(LAST_DAY('2024-02-01'), INTERVAL -RAND()*30 DAY), 1, 11),
#     (2, 2, DATE_ADD(LAST_DAY('2024-02-01'), INTERVAL -RAND()*30 DAY), 1, 11),
#     (2, 2, DATE_ADD(LAST_DAY('2024-02-01'), INTERVAL -RAND()*30 DAY), 1, 11),
#     (2, 2, DATE_ADD(LAST_DAY('2024-02-01'), INTERVAL -RAND()*30 DAY), 1, 11),
#
#     (3, 3, DATE_ADD(LAST_DAY('2024-03-01'), INTERVAL -RAND()*30 DAY), 1, 12),
#     (3, 3, DATE_ADD(LAST_DAY('2024-03-01'), INTERVAL -RAND()*30 DAY), 1, 12),
#     (3, 3, DATE_ADD(LAST_DAY('2024-03-01'), INTERVAL -RAND()*30 DAY), 1, 12),
#     (3, 3, DATE_ADD(LAST_DAY('2024-03-01'), INTERVAL -RAND()*30 DAY), 1, 12),
#     (3, 3, DATE_ADD(LAST_DAY('2024-03-01'), INTERVAL -RAND()*30 DAY), 1, 12),
#     (3, 3, DATE_ADD(LAST_DAY('2024-03-01'), INTERVAL -RAND()*30 DAY), 1, 12),
#     (3, 3, DATE_ADD(LAST_DAY('2024-03-01'), INTERVAL -RAND()*30 DAY), 1, 12),
#     (3, 3, DATE_ADD(LAST_DAY('2024-03-01'), INTERVAL -RAND()*30 DAY), 1, 12),
#
#     (1, 1, DATE_ADD(LAST_DAY('2024-04-01'), INTERVAL -RAND()*30 DAY), 1, 13),
#     (1, 1, DATE_ADD(LAST_DAY('2024-04-01'), INTERVAL -RAND()*30 DAY), 1, 13),
#     (1, 1, DATE_ADD(LAST_DAY('2024-04-01'), INTERVAL -RAND()*30 DAY), 1, 13),
#     (1, 1, DATE_ADD(LAST_DAY('2024-04-01'), INTERVAL -RAND()*30 DAY), 1, 13),
#     (1, 1, DATE_ADD(LAST_DAY('2024-04-01'), INTERVAL -RAND()*30 DAY), 1, 13),
#     (1, 1, DATE_ADD(LAST_DAY('2024-04-01'), INTERVAL -RAND()*30 DAY), 1, 13),
#     (1, 1, DATE_ADD(LAST_DAY('2024-04-01'), INTERVAL -RAND()*30 DAY), 1, 13),
#     (1, 1, DATE_ADD(LAST_DAY('2024-04-01'), INTERVAL -RAND()*30 DAY), 1, 13),
#
#     (2, 2, DATE_ADD(LAST_DAY('2024-05-01'), INTERVAL -RAND()*30 DAY), 1, 14),
#     (2, 2, DATE_ADD(LAST_DAY('2024-05-01'), INTERVAL -RAND()*30 DAY), 1, 14),
#     (2, 2, DATE_ADD(LAST_DAY('2024-05-01'), INTERVAL -RAND()*30 DAY), 1, 14),
#     (2, 2, DATE_ADD(LAST_DAY('2024-05-01'), INTERVAL -RAND()*30 DAY), 1, 14),
#     (2, 2, DATE_ADD(LAST_DAY('2024-05-01'), INTERVAL -RAND()*30 DAY), 1, 14),
#     (2, 2, DATE_ADD(LAST_DAY('2024-05-01'), INTERVAL -RAND()*30 DAY), 1, 14),
#     (2, 2, DATE_ADD(LAST_DAY('2024-05-01'), INTERVAL -RAND()*30 DAY), 1, 14),
#     (2, 2, DATE_ADD(LAST_DAY('2024-05-01'), INTERVAL -RAND()*30 DAY), 1, 14),
#
#     (3, 3, DATE_ADD(LAST_DAY('2024-06-01'), INTERVAL -RAND()*30 DAY), 1, 15),
#     (3, 3, DATE_ADD(LAST_DAY('2024-06-01'), INTERVAL -RAND()*30 DAY), 1, 15),
#     (3, 3, DATE_ADD(LAST_DAY('2024-06-01'), INTERVAL -RAND()*30 DAY), 1, 15),
#     (3, 3, DATE_ADD(LAST_DAY('2024-06-01'), INTERVAL -RAND()*30 DAY), 1, 15),
#     (3, 3, DATE_ADD(LAST_DAY('2024-06-01'), INTERVAL -RAND()*30 DAY), 1, 15),
#     (3, 3, DATE_ADD(LAST_DAY('2024-06-01'), INTERVAL -RAND()*30 DAY), 1, 15),
#     (3, 3, DATE_ADD(LAST_DAY('2024-06-01'), INTERVAL -RAND()*30 DAY), 1, 15),
#     (3, 3, DATE_ADD(LAST_DAY('2024-06-01'), INTERVAL -RAND()*30 DAY), 1, 15),
#     (3, 3, DATE_ADD(LAST_DAY('2024-06-01'), INTERVAL -RAND()*30 DAY), 1, 15),
#
#     (1, 1, DATE_ADD(LAST_DAY('2024-07-01'), INTERVAL -RAND()*30 DAY), 1, 16),
#     (1, 1, DATE_ADD(LAST_DAY('2024-07-01'), INTERVAL -RAND()*30 DAY), 1, 16),
#     (1, 1, DATE_ADD(LAST_DAY('2024-07-01'), INTERVAL -RAND()*30 DAY), 1, 16),
#     (1, 1, DATE_ADD(LAST_DAY('2024-07-01'), INTERVAL -RAND()*30 DAY), 1, 16),
#     (1, 1, DATE_ADD(LAST_DAY('2024-07-01'), INTERVAL -RAND()*30 DAY), 1, 16),
#     (1, 1, DATE_ADD(LAST_DAY('2024-07-01'), INTERVAL -RAND()*30 DAY), 1, 16),
#     (1, 1, DATE_ADD(LAST_DAY('2024-07-01'), INTERVAL -RAND()*30 DAY), 1, 16),
#     (1, 1, DATE_ADD(LAST_DAY('2024-07-01'), INTERVAL -RAND()*30 DAY), 1, 16),
#     (1, 1, DATE_ADD(LAST_DAY('2024-07-01'), INTERVAL -RAND()*30 DAY), 1, 16),
#     (1, 1, DATE_ADD(LAST_DAY('2024-07-01'), INTERVAL -RAND()*30 DAY), 1, 16),
#
#     (2, 2, DATE_ADD(LAST_DAY('2024-08-01'), INTERVAL -RAND()*30 DAY), 1, 17),
#     (2, 2, DATE_ADD(LAST_DAY('2024-08-01'), INTERVAL -RAND()*30 DAY), 1, 17),
#     (2, 2, DATE_ADD(LAST_DAY('2024-08-01'), INTERVAL -RAND()*30 DAY), 1, 17),
#     (2, 2, DATE_ADD(LAST_DAY('2024-08-01'), INTERVAL -RAND()*30 DAY), 1, 17),
#     (2, 2, DATE_ADD(LAST_DAY('2024-08-01'), INTERVAL -RAND()*30 DAY), 1, 17),
#     (2, 2, DATE_ADD(LAST_DAY('2024-08-01'), INTERVAL -RAND()*30 DAY), 1, 17),
#     (2, 2, DATE_ADD(LAST_DAY('2024-08-01'), INTERVAL -RAND()*30 DAY), 1, 17),
#     (2, 2, DATE_ADD(LAST_DAY('2024-08-01'), INTERVAL -RAND()*30 DAY), 1, 17),
#     (2, 2, DATE_ADD(LAST_DAY('2024-08-01'), INTERVAL -RAND()*30 DAY), 1, 17),
#     (2, 2, DATE_ADD(LAST_DAY('2024-08-01'), INTERVAL -RAND()*30 DAY), 1, 17),
#
#     (3, 3, DATE_ADD(LAST_DAY('2024-09-01'), INTERVAL -RAND()*30 DAY), 1, 18),
#     (3, 3, DATE_ADD(LAST_DAY('2024-09-01'), INTERVAL -RAND()*30 DAY), 1, 18),
#     (3, 3, DATE_ADD(LAST_DAY('2024-09-01'), INTERVAL -RAND()*30 DAY), 1, 18),
#     (3, 3, DATE_ADD(LAST_DAY('2024-09-01'), INTERVAL -RAND()*30 DAY), 1, 18),
#     (3, 3, DATE_ADD(LAST_DAY('2024-09-01'), INTERVAL -RAND()*30 DAY), 1, 18),
#     (3, 3, DATE_ADD(LAST_DAY('2024-09-01'), INTERVAL -RAND()*30 DAY), 1, 18),
#     (3, 3, DATE_ADD(LAST_DAY('2024-09-01'), INTERVAL -RAND()*30 DAY), 1, 18),
#     (3, 3, DATE_ADD(LAST_DAY('2024-09-01'), INTERVAL -RAND()*30 DAY), 1, 18),
#     (3, 3, DATE_ADD(LAST_DAY('2024-09-01'), INTERVAL -RAND()*30 DAY), 1, 18),
#     (3, 3, DATE_ADD(LAST_DAY('2024-09-01'), INTERVAL -RAND()*30 DAY), 1, 18),
#     (3, 3, DATE_ADD(LAST_DAY('2024-09-01'), INTERVAL -RAND()*30 DAY), 1, 18),
#
#     (1, 1, DATE_ADD(LAST_DAY('2024-10-01'), INTERVAL -RAND()*30 DAY), 1, 19),
#     (1, 1, DATE_ADD(LAST_DAY('2024-10-01'), INTERVAL -RAND()*30 DAY), 1, 19),
#     (1, 1, DATE_ADD(LAST_DAY('2024-10-01'), INTERVAL -RAND()*30 DAY), 1, 19),
#     (1, 1, DATE_ADD(LAST_DAY('2024-10-01'), INTERVAL -RAND()*30 DAY), 1, 19),
#     (1, 1, DATE_ADD(LAST_DAY('2024-10-01'), INTERVAL -RAND()*30 DAY), 1, 19),
#     (1, 1, DATE_ADD(LAST_DAY('2024-10-01'), INTERVAL -RAND()*30 DAY), 1, 19),
#     (1, 1, DATE_ADD(LAST_DAY('2024-10-01'), INTERVAL -RAND()*30 DAY), 1, 19),
#     (1, 1, DATE_ADD(LAST_DAY('2024-10-01'), INTERVAL -RAND()*30 DAY), 1, 19),
#     (1, 1, DATE_ADD(LAST_DAY('2024-10-01'), INTERVAL -RAND()*30 DAY), 1, 19),
#     (1, 1, DATE_ADD(LAST_DAY('2024-10-01'), INTERVAL -RAND()*30 DAY), 1, 19),
#
#     (2, 2, DATE_ADD(LAST_DAY('2024-11-01'), INTERVAL -RAND()*30 DAY), 1, 11),
#     (2, 2, DATE_ADD(LAST_DAY('2024-11-01'), INTERVAL -RAND()*30 DAY), 1, 11),
#     (2, 2, DATE_ADD(LAST_DAY('2024-11-01'), INTERVAL -RAND()*30 DAY), 1, 11),
#     (2, 2, DATE_ADD(LAST_DAY('2024-11-01'), INTERVAL -RAND()*30 DAY), 1, 11),
#     (2, 2, DATE_ADD(LAST_DAY('2024-11-01'), INTERVAL -RAND()*30 DAY), 1, 11),
#     (2, 2, DATE_ADD(LAST_DAY('2024-11-01'), INTERVAL -RAND()*30 DAY), 1, 11),
#     (2, 2, DATE_ADD(LAST_DAY('2024-11-01'), INTERVAL -RAND()*30 DAY), 1, 11),
#     (2, 2, DATE_ADD(LAST_DAY('2024-11-01'), INTERVAL -RAND()*30 DAY), 1, 11),
#     (2, 2, DATE_ADD(LAST_DAY('2024-11-01'), INTERVAL -RAND()*30 DAY), 1, 11),
#     (2, 2, DATE_ADD(LAST_DAY('2024-11-01'), INTERVAL -RAND()*30 DAY), 1, 11),
#     (2, 2, DATE_ADD(LAST_DAY('2024-11-01'), INTERVAL -RAND()*30 DAY), 1, 11),
#     (2, 2, DATE_ADD(LAST_DAY('2024-11-01'), INTERVAL -RAND()*30 DAY), 1, 11),
#
#     (3, 3, DATE_ADD(LAST_DAY('2024-12-01'), INTERVAL -RAND()*30 DAY), 1, 12),
#     (3, 3, DATE_ADD(LAST_DAY('2024-12-01'), INTERVAL -RAND()*30 DAY), 1, 12),
#     (3, 3, DATE_ADD(LAST_DAY('2024-12-01'), INTERVAL -RAND()*30 DAY), 1, 12),
#     (3, 3, DATE_ADD(LAST_DAY('2024-12-01'), INTERVAL -RAND()*30 DAY), 1, 12),
#     (3, 3, DATE_ADD(LAST_DAY('2024-12-01'), INTERVAL -RAND()*30 DAY), 1, 12),
#     (3, 3, DATE_ADD(LAST_DAY('2024-12-01'), INTERVAL -RAND()*30 DAY), 1, 12),
#     (3, 3, DATE_ADD(LAST_DAY('2024-12-01'), INTERVAL -RAND()*30 DAY), 1, 12),
#     (3, 3, DATE_ADD(LAST_DAY('2024-12-01'), INTERVAL -RAND()*30 DAY), 1, 12),
#     (3, 3, DATE_ADD(LAST_DAY('2024-12-01'), INTERVAL -RAND()*30 DAY), 1, 12),
#     (3, 3, DATE_ADD(LAST_DAY('2024-12-01'), INTERVAL -RAND()*30 DAY), 1, 12);


#     (1, 1, '2024-10-22', 1, 1),  -- 작업장 1에 직원 1 배정
#     (2, 2, '2024-10-22', 1, 2),  -- 작업장 2에 직원 2 배정
#     (3, 3, '2024-10-22', 1, 3),  -- 작업장 3에 직원 3 배정
#
# -- 241023 확정된 작업지시에 맞춰서 초기데이터 삽입
# -- 1. 주간 엔진 오일 필터 프레스 작업 (workers = 4)
#     (1, 1, '2024-10-23', 1, 1),
#     (1, 2, '2024-10-23', 1, 1),
#     (1, 3, '2024-10-23', 1, 1),
#     (1, 4, '2024-10-23', 1, 1),
#
#     -- 2. 야간 엔진 오일 필터 조정 작업 (workers = 5)
#     (2, 5, '2024-10-23', 2, 2),
#     (2, 6, '2024-10-23', 2, 2),
#     (2, 7, '2024-10-23', 2, 2),
#     (2, 8, '2024-10-23', 2, 2),
#     (2, 9, '2024-10-23', 2, 2),
#
#     -- 3. 주간 엔진 오일 필터 검수 작업 (workers = 3)
#     (3, 10, '2024-10-23', 1, 3),
#     (3, 11, '2024-10-23', 1, 3),
#     (3, 12, '2024-10-23', 1, 3),
#
#     -- 4. 야간 엔진 오일 필터 마무리 작업 (workers = 6)
#     (4, 13, '2024-10-23', 2, 4),
#     (4, 14, '2024-10-23', 2, 4),
#     (4, 15, '2024-10-23', 2, 4),
#     (4, 1, '2024-10-23', 2, 4),
#     (4, 2, '2024-10-23', 2, 4),
#     (4, 3, '2024-10-23', 2, 4),
#
#     -- 5. 주간 엔진 오일 필터 조립 - 테스트 (workers = 3)
#     (2, 4, '2024-10-23', 1, 5),
#     (2, 5, '2024-10-23', 1, 5),
#     (2, 6, '2024-10-23', 1, 5),
#
#     -- 6. 야간 엔진 오일 필터 품질 검사 - 특수 (workers = 4)
#     (5, 7, '2024-10-23', 2, 6),
#     (5, 8, '2024-10-23', 2, 6),
#     (5, 9, '2024-10-23', 2, 6),
#     (5, 10, '2024-10-23', 2, 6),
#
#     -- 7. 주간 엔진 오일 필터 마킹 작업 (workers = 2)
#     (3, 11, '2024-10-23', 1, 7),
#     (3, 12, '2024-10-23', 1, 7),
#
#     -- 8. 야간 엔진 오일 필터 열처리 - 긴급 (workers = 5)
#     (6, 13, '2024-10-23', 2, 8),
#     (6, 14, '2024-10-23', 2, 8),
#     (6, 15, '2024-10-23', 2, 8),
#     (6, 1, '2024-10-23', 2, 8),
#     (6, 2, '2024-10-23', 2, 8),
#
#     -- 9. 주간 엔진 오일 필터 포장 작업 (workers = 3)
#     (4, 3, '2024-10-23', 1, 9),
#     (4, 4, '2024-10-23', 1, 9),
#     (4, 5, '2024-10-23', 1, 9),
#
#     -- 10. 야간 엔진 오일 필터 세척 작업 (workers = 6)
#     (5, 6, '2024-10-23', 2, 10),
#     (5, 7, '2024-10-23', 2, 10),
#     (5, 8, '2024-10-23', 2, 10),
#     (5, 9, '2024-10-23', 2, 10),
#     (5, 10, '2024-10-23', 2, 10),
#     (5, 11, '2024-10-23', 2, 10);
#

