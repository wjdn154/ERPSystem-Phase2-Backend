INSERT INTO warehouse_address (id, company_id, warehouse_address, detail_address, warehouse_postal_code, warehouse_place)
VALUES (1, 1, '해운대구', '센텀동로', '31', '부산정보산업진흥원');


INSERT INTO hierarchy_group (id, company_id, hierarchy_group_code, hierarchy_group_name)
VALUES (1, 1, 'HG001', '그룹 A'),
       (2, 1, 'HG002', '그룹 B'),
       (3, 1, 'HG003', '그룹 C'),
       (4, 2, 'HG004', '그룹 D'),
       (5, 2, 'HG005', '그룹 E'),
       (6, 2, 'HG006', '그룹 F');


INSERT INTO warehouse (id, company_id, address_id, warehouse_code, warehouse_type, name, production_process, is_active)
VALUES (1, 1, 1, 'WH001', 'WAREHOUSE', '재고창고', '지혁몸통창고', TRUE),
       (2, 1, 1, 'WH002', 'FACTORY', '생산공장', '지혁이몸통생산', FALSE),
       (3, 2, 1, 'WH002', 'FACTORY', '과일창고', '지혁이머리저장', FALSE),
       (4, 2, 1, 'WH002', 'FACTORY', '부품공장', '지혁이머리생산', TRUE);

INSERT INTO warehouse_hierarchy_group (id, company_id, warehouse_id, hierarchy_group_id)
VALUES (1, 1, 1, 1),
       (2, 1, 1, 2),
       (3, 1, 2, 3),
       (4, 2, 3, 4),
       (5, 2, 3, 5),
       (6, 2, 4, 6);

# drop DATABASE erp;
# create DATABASE erp;
# drop DATABASE erp_test;
# create DATABASE erp_test;