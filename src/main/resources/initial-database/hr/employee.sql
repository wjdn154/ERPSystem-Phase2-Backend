INSERT INTO employee_department (department_code, department_name, location) VALUES
('0001','인사부','1층'),
('0002','재무부','2층'),
('0003','생산부','3층'),
('0004','물류부','4층');

INSERT INTO employee_position (code,description, position_name) VALUES
('0001', '회사의 전체 전략과 방향을 설정하고 리더십을 제공함', '사장'),
('0002', '회사의 운영을 총괄하고 모든 부서의 효율성을 관리함', '부사장'),
('0003', '특정 부서를 관리하고 부서의 목표 달성을 책임짐', '이사'),
('0004', '팀의 일상적인 업무를 지휘하고 관리함', '부장'),
('0005', '부서 내 프로젝트 실행과 팀 성과를 관리함', '팀장'),
('0006', '소프트웨어 및 IT 시스템을 개발 및 유지하며, 회사의 목표에 맞게 조정함', '소프트웨어 엔지니어');

INSERT INTO employee_job_title (code,description, title_name) VALUES
('1001','회사의 전반적인 인사 정책을 총괄하며, 인사 계획을 수립하고 실행','인사부장'),
('1002','채용, 급여 관리, 직원 교육, 복리후생 등을 담당합니다.','HR 매니저'),
('2001','회사의 재무 전략을 수립하고, 예산, 투자, 자금 조달을 관리','재무부장'),
('2002','재무 보고서 작성, 회계 관리, 비용 분석, 세무 관리 등을 담당','회계팀장'),
('3001','생산 라인과 공정을 총괄하며, 품질과 생산성을 관리하는 역할','생산부장'),
('3002','생산 계획을 수립하고, 작업 현장에서 작업자들을 감독하여 생산 목표를 달성','생산팀장'),
('4001','물류 운영 전반을 관리하며, 물류 전략을 세우고 실행하는 역할','물류부장'),
('4002','물류 창고를 관리하고, 배송 일정 및 재고를 체계적으로 관리','물류팀장');

INSERT INTO employee (date_of_birth, hire_date, is_household_head, department_id, job_title_id, position_id, address, email, employee_number, first_name, last_name, phone_number, profile_picture, employment_status, employment_type)
VALUES
('2000-10-16', '2027-07-07', TRUE, 3, 2, 3, '거제', 'wkdgywjd77@naver.com', '270707001', '정현', '박', '010-2234-5678', 'img7.jpg', 'ACTIVE', 'FULL_TIME'),
('2000-12-25', '2028-08-08', TRUE, 3, 2, 3, '서울', 'readyoun@omz.com', '280808001', '희연', '임', '010-2234-5678', 'img8.jpg', 'ACTIVE', 'INTERN'),
('1990-05-15', '2021-01-01', TRUE, 1, 1, 1, '서울', 'gj1563@naver.com', '210101001', '건호', '허', '010-1234-5678', 'img1.jpg', 'ACTIVE', 'FULL_TIME'),
('1985-11-22', '2022-02-02', FALSE, 2, 2, 2, '부산', 'ckacl2512@naver.com', '220202001', '민성', '김', '010-9876-5432', 'img2.jpg', 'ACTIVE', 'PART_TIME'),
('1995-03-10', '2023-03-03', TRUE, 1, 1, 1, '대구', 'hshdla@naver.com', '230303001', '홍스', '홍', '010-2234-5678', 'img3.jpg', 'ACTIVE', 'INTERN'),
('1999-12-12', '2024-04-04', TRUE, 4, 1, 2, '부산', 'chlwlgur0407@naver.com', '240404001', '지혁', '최', '010-2234-5678', 'img4.jpg', 'ACTIVE', 'INTERN'),
('2000-01-01', '2025-05-05', TRUE, 4, 3, 1, '제주', 'cksals@naver.com', '250505001', '찬민', '김', '010-2234-5678', 'img5.jpg', 'ACTIVE', 'INTERN'),
('2000-10-16', '2026-06-06', TRUE, 2, 2, 3, '거제', 'labe1827@gmail.com', '260606001', '태종', '하', '010-2234-5678', 'img6.jpg', 'ACTIVE', 'INTERN');



INSERT INTO employee_performance (evaluation_date, employee_id, title, evaluator_id, comments, score) VALUES
('2021-06-15', 1, '2021년 성과평가', 1, '탁월한 성과를 내며, 기대치를 꾸준히 충족했습니다.', 'A'),
('2022-06-20', 2, '2022년 성과평가', 1, '기한 준수가 필요합니다.', 'C'),
('2023-07-01', 3, '2023년 성과평가', 1, '문제 해결 능력이 뛰어납니다.', 'A+'),
('2023-06-30', 4, '2023년 성과평가', 2, '팀워크가 우수하며, 업무를 효율적으로 처리합니다.', 'B+'),
('2023-07-05', 5, '2023년 성과평가', 2, '창의적인 사고로 프로젝트에 새로운 아이디어를 제공했습니다.', 'A');


INSERT INTO employee_transfer (reason, transfer_date, transfer_type, employee_id, from_department_id, to_department_id) VALUES
('승진으로 인한 부서 이동','2021-09-09','승진',1,1,2),
('부진으로 인한 부서 이동','2022-11-11','강등',2,2,1),
('휴직으로 인한 부서 이동','2023-12-12','휴식',3,3,1),
('적성에 맞는 부서로 이동','2024-04-04','적성',4,3,2);




