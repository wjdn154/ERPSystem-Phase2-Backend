-- 은행 계좌 테이블
INSERT INTO financial_bank_account (
    account_type_id, bank_id, address_id, contact_id, code, name,
    account_number, bank_branch_name, account_opening_date, deposit_type,
    maturity_date, interest_rate, monthly_payment, overdraft_limit, business_account
) VALUES
      (1, 1, 1, 1, '001', '김철수', '110-456-123456', '신한은행 강남역지점', '2023-01-05', '정기예금', '2025-01-05', 1.8, 1500000, 3000000, true),
      (2, 2, 2, 2, '002', '이영희', '302-987-654321', '국민은행 서울역지점', '2023-02-10', '보통예금', NULL, 0.3, 0, 0, false),
      (3, 3, 3, 3, '003', '박민수', '333-765-432198', '우리은행 종로지점', '2023-03-15', '정기적금', '2026-03-15', 2.0, 2000000, 0, true),
      (4, 4, 4, 4, '004', '최지훈', '123-456-789012', '하나은행 강남구청지점', '2023-04-20', '자유적금', '2025-04-20', 1.6, 1000000, 0, false),
      (5, 5, 5, 5, '005', '송은지', '456-789-012345', 'IBK기업은행 여의도지점', '2023-05-25', '정기예금', '2024-05-25', 1.9, 2500000, 5000000, true),
      (6, 6, 6, 6, '006', '정해인', '789-012-345678', 'KDB산업은행 광화문지점', '2023-06-30', '보통예금', NULL, 0.4, 0, 2000000, false),
      (7, 7, 7, 7, '007', '한지민', '110-234-567890', '신한은행 압구정로데오지점', '2023-07-10', '정기적금', '2027-07-10', 2.2, 1800000, 0, true),
      (8, 8, 8, 8, '008', '장동건', '220-567-890123', '국민은행 강남구청역지점', '2023-08-15', '정기예금', '2028-08-15', 2.5, 3000000, 0, true),
      (9, 9, 9, 9, '009', '김혜수', '333-890-123456', '우리은행 사당역지점', '2023-09-05', '보통예금', NULL, 0.2, 0, 1000000, false),
      (10, 10, 10, 10, '010', '이정재', '444-012-345678', '하나은행 잠실역지점', '2023-10-20', '정기예금', '2026-10-20', 2.1, 2200000, 4000000, true);