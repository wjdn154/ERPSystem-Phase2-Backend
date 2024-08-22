INSERT INTO warehouse_address (id, warehouse_address, detail_address, warehouse_postal_code, warehouse_place)
VALUES (1, '해운대구', '센텀동로', '31', '부산정보산업진흥원');


INSERT INTO hierarchy_group (id, hierarchy_group_code, hierarchy_group_name)
VALUES (1, 'HG001', '그룹 A'),
       (2, 'HG002', '그룹 B'),
       (3, 'HG003', '그룹 C');

INSERT INTO warehouse (id, address_id, warehouse_code, warehouse_type, name, production_process, is_active)
VALUES (1, 1, 'WH001', 'WAREHOUSE', '재고창고', '지혁창고', TRUE),
       (2, 1, 'WH002', 'FACTORY', '생산공장', '노예생산', FALSE);

INSERT INTO warehouse_hierarchy_group (id, warehouse_id, hierarchy_group_id)
VALUES (1, 1, 1),
       (2, 1, 2),
       (3, 2, 3);

# drop DATABASE erp;
# create DATABASE erp;