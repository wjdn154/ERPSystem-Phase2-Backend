INSERT INTO warehouse_address (id, warehouse_address, detail_address, warehouse_postal_code, warehouse_place)
VALUES (1,  '해운대구', '센텀동로', '31', '부산정보산업진흥원');

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

INSERT INTO warehouse (id, warehouse_address_id, process_detail_id, warehouse_code, warehouse_type, name, production_process, is_active)
VALUES (1,  1, null,'WH001', 'WAREHOUSE', '재고창고', '지혁몸통창고', TRUE),
       (2,  1, 1,'WH002', 'FACTORY', '생산공장', '지혁이몸통생산', FALSE),
       (3,  1, 2,'WH003', 'FACTORY', '과일창고', '지혁이머리저장', FALSE),
       (4,  1, 3,'WH004', 'FACTORY', '부품공장', '지혁이머리생산', TRUE),
       (5,  1, null,'WH005', 'WAREHOUSE', '부품공장', null, TRUE),
       (6,  1, null,'WH006', 'WAREHOUSE', '식품저장', null, TRUE),
       (7,  1, null,'WH007', 'WAREHOUSE', '출하창고', null, TRUE),
       (8,  1, null,'WH008', 'WAREHOUSE', '입고창고', null, TRUE),
       (9,  1, null,'WH009', 'WAREHOUSE', '지혁창고', null, TRUE),
       (10,  1, null,'WH010', 'WAREHOUSE', '부품저장창고', null, TRUE),
       (11,  1, null,'WH011', 'WAREHOUSE', '반품창고', null, TRUE),
       (12,  1, null,'WH012', 'WAREHOUSE', '창고2', null, TRUE),
       (13,  1, null,'WH013', 'WAREHOUSE', '창고3', null, TRUE),
       (14,  1, null,'WH014', 'WAREHOUSE', '창고4', null, TRUE),
       (15,  1, null,'WH015', 'WAREHOUSE', '창고5', null, TRUE);

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

INSERT INTO warehouse_location (id, warehouse_id, location_name, is_active)
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

INSERT INTO inventory (id, warehouse_location_id, quantity, standard, product_id, warehouse_id)
VALUES (1, 1, 30, null, 1, 1),
       (2, 2, 40, null, 1, 1),
       (3, 3, 70, null, 1, 6),
       (4, 4, 60, null, 1, 7),
       (5, 1, 90, null, 1, 8),
       (6, 2, 80, null, 1, 9),
       (7, 3, 70, null, 1, 10),
       (8, 1, 10, null, 1, 11),
       (9, 2, 500, null, 1, 12),
       (10, 5, 240, null, 1, 13),
       (11, 6, 310, null, 1, 14),
       (12, 7, 200, null, 1, 15);

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

