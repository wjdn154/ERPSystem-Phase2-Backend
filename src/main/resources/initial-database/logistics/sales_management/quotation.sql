-- 견적서(Quotation) 초기 데이터 삽입
INSERT INTO quotation (client_id, manager_id, warehouse_id, currency_id, vat_id, journal_entry_code, electronic_tax_invoice_status, date, state, remarks)
VALUES
    (1, 2, 6, 1, 1, '4', 'UNPUBLISHED', '2024-10-01', 'IN_PROGRESS', '고객 요청에 따른 견적서'),
    (2, 3, 6, 2, 2, '5', 'PUBLISHED', '2024-10-02', 'IN_PROGRESS', '정기 계약 견적서 발행'),
    (3, 4, 6, 1, 3, '6', 'UNPUBLISHED', '2024-10-03', 'IN_PROGRESS', '신규 거래처 견적서 작성'),
    (4, 5, 6, 2, 4, '5', 'UNPUBLISHED', '2024-10-04', 'COMPLETED', '대량 주문에 대한 견적서'),
    (5, 6, 6, 1, 5, '4', 'PUBLISHED', '2024-10-05', 'IN_PROGRESS', '고객 요청에 따른 추가 견적서');

-- 견적서 상세(QuotationDetail) 초기 데이터 삽입
INSERT INTO quotation_detail (quotation_id, product_id, quantity, supply_price, local_amount, vat, remarks)
VALUES
    -- 견적서 1의 상세 항목들
    (1, 11, 100, 500000.00, NULL, 50000.00, '기본 품목 A'),
    (1, 2, 50, 250000.00, NULL, 25000.00, '기본 품목 B'),
    (1, 3, 30, 150000.00, NULL, 15000.00, '기본 품목 C'),

    -- 견적서 2의 상세 항목들
    (2, 14, 200, 1200000.00, NULL, 120000.00, '외자 품목 D'),
    (2, 5, 100, 600000.00, NULL, 60000.00, '외자 품목 E'),

    -- 견적서 3의 상세 항목들
    (3, 16, 150, 750000.00, NULL, 75000.00, '국내 품목 F'),
    (3, 7, 120, 600000.00, NULL, 60000.00, '국내 품목 G'),

    -- 견적서 4의 상세 항목들
    (4, 8, 300, 1500000.00, NULL, 150000.00, '대량 주문 품목 H'),
    (4, 19, 100, 500000.00, NULL, 50000.00, '대량 주문 품목 I'),

    -- 견적서 5의 상세 항목들
    (5, 12, 80, 400000.00, NULL, 40000.00, '추가 품목 J'),
    (5, 11, 60, 300000.00, NULL, 30000.00, '추가 품목 K');
