INSERT INTO employee_department (department_code, department_name, location) VALUES
('0001','인사부','1층'),
('0002','재무부','2층'),
('0003','생산부','3층'),
('0004','물류부','4층');

INSERT INTO employee_position (position_Code, position_name) VALUES
('100', '사장'),
('200', '부사장'),
('300', '이사'),
('400', '부장'),
('500', '차장'),
('600', '과장'),
('700', '대리'),
('800', '주임'),
('900','사원'),
('1000', '상무'),
('1100', '전무');

INSERT INTO employee_job_title (job_title_Code,description, job_title_name) VALUES
('1001','회사의 전반적인 인사 정책을 총괄하며, 인사 계획을 수립하고 실행','인사부장'),
('1002','채용, 급여 관리, 직원 교육, 복리후생 등을 담당합니다.','HR 매니저'),
('2001','회사의 재무 전략을 수립하고, 예산, 투자, 자금 조달을 관리','재무부장'),
('2002','재무 보고서 작성, 회계 관리, 비용 분석, 세무 관리 등을 담당','회계팀장'),
('3001','생산 라인과 공정을 총괄하며, 품질과 생산성을 관리하는 역할','생산부장'),
('3002','생산 계획을 수립하고, 작업 현장에서 작업자들을 감독하여 생산 목표를 달성','생산팀장'),
('4001','물류 운영 전반을 관리하며, 물류 전략을 세우고 실행하는 역할','물류부장'),
('4002','물류 창고를 관리하고, 배송 일정 및 재고를 체계적으로 관리','물류팀장'),
('4003','구매 전략을 수립하고, 공급업체와의 계약 및 구매 활동을 총괄 관리','구매부장'),
('4004','영업 전략을 수립하고, 매출 목표 달성을 위한 영업 활동을 총괄','영업부장');

INSERT INTO employee_bank_account (bank_id,account_number) VALUES
(1, '123-456-789012'),
(2, '234-567-890123'),
(3, '345-678-901234'),
(4, '456-789-012345'),
(5, '567-890-123456'),
(6, '678-901-234567'),
(7, '789-012-345678'),
(8, '890-123-456789'),
(9, '901-234-567890'),
(10, '012-345-678901'),
(11, '123-987-654321'),
(12, '234-876-543210'),
(13, '345-765-432109'),
(14, '456-654-321098'),
(15, '567-543-210987'),
(16, '678-432-109876'),
(17, '789-321-098765'),
(18, '890-210-987654'),
(19, '901-109-876543'),
(20, '012-098-765432'),
(21, '111-222-333444'),
(22, '222-333-444555'),
(23, '333-444-555666'),
(24, '444-555-666777'),
(25, '555-666-777888'),
(26, '666-777-888999'),
(27, '777-888-999000'),
(28, '888-999-000111'),
(29, '999-000-111222'),
(30, '000-111-222333'),
(31, '111-222-333444'),
(32, '222-333-444555'),
(33, '333-444-555666'),
(34, '444-555-666777'),
(35, '555-666-777888'),
(36, '666-777-888999');

