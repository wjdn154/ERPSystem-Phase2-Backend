-- 발주요청 초기 데이터 삽입
INSERT INTO purchase_request (manager_id, warehouse_id, vat_type, currency_id, date, delivery_date, remarks, status) VALUES
(1, 1, TRUE, 1, '2024-09-01', '2024-09-15', '긴급 발주 요청', 'WAITING_FOR_PURCHASE'),
(2, 2, FALSE, 2, '2024-09-05', '2024-09-20', '정기 발주 요청', 'WAITING_FOR_PURCHASE'),
(3, 3, TRUE, 1, '2024-09-10', '2024-09-25', '재고 보충 요청', 'WAITING_FOR_PURCHASE');

-- 발주요청 상세 초기 데이터 삽입
INSERT INTO purchase_request_detail (purchase_request_id, product_id, quantity, price, supply_price, vat, remarks) VALUES
( 1, 1, 100, 1200,120000, 12000, '즉석밥 100개 발주'),
( 1, 2, 50, 3500,175000, 17500, '유기농 토마토 50박스 발주'),
( 2, 3, 200, 2000,400000, 40000, '세탁세제 200개 발주'),
( 2, 4, 30, 15000,600000, 60000, '상비약 세트 30개 발주'),
( 3, 5, 20, 5000,100000, 10000, '영화 티켓 20매 발주'),
( 3, 7, 15, 80000,1200000, 120000, '원목 책상 15개 발주');
