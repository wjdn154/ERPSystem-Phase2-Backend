INSERT INTO employee_department (department_code, department_name, location) VALUES
('0001','재무부','1층'),
('0002','인사부','2층'),
('0003','생산부','3층'),
('0004','물류부','4층');

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
    ('1990-05-15', '2021-04-01', TRUE, 1, 1, 1, '서울', 'gj1563@naver.com', '20270910001', '건호', '허', '010-1234-5678', 'img1.jpg', 'ACTIVE', 'FULL_TIME'),
    ('1985-11-22', '2020-07-15', FALSE, 2, 2, 2, '부산', 'ckacl2512@naver.com', '20270910002', '민성', '김', '010-9876-5432', 'img2.jpg', 'ACTIVE', 'PART_TIME'),
    ('1995-03-10', '2022-03-20', TRUE, 1, 1, 1, '대구', 'hshdla@naver.com', '20270910003', '홍스', '홍', '010-2234-5678', 'img3.jpg', 'ACTIVE', 'INTERN'),
    ('1999-12-12', '2024-03-20', TRUE, 4, 1, 2, '부산', 'chlwlgur0407@naver.com', '20270910004', '지혁', '최', '010-2234-5678', 'img4.jpg', 'ACTIVE', 'INTERN'),
    ('2000-01-01', '2022-03-20', TRUE, 4, 3, 1, '제주', 'cksals@naver.com', '20270910005', '찬민', '김', '010-2234-5678', 'img5.jpg', 'ACTIVE', 'INTERN'),
    ('2000-10-16', '2024-12-13', TRUE, 2, 2, 3, '거제', 'labe1827@gmail.com', '20270910006', '태종', '하', '010-2234-5678', 'img6.jpg', 'ACTIVE', 'INTERN'),
    ('2000-10-16', '2024-12-10', TRUE, 3, 2, 3, '거제', 'wkdgywjd77@naver.com', '20270910007', '정현', '박', '010-2234-5678', 'img7.jpg', 'ACTIVE', 'FULL_TIME'),
    ('2000-12-25', '2024-12-15', TRUE, 3, 2, 3, '서울', 'readyoun@omz.com', '20270910008', '희연', '임', '010-2234-5678', 'img8.jpg', 'ACTIVE', 'INTERN');

INSERT INTO employee_bank_account (employee_id,account_number, bank_name) VALUES
(1,'291-12-0239314','부산은행'),
(2,'939302-00-747614','국민은행'),
(3,'01025129361','기업은행');

INSERT INTO employee_performance (evaluation_date, employee_id, evaluator_id, comments, score) VALUES
('2023-06-15', 1, 1, 'Excellent performance, consistently meets expectations.', 'A'),
('2023-06-20', 2, 1, 'Needs improvement in meeting deadlines.', 'C'),
('2023-07-01', 3, 1, 'Outstanding problem-solving skills.', 'A+');



