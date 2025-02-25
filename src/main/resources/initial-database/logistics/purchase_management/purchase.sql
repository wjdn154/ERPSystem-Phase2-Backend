-- Purchase 테이블 초기 데이터 삽입
INSERT INTO purchase (client_id, manager_id, warehouse_id, currency_id, vat_id, journal_entry_code, electronic_tax_invoice_status, date, remarks, status, accounting_reflection, invoice_id)
VALUES
    (1, 1, 1, 6, 1, '1', 'UNPUBLISHED', '2024-10-01', '고급 전자제품 긴급 구매 요청', 'WAITING_FOR_RECEIPT', false, null),
    (2, 2, 2, 6, 2, '2', 'PUBLISHED', '2024-10-02', '부품 정기 구매 요청', 'ACCOUNTED', true, null),
    (3, 3, 3, 6, 3, '3', 'UNPUBLISHED', '2024-10-03', '고객 주문에 따른 대량 구매', 'WAITING_FOR_RECEIPT', false, null),
    (4, 4, 3, 6, 4, '1', 'PUBLISHED', '2024-10-04', '특별 할인 이벤트로 인한 추가 구매', 'RECEIPT_COMPLETED', true, null),
    (5, 5, 2, 6, 5, '2', 'UNPUBLISHED', '2024-10-05', '신제품 출시 전 대량 재고 확보를 위한 구매', 'INVOICED', false, null),
    (6, 1, 1, 6, 1, '3', 'UNPUBLISHED', '2024-10-06', '국내 긴급 소량 구매', 'WAITING_FOR_RECEIPT', false, null),
    (7, 2, 2, 6, 2, '1', 'PUBLISHED', '2024-10-07', '계약 체결에 따른 대량 구매', 'ACCOUNTED', true, null),
    (8, 3, 3, 6, 3, '2', 'UNPUBLISHED', '2024-10-08', '고객 요청에 따른 추가 구매', 'WAITING_FOR_RECEIPT', false, null),
    (9, 4, 4, 6, 4, '3', 'PUBLISHED', '2024-10-09', '신규 거래처 긴급 구매', 'RECEIPT_COMPLETED', true, null),
    (10, 5, 1, 6, 5, '1', 'UNPUBLISHED', '2024-10-10', '프로젝트용 대량 부품 구매', 'INVOICED', false, null);

