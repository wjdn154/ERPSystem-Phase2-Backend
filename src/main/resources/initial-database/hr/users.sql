INSERT INTO users_role (role_name,role_type) VALUES
('대장','APPROVER');

INSERT INTO users_permission(permission_name) VALUES
('READ_PRIVILEGES'),
('CONFIGURE_PRIVILEGES');

INSERT INTO users (employee_id, role_id,password,user_name) VALUES
(1,1,'12345678','만수이'),
(2,1,'akstndl','정현이'),
(3,1,'wlgurdl','지혁이');

