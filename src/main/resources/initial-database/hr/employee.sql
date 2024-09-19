INSERT INTO employee_department (department_code, department_name, location) VALUES
('0002','영업부','1층'),
('0003','생산부','2층'),
('0004','물류부','3층');

INSERT INTO employee_position (code,description, position_name) VALUES
('01','Responsible for managing team and overseeing projects.', '사원'),
('02','Leads the development team and coordinates between departments.', '주임'),
('03','Develops and maintains software applications.', '대리');

INSERT INTO employee_job_title (code,description, title_name) VALUES
('12','1','인사 부장'),
('13','2','총무과장'),
('14','3','마케팅 팀장');

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
    ('2000-10-14', '2024-12-13' , TRUE, 3, 2, 3, '울산', 'soojin.choi@example.com', '0006', '이주', '최', '010-2234-5678', 'img6.jpg', 'ACTIVE', 'INTERN');

INSERT INTO employee (date_of_birth, hire_date, is_household_head, department_id, job_title_id, position_id, address, email, employee_number, first_name, last_name, phone_number, profile_picture, employment_status, employment_type)
VALUES
    ('2000-10-16', '2024-12-10' , TRUE, 3, 2, 3, '거제', 'wkdgywjd77@naver.com', '0006', '정현', '박', '010-2234-5678', 'img7.jpg', 'ACTIVE', 'FULL_TIME');

INSERT INTO employee (date_of_birth, hire_date, is_household_head, department_id, job_title_id, position_id, address, email, employee_number, first_name, last_name, phone_number, profile_picture, employment_status, employment_type)
VALUES
    ('2000-12-25', '2024-12-15' , TRUE, 3, 2, 3, '서울', 'sojung.park@example.com', '0006', '소정', '박', '010-2234-5678', 'img8.jpg', 'ACTIVE', 'INTERN');

INSERT INTO employee_bank_account (employee_id,account_number, bank_name) VALUES
(1,'291-12-0239314','부산은행'),
(2,'939302-00-747614','국민은행'),
(3,'01025129361','우리은행'),
(4,'01029392102','하나은행'),
(5,'01093201932','국민은행'),
(6,'01029192011','기업은행');
INSERT INTO employee_performance (evaluation_date, employee_id, evaluator_id, comments, score) VALUES
('2023-06-15', 1, 1, 'Excellent performance, consistently meets expectations.', 'A'),
('2023-06-20', 2, 1, 'Needs improvement in meeting deadlines.', 'C'),
('2023-07-01', 3, 1, 'Outstanding problem-solving skills.', 'A+');

INSERT INTO employee_transfer (transfer_date, employee_id, from_department_id, to_department_id, reason, transfer_type ) VALUES
('2024-09-19', 1, 1, 2, '승진', 'promotion'),
('2024-10-01', 2, 1, 2, '부서 이동', 'lateral');



