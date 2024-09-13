INSERT INTO warehouse_address (id, company_id, warehouse_address, detail_address, warehouse_postal_code, warehouse_place)
VALUES (1, 1, '해운대구', '센텀동로', '31', '부산정보산업진흥원');

INSERT INTO hierarchy_group (id, company_id, parent_group_id, hierarchy_group_code, hierarchy_group_name)
VALUES (1, 1, null,'root', 'root'),
       (2, 1, 1,'HG002', '그룹 B'),
       (3, 1, 1,'HG003', '그룹 C'),
       (4, 2, null,'root1', 'root'),
       (5, 2, 4,'HG005', '그룹 E'),
       (6, 2, 4,'HG006', '그룹 F'),
       (7, 1, 2,'HG007', '그룹 G'),
       (8, 1, 2,'HG008', '그룹 H'),
       (9, 1, 7,'HG009', '그룹 I');

INSERT INTO warehouse (id, company_id, warehouse_address_id, process_detail_id, warehouse_code, warehouse_type, name, production_process, is_active)
VALUES (1, 1, 1, null,'WH001', 'WAREHOUSE', '재고창고', '지혁몸통창고', TRUE),
       (2, 1, 1, 1,'WH002', 'FACTORY', '생산공장', '지혁이몸통생산', FALSE),
       (3, 2, 1, 2,'WH003', 'FACTORY', '과일창고', '지혁이머리저장', FALSE),
       (4, 2, 1, 3,'WH004', 'FACTORY', '부품공장', '지혁이머리생산', TRUE);

INSERT INTO warehouse_hierarchy_group (id, company_id, warehouse_id, hierarchy_group_id)
VALUES (1, 1, 1, 1),
       (2, 1, 2, 1),
       (3, 1, 2, 1),
       (4, 2, 3, 4),
       (5, 2, 3, 5),
       (6, 2, 3, 6),
       (7, 1, 1, 7),
       (8, 1, 1, 8),
       (9, 1, 1, 9);

INSERT INTO warehouse_location (id, company_id, warehouse_id, location_name, is_active)
VALUES (1, 1, 1, 'A1', true),
       (2, 1, 1, 'A2', true),
       (3, 1, 1, 'A3', true),
       (4, 1, 1, 'A4', true),
       (5, 2, 3, 'A5', true),
       (6, 2, 3, 'A6', true),
       (7, 2, 3, 'A7', true),
       (8, 2, 3, 'A8', true);

INSERT INTO warehouse_transfer (id, company_id, employee_id, sending_warehouse_id, receiving_warehouse_id, transfer_date, transfer_number, comment, status)
VALUES (1, 1, 1, 1, 2, '2024-09-10', '1', null, '미확인'),
       (2, 1, 1, 1, 2, '2024-09-10', '2', null, '확인'),
       (3, 1, 1, 1, 2, '2024-09-10', '3', null, '미확인'),
       (4, 1, 1, 1, 2, '2024-09-10', '4', null, '확인'),
       (5, 2, 2, 3, 4, '2024-09-10', '5', null, '확인'),
       (6, 2, 2, 3, 4, '2024-09-10', '6', null, '미확인'),
       (7, 2, 2, 3, 4, '2024-09-10', '7', null, '미확인'),
       (8, 2, 2, 3, 4, '2024-09-10', '8', null, '확인');

INSERT INTO warehouse_transfer_product (id, company_id, warehouse_transfer_id, product_id, quantity, comment)
VALUES (1, 1, 1, 1, 100, null),
       (2, 1, 1, 1, 10, null),
       (3, 1, 1, 1, 1, null),
       (4, 1, 1, 1, 200, null),
       (5, 1, 1, 1, 300, null),
       (6, 2, 5, 2, 100, null),
       (7, 2, 5, 2, 10, null),
       (8, 2, 5, 2, 1, null),
       (9, 2, 5, 2, 200, null),
       (10,2, 5, 2, 300, null);