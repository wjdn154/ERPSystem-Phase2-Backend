INSERT INTO warehouse_address (id, warehouse_address, detail_address, warehouse_postal_code, warehouse_place)
VALUES (1,'부산광역시 해운대구 센텀서로 30', '센텀그린타워 5층', '48058', '센텀동'),
       (2,'부산광역시 동구 중앙대로 210', '유림빌딩 4층', '48814', '초량동'),
       (3,'부산광역시 남구 유엔평화로 76', '부산경상대학교 3층', '48538', '대연동'),
       (4,'부산광역시 북구 금곡대로 227', '덕천프라자 2층', '46532', '덕천동'),
       (5,'부산광역시 사하구 다대로 297', '부산테크노파크 1층', '49307', '다대동'),
       (6,'부산광역시 연제구 월드컵대로 344', '연산빌딩 7층', '47503', '연산동'),
       (7,'부산광역시 서구 구덕로 225', '서부산빌딩 10층', '49202', '암남동'),
       (8,'부산광역시 중구 중앙대로 68', '부산중앙우체국 1층', '48946', '광복동'),
       (9,'부산광역시 금정구 금강로 247', '금정프라자 8층', '46282', '장전동'),
       (10,'부산광역시 동래구 충렬대로 112', '동래코아 12층', '47818', '명륜동'),
       (11,'부산광역시 부산진구 가야대로 721', '가야빌딩 3층', '47206', '가야동'),
       (12,'부산광역시 사상구 학감대로 217', '사상테크노밸리 5층', '46901', '학장동'),
       (13,'부산광역시 강서구 명지오션시티5로 19', '명지프라자 2층', '46774', '명지동'),
       (14,'부산광역시 기장군 기장읍 차성로 35', '기장타워 4층', '46012', '기장읍'),
       (15,'부산광역시 영도구 절영로 50', '영도빌딩 7층', '49019', '대교동');

INSERT INTO hierarchy_group (id, parent_group_id, hierarchy_group_code, hierarchy_group_name)
VALUES (1,  null,'root', 'root'),
       (2,  1,'HG002', '그룹 B'),
       (3,  1,'HG003', '그룹 C'),
       (4,  null,'root1', 'root'),
       (5,  4,'HG005', '그룹 E'),
       (6,  4,'HG006', '그룹 F'),
       (7,  2,'HG007', '그룹 G'),
       (8,  2,'HG008', '그룹 H'),
       (9,  7,'HG009', '그룹 I');

INSERT INTO warehouse (id, warehouse_address_id, process_detail_id, warehouse_code, warehouse_type, name, is_active)
VALUES (1,  1, null,'WH001', 'WAREHOUSE', '재고창고', TRUE),
       (2,  2, 1,'WH002', 'FACTORY', '생산공장', FALSE),
       (3,  3, 2,'WH003', 'FACTORY', '과일창고', FALSE),
       (4,  4, 3,'WH004', 'FACTORY', '부품공장', TRUE),
       (5,  5, null,'WH005', 'WAREHOUSE', '부품공장', TRUE),
       (6,  6, null,'WH006', 'WAREHOUSE', '식품저장', TRUE),
       (7,  7, null,'WH007', 'WAREHOUSE', '출하창고', TRUE),
       (8,  8, null,'WH008', 'WAREHOUSE', '입고창고', TRUE),
       (9,  9, null,'WH009', 'WAREHOUSE', '지혁창고', TRUE),
       (10,  10, null,'WH010', 'WAREHOUSE', '부품저장창고', TRUE),
       (11,  11, null,'WH011', 'WAREHOUSE', '반품창고', TRUE),
       (12,  12, null,'WH012', 'WAREHOUSE', '창고2', TRUE),
       (13,  13, null,'WH013', 'WAREHOUSE', '창고3', TRUE),
       (14,  14, null,'WH014', 'WAREHOUSE', '창고4', TRUE),
       (15,  15, null,'WH015', 'WAREHOUSE', '창고5', TRUE),
       (16,  1, null,'testWarehouse', 'WAREHOUSE', 'testWarehouse', TRUE),
       (17,  1, null,'testWarehouse2', 'WAREHOUSE', 'testWarehouse2', TRUE);

