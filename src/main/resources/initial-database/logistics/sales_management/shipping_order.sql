-- ShippingOrder 테이블 초기 데이터 삽입
INSERT INTO shipping_order (client_id, manager_id, warehouse_id, shipping_address, postal_code, shipping_date, date, remarks, state) VALUES
(1, 1, 1, '서울특별시 강남구 테헤란로 123', '06242', '2024-10-10', '2024-10-01', '긴급 출하 요청', 'WAITING_FOR_SHIPMENT'),
(2, 2, 2, '부산광역시 해운대구 해운대로 456', '48094', '2024-10-15', '2024-10-02', '일반 출하', 'WAITING_FOR_SHIPMENT'),
(3, 3, 3, '인천광역시 남동구 구월로 789', '21554', '2024-10-20', '2024-10-03', '우선 처리 출하', 'WAITING_FOR_SHIPMENT'),
(4, 1, 2, '대구광역시 중구 동성로 101', '41941', '2024-10-25', '2024-10-04', '정기 출하', 'WAITING_FOR_SHIPMENT'),
(5, 3, 1, '대전광역시 서구 둔산로 202', '35209', '2024-10-30', '2024-10-05', '대량 출하', 'WAITING_FOR_SHIPMENT');

-- ShippingOrderDetail 테이블 초기 데이터 삽입
INSERT INTO shipping_order_detail (shipping_order_id, product_id, quantity, remarks) VALUES
(1, 1, 50, '상품 A 대량 출하'),
(1, 2, 30, '상품 B 추가 출하'),
(2, 3, 100, '상품 C 대량 출하'),
(2, 4, 20, '상품 D 긴급 출하'),
(3, 5, 40, '상품 E 고객 요청 출하'),
(3, 1, 60, '상품 A 대량 출하'),
(4, 2, 25, '상품 B 정기 출하'),
(4, 3, 35, '상품 C 정기 출하'),
(5, 4, 45, '상품 D 대량 출하'),
(5, 5, 55, '상품 E 대량 출하');
