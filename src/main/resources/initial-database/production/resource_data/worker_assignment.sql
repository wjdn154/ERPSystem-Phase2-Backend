-- 작업자 배정 테이블에 데이터 삽입
INSERT INTO resource_data_worker_assignment (workcenter_id, worker_id, assignment_date, shift, work_order_id)
VALUES
    (1, 1, '2024-09-05', '주간', 1), -- 작업장 ID 1에 직원 ID 1 배정
    (2, 2, '2024-09-05', '주간', 2), -- 작업장 ID 2에 직원 ID 2 배정
    (3, 3, '2024-09-05', '주간', 3); -- 작업장 ID 3에 직원 ID 3 배정
