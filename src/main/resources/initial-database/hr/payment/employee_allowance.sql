

INSERT INTO employee_allowance (code, name, description, type, tax_type, limit_amount) VALUES
('1', '기본급', '기본급 수당', 'POSITION_BASED_ALLOWANCE', 'TAXABLE', 0),
('2', '급호수당', '직책에 따른 급호수당', 'POSITION_BASED_ALLOWANCE', 'TAXABLE', 0),
('3', '연장수당', '연장근무 수당', 'POSITION_BASED_ALLOWANCE', 'TAXABLE', 0),
('4', '하계 휴가비', '하계 휴가비 수당', 'GENERAL_ALLOWANCE', 'TAXABLE', 0),
('5', '특별급여', '특별 급여 수당', 'GENERAL_ALLOWANCE', 'TAXABLE', 0),
('6', '연장근로수당', '연장근무에 대한 수당', 'GENERAL_ALLOWANCE', 'TAXABLE', 0),
('7', '자격수당', '자격증 소유에 따른 수당', 'GENERAL_ALLOWANCE', 'TAXABLE', 0),
('8', '직무발명보상금', '발명 관련 보상 수당', 'GENERAL_ALLOWANCE', 'TAXABLE', 0),
('9', '근속수당', '근속 기간에 따른 수당', 'GENERAL_ALLOWANCE', 'TAXABLE', 0),
('10', '가족수당', '가족 구성원에 따른 수당', 'GENERAL_ALLOWANCE', 'NON_TAXABLE', 200000),
('11', '육아수당', '육아 관련 수당', 'GENERAL_ALLOWANCE', 'NON_TAXABLE', 100000),
('12', '식대보조비', '식사 비용 보조', 'GENERAL_ALLOWANCE', 'NON_TAXABLE', 100000),
('13', '영업출장비', '영업 출장에 따른 보조', 'GENERAL_ALLOWANCE', 'NON_TAXABLE', 150000),
('14', '월차수당', '월차에 따른 수당', 'GENERAL_ALLOWANCE', 'TAXABLE', 0),
('15', '회차수당', '회차에 따른 수당', 'GENERAL_ALLOWANCE', 'TAXABLE', 0),
('16', '직책수당', '직책에 따른 수당', 'GENERAL_ALLOWANCE', 'TAXABLE', 0),
('17', '연차수당', '연차에 따른 수당', 'GENERAL_ALLOWANCE', 'TAXABLE', 0),
('18', '상여', '보너스 상여금', 'GENERAL_ALLOWANCE', 'TAXABLE', 0),
('19', '사회보험부담금', '사회 보험에 따른 부담금', 'GENERAL_ALLOWANCE', 'NON_TAXABLE', 0);