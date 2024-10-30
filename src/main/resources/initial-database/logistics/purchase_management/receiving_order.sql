-- ReceivingOrder 테이블 초기 데이터 삽입
INSERT INTO receiving_order (client_id, manager_id, warehouse_id, delivery_date, date, remarks, status) VALUES
(1, 1, 1, '2024-10-01', '2024-09-25', '긴급', 'WAITING_FOR_RECEIPT'),
(2, 2, 2, '2024-10-03', '2024-09-28', '일반', 'WAITING_FOR_RECEIPT'),
(3, 3, 3, '2024-10-05', '2024-09-30', '우선 처리', 'WAITING_FOR_RECEIPT'),
(1, 2, 1, '2024-10-06', '2024-09-27', '검수 필요', 'WAITING_FOR_RECEIPT'),
(3, 1, 2, '2024-10-07', '2024-09-29', '정기 발주', 'WAITING_FOR_RECEIPT'),
(2, 3, 1, '2024-10-10', '2024-09-30', '창고 부족', 'WAITING_FOR_RECEIPT'),
(1, 1, 3, '2024-10-15', '2024-10-01', '다음 주 입고', 'WAITING_FOR_RECEIPT'),
(3, 2, 2, '2024-10-12', '2024-09-28', '고객 요청', 'WAITING_FOR_RECEIPT'),
(2, 1, 3, '2024-10-18', '2024-10-05', '주문 취소 가능', 'WAITING_FOR_RECEIPT'),
(1, 2, 1, '2024-10-20', '2024-10-08', '긴급 배송 요청', 'WAITING_FOR_RECEIPT');

-- ReceivingOrderDetail 테이블 초기 데이터 삽입
INSERT INTO receiving_order_detail (receiving_order_id, product_id, quantity, unreceived_quantity, remarks, status)
VALUES (1, 1, 50, 50, '양호', 'WAITING_FOR_RECEIPT'),
       (1, 2, 30, 30, '포장 손상', 'WAITING_FOR_RECEIPT'),
       (2, 3, 100, 100, '대량 주문', 'WAITING_FOR_RECEIPT'),
       (3, 4, 20, 20, '빠른 배송 필요', 'WAITING_FOR_RECEIPT'),
       (3, 5, 40, 40, '사전 주문', 'WAITING_FOR_RECEIPT'),
       (4, 1, 60, 60, '지연된 배송', 'WAITING_FOR_RECEIPT'),
       (4, 2, 25, 25, '검수 요망', 'WAITING_FOR_RECEIPT'),
       (5, 3, 35, 35, '환불 가능', 'WAITING_FOR_RECEIPT'),
       (5, 5, 10, 10, '고가 품목', 'WAITING_FOR_RECEIPT'),
       (6, 1, 75, 75, '입고 대기 중', 'WAITING_FOR_RECEIPT'),
       (6, 2, 45, 45, '추가 주문', 'WAITING_FOR_RECEIPT'),
       (7, 3, 90, 90, '정기 공급', 'WAITING_FOR_RECEIPT'),
       (8, 4, 55, 55, '긴급 발주', 'WAITING_FOR_RECEIPT'),
       (9, 5, 65, 65, '확인 필요', 'WAITING_FOR_RECEIPT'),
       (10, 1, 40, 40, '고객 요청 품목', 'WAITING_FOR_RECEIPT'),
       (10, 2, 20, 20, '빠른 처리 요청', 'WAITING_FOR_RECEIPT');