INSERT INTO warehouse_hierarchy_group (id, warehouse_id, hierarchy_group_id)
VALUES (1, 1, 2),
       (2, 2, 2),
       (3, 3, 2),
       (4, 1, 4),
       (5, 2, 5),
       (6, 2, 6),
       (7, 1, 7),
       (8, 2, 8),
       (9, 1, 9);

INSERT INTO warehouse_location (id, warehouse_id, location_name, active)
VALUES (1, 1, 'A1', true),
       (2, 1, 'A2', true),
       (3, 1, 'A3', true),
       (4, 1, 'A4', true),
       (5, 3, 'A5', true),
       (6, 3, 'A6', true),
       (7, 3, 'A7', true),
       (8, 3, 'A8', true);

INSERT INTO warehouse_transfer (id, employee_id, sending_warehouse_id, receiving_warehouse_id, transfer_date, transfer_number, comment, status)
VALUES (1, 1, 1, 2, '2024-09-10', '1', null, '미확인'),
       (2, 1, 5, 7, '2024-09-10', '2', null, '확인'),
       (3, 1, 6, 7, '2024-09-10', '3', null, '미확인'),
       (4, 1, 8, 7, '2024-09-10', '4', null, '확인'),
       (5, 2, 1, 2, '2024-09-10', '5', null, '확인'),
       (6, 2, 5, 7, '2024-09-10', '6', null, '미확인'),
       (7, 2, 6, 7, '2024-09-10', '7', null, '미확인'),
       (8, 2, 8, 7, '2024-09-10', '8', null, '확인');

INSERT INTO warehouse_transfer_product (id, warehouse_transfer_id, product_id, quantity, comment)
VALUES (1,  1, 1, 100, null),
       (2,  2, 1, 10, null),
       (3,  3, 1, 1, null),
       (4,  4, 1, 200, null),
       (5,  5, 1, 300, null),
       (6,  7, 2, 100, null),
       (7,  8, 2, 10, null),
       (8,  1, 2, 1, null),
       (9,  2, 2, 200, null),
       (10, 3, 2, 300, null);

INSERT INTO inventory (id, warehouse_location_id, inventory_number,quantity, standard, product_id, warehouse_id)
VALUES (1, 1, 0000000001, 30, null, 1, 1),
       (2, 2, 0000000002, 40, null, 1, 1),
       (3, 3, 0000000003, 70, null, 1, 6),
       (4, 4, 0000000004, 60, null, 1, 7),
       (5, 1, 0000000005, 90, null, 1, 8),
       (6, 2, 0000000006, 80, null, 1, 9),
       (7, 3, 0000000007, 70, null, 1, 10),
       (8, 1, 0000000008, 10, null, 1, 11),
       (9, 2, 0000000009, 500, null, 1, 12),
       (10, 5, 0000000010, 240, null, 1, 13),
       (11, 6, 0000000011, 310, null, 1, 14),
       (12, 7, 0000000012, 200, null, 1, 15);

INSERT INTO inventory_inspection (id, employee_id, warehouse_id, inspection_date, inspection_number, status, comment)
values (1, 1, 1, '2024-09-12', 1, '조정완료', '2024년 9월 정기 재고 실시'),
       (2, 1, 1, '2024-08-12', 1, '미조정', '2024년 8월 정기 재고 실시'),
       (3, 2, 1, '2024-07-12', 1, '미조정', '2024년 7월 정기 재고 실시');

INSERT INTO inventory_inspection_detail (id, inventory_id, inventory_inspection_id, product_id, warehouse_location_id, product_code, product_name, actual_quantity, book_quantity, difference_quantity, comment)
values (1, 1, 1, 1, 1, 'B0001', '즉석밥', 200, 30, 170, null),
       (2, 2, 1, 1, 2, 'B0001', '즉석밥', 40, 30, 10, null),
       (3, 3, 1, 1, 3, 'B0001', '즉석밥', 40, 70, -30, null),
       (4, 4, 2, 1, 4, 'B0001', '즉석밥', 100, null, null, null),
       (5, 5, 3, 1, 1, 'B0001', '즉석밥', 70, null, null, null);

