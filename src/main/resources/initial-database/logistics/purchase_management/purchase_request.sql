-- 발주요청 초기 데이터 삽입
INSERT INTO purchase_request (manager_id, warehouse_id,  currency_id, date, delivery_date, remarks, status)
VALUES
    (1, 1, 6, '2024-09-01', '2024-09-15', '고급 전자제품 긴급 발주 요청', 'WAITING_FOR_PURCHASE'),
    (2, 2, 6, '2024-09-05', '2024-09-20', '정기 부품 발주 요청', 'WAITING_FOR_PURCHASE'),
    (3, 3, 1, '2024-09-10', '2024-09-25', '재고 보충을 위한 발주 요청', 'WAITING_FOR_PURCHASE'),
    (4, 1, 6, '2024-09-15', '2024-09-30', '신제품 출시 관련 발주 요청', 'WAITING_FOR_PURCHASE'),
    (5, 2, 6, '2024-09-18', '2024-10-05', '고객 주문 대비 발주 요청', 'WAITING_FOR_PURCHASE'),
    (6, 3, 6, '2024-09-20', '2024-10-10', '특별 프로모션을 위한 발주 요청', 'WAITING_FOR_PURCHASE');

-- 추가 발주요청 상세 초기 데이터 삽입
INSERT INTO purchase_request_detail (purchase_request_id, product_id, quantity, price, supply_price, remarks)
VALUES
    (1, 17, 150, 30000, 4500000, 'SSD 150개 발주'),
    (1, 18, 100, 15000, 1500000, '파워 서플라이 100개 발주'),
    (2, 3, 60, 80000, 4800000, '무선 키보드 60세트 발주'),
    (2, 19, 200, 20000, 4000000, '쿨러 200개 발주'),
    (3, 10, 50, 120000, 6000000, '무선 이어폰 50세트 발주'),
    (3, 20, 300, 5000, 1500000, 'USB 케이블 300개 발주'),
    (4, 4, 100, 150000, 15000000, '블루투스 스피커 100대 발주'),
    (4, 16, 200, 10000, 2000000, 'RAM 메모리 200개 발주'),
    (5, 12, 40, 180000, 7200000, '무선 공유기 40대 발주'),
    (5, 13, 30, 250000, 7500000, '프린터 30대 발주'),
    (6, 17, 120, 30000, 3600000, 'SSD 120개 발주'),
    (6, 8, 90, 40000, 3600000, '외장 하드디스크 90개 발주'),
    (4, 14, 25, 1500000, 37500000, '4K 스마트 TV 25대 발주'),
    (6, 7, 50, 700000, 35000000, '게이밍 데스크탑 50대 발주'),
    (3, 20, 500, 5000, 2500000, 'USB 케이블 500개 발주'),
    (5, 19, 100, 20000, 2000000, '쿨러 100개 발주'),
    (2, 5, 75, 10000, 750000, '고속 충전기 75개 발주'),
    (1, 11, 100, 50000, 5000000, '스마트폰 보호 케이스 100개 발주'),
    (3, 16, 150, 10000, 1500000, 'RAM 메모리 150개 발주');