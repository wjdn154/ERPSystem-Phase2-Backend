INSERT INTO product_image (image_path) VALUES
('images/product1_image.jpg'),
('images/product2_image.jpg'),
('images/product3_image.jpg'),
('images/product4_image.jpg'),
('images/product5_image.jpg'),
('images/product6_image.jpg'),
('images/product7_image.jpg'),
('images/product8_image.jpg');

INSERT INTO product (code, product_group_id, client_id, process_routing_id, product_type, purchase_price, sales_price, name, standard, unit, image_path, remarks, is_active) VALUES
('B0001', 1, 1, 1, 'GOODS', 1200, 1500, '즉석밥', '200g', 'EA', null, '가공식품', TRUE),
('B0002', 2, 2, 1, 'GOODS', 3500, 5000, '유기농 토마토', '500g', 'PKG', null, '신선식품', TRUE),
('B0003', 3, 3, 2, 'GOODS', 2000, 2500, '세탁세제', '1L', 'BOTTLE', null, '일상용품', TRUE),
('B0004', 5, 4, 3, 'GOODS', 15000, 20000, '상비약 세트', '10알', 'SET', '/images/medicine-set.jpg', '의약품/의료기기', TRUE),
('B0005', 6, 5, 4, 'GOODS', 5000, 7000, '영화 티켓', '1매', 'EA', '/images/movie-ticket.jpg', '교육/문화용품', TRUE),
('B0006', 7, 6, 5, 'GOODS', 500000, 550000, '고급 노트북', '1대', 'SET', '/images/laptop.jpg', '디지털/가전', TRUE),
('B0007', 8, 7, 6, 'GOODS', 80000, 100000, '원목 책상', '1개', 'SET', '/images/desk.jpg', '가구/인테리어', TRUE),
('B0008', 9, 8, 2, 'GOODS', 25000, 30000, '기능성 재킷', 'M사이즈', 'EA', '/images/jacket.jpg', '의류', TRUE),
('B0009', 10, 9, 3, 'GOODS', 100000, 120000, '산악자전거', '1대', 'SET', '/images/bike.jpg', '전문스포츠/레저', TRUE),
('B0010', 11, 10, 4, 'GOODS', 7000, 10000, '가죽 지갑', '1개', 'EA', '/images/wallet.jpg', '패션잡화', TRUE),
('B0011', 11, 1, 5, 'GOODS', 2000, 3000, '잡화 바구니', '1개', 'EA', '/images/basket.jpg', '기타', TRUE),
('B0012', 1, 2, 6, 'GOODS', 1200, 1500, '라면', '120g', 'PKG', null, '가공식품', TRUE),
('B0013', 2, 3, 2, 'GOODS', 5000, 7000, '유기농 사과', '1kg', 'PKG', null, '신선식품', TRUE),
('B0014', 3, 4, 1, 'GOODS', 8000, 10000, '샴푸', '500ml', 'BOTTLE', null, '일상용품', TRUE),
('B0015', 5, 5, 3, 'GOODS', 30000, 40000, '응급 처치 키트', '1세트', 'SET', '/images/first-aid-kit.jpg', '의약품/의료기기', TRUE);



