-- 발주서 초기 데이터 삽입
INSERT INTO purchase_order (client_id, manager_id, warehouse_id, vat_id, journal_entry_code, electronic_tax_invoice_status, currency_id, date, delivery_date, remarks, status)
VALUES
    (1, 1, 1, 1, '1', 'UNPUBLISHED',6, '2024-09-01', '2024-09-15', '고급 전자제품 긴급 발주 처리', 'PURCHASE_COMPLETED'),
    (2, 2, 2, 2, '2', 'UNPUBLISHED',6, '2024-09-05', '2024-09-20', '정기 부품 발주 처리', 'PURCHASE_COMPLETED'),
    (3, 3, 3, 3, '3', 'UNPUBLISHED',6, '2024-09-10', '2024-09-25', '재고 보충 발주 처리', 'PURCHASE_COMPLETED'),
    (4, 4, 1, 4, '1', 'UNPUBLISHED',6, '2024-09-15', '2024-09-30', '신제품 출시 관련 발주 처리', 'PURCHASE_COMPLETED'),
    (5, 5, 2, 5, '2', 'UNPUBLISHED',6, '2024-09-18', '2024-10-05', '고객 주문 대비 발주 처리', 'CANCELED'),
    (6, 6, 3, 6, '3', 'UNPUBLISHED',6, '2024-09-20', '2024-10-10', '특별 프로모션 발주 처리', 'PURCHASE_COMPLETED');

-- 발주서 상세 초기 데이터 삽입
INSERT INTO purchase_order_detail (purchase_order_id, product_id, quantity,  supply_price, local_amount, vat, remarks)
VALUES
    (1, 1, 50,  25000000, NULL, 2500000, '고급 노트북 50대 발주'),
    (1, 2, 30,  6000000, NULL, 600000, '게이밍 모니터 30대 발주'),
    (2, 16, 100, 1000000, NULL, 100000, 'RAM 메모리 100개 발주'),
    (2, 17, 50, 1500000, NULL, 150000, 'SSD 50개 발주'),
    (3, 8, 40, 1600000, NULL, 160000, '외장 하드디스크 40개 발주'),
    (3, 19, 25, 500000, NULL, 50000, '쿨러 25개 발주'),
    (4, 14, 20,  30000000, NULL, 3000000, '4K 스마트 TV 20대 발주'),
    (4, 18, 80, 1200000, NULL, 120000, '파워 서플라이 80개 발주'),
    (5, 9, 60, 1500000, NULL, 150000, 'USB-C 허브 60개 발주'),
    (5, 10, 100,  12000000, NULL, 1200000, '무선 이어폰 100세트 발주'),
    (6, 7, 30,  21000000, NULL, 2100000, '게이밍 데스크탑 30대 발주'),
    (6, 20, 200, 1000000, NULL, 100000, 'USB 케이블 200개 발주');