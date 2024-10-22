INSERT INTO product_image (image_path, created_at) VALUES
('images/product1_image.jpg', '2024-09-30 12:04:05.630002'),
('images/product2_image.jpg', '2024-09-30 12:04:05.630002'),
('images/product3_image.jpg', '2024-09-30 12:04:05.630002'),
('images/product4_image.jpg', '2024-09-30 12:04:05.630002'),
('images/product5_image.jpg', '2024-09-30 12:04:05.630002'),
('images/product6_image.jpg', '2024-09-30 12:04:05.630002'),
('images/product7_image.jpg', '2024-09-30 12:04:05.630002'),
('images/product8_image.jpg', '2024-09-30 12:04:05.630002');

INSERT INTO product (code, product_group_id, client_id, process_routing_id, product_type, purchase_price, sales_price, name, standard, unit, image_path, remarks, is_active) VALUES
('B0001', 1, 1, 1, 'GOODS', 1200, 1500, '즉석밥', '200g', 'EA', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/9fddbad9-c34e-4452-b596-ec8ea785ce06_즉석밥.png', '가공식품', TRUE),
('B0002', 2, 2, 1, 'GOODS', 3500, 5000, '유기농 토마토', '500g', 'PKG', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/cd5de099-2df8-46ed-9052-c0e3436f9008_유기농토마토.png', '신선식품', TRUE),
('B0003', 3, 3, 2, 'GOODS', 2000, 2500, '세탁세제', '1L', 'BOTTLE', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/0a8c2ece-ecfb-4c7e-9ddd-92125a73ffa4_세탁세제.png', '일상용품', TRUE),
('B0004', 5, 4, 3, 'GOODS', 15000, 20000, '상비약 세트', '10알', 'SET', null, '의약품/의료기기', TRUE),
('B0005', 6, 5, 4, 'GOODS', 5000, 7000, '영화 티켓', '1매', 'EA', null, '교육/문화용품', TRUE),
('B0006', 7, 6, 5, 'GOODS', 500000, 550000, '고급 노트북', '1대', 'SET', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/74c51b6e-7e0e-495a-89c4-ad70879cae33_고급노트북.png', '디지털/가전', TRUE),
('B0007', 8, 7, 6, 'GOODS', 80000, 100000, '원목 책상', '1개', 'SET', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/7ae6203b-ba0a-4a05-b153-cdcc5ca88988_원목책상사진.png', '가구/인테리어', TRUE),
('B0008', 9, 8, 2, 'GOODS', 25000, 30000, '기능성 재킷', 'M사이즈', 'EA', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/30b7aefd-eaba-4d8a-b8c5-30d8df7ed977_기능성재킷.png', '의류', TRUE),
('B0009', 10, 9, 3, 'GOODS', 100000, 120000, '산악자전거', '1대', 'SET', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/6e88cad1-326a-4494-a296-63e4dfed496e_산악자전거.png', '전문스포츠/레저', TRUE),
('B0010', 11, 10, 4, 'GOODS', 7000, 10000, '가죽 지갑', '1개', 'EA', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/10d57c2f-f1e1-4c14-bfa7-8c78f307b5d7_가죽지갑.png', '패션잡화', TRUE),
('B0011', 11, 1, 5, 'GOODS', 2000, 3000, '잡화 바구니', '1개', 'EA', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/5ed7ec83-ef8d-4892-b8cb-ad81eea13cb1_잡화바구니.jpg', '기타', TRUE),
('B0012', 1, 2, 6, 'GOODS', 1200, 1500, '라면', '120g', 'PKG', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/86919bee-e8b9-4bc5-b475-e2220243749f_라면.png', '가공식품', TRUE),
('B0013', 2, 3, 2, 'GOODS', 5000, 7000, '유기농 사과', '1kg', 'PKG', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/58cb04bd-d3ba-4311-9b56-870759ef89ba_유기농 사과.png', '신선식품', TRUE),
('B0014', 3, 4, 1, 'GOODS', 8000, 10000, '샴푸', '500ml', 'BOTTLE', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/cbe86507-c512-426f-bf02-8a884cd6e159_샴푸.png', '일상용품', TRUE),
('B0015', 5, 5, 3, 'GOODS', 30000, 40000, '응급 처치 키트', '1세트', 'SET', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/e4e20556-31bd-47dc-9f01-f49d909b4876_응급처치키트.png', '의약품/의료기기', TRUE);


# s3 경로는 임시작성된 상태임
INSERT INTO product (
    code, product_group_id, client_id, process_routing_id, product_type, purchase_price, sales_price, name, standard, unit, image_path, remarks, is_active
) VALUES
      ('B0016', 1, 1, 1, 'SEMI_FINISHED_PRODUCT', 1500, 2000, '엔진 오일 필터', '200mm', 'EA', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/123456_엔진오일필터.png', '자동차 엔진 오일 필터', TRUE),

      ('B0017', 2, 2, 1, 'SEMI_FINISHED_PRODUCT', 5000, 7000, '브레이크 패드', '300mm', 'EA', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/123457_브레이크패드.png', '고성능 브레이크 패드', TRUE),

      ('B0018', 3, 3, 2, 'SEMI_FINISHED_PRODUCT', 8000, 10000, '연료 필터', '150mm', 'EA', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/123458_연료필터.png', '차량 연료 정화용 필터', TRUE),

      ('B0019', 5, 4, 3, 'SEMI_FINISHED_PRODUCT', 12000, 15000, '점화 플러그', '100mm', 'SET', null, '자동차 점화 플러그 세트', TRUE),

      ('B0020', 6, 5, 4, 'SEMI_FINISHED_PRODUCT', 3000, 5000, '에어 필터', '250mm', 'EA', null, '자동차 실내 공기 정화용 에어 필터', TRUE),

      ('B0021', 7, 6, 5, 'SEMI_FINISHED_PRODUCT', 60000, 80000, '알터네이터', '1대', 'SET', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/123459_알터네이터.png', '자동차 전기 발전기', TRUE),

      ('B0022', 8, 7, 6, 'SEMI_FINISHED_PRODUCT', 20000, 30000, '서스펜션 스프링', '1개', 'SET', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/123460_서스펜션스프링.png', '차량 서스펜션용 스프링', TRUE),

      ('B0023', 9, 8, 2, 'SEMI_FINISHED_PRODUCT', 45000, 60000, '배기 머플러', 'M사이즈', 'EA', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/123461_배기머플러.png', '자동차 배기 시스템 부품', TRUE),

      ('B0024', 10, 9, 3, 'SEMI_FINISHED_PRODUCT', 90000, 120000, '디스크 로터', '1개', 'EA', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/123462_디스크로터.png', '브레이크 시스템용 디스크 로터', TRUE),

      ('B0025', 11, 10, 4, 'SEMI_FINISHED_PRODUCT', 10000, 15000, '와이퍼 블레이드', '1개', 'EA', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/123463_와이퍼블레이드.png', '자동차 유리 와이퍼', TRUE),

      ('B0026', 11, 1, 5, 'SEMI_FINISHED_PRODUCT', 1800, 3000, '라디에이터 캡', '1개', 'EA', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/123464_라디에이터캡.jpg', '자동차 냉각 시스템용 캡', TRUE),

      ('B0027', 1, 2, 6, 'SEMI_FINISHED_PRODUCT', 4000, 5500, '클러치 디스크', '180mm', 'EA', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/123465_클러치디스크.png', '자동차 클러치 시스템 부품', TRUE),

      ('B0028', 2, 3, 2, 'SEMI_FINISHED_PRODUCT', 7000, 9000, '타이밍 벨트', '1.2m', 'EA', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/123466_타이밍벨트.png', '엔진 타이밍 벨트', TRUE),

      ('B0029', 3, 4, 1, 'SEMI_FINISHED_PRODUCT', 12000, 15000, '오일 펌프', '600ml', 'BOTTLE', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/123467_오일펌프.png', '자동차 엔진 오일 펌프', TRUE),

      ('B0030', 5, 5, 3, 'SEMI_FINISHED_PRODUCT', 35000, 45000, '브레이크 캘리퍼', '1세트', 'SET', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/123468_브레이크캘리퍼.png', '브레이크 시스템용 캘리퍼', TRUE),

      ('B0031', 1, 1, 1, 'SEMI_FINISHED_PRODUCT', 1600, 2100, '브레이크 디스크', '220mm', 'EA', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/123469_브레이크디스크.png', '자동차 브레이크 디스크', TRUE),

      ('B0032', 2, 2, 1, 'SEMI_FINISHED_PRODUCT', 5200, 7500, 'ABS 모듈', '300mm', 'EA', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/123470_ABS모듈.png', '자동차 ABS 시스템 모듈', TRUE),

      ('B0033', 3, 3, 2, 'SEMI_FINISHED_PRODUCT', 8200, 10500, '냉각 팬', '350mm', 'EA', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/123471_냉각팬.png', '엔진 냉각 시스템용 팬', TRUE),

      ('B0034', 4, 4, 3, 'SEMI_FINISHED_PRODUCT', 13000, 16000, '연료 펌프', '150mm', 'EA', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/123472_연료펌프.png', '자동차 연료 시스템용 펌프', TRUE),

      ('B0035', 5, 5, 4, 'SEMI_FINISHED_PRODUCT', 3200, 4800, '점화 코일', '120mm', 'EA', null, '자동차 점화 시스템용 코일', TRUE),

      ('B0036', 6, 6, 5, 'SEMI_FINISHED_PRODUCT', 61000, 81000, '발전기 벨트', '2m', 'EA', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/123473_발전기벨트.png', '자동차 발전기 벨트', TRUE),

      ('B0037', 7, 7, 6, 'SEMI_FINISHED_PRODUCT', 21000, 32000, '쇼크 업소버', '1개', 'SET', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/123474_쇼크업소버.png', '차량 서스펜션용 쇼크 업소버', TRUE),

      ('B0038', 8, 8, 2, 'SEMI_FINISHED_PRODUCT', 46000, 62000, '배터리 케이블', '3m', 'EA', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/123475_배터리케이블.png', '자동차 배터리 연결 케이블', TRUE),

      ('B0039', 9, 9, 3, 'SEMI_FINISHED_PRODUCT', 91000, 122000, '터보차저', '1개', 'EA', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/123476_터보차저.png', '자동차 엔진용 터보차저', TRUE),

      ('B0040', 10, 10, 4, 'SEMI_FINISHED_PRODUCT', 11000, 17000, '배기 가스 센서', '1개', 'EA', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/123477_배기가스센서.png', '자동차 배기가스 제어 센서', TRUE),

      ('B0041', 11, 1, 5, 'SEMI_FINISHED_PRODUCT', 2000, 3100, '서모스탯', '90mm', 'EA', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/123478_서모스탯.png', '자동차 냉각 시스템용 서모스탯', TRUE),

      ('B0042', 1, 2, 6, 'SEMI_FINISHED_PRODUCT', 4200, 5700, '핸들 조인트', '250mm', 'EA', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/123479_핸들조인트.png', '자동차 스티어링 시스템용 조인트', TRUE),

      ('B0043', 2, 3, 2, 'SEMI_FINISHED_PRODUCT', 7500, 9500, '연료 레일', '1.5m', 'EA', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/123480_연료레일.png', '엔진 연료 공급 레일', TRUE),

      ('B0044', 3, 4, 1, 'SEMI_FINISHED_PRODUCT', 12500, 15500, '변속기 오일 팬', '400mm', 'EA', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/123481_변속기오일팬.png', '자동차 변속기 오일 팬', TRUE),

      ('B0045', 4, 5, 3, 'SEMI_FINISHED_PRODUCT', 36000, 47000, '스티어링 휠', '1개', 'SET', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/123482_스티어링휠.png', '자동차 스티어링 휠', TRUE),

      ('B0046', 5, 6, 4, 'SEMI_FINISHED_PRODUCT', 5000, 7200, '워터 펌프', '200mm', 'EA', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/123483_워터펌프.png', '자동차 냉각 시스템 워터 펌프', TRUE),

      ('B0047', 6, 7, 5, 'SEMI_FINISHED_PRODUCT', 2800, 4000, '라디에이터 호스', '1m', 'EA', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/123484_라디에이터호스.png', '자동차 라디에이터 연결 호스', TRUE),

      ('B0048', 7, 8, 6, 'SEMI_FINISHED_PRODUCT', 8000, 10500, '브레이크 라인', '2m', 'EA', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/123485_브레이크라인.png', '브레이크 시스템용 라인', TRUE),

      ('B0049', 8, 9, 2, 'SEMI_FINISHED_PRODUCT', 18500, 23000, '에어컨 압축기', '1개', 'EA', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/123486_에어컨압축기.png', '자동차 에어컨 시스템 압축기', TRUE),

      ('B0050', 9, 10, 3, 'SEMI_FINISHED_PRODUCT', 34000, 46000, '스태빌라이저 링크', '1개', 'SET', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/123487_스태빌라이저링크.png', '서스펜션 안정화 장치', TRUE),

      ('B0051', 10, 1, 4, 'SEMI_FINISHED_PRODUCT', 1200, 1900, '퓨즈 박스', '1개', 'EA', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/123488_퓨즈박스.png', '자동차 전기 시스템용 퓨즈 박스', TRUE),

      ('B0052', 11, 2, 5, 'SEMI_FINISHED_PRODUCT', 5400, 7100, '차량 제어 유닛', '500g', 'EA', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/123489_차량제어유닛.png', '자동차 제어 시스템 유닛', TRUE),

      ('B0053', 1, 3, 6, 'SEMI_FINISHED_PRODUCT', 8800, 12000, '점화 분배기', '1개', 'EA', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/123490_점화분배기.png', '자동차 점화 시스템 분배기', TRUE),

      ('B0054', 2, 4, 1, 'SEMI_FINISHED_PRODUCT', 13500, 17500, '엔진 마운트', '1개', 'EA', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/123491_엔진마운트.png', '엔진 장착용 마운트', TRUE),

      ('B0055', 3, 5, 2, 'SEMI_FINISHED_PRODUCT', 26000, 34000, '클러치 마스터 실린더', '300mm', 'EA', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/123492_클러치마스터실린더.png', '자동차 클러치 작동용 실린더', TRUE),

      ('B0056', 4, 6, 3, 'SEMI_FINISHED_PRODUCT', 9000, 12000, '릴레이 어셈블리', '150mm', 'EA', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/123493_릴레이어셈블리.png', '자동차 전기 시스템 릴레이', TRUE),

      ('B0057', 5, 7, 4, 'SEMI_FINISHED_PRODUCT', 7000, 9500, '서스펜션 암', '450mm', 'EA', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/123494_서스펜션암.png', '차량 서스펜션 연결 암', TRUE),

      ('B0058', 6, 8, 5, 'SEMI_FINISHED_PRODUCT', 1500, 2500, '배터리 홀더', '200mm', 'EA', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/123495_배터리홀더.png', '자동차 배터리 고정용 홀더', TRUE),

      ('B0059', 7, 9, 6, 'SEMI_FINISHED_PRODUCT', 4800, 6200, '공기 흐름 센서', '100mm', 'EA', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/123496_공기흐름센서.png', '자동차 공기 흐름 감지 센서', TRUE),

      ('B0060', 8, 10, 1, 'SEMI_FINISHED_PRODUCT', 11500, 15000, '에어컨 필터', '180mm', 'EA', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/123497_에어컨필터.png', '자동차 에어컨 공기 필터', TRUE);




