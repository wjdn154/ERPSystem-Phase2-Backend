INSERT INTO process_routing
(id, process_routing_code, name, description, is_standard, is_active)
VALUES
    -- Product code: B0001, Name: 엔진 오일 필터
    (1, 'ROUT001', '엔진 오일 필터 제조', '엔진 오일 필터 제조 과정', true, true),

    -- Product code: B0023, Name: 배터리 케이블
    (2, 'ROUT002', '배터리 케이블 제조', '자동차 배터리 연결 케이블 제조 및 품질 검사 과정', true, true),
    -- Product ID: B0003, Name: 연료 필터
    (3, 'ROUT003', '연료 필터 제조', '연료 필터의 조립 및 품질 검사 과정', true, false),

    -- Product ID: B0004, Name: 점화 플러그
    (4, 'ROUT004', '점화 플러그 제조', '자동차 점화 플러그 제조 및 테스트 과정', false, true),

    -- Product ID: B0005, Name: 에어 필터
    (5, 'ROUT005', '에어 필터 제조', '자동차 공기 정화용 에어 필터 제조 과정', true, true),

    -- Product code: B0022, B0033, Name: 쇼크 업소버, 브레이크 라인
    (6, 'ROUT006', '브레이크 라인, 쇼크 업소버 제조', '차량 서스펜션용 쇼크 업소버 및 브레이크 라인 제조 과정', true, true),

    -- Product ID: B0007, Name: 서스펜션 스프링
    (7, 'ROUT007', '서스펜션 스프링 제조', '차량 서스펜션용 스프링 제조 및 테스트 과정', true, false),

    -- Product ID: B0008, Name: 배기 머플러
    (8, 'ROUT008', '배기 머플러 제조', '자동차 배기 시스템 부품 제조 과정', false, true),

    -- Product ID: B0009, Name: 디스크 로터
    (9, 'ROUT009', '디스크 로터 제조', '브레이크 시스템용 디스크 로터 제조 과정', true, true),

    -- Product ID: B0010, Name: 와이퍼 블레이드
    (10, 'ROUT010', '와이퍼 블레이드 제조', '자동차 유리 와이퍼 블레이드 제조 과정', false, false);
