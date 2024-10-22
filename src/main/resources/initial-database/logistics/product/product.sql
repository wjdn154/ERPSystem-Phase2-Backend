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