INSERT INTO employee (registration_Number, hire_date,is_household_head, department_id, job_title_id, position_id, bank_account_id ,address, email, employee_number, first_name, last_name, phone_number, profile_picture, employment_status, employment_type)
VALUES
('900101-1234567', '2022-01-01' ,TRUE, 3, 2, 3, 1,'거제', 'wkdgywjd77@naver.com', '220101001', '정현', '박', '010-2234-5678', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/f0abd1ee-bc28-41a4-bc50-6d3ee3d5d820_박정현.png', 'RESIGNED', 'FREELANCE'),
('880215-2345678', '2022-02-01',TRUE, 3, 2, 3, 2,'서울', 'readyoun@omz.com', '220201001', '희연', '임', '010-2234-5678', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/a027509b-d595-4c67-baeb-cf2e8e161e39_임희연.png', 'ON_LEAVE', 'PART_TIME'),
('950505-3456789', '2022-03-01' ,TRUE, 2, 1, 1, 3,'경기', 'gj1563@naver.com', '220301001', '건호', '허', '010-1234-5678', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/b0c8319c-bf31-4fe5-8a37-cdb19c3bcef1_허건호.png', 'ACTIVE', 'FULL_TIME'),
('780630-4567890', '2022-04-01',FALSE, 1, 2, 2, 4,'부산', 'ckacl2512@naver.com', '220401001', '민성', '김', '010-9876-5432', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/75eac5d4-0bac-4988-925d-b75dfc46804e_김민성.png', 'ACTIVE', 'CONTRACT'),
('820312-5678901', '2022-05-01' ,TRUE, 2, 1, 1, 5,'대구', 'hshdla@naver.com', '220501001', '홍스', '홍', '010-2234-5678', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/a3236e77-e6cf-404e-aafd-80c32c71f48d_홍성화.png', 'ON_LEAVE', 'PART_TIME'),
('860910-6789012', '2022-06-01' ,TRUE, 4, 1, 2, 6,'울산', 'chlwlgur0407@naver.com', '220601001', '지혁', '최', '010-2234-5678', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/206e6825-a455-432b-8a35-df175a8fc741_최지혁.png', 'ACTIVE', 'TEMPORARY'),
('921224-7890123', '2022-07-01' ,TRUE, 4, 3, 1, 7,'제주', 'cksals@naver.com', '220701001', '찬민', '김', '010-2234-5678', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/f60821ee-b5bc-4e9b-bbc5-a12f4213c838_김찬민.png', 'ACTIVE', 'INTERN'),
('750808-8901234', '2022-08-01' ,TRUE, 1, 2, 3, 8,'진주', 'labe1827@gmail.com', '220801001', '주빈', '이', '010-2234-5678', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/d9b0a779-e657-497a-9900-295ce57de739_이주빈.png', 'RESIGNED', 'CASUAL'),
('981105-9012345', '2022-09-01', TRUE, 1, 3, 1, 9, '창원', 'scarlett.jo@gmail.com', '220901001', '예슬', '한', '010-9012-3456', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/cf7ae009-c7cf-41d2-ad0e-9fad9f098507_한예슬.png', 'ACTIVE', 'FREELANCE'),
('930402-0123456', '2022-10-01', TRUE, 2, 1, 2, 10, '포항', 'matt.damon@gmail.com', '221001001', '지헌', '백', '010-0123-4567', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/b40122b2-af55-46fa-8dc8-d0c476a30537_백지헌.png', 'ON_LEAVE', 'CONTRACT'),
('970707-1234567', '2022-11-01', TRUE, 3, 2, 3, 11, '청주', 'tom.cruise@gmail.com', '221101001', '은우', '차', '010-1234-5678', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/1153733f-75f3-4bbe-a81b-8558641e864b_차은우.png', 'ACTIVE', 'PART_TIME'),
('740930-2345678', '2022-12-01', TRUE, 4, 3, 1, 12, '전주', 'chris.evans@gmail.com', '221201001', '지민', '유', '010-2345-6789', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/f4d45c6b-6367-4f0b-a313-60d7e66b9d25_유지민.png', 'RESIGNED', 'INTERN'),
('810418-4567890', '2023-01-01', TRUE, 1, 1, 1, 13, '서울', 'john.doe2@gmail.com', '230101001', '민정', '김', '010-3456-7890', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/e358e7eb-84ac-43af-a613-c621673c5eed_김민정.png', 'ACTIVE', 'FULL_TIME'),
('850727-5678901', '2023-02-01', TRUE, 2, 2, 2, 14, '부산', 'jane.doe2@gmail.com', '230201001', '제훈', '이', '010-4567-8901', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/36454b43-1e27-48c3-a846-095164be9223_이제훈.png', 'ON_LEAVE', 'PART_TIME'),
('780909-6789012', '2023-03-01', TRUE, 3, 3, 3, 15, '대전', 'james.bond2@gmail.com', '230301001', '채원', '문', '010-5678-9012', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/91b93ca9-680a-40e0-88ae-d08ffc17326c_문채원.png', 'ACTIVE', 'CONTRACT'),
('760202-7890123', '2023-04-01', TRUE, 4, 1, 2, 16, '광주', 'lisa.kim2@gmail.com', '230401001', '혜선', '구', '010-6789-0123', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/361ebbda-1096-42f2-9f89-4f257890e93b_구혜선.png', 'RESIGNED', 'FREELANCE'),
('941020-8901234', '2023-05-01', TRUE, 1, 2, 3, 17, '대구', 'charlie.brown2@gmail.com', '230501001', '동석', '마', '010-7890-1234', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/b8cf457d-782a-41e9-98c6-dd485a6fe46f_마동석.png', 'ON_LEAVE', 'TEMPORARY'),
('911106-9012345', '2023-06-01', TRUE, 2, 3, 1, 18, '인천', 'emma.stone2@gmail.com', '230601001', '진희', '지', '010-8901-2345', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/49c75cd6-590d-4dee-8c81-2e4a8da72e95_지진희.png', 'ACTIVE', 'FULL_TIME'),
('720415-0123456', '2023-07-01', TRUE, 3, 1, 2, 19, '울산', 'john.smith2@gmail.com', '230701001', '종석', '이', '010-9012-3456', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/757adb20-8188-4427-9ae6-a4b25abf44e8_이종석.png', 'RESIGNED', 'INTERN'),
('790123-1234567', '2023-08-01', TRUE, 4, 2, 3, 20, '수원', 'will.smith2@gmail.com', '230801001', '인나', '유', '010-0123-4567', 'https://rjsgh-bucket.s3.ap-northeast-2.amazonaws.com/02566dab-4e5e-4f08-8a5b-00e2899e8f18_유인나.png', 'ON_LEAVE', 'CASUAL'),
('861224-2345678', '2023-09-01', TRUE, 1, 3, 1, 21, '창원', 'scarlett.jo2@gmail.com', '230901001', '영준', '장', '010-1234-5678', 'img21.jpg', 'ACTIVE', 'FREELANCE'),
('990909-3456789', '2023-10-01', TRUE, 2, 1, 2, 22, '포항', 'matt.damon2@gmail.com', '231001001', '은비', '박', '010-2345-6789', 'img22.jpg', 'ON_LEAVE', 'CONTRACT'),
('830525-4567890', '2023-11-01', TRUE, 3, 2, 3, 23, '청주', 'tom.cruise2@gmail.com', '231101001', '서준', '한', '010-3456-7890', 'img23.jpg', 'ACTIVE', 'PART_TIME'),
('770612-5678901', '2023-12-01', TRUE, 4, 3, 1, 24, '전주', 'chris.evans2@gmail.com', '231201001', '진서', '강', '010-4567-8901', 'img24.jpg', 'RESIGNED', 'INTERN'),
('880910-7890123', '2024-01-01', TRUE, 1, 1, 1, 25, '서울', 'john.doe3@gmail.com', '240101001', '태민', '이', '010-5678-9012', 'img25.jpg', 'ACTIVE', 'FULL_TIME'),
('940414-8901234', '2024-02-01', TRUE, 2, 2, 2, 26, '부산', 'jane.doe3@gmail.com', '240201001', '도윤', '김', '010-6789-0123', 'img26.jpg', 'ON_LEAVE', 'PART_TIME'),
('800223-9012345', '2024-03-01', TRUE, 3, 3, 3, 27, '대전', 'james.bond3@gmail.com', '240301001', '수빈', '최', '010-7890-1234', 'img27.jpg', 'ACTIVE', 'CONTRACT'),
('960517-0123456', '2024-04-01', TRUE, 4, 1, 2, 28, '광주', 'lisa.kim3@gmail.com', '240401001', '민지', '박', '010-8901-2345', 'img28.jpg', 'RESIGNED', 'FREELANCE'),
('950404-1234567', '2024-05-01', TRUE, 1, 2, 3, 29, '대구', 'charlie.brown3@gmail.com', '240501001', '서현', '한', '010-9012-3456', 'img29.jpg', 'ON_LEAVE', 'TEMPORARY'),
('730817-2345678', '2024-06-01', TRUE, 2, 3, 1, 30, '인천', 'emma.stone3@gmail.com', '240601001', '민우', '이', '010-0123-4567', 'img30.jpg', 'ACTIVE', 'FULL_TIME'),
('870908-3456789', '2024-07-01', TRUE, 3, 1, 2, 31, '울산', 'john.smith3@gmail.com', '240701001', '지영', '장', '010-1234-5678', 'img31.jpg', 'RESIGNED', 'INTERN'),
('910606-4567890', '2024-08-01', TRUE, 4, 2, 3, 32, '수원', 'will.smith3@gmail.com', '240801001', '정우', '박', '010-2345-6789', 'img32.jpg', 'ON_LEAVE', 'CASUAL'),
('930323-5678901', '2024-09-01', TRUE, 1, 3, 1, 33, '창원', 'scarlett.jo3@gmail.com', '240901001', '서연', '강', '010-3456-7890', 'img33.jpg', 'ACTIVE', 'FREELANCE'),
('790101-6789012', '2024-10-01', TRUE, 2, 1, 2, 34, '포항', 'matt.damon3@gmail.com', '241001001', '도영', '김', '010-4567-8901', 'img34.jpg', 'ON_LEAVE', 'CONTRACT'),
('890213-3456789', '2024-11-01', TRUE, 3, 2, 3, 35, '청주', 'tom.cruise3@gmail.com', '241101001', '태리', '한', '010-5678-9012', 'img35.jpg', 'ACTIVE', 'PART_TIME'),
('751030-6789012', '2024-12-01', TRUE, 4, 3, 1, 36, '전주', 'chris.evans3@gmail.com', '241201001', '영훈', '이', '010-6789-0123', 'img36.jpg', 'RESIGNED', 'INTERN');


INSERT INTO employee_performance (evaluation_date, employee_id, title, evaluator_id, comments, score) VALUES
('2021-06-15', 1, '2021년 성과평가', 2, '탁월한 성과를 내며, 기대치를 꾸준히 충족했습니다.', 'A'),
('2022-06-20', 2, '2022년 성과평가', 6, '기한 준수가 필요합니다.', 'C'),
('2023-07-01', 3, '2023년 성과평가', 7, '문제 해결 능력이 뛰어납니다.', 'B'),
('2023-06-30', 4, '2023년 성과평가', 8, '팀워크가 우수하며, 업무를 효율적으로 처리합니다.', 'B+'),
('2023-07-05', 5, '2023년 성과평가', 9, '창의적인 사고로 프로젝트에 새로운 아이디어를 제공했습니다.', 'D'),
('2021-07-10', 6, '2021년 성과평가', 2, '업무의 정확도가 높습니다.', 'B'),
('2022-08-15', 7, '2022년 성과평가', 3, '기한 준수가 필요하며 개선이 필요합니다.', 'C+'),
('2023-06-25', 8, '2023년 성과평가', 4, '자발적인 학습 의지가 돋보입니다.', 'A-'),
('2022-05-15', 9, '2022년 성과평가', 5, '책임감이 높으며, 독립적으로 업무를 수행합니다.', 'A'),
('2023-06-28', 10, '2023년 성과평가', 6, '피드백을 적극 수용하며 빠르게 성장합니다.', 'B+'),
('2021-06-30', 11, '2021년 성과평가', 7, '커뮤니케이션 능력이 뛰어나며, 팀 내 협력이 좋습니다.', 'A-'),
('2022-07-05', 12, '2022년 성과평가', 8, '세부 사항에 대한 주의가 필요합니다.', 'C'),
('2023-06-29', 13, '2023년 성과평가', 9, '도전적인 업무를 잘 수행하며, 업무 성과가 뛰어납니다.', 'A'),
('2022-08-01', 14, '2022년 성과평가', 10, '일관된 성과를 유지하고 있습니다.', 'B+'),
('2023-07-03', 15, '2023년 성과평가', 11, '문서화 및 기록 관리가 필요합니다.', 'D');


INSERT INTO employee_transfer (employee_id, from_department_id, to_department_id, transfer_date, transfer_type, reason) VALUES
(1, 1, 2, '2021-09-01', 'PROMOTION', '팀 리더로 승진하여 새로운 부서로 이동'),
(2, 2, 3, '2022-03-15', 'DEMOTION', '업무 성과 저조로 강등 조치'),
(3, 3, 4, '2023-01-05', 'LATERAL', '해외 프로젝트로 인한 전보'),
(4, 4, 1, '2023-07-10', 'TEMPORARY_ASSIGNMENT', '단기 출장으로 임시 배치'),
(5, 2, 3, '2024-02-20', 'RELOCATION', '부서 이전으로 인한 이동'),
(6, 3, 2, '2024-05-25', 'RETURN', '출산 휴가 복귀 후 원부서로 복귀');
