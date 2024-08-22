

INSERT INTO warehouse ( address_id, warehouse_code, warehouse_type, name, production_process, is_active)
VALUES ( 1, 'WH001', 'WAREHOUSE', '재고창고', '지혁창고', TRUE),
       ( 1, 'WH002', 'FACTORY', '생산공장', '노예생산', FALSE);



insert into routing_management_process_details(is_process_outsourced, process_cost, process_defect_rate, process_duration, process_is_used, process_code, process_description, process_name)
values (true, 1,1.1,2.2,true,'코드','설명','이름');


insert into basic_data_workcenter(is_active, process_id, warehouse_id, code, description, name, workcenter_type)
values (true,1,1,'c','c','작업장1','PRESS');

INSERT INTO equipment_data (cost,install_date,purchase_date,factory_id,workcenter_id,equipment_img,equipment_name,equipment_num,manufacturer,model_name,equipment_type,operation_status) VALUES
    (1,'2022-02-02','2022-02-03',1,1,'a','b','c','d','e','ASSEMBLY','BEFORE_OPERATION');