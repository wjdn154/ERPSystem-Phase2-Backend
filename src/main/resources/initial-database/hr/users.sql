-- # INSERT INTO users_role (role_name,role_type) VALUES
-- # ('대장','APPROVER');
-- #
-- # INSERT INTO users_permission(permission_name) VALUES
-- # ('READ_PRIVILEGES'),
-- # ('CONFIGURE_PRIVILEGES');

-- #INSERT INTO users (employee_id, role_id,password,user_name,user_nick_name) VALUES
-- #(1,1,'12345678','만수이'),
-- #(2,1,'akstndl','정현이'),
-- #(3,1,'wlgurdl','지혁이');
-- INSERT INTO users_permission (
--     admin_privileges,
--     configure_privileges,
--     delete_privileges,
--     finance_delete,
--     finance_edit,
--     finance_view,
--     hr_delete,
--     hr_edit,
--     hr_view,
--     logistics_delete,
--     logistics_edit,
--     logistics_view,
--     manage_users,
--     production_delete,
--     production_edit,
--     production_view,
--     read_privileges,
--     update_privileges,
--     write_privileges
-- ) VALUES (
--              true,  -- admin_privileges
--              true,  -- configure_privileges
--              true,  -- delete_privileges
--              true,  -- finance_delete
--              true,  -- finance_edit
--              true,  -- finance_view
--              true,  -- hr_delete
--              true,  -- hr_edit
--              true,  -- hr_view
--              true,  -- logistics_delete
--              true,  -- logistics_edit
--              true,  -- logistics_view
--              true,  -- manage_users
--              true,  -- production_delete
--              true,  -- production_edit
--              true,  -- production_view
--              true,  -- read_privileges
--              true,  -- update_privileges
--              false  -- write_privileges
--          ),(
--     true,  -- admin_privileges
--     true,  -- configure_privileges
--     true,  -- delete_privileges
--     true,  -- finance_delete
--     true,  -- finance_edit
--     true,  -- finance_view
--     true,  -- hr_delete
--     true,  -- hr_edit
--     true,  -- hr_view
--     true,  -- logistics_delete
--     true,  -- logistics_edit
--     true,  -- logistics_view
--     true,  -- manage_users
--     true,  -- production_delete
--     true,  -- production_edit
--     true,  -- production_view
--     true,  -- read_privileges
--     true,  -- update_privileges
--     false  -- write_privileges
-- ),(
--     true,  -- admin_privileges
--     true,  -- configure_privileges
--     true,  -- delete_privileges
--     true,  -- finance_delete
--     true,  -- finance_edit
--     true,  -- finance_view
--     true,  -- hr_delete
--     true,  -- hr_edit
--     true,  -- hr_view
--     true,  -- logistics_delete
--     true,  -- logistics_edit
--     true,  -- logistics_view
--     true,  -- manage_users
--     true,  -- production_delete
--     true,  -- production_edit
--     true,  -- production_view
--     true,  -- read_privileges
--     true,  -- update_privileges
--     false  -- write_privileges
-- );

INSERT INTO users_permission (
account_ledger_permission
,account_subject_permission
,admin_permission
,applicant_management_permission
,application_management_permission
,assignment_management_permission
,bills_payable_permission
,bills_receivable_permission
,bom_management_permission
,cash_book_permission
,cash_flow_permission
,client_account_ledger_permission
,client_initial_permission
,client_ledger_permission
,client_registration_permission
,closing_annex_permission
,closing_carryover_permission
,closing_data_permission
,cost_statement_permission
,daily_finance_permission
,daily_monthly_permission
,department_management_permission
,deposits_status_permission
,electronic_tax_permission
,employee_management_permission
,environment_permission
,equipment_management_permission
,equity_changes_permission
,financial_position_permission
,fixed_asset_register_permission
,general_ledger_permission
,general_voucher_permission
,inbound_expected_permission
,inbound_order_permission
,inbound_processing_permission
,income_statement_permission
,interview_management_permission
,inventory_adjustment_status_permission
,inventory_adjustment_steps_permission
,inventory_inspection_status_permission
,inventory_inspection_view_permission
,item_group_management_permission
,item_management_permission
,job_offers_permission
,job_postings_permission
,journal_permission
,leave_management_permission
,lot_management_permission
,maintenance_history_permission
,material_input_status_permission
,material_management_permission
,order_permission
,outbound_expected_permission
,outbound_expected_status_permission
,outbound_processing_permission
,outsourcing_inspection_permission
,outsourcing_order_permission
,outsourcing_performance_permission
,outsourcing_price_permission
,overtime_management_permission
,performance_evaluation_permission
,previous_cost_statement_permission
,previous_financial_position_permission
,previous_income_statement_permission
,previous_profit_distribution_permission
,price_request_permission
,process_details_permission
,production_order_permission
,production_request_permission
,profit_distribution_permission
,purchase_order_permission
,purchase_permission
,purchase_plan_permission
,purchase_request_permission
,quality_inspection_permission
,quotation_permission
,register_book_permission
,retirement_management_permission
,returns_reception_permission
,returns_status_permission
,routing_management_permission
,sale_permission
,sales_purchase_ledger_permission
,sales_purchase_voucher_permission
,serial_management_permission
,shipment_input_permission
,shipment_permission
,shipment_status_permission
,shipment_view_permission
,shipping_order_input_permission
,shipping_order_permission
,shipping_order_view_permission
,tax_invoice_permission
,time_management_permission
,transferred_depreciation_permission
,trial_balance_permission
,undepreciated_permission
,user_management_permission
,voucher_print_permission
,warehouse_registration_permission
,work_performance_permission
,workcenter_management_permission
,worker_management_permission
) VALUES
(
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS',
 'NO_ACCESS'
),
(
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS'
),
(
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS'
),
(
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS'
),
(
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS'
),
(
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS'
),
(
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS'
),
(
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS',
    'NO_ACCESS'
);



INSERT INTO users (password, user_name, user_nickname, employee_id, permission_id) VALUES
('$2a$10$KIdjiluwxpiwWzMdjIyYHOtaWC8PZtav6W3M.tQyGb2TzAaTO3VkS', 'gj1563@naver.com', '건호', 3, 3),
('$2a$10$KIdjiluwxpiwWzMdjIyYHOtaWC8PZtav6W3M.tQyGb2TzAaTO3VkS', 'ckacl2512@naver.com', '만수이!!', 4, 4),
('$2a$10$KIdjiluwxpiwWzMdjIyYHOtaWC8PZtav6W3M.tQyGb2TzAaTO3VkS', 'hshdla@naver.com', '성화', 5, 5),
('$2a$10$KIdjiluwxpiwWzMdjIyYHOtaWC8PZtav6W3M.tQyGb2TzAaTO3VkS', 'chlwlgur0407@naver.com', '지혁이', 6, 6),
('$2a$10$KIdjiluwxpiwWzMdjIyYHOtaWC8PZtav6W3M.tQyGb2TzAaTO3VkS', 'cksals@naver.com', '찬민', 7, 7),
('$2a$10$KIdjiluwxpiwWzMdjIyYHOtaWC8PZtav6W3M.tQyGb2TzAaTO3VkS', 'labe1827@gmail.com', '태종', 8, 8),
('$2a$10$KIdjiluwxpiwWzMdjIyYHOtaWC8PZtav6W3M.tQyGb2TzAaTO3VkS', 'wkdgywjd77@naver.com', '정콩콩', 1, 1),
('$2a$10$KIdjiluwxpiwWzMdjIyYHOtaWC8PZtav6W3M.tQyGb2TzAaTO3VkS', 'readyoun@omz.com', '이미연', 2, 2);

