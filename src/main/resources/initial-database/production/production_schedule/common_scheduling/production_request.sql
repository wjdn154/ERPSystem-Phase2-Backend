INSERT INTO common_scheduling_production_requests
(request_type, progress_type, name, is_confirmed, request_date,
 deadline_of_completion, due_date_to_provide, client_id, request_quantity,
 product_id, requester_id, remarks)
VALUES
    ('MASS_PRODUCTION', 'CREATED', '생산 의뢰 1', false, '2024-10-23',
     '2024-11-01', '2024-11-05', 1, 500, 1, 1, '긴급한 요청입니다.'),
    ('PILOT_PRODUCTION', 'NOT_STARTED', '시험 양산 의뢰', true, '2024-09-01',
     '2024-09-15', NULL, 2, 300, 2, 1, '품질 검사를 위해 시험적으로 진행됨.');
