

INSERT INTO equipment_data (cost,install_date,purchase_date,company_id,factory_id,workcenter_id,equipment_img,equipment_name,equipment_num,manufacturer,model_name,equipment_type,operation_status) VALUES
(5500000,'2022-02-02','2022-02-03',1,1,1,'설비 사진1','자동 조립 라인','PRD-EM-001','삼성','ASLINE-100','ASSEMBLY','BEFORE_OPERATION'),
(7500000,'2022-02-02','2022-02-04',1,2,2,'설비 사진2','5축 CNC 기계','PRD-EM1-002','LG','5AXIS-CNC','ASSEMBLY','BEFORE_OPERATION'),
(800000,'2022-02-02','2022-02-04',1,1,7,'설비 사진3','X-Ray 검사기','PRD-EM1-003','YG','XRAY-X','ASSEMBLY','BEFORE_OPERATION'),
(6000000,'2022-02-02','2022-02-04',1,1,6,'설비 사진4','자동화 조립 로봇','PRD-EM1-004','현대','ROBO-AS1','ASSEMBLY','BEFORE_OPERATION'),
(900000,'2022-02-02','2022-02-04',1,2,14,'설비 사진5','고속 포장기','PRD-EM1-005','삼성','PK-FAST','ASSEMBLY','BEFORE_OPERATION'),
(3000000,'2022-02-02','2022-02-04',1,1,15,'설비 사진6','자율 물류 로봇','PRD-EM1-006','삼성','LOGI-AUTO','ASSEMBLY','REPAIRING'),
(1500000,'2022-03-02','2021-02-04',1,1,1,'설비 사진7','조립설비3','PRD-EM1-007','LG','메가존클라우드','ASSEMBLY','REPAIRING'),
(1500000,'2022-03-02','2021-02-04',1,1,1,'설비 사진8','포장설비1','PRD-EM1-008','LG','메가존클라우드','ASSEMBLY','REPAIRING'),
(1500000,'2022-03-02','2021-02-04',1,1,1,'설비 사진9','포장설비2','PRD-EM1-009','현대자동차','메가존클라우드','ASSEMBLY','REPAIRING'),
(1500000,'2022-03-02','2021-02-04',1,1,1,'설비 사진10','포장설비3','PRD-EM1-010','삼성','메가존클라우드','ASSEMBLY','REPAIRING'),
(1500000,'2022-03-02','2021-02-04',2,1,1,'설비 사진1','포장설비3','PRD-EM2-001','삼성','메가존클라우드','ASSEMBLY','REPAIRING'),
(1500000,'2022-04-02','2021-04-04',2,1,1,'설비 사진2','포장설비3','PRD-EM2-002','삼성','메가존클라우드','ASSEMBLY','REPAIRING');
#
# -- WH002 작업장 설비 (조립, 가공)
# INSERT INTO equipment_data
# (cost, install_date, purchase_date, company_id, factory_id, workcenter_id, equipment_img, equipment_name, equipment_num, manufacturer, model_name, equipment_type, operation_status)
# VALUES
#     (5500000, '2023-03-01', '2023-02-15', 1, 2, 1, NULL, '자동 조립 라인', 'AS-1001', 'ASSEMBLY_CORP', 'ASLINE-100', 'ASSEMBLY', 'OPERATING'),
#     (7500000, '2023-04-10', '2023-04-01', 2, 2, 2, NULL, '5축 CNC 기계', 'MC-2001', 'CNC_MASTER', '5AXIS-CNC', 'MACHINING', 'OPERATING');
#
# -- WH003 작업장 설비 (검사, 조립)
# INSERT INTO equipment_data
# (cost, install_date, purchase_date, company_id, factory_id, workcenter_id, equipment_img, equipment_name, equipment_num, manufacturer, model_name, equipment_type, operation_status)
# VALUES
#     (800000, '2021-12-15', '2021-12-01', 1, 3, 7, NULL, 'X-Ray 검사기', 'IN-7001', 'INSPECTTECH', 'XRAY-X', 'INSPECTION', 'OPERATING'),
#     (6000000, '2023-05-01', '2023-04-15', 2, 3, 6, NULL, '자동화 조립 로봇', 'AS-6001', 'ROBO_ASSEMBLY', 'ROBO-AS1', 'ASSEMBLY', 'OPERATING');
#
# -- WH004 작업장 설비 (포장, 물류)
# INSERT INTO equipment_data
# (cost, install_date, purchase_date, company_id, factory_id, workcenter_id, equipment_img, equipment_name, equipment_num, manufacturer, model_name, equipment_type, operation_status)
# VALUES
#     (900000, '2023-01-05', '2023-01-01', 1, 4, 14, NULL, '고속 포장기', 'PK-9001', 'PACKMATIC', 'PK-FAST', 'PACKAGING', 'OPERATING'),
#     (3000000, '2022-03-10', '2022-03-01', 2, 4, 15, NULL, '자율 물류 로봇', 'LG-15001', 'LOGI_AI', 'LOGI-AUTO', 'LOGISTICS', 'OPERATING');
