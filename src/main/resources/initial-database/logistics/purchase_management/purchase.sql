-- Purchase 테이블 초기 데이터 삽입
INSERT INTO purchase (client_id, manager_id, warehouse_id, currency_id, vat_id, journal_entry_code, electronic_tax_invoice_status, date, remarks, status, accounting_reflection, invoice_id) VALUES
(1, 1, 1, 1, 1, '1', 'UNPUBLISHED', '2024-10-01', '긴급 구매', 'WAITING_FOR_RECEIPT', false, null),
(2, 2, 2, 2, 2, '2', 'PUBLISHED', '2024-10-02', '정기 구매', 'PURCHASE_COMPLETED', true, null),
(3, 3, 3, 3, 3, '3', 'UNPUBLISHED', '2024-10-03', '고객 요청 구매', 'WAITING_FOR_RECEIPT', false, null),
(1, 2, 1, 1, 4, '1', 'PUBLISHED', '2024-10-04', '특별 할인 구매', 'PURCHASE_COMPLETED', true, null),
(3, 1, 2, 3, 5, '2', 'UNPUBLISHED', '2024-10-05', '대량 구매', 'WAITING_FOR_RECEIPT', false, null);

-- PurchaseDetail 테이블 초기 데이터 삽입
INSERT INTO purchase_detail (purchase_id, product_id, quantity, supply_price, local_amount, vat, remarks) VALUES
(1, 1, 50, 100000, 110000, 10000, '상품 A 대량 구매'),
(1, 2, 30, 60000, 66000, 6000, '상품 B 추가 구매'),
(2, 3, 100, 200000, 220000, 20000, '상품 C 대량 구매'),
(3, 4, 20, 50000, 55000, 5000, '상품 D 긴급 주문'),
(3, 5, 40, 120000, 132000, 12000, '상품 E 주문'),
(4, 1, 60, 120000, 132000, 12000, '상품 A 대량 구매'),
(4, 2, 25, 50000, 55000, 5000, '상품 B 추가 구매'),
(5, 3, 35, 140000, 154000, 14000, '상품 C 대량 구매'),
(5, 5, 10, 30000, 33000, 3000, '상품 E 소량 구매');
