-- Purchase 테이블 초기 데이터 삽입
INSERT INTO purchase (client_id, manager_id, warehouse_id, currency_id, vat_id, journal_entry_code, electronic_tax_invoice_status, date, remarks, status, accounting_reflection, invoice_id)
VALUES
    (1, 1, 1, 1, 1, '1', 'UNPUBLISHED', '2024-10-01', '고급 전자제품 긴급 구매 요청', 'WAITING_FOR_RECEIPT', false, null),
    (2, 2, 2, 2, 2, '2', 'PUBLISHED', '2024-10-02', '부품 정기 구매 요청', 'PURCHASE_COMPLETED', true, null),
    (3, 3, 3, 3, 3, '3', 'UNPUBLISHED', '2024-10-03', '고객 주문에 따른 대량 구매', 'WAITING_FOR_RECEIPT', false, null),
    (1, 2, 1, 1, 4, '1', 'PUBLISHED', '2024-10-04', '특별 할인 이벤트로 인한 추가 구매', 'PURCHASE_COMPLETED', true, null),
    (3, 1, 2, 3, 5, '2', 'UNPUBLISHED', '2024-10-05', '신제품 출시 전 대량 재고 확보를 위한 구매', 'WAITING_FOR_RECEIPT', false, null);

-- PurchaseDetail 테이블 초기 데이터 삽입
INSERT INTO purchase_detail (purchase_id, product_id, quantity, supply_price, local_amount, vat, remarks)
VALUES
    (1, 'B0001', 50, 500000, 550000, 50000, '고급 노트북 50대 긴급 구매'),
    (1, 'B0002', 30, 200000, 220000, 20000, '게이밍 모니터 30대 추가 구매'),
    (2, 'P0016', 100, 10000, 110000, 10000, 'RAM 메모리 100개 정기 구매'),
    (2, 'P0017', 50, 30000, 330000, 30000, 'SSD 50개 정기 구매'),
    (3, 'B0007', 20, 700000, 770000, 70000, '게이밍 데스크탑 20대 고객 요청 구매'),
    (3, 'P0018', 40, 15000, 165000, 15000, '파워 서플라이 40개 긴급 주문'),
    (4, 'B0001', 60, 500000, 550000, 50000, '고급 노트북 60대 할인 구매'),
    (4, 'B0003', 25, 80000, 88000, 8000, '무선 키보드 25세트 할인 구매'),
    (5, 'B0014', 35, 1500000, 1650000, 150000, '4K 스마트 TV 35대 대량 구매'),
    (5, 'P0019', 10, 20000, 220000, 20000, '쿨러 10개 소량 구매');
