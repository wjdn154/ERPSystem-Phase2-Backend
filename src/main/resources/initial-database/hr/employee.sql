INSERT INTO employee_department (department_code, department_name, location) VALUES
('0002','영업부','1층'),
('0003','생산부','2층'),
('0004','물류부','3층');

INSERT INTO employee_position (description, position_name) VALUES
('Responsible for managing team and overseeing projects.', 'Manager'),
('Leads the development team and coordinates between departments.', 'Team Lead'),
('Develops and maintains software applications.', 'Senior Developer');

INSERT INTO employee_job_title (description, title_name) VALUES
('1','강사 개발자'),
('2','백엔드 개발자'),
('3','프론트 개발자');

-- INSERT INTO employee (date_of_birth, hire_date, is_household_head, department_id, job_title_id, position_id, address, email, employee_number, first_name, last_name, phone_number, profile_picture, employment_status, employment_type) VALUES
-- ('2000-07-20','2024-08-20',TRUE,1, 1,  1,'부산','ckacl2512@naver.com','0001','김', '민성','010-2512-9361','x','ACTIVE', 'INTERN'),
-- ('2000-07-20','2024-08-20',TRUE,2, 2,  2,'부산','ckacl2512@naver.com','0002','김', '민성','010-2512-9361','x','ACTIVE', 'INTERN'),
-- ('2000-07-20','2024-08-20',TRUE,3, 3,  3,'부산','ckacl2512@naver.com','0003','김', '민성','010-2512-9361','x','ACTIVE', 'INTERN');

INSERT INTO employee (date_of_birth, hire_date, is_household_head, department_id, job_title_id, position_id, address, email, employee_number, first_name, last_name, phone_number, profile_picture, employment_status, employment_type)
VALUES
    ('1990-05-15', '2021-04-01', TRUE, 1, 1, 1, '서울', 'jiho.kim@example.com', '0001', '지호', '김', '010-1234-5678', 'img1.jpg', 'ACTIVE', 'FULL_TIME');

INSERT INTO employee (date_of_birth, hire_date, is_household_head, department_id, job_title_id, position_id, address, email, employee_number, first_name, last_name, phone_number, profile_picture, employment_status, employment_type)
VALUES
    ('1985-11-22', '2020-07-15', FALSE, 2, 2, 2, '부산', 'sungmin.lee@example.com', '0002', '성민', '이', '010-9876-5432', 'img2.jpg', 'ACTIVE', 'PART_TIME');

INSERT INTO employee (date_of_birth, hire_date, is_household_head, department_id, job_title_id, position_id, address, email, employee_number, first_name, last_name, phone_number, profile_picture, employment_status, employment_type)
VALUES
    ('1995-03-10', '2022-03-20', TRUE, 2, 1, 1, '대구', 'soojin.choi@example.com', '0003', '수진', '최', '010-2234-5678', 'img3.jpg', 'ACTIVE', 'INTERN');

INSERT INTO employee (date_of_birth, hire_date, is_household_head, department_id, job_title_id, position_id, address, email, employee_number, first_name, last_name, phone_number, profile_picture, employment_status, employment_type)
VALUES
    ('1999-12-12', '2024-03-20', TRUE, 2, 1, 2, '부산', 'soojin.choi@example.com', '0004', '지혁', '최', '010-2234-5678', 'img4.jpg', 'ACTIVE', 'INTERN');

INSERT INTO employee (date_of_birth, hire_date, is_household_head, department_id, job_title_id, position_id, address, email, employee_number, first_name, last_name, phone_number, profile_picture, employment_status, employment_type)
VALUES
    ('2000-01-01', '2022-03-20', TRUE, 2, 3, 1, '제주', 'soojin.choi@example.com', '0005', '민성', '김', '010-2234-5678', 'img5.jpg', 'ACTIVE', 'INTERN');

INSERT INTO employee (date_of_birth, hire_date, is_household_head, department_id, job_title_id, position_id, address, email, employee_number, first_name, last_name, phone_number, profile_picture, employment_status, employment_type)
VALUES
    ('2000-10-16', '2024-12-13' , TRUE, 3, 2, 3, '거제', 'soojin.choi@example.com', '0006', '정현', '박', '010-2234-5678', 'img6.jpg', 'ACTIVE', 'INTERN');

INSERT INTO employee_bank_account (employee_id,account_number, bank_name) VALUES
(1,'291-12-0239314','부산은행'),
(2,'939302-00-747614','국민은행'),
(3,'01025129361','기업은행');

INSERT INTO employee_performance (evaluation_date, employee_id, evaluator_id, comments, score) VALUES
('2023-06-15', 1, 1, 'Excellent performance, consistently meets expectations.', 'A'),
('2023-06-20', 2, 1, 'Needs improvement in meeting deadlines.', 'C'),
('2023-07-01', 3, 1, 'Outstanding problem-solving skills.', 'A+');