-- PurchaseDetail 테이블 초기 데이터 삽입 (purchase.client_id == product.client_id 조건 충족, 구매처별로 품목을 분배하여 추가)
INSERT INTO purchase_detail (purchase_id, product_id, quantity, supply_price, local_amount, vat, remarks)
VALUES
    -- 구매처 1에 해당하는 상세 항목 (클라이언트 ID 1)
    (1, 1, 50, 75000, null, 7500, '엔진 오일 필터 50개 구매'),
    (1, 11, 40, 72000, null, 7200, '라디에이터 캡 40개 추가 구매'),
    (1, 16, 30, 48000, null, 4800, '브레이크 디스크 30개 구매'),
    (1, 26, 20, 180000, null, 18000, '서모스탯 20개 구매'),
    (1, 36, 15, 120000, null, 12000, '퓨즈 박스 15개 구매'),

    -- 구매처 2에 해당하는 상세 항목 (클라이언트 ID 2)
    (2, 2, 100, 500000, null, 50000, '브레이크 패드 100개 정기 구매'),
    (2, 12, 20, 80000, null, 8000, '클러치 디스크 20개 추가 구매'),
    (2, 17, 30, 156000, null, 15600, 'ABS 모듈 30개 구매'),
    (2, 27, 40, 200000, null, 20000, '서스펜션 암 40개 구매'),
    (2, 37, 25, 160000, null, 16000, '차량 제어 유닛 25개 구매'),

    -- 구매처 3에 해당하는 상세 항목 (클라이언트 ID 3)
    (3, 3, 60, 480000, null, 48000, '연료 필터 60개 긴급 구매'),
    (3, 13, 40, 280000, null, 28000, '타이밍 벨트 40개 추가 구매'),
    (3, 18, 50, 350000, null, 35000, '냉각 팬 50개 추가 구매'),
    (3, 28, 35, 170000, null, 17000, '쇼크 업소버 35개 구매'),
    (3, 38, 25, 125000, null, 12500, '연료 레일 25개 구매'),

    -- 구매처 4에 해당하는 상세 항목 (클라이언트 ID 4)
    (4, 4, 80, 960000, null, 96000, '점화 플러그 80개 구매'),
    (4, 14, 60, 780000, null, 78000, '오일 펌프 60개 추가 구매'),
    (4, 19, 30, 300000, null, 30000, '연료 펌프 30개 추가 구매'),
    (4, 29, 20, 240000, null, 24000, '변속기 오일 팬 20개 대량 구매'),
    (4, 39, 50, 600000, null, 60000, '에어컨 압축기 50개 구매'),

    -- 구매처 5에 해당하는 상세 항목 (클라이언트 ID 5)
    (5, 5, 45, 135000, null, 13500, '에어 필터 45개 구매'),
    (5, 15, 25, 80000, null, 8000, '점화 코일 25개 추가 구매'),
    (5, 20, 35, 175000, null, 17500, '스티어링 휠 35개 구매'),
    (5, 30, 40, 200000, null, 20000, '클러치 마스터 실린더 40개 구매'),
    (5, 40, 55, 275000, null, 27500, '차량용 에어컨 필터 55개 구매'),

    -- 구매처 6에 해당하는 상세 항목 (클라이언트 ID 6)
    (6, 6, 50, 305000, null, 30500, '발전기 벨트 50개 긴급 구매'),
    (6, 21, 30, 180000, null, 18000, '라디에이터 호스 30개 구매'),
    (6, 31, 20, 150000, null, 15000, '차량용 에어컨 컴프레서 20개 추가 구매'),
    (6, 41, 40, 220000, null, 22000, '차량 전원 공급 장치 40개 구매'),
    (6, 51, 25, 200000, null, 20000, '차량 제어 모듈 25개 구매'),

    -- 구매처 7에 해당하는 상세 항목 (클라이언트 ID 7)
    (7, 7, 100, 700000, null, 70000, '서스펜션 스프링 100개 대량 구매'),
    (7, 22, 30, 200000, null, 20000, '핸들 조인트 30개 추가 구매'),
    (7, 32, 50, 250000, null, 25000, '서스펜션 암 50개 구매'),
    (7, 42, 35, 175000, null, 17500, '스태빌라이저 링크 35개 구매'),
    (7, 52, 20, 100000, null, 10000, '브레이크 시스템용 필터 20개 구매'),

    -- 구매처 8에 해당하는 상세 항목 (클라이언트 ID 8)
    (8, 8, 40, 160000, null, 16000, '배기 머플러 40개 구매'),
    (8, 23, 25, 130000, null, 13000, '배터리 홀더 25개 추가 구매'),
    (8, 33, 30, 180000, null, 18000, '에어컨 필터 30개 구매'),
    (8, 43, 20, 100000, null, 10000, '차량 방향제 20개 구매'),
    (8, 53, 45, 225000, null, 22500, '차량용 워터 펌프 45개 구매'),

    -- 구매처 9에 해당하는 상세 항목 (클라이언트 ID 9)
    (9, 9, 60, 450000, null, 45000, '디스크 로터 60개 신규 구매'),
    (9, 24, 25, 200000, null, 20000, '공기 흐름 센서 25개 추가 구매'),
    (9, 34, 50, 310000, null, 31000, '차량용 블레이드 50개 구매'),
    (9, 44, 40, 260000, null, 26000, '차량 내비게이션 40개 구매'),
    (9, 54, 20, 120000, null, 12000, '차량 진단 시스템 20개 구매'),

    -- 구매처 10에 해당하는 상세 항목 (클라이언트 ID 10)
    (10, 10, 75, 1125000, null, 112500, '와이퍼 블레이드 75개 대량 구매'),
    (10, 25, 60, 660000, null, 66000, '터보차저 60개 프로젝트용 구매'),
    (10, 35, 40, 580000, null, 58000, '에어컨 필터 40개 추가 구매'),
    (10, 45, 30, 210000, null, 21000, '차량 엔진 컨트롤 30개 구매');
