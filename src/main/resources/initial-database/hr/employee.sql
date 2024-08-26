INSERT INTO employee_department (department_code, department_name, location, manger_id) VALUES
('0002','영업부','1층','1'),
('0003','생산부','2층','2'),
('0004','물류부','3층','3');

INSERT INTO employee_position (description, position_name) VALUES
('Responsible for managing team and overseeing projects.', 'Manager'),
('Leads the development team and coordinates between departments.', 'Team Lead'),
('Develops and maintains software applications.', 'Senior Developer');


INSERT INTO employee_bank_account (account_number, bank_name) VALUES
('291-12-0239314','부산은행'),
('939302-00-747614','국민은행'),
('01025129361','기업은행');

INSERT INTO employee_job_title (description, title_name) VALUES
('1','1'),
('2','2'),
('3','3');

INSERT INTO employee (date_of_birth, hire_date, is_household_head, bankaccount_id, department_id, job_title_id, position_id, address, email, employee_number, first_name, last_name, phone_number, profile_picture, employment_status, employment_type) VALUES
('2000-07-20','2024-08-20',TRUE,1, 1, 1, 1,'부산','ckacl2512@naver.com','0001','김', '민성','010-2512-9361','x','ACTIVE', 'INTERN'),
('2000-07-20','2024-08-20',TRUE,2, 2, 2, 2,'부산','ckacl2512@naver.com','0002','김', '민성','010-2512-9361','x','ACTIVE', 'INTERN'),
('2000-07-20','2024-08-20',TRUE,3, 3, 3, 3,'부산','ckacl2512@naver.com','0003','김', '민성','010-2512-9361','x','ACTIVE', 'INTERN');

INSERT INTO employee_performance (evaluation_date, employee_id, evaluator_id, comments, score) VALUES
('2023-06-15', 1, 1, 'Excellent performance, consistently meets expectations.', 'A'),
('2023-06-20', 2, 1, 'Needs improvement in meeting deadlines.', 'C'),
('2023-07-01', 3, 1, 'Outstanding problem-solving skills.', 'A+');