INSERT INTO shipment (id, warehouse_id, warehouse_address_id, employee_id, client_id, contact_info_id, shipment_date, shipment_number, comment)
values (1, 1, 1, 1, 1, 1, '2024-10-2', 1, null),
       (2, 2, 2, 2, 2, 2, '2024-10-1', 1, null),
       (3, 3, 3, 3, 3, 3, '2024-09-27', 1, null),
       (4, 4, 4, 4, 4, 4, '2024-09-26', 1, null),
       (5, 5, 5, 5, 5, 5, '2024-09-24', 1, null),
       (6, 6, 6, 1, 6, 6, '2024-09-23', 1, null),
       (7, 7, 7, 2, 7, 7, '2024-09-21', 1, null),
       (8, 8, 8, 3, 8, 8, '2024-09-20', 1, null),
       (9, 9, 9, 4, 9, 9, '2024-09-19', 1, null),
       (10, 10, 10, 5, 10, 10, '2024-09-15', 1, null),
       (11, 11, 11, 1, 11, 11, '2024-09-14', 1, null),
       (12, 16, 1, 1, 11, 11, '2024-09-11', 1, null);

INSERT INTO shipment_product (id, comment, product_code, product_name, quantity, standard, unit, product_id, shipment_id)
values (1, null, 'B0001', '즉석밥', 100, null, null, 1, 1),
       (2, null, 'B0002', '유기농 토마토', 100, null, null, 2, 1),
       (3, null, 'B0003', '세탁세제', 100, null, null, 3, 1),
       (4, null, 'B0001', '즉석밥', 100, null, null, 1, 2),
       (5, null, 'B0002', '유기농 토마토', 100, null, null, 2, 2),
       (6, null, 'B0003', '세탁세제', 100, null, null, 3, 2),
       (7, null, 'B0001', '즉석밥', 100, null, null, 1, 3),
       (8, null, 'B0002', '유기농 토마토', 100, null, null, 2, 3),
       (9, null, 'B0003', '세탁세제', 100, null, null, 3, 3),
       (10, null, 'B0001', '즉석밥', 100, null, null, 1, 4),
       (11, null, 'B0002', '유기농 토마토', 100, null, null, 2, 4),
       (12, null, 'B0003', '세탁세제', 100, null, null, 3, 4),
       (13, null, 'B0001', '즉석밥', 100, null, null, 1, 5),
       (14, null, 'B0002', '유기농 토마토', 100, null, null, 2, 5),
       (15, null, 'B0003', '세탁세제', 100, null, null, 3, 5),
       (16, null, 'B0001', '즉석밥', 100, null, null, 1, 6),
       (17, null, 'B0002', '유기농 토마토', 100, null, null, 2, 6),
       (18, null, 'B0003', '세탁세제', 100, null, null, 3, 6),
       (19, null, 'B0001', '즉석밥', 100, null, null, 1, 7),
       (20, null, 'B0002', '유기농 토마토', 100, null, null, 2, 7),
       (21, null, 'B0003', '세탁세제', 100, null, null, 3, 7),
       (22, null, 'B0001', '즉석밥', 100, null, null, 1, 8),
       (23, null, 'B0002', '유기농 토마토', 100, null, null, 2, 8),
       (24, null, 'B0003', '세탁세제', 100, null, null, 3, 8),
       (25, null, 'B0001', '즉석밥', 100, null, null, 1, 9),
       (26, null, 'B0002', '유기농 토마토', 100, null, null, 2, 9),
       (27, null, 'B0003', '세탁세제', 100, null, null, 3, 9),
       (28, null, 'B0001', '즉석밥', 100, null, null, 1, 10),
       (29, null, 'B0002', '유기농 토마토', 100, null, null, 2, 10),
       (30, null, 'B0003', '세탁세제', 100, null, null, 3, 10),
       (31, null, 'B0001', '즉석밥', 100, null, null, 1, 11),
       (32, null, 'B0002', '유기농 토마토', 100, null, null, 2, 11),
       (33, null, 'B0003', '세탁세제', 100, null, null, 3, 11),
       (34, null, 'B0001', '즉석밥', 100, null, null, 1, 12),
       (35, null, 'B0002', '유기농 토마토', 100, null, null, 2, 12),
       (36, null, 'B0003', '세탁세제', 100, null, null, 3, 12);




