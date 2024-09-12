-- 작업자 배정 테이블에 데이터 삽입
INSERT INTO work_order_assign_worker_assignment (workcenter_id, worker_id, assignment_date, shift_type_id, work_order_id)
VALUES
    -- 현재 날짜로 배정
    (1, 1, CURRENT_DATE, 1, 1),  -- 작업장 1에 직원 1 배정
    (2, 2, CURRENT_DATE, 1, 2),  -- 작업장 2에 직원 2 배정
    (3, 3, CURRENT_DATE, 1, 3),  -- 작업장 3에 직원 3 배정

    -- 2024년 1월에서 2월 사이 배정
    (1, 1, '2024-01-05', 1, 4),
    (2, 2, '2024-01-12', 1, 5),
    (3, 3, '2024-01-18', 1, 6),
    (1, 1, '2024-02-08', 1, 7),
    (2, 2, '2024-02-13', 1, 8),
    (3, 3, '2024-02-20', 1, 9),

    -- 랜덤 날짜 생성
    (1, 1, DATE_ADD(LAST_DAY('2024-03-01'), INTERVAL -RAND()*30 DAY), 1, 10),
    (2, 2, DATE_ADD(LAST_DAY('2024-04-01'), INTERVAL -RAND()*30 DAY), 1, 11),
    (3, 3, DATE_ADD(LAST_DAY('2024-05-01'), INTERVAL -RAND()*30 DAY), 1, 12),
    (1, 1, DATE_ADD(LAST_DAY('2024-06-01'), INTERVAL -RAND()*30 DAY), 1, 13),
    (2, 2, DATE_ADD(LAST_DAY('2024-07-01'), INTERVAL -RAND()*30 DAY), 1, 14),
    (3, 3, DATE_ADD(LAST_DAY('2024-08-01'), INTERVAL -RAND()*30 DAY), 1, 15),
    (1, 1, DATE_ADD(LAST_DAY('2024-09-01'), INTERVAL -RAND()*30 DAY), 1, 16),
    (2, 2, DATE_ADD(LAST_DAY('2024-10-01'), INTERVAL -RAND()*30 DAY), 1, 17),
    (3, 3, DATE_ADD(LAST_DAY('2024-11-01'), INTERVAL -RAND()*30 DAY), 1, 18),
    (1, 1, DATE_ADD(LAST_DAY('2024-12-01'), INTERVAL -RAND()*30 DAY), 1, 19);