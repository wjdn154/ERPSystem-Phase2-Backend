-- ShippingOrder 테이블 초기 데이터 삽입
INSERT INTO shipping_order (client_id, manager_id, warehouse_id, shipping_address, postal_code, shipping_date, date, remarks, state) VALUES
(1, 1, 1, '서울특별시 강남구 테헤란로 123', '06242', '2024-10-10', '2024-10-01', '긴급 출하 요청', 'WAITING_FOR_SHIPMENT'),
(2, 2, 2, '부산광역시 해운대구 해운대로 456', '48094', '2024-10-15', '2024-10-02', '일반 출하', 'WAITING_FOR_SHIPMENT'),
(3, 3, 3, '인천광역시 남동구 구월로 789', '21554', '2024-10-20', '2024-10-03', '우선 처리 출하', 'WAITING_FOR_SHIPMENT'),
(4, 1, 2, '대구광역시 중구 동성로 101', '41941', '2024-10-25', '2024-10-04', '정기 출하', 'WAITING_FOR_SHIPMENT'),
(5, 3, 1, '대전광역시 서구 둔산로 202', '35209', '2024-10-30', '2024-10-05', '대량 출하', 'WAITING_FOR_SHIPMENT'),
(6, 2, 1, '광주광역시 북구 용봉로 333', '61186', '2024-10-12', '2024-10-06', '급한 출하', 'WAITING_FOR_SHIPMENT'),
(7, 3, 2, '경기도 수원시 영통구 영통로 100', '16688', '2024-10-18', '2024-10-07', '일반 출하', 'WAITING_FOR_SHIPMENT'),
(8, 1, 3, '울산광역시 남구 삼산로 222', '44714', '2024-10-22', '2024-10-08', '긴급한 출하', 'WAITING_FOR_SHIPMENT'),
(9, 2, 2, '충청북도 청주시 상당구 상당로 99', '28528', '2024-10-28', '2024-10-09', '특별 출하', 'WAITING_FOR_SHIPMENT'),
(10, 3, 1, '경상남도 창원시 의창구 창원대로 300', '51154', '2024-10-31', '2024-10-10', '대규모 출하', 'WAITING_FOR_SHIPMENT');


-- ShippingOrderDetail 테이블 초기 데이터 삽입
INSERT INTO shipping_order_detail (shipping_order_id, product_id, quantity, remarks, shipping_status)
VALUES (1, 1, 50, '상품 A 대량 출하', 'ORDER_FOR_SHIPMENT'),
       (1, 2, 30, '상품 B 추가 출하', 'ORDER_FOR_SHIPMENT'),
       (2, 3, 100, '상품 C 대량 출하', 'ORDER_FOR_SHIPMENT'),
       (2, 4, 20, '상품 D 긴급 출하', 'ORDER_FOR_SHIPMENT'),
       (3, 5, 40, '상품 E 고객 요청 출하', 'ORDER_FOR_SHIPMENT'),
       (3, 1, 60, '상품 A 대량 출하', 'ORDER_FOR_SHIPMENT'),
       (4, 2, 25, '상품 B 정기 출하', 'ORDER_FOR_SHIPMENT'),
       (4, 3, 35, '상품 C 정기 출하', 'ORDER_FOR_SHIPMENT'),
       (5, 4, 45, '상품 D 대량 출하', 'ORDER_FOR_SHIPMENT'),
       (5, 5, 55, '상품 E 대량 출하', 'ORDER_FOR_SHIPMENT'),
       (6, 6, 60, '상품 F 급히 출하', 'ORDER_FOR_SHIPMENT'),
       (6, 1, 20, '상품 A 보충 출하', 'ORDER_FOR_SHIPMENT'),
       (7, 7, 90, '상품 G 고객 요청 출하', 'ORDER_FOR_SHIPMENT'),
       (7, 2, 15, '상품 B 추가 출하', 'ORDER_FOR_SHIPMENT'),
       (8, 8, 75, '상품 H 대량 출하', 'ORDER_FOR_SHIPMENT'),
       (8, 3, 25, '상품 C 추가 출하', 'ORDER_FOR_SHIPMENT'),
       (9, 9, 50, '상품 I 특별 출하', 'ORDER_FOR_SHIPMENT'),
       (9, 4, 30, '상품 D 보충 출하', 'ORDER_FOR_SHIPMENT'),
       (10, 10, 120, '상품 J 대규모 출하', 'ORDER_FOR_SHIPMENT'),
       (10, 5, 70, '상품 E 추가 출하', 'ORDER_FOR_SHIPMENT');

