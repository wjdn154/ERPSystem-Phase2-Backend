
INSERT INTO employee_department(department_name,location, manager_id) VALUES
('영업부','부산시 사상구','1'),
('생산부','부산시 해운대구','2'),
('인사부','부산시 진구','3');

INSERT INTO employee_position( description, position_name) VALUES
('Responsible for managing team and overseeing projects.', 'Manager'),
('Leads the development team and coordinates between departments.', 'Team Lead'),
('Develops and maintains software applications.', 'Senior Developer');

INSERT INTO employee_bank_account(account_number,bank_name) VALUES
('291-12-0239314','부산은행'),
('939302-00-747614','국민은행'),
('01025129361','기업은행');

INSERT INTO employee_job_title( title_name,description ) VALUES
('1','1'),
('2','2'),
('3','3');

INSERT INTO employee ( department_id, job_title_id, position_id, bankaccount_id, first_name, last_name, date_of_birth, phone_number, employment_status, employment_type, email, address, hire_date, is_household_head, profile_picture) VALUES
    ( 1, 1, 1, 1, '김', '민성', '2000-07-20', '010-2512-9361', 'ACTIVE', 'INTERN', 'ckacl2512@naver.com', '부산', '2024-08-20', TRUE, 'x'),
 ( 2, 2, 2, 2, '홍', '성화', '2000-07-21', '010-2512-9361', 'ACTIVE', 'INTERN', 'ckacl2512@naver.com', '부산', '2024-08-20', TRUE, 'x'),
 ( 3, 3, 3, 3, '허', '건호', '2000-07-22', '010-2512-9361', 'ACTIVE', 'INTERN', 'ckacl2512@naver.com', '부산', '2024-08-20', TRUE, 'x');

INSERT INTO employee_performance (evaluation_date, employee_id, evaluator_id,  comments,score) VALUES
('2023-06-15', 1, 1, 'Excellent performance, consistently meets expectations.', 'A'),
('2023-06-20', 2, 1, 'Needs improvement in meeting deadlines.', 'C'),
('2023-07-01', 3, 1, 'Outstanding problem-solving skills.', 'A+');



