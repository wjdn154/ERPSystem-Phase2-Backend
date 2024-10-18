-- 계정과목 체계 테이블
INSERT INTO account_subject_structure (code, large_category, medium_category, small_category, min, max,financial_statement_type) VALUES
('1', '자산 (Assets)', '유동자산', '당좌자산', 100, 200,'STANDARD_FINANCIAL_STATEMENT'),
('2', '자산 (Assets)', '유동자산', '재고자산', 200, 300,'STANDARD_FINANCIAL_STATEMENT'),
('3', '자산 (Assets)', '비유동자산', '투자자산', 300, 400,'STANDARD_FINANCIAL_STATEMENT'),
('4', '자산 (Assets)', '비유동자산', '유형자산', 400, 500,'STANDARD_FINANCIAL_STATEMENT'),
('5', '자산 (Assets)', '비유동자산', '무형자산', 500, 600,'STANDARD_FINANCIAL_STATEMENT'),
('6', '자산 (Assets)', '비유동자산', '기타비유동자산', 600, 700,'STANDARD_FINANCIAL_STATEMENT'),
('7', '부채 (Liabilities)', '유동부채', '유동부채', 700, 800,'STANDARD_FINANCIAL_STATEMENT'),
('8', '부채 (Liabilities)', '비유동부채', '비유동부채', 800, 900,'STANDARD_FINANCIAL_STATEMENT'),
('9', '자본 (Equity)', '자본금', '자본금', 900, 1000,'STANDARD_FINANCIAL_STATEMENT'),
('10', '자본 (Equity)', '자본잉여금', '자본잉여금', 1000, 1100,'NONE'),
('11', '자본 (Equity)', '자본조정', '자본조정', 1100, 1200,'NONE'),
('12', '자본 (Equity)', '기타포괄손익', '기타포괄손익', 1200, 1300,'NONE'),
('13', '자본 (Equity)',    '이익잉여금', '이익잉여금', 1300, 1400,'NONE'),
('14', '수익 (Revenue)', '매출', '매출', 1400, 1500,'NONE'),
('15', '비용 (Expenses)', '매출원가', '매출원가', 1500, 1600,'NONE'),
('16', '비용 (Expenses)', '제조원가', '제조원가', 1600, 1700,'NONE'),
('17', '비용 (Expenses)', '도급원가', '도급원가', 1700, 1800,'NONE'),
('18', '비용 (Expenses)', '보관원가', '보관원가', 1800, 1900,'NONE'),
('19', '비용 (Expenses)', '분양원가', '분양원가', 1900, 2000,'NONE'),
('20', '비용 (Expenses)', '운송원가', '운송원가', 2000, 2100,'NONE'),
('21', '비용 (Expenses)', '판매관리비', '판매관리비', 2100, 2200,'NONE'),
('22', '수익 (Revenue)', '영업외수익', '영업외수익', 2200, 2300,'NONE'),
('23', '비용 (Expenses)', '영업외비용', '영업외비용', 2300, 2400,'NONE'),
('24', '비용 (Expenses)', '법인세 (소득세)', '법인세', 2400, 2500,'NONE'),
('25', '기타 (Special)', '특수계정', '특수계정과목', 2500, 2600,'NONE'),
('26', '손익 (Profit and Loss)', '손익계정', '손익', 2600, 2700,'NONE');