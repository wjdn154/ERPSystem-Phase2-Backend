
INSERT INTO department(department_name,location, manager_id) VALUES
('1','1','1'),
('1','1','1'),
('1','1','1');

INSERT INTO employee_position( description, position_name) VALUES
('Responsible for managing team and overseeing projects.', 'Manager'),
('Leads the development team and coordinates between departments.', 'Team Lead'),
('Develops and maintains software applications.', 'Senior Developer');

INSERT INTO bank_account(account_number,bank_name) VALUES
('1','1'),
('1','1'),
('1','1');

INSERT INTO job_title( title_name,description ) VALUES
('1','1'),
('2','2'),
('3','3');

INSERT INTO employee ( department_id, job_title_id, position_id, bankaccount_id, first_name, last_name, date_of_birth, phone_number, employment_status, employment_type, email, address, hire_date, is_household_head, profile_picture) VALUES
    ( 1, 1, 1, 1, '김', '민성', '2000-07-20', '010-2512-9361', 'ACTIVE', 'INTERN', 'ckacl2512@naver.com', '부산', '2024-08-20', TRUE, 'x');
# ( 2, 1, 1, 1, '홍', '성화', '2000-07-21', '010-2512-9361', 'ACTIVE', 'INTERN', 'ckacl2512@naver.com', '부산', '2024-08-20', TRUE, 'x'),
# ( 3, 1, 1, 1, '허', '건호', '2000-07-22', '010-2512-9361', 'ACTIVE', 'INTERN', 'ckacl2512@naver.com', '부산', '2024-08-20', TRUE, 'x');

INSERT INTO performance (evaluation_date, employee_id, evaluator_id,  comments,score) VALUES
('2023-06-15', 1, 1, 'Excellent performance, consistently meets expectations.', 'A');
# ('2023-06-20', 1, 3, 'Needs improvement in meeting deadlines.', 'C'),
# ('2023-07-01', 1, 1, 'Outstanding problem-solving skills.', 'A+');



