-- 계정과목 체계 테이블
INSERT INTO account_subject_structure (code, name, min, max) VALUES
('1', '당좌자산', 100, 200),
('2', '재고자산', 200, 300),
('3', '투자자산', 300, 400),
('4', '유형자산', 400, 500),
('5', '무형자산', 500, 600),
('6', '기타비유동자산', 600, 700),
('7', '유동부채', 700, 800),
('8', '비유동부채', 800, 900),
('9', '자본금', 900, 1000),
('10', '자본잉여금', 1000, 1100),
('11', '자본조정', 1100, 1200),
('12', '기타포괄손익', 1200, 1300),
('13', '이익잉여금', 1300, 1400),
('14', '매출', 1400, 1500),
('15', '매출원가', 1500, 1600),
('16', '제조원가', 1600, 1700),
('17', '도급원가', 1700, 1800),
('18', '보관원가', 1800, 1900),
('19', '분양원가', 1900, 2000),
('20', '운송원가', 2000, 2100),
('21', '판매관리비', 2100, 2200),
('22', '영업외수익', 2200, 2300),
('23', '영업외비용', 2300, 2400),
('24', '법인(소득)', 2400, 2500),
('25', '특수계정과목', 2500, 2600),
('26', '손익', 2600, 2700);