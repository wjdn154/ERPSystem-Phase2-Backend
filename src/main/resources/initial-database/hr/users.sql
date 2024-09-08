# INSERT INTO users_role (role_name,role_type) VALUES
# ('대장','APPROVER');
#
# INSERT INTO users_permission(permission_name) VALUES
# ('READ_PRIVILEGES'),
# ('CONFIGURE_PRIVILEGES');

#INSERT INTO users (employee_id, role_id,password,users_Id,user_name) VALUES
#(1,1,'12345678','만수이'),
#(2,1,'akstndl','정현이'),
#(3,1,'wlgurdl','지혁이');
INSERT INTO users_permission (
    admin_privileges,
    configure_privileges,
    delete_privileges,
    finance_delete,
    finance_edit,
    finance_view,
    hr_delete,
    hr_edit,
    hr_view,
    logistics_delete,
    logistics_edit,
    logistics_view,
    manage_users,
    production_delete,
    production_edit,
    production_view,
    read_privileges,
    update_privileges,
    write_privileges
) VALUES (
             true,  -- admin_privileges
             true,  -- configure_privileges
             true,  -- delete_privileges
             true,  -- finance_delete
             true,  -- finance_edit
             true,  -- finance_view
             true,  -- hr_delete
             true,  -- hr_edit
             true,  -- hr_view
             true,  -- logistics_delete
             true,  -- logistics_edit
             true,  -- logistics_view
             true,  -- manage_users
             true,  -- production_delete
             true,  -- production_edit
             true,  -- production_view
             true,  -- read_privileges
             true,  -- update_privileges
             false  -- write_privileges
         ),(
    true,  -- admin_privileges
    true,  -- configure_privileges
    true,  -- delete_privileges
    true,  -- finance_delete
    true,  -- finance_edit
    true,  -- finance_view
    true,  -- hr_delete
    true,  -- hr_edit
    true,  -- hr_view
    true,  -- logistics_delete
    true,  -- logistics_edit
    true,  -- logistics_view
    true,  -- manage_users
    true,  -- production_delete
    true,  -- production_edit
    true,  -- production_view
    true,  -- read_privileges
    true,  -- update_privileges
    false  -- write_privileges
),(
    true,  -- admin_privileges
    true,  -- configure_privileges
    true,  -- delete_privileges
    true,  -- finance_delete
    true,  -- finance_edit
    true,  -- finance_view
    true,  -- hr_delete
    true,  -- hr_edit
    true,  -- hr_view
    true,  -- logistics_delete
    true,  -- logistics_edit
    true,  -- logistics_view
    true,  -- manage_users
    true,  -- production_delete
    true,  -- production_edit
    true,  -- production_view
    true,  -- read_privileges
    true,  -- update_privileges
    false  -- write_privileges
);

INSERT INTO users (employee_id,permission_id,password,users_id,user_name) VALUES
                                                                         (1,1,'12345678','aks','만수이'),
                                                                         (2,2,'akstndl','aaa','정현이'),
                                                                         (3,3,'wlgurdl','asd','지혁이');