
INSERT INTO warehouse_address (id, warehouse_address, detail_address, warehouse_postal_code, warehouse_place)
VALUES (1, '해운대구', '센텀동로', '31', '부산정보산업진흥원');


INSERT INTO hierarchy_group (id, hierarchy_group_code, hierarchy_group_name)
VALUES (1, 'HG001', '그룹 A'),
       (2, 'HG002', '그룹 B'),
       (3, 'HG003', '그룹 C');

INSERT INTO warehouse_type (id, name)
VALUES (1, '창고'),
       (2, '공장'),
       (3, '외주공장');

INSERT INTO warehouse (id, address_id, warehouse_type_id, warehouse_code, name, production_process, is_active)
VALUES (1, 1, 1, 'WH001', '창고', '재고창고', TRUE),
       (2, 1, 2, 'WH002', '공장', '생산공장', FALSE);

INSERT INTO warehouse_hierarchy_group (id, warehouse_id, hierarchy_group_id)
VALUES (1, 1, 1),
       (2, 1, 2),
       (3, 2, 3);
