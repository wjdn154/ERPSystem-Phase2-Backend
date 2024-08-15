-- 계정과목 성격 테이블
INSERT INTO account_subject_nature (code, name, structure_id) VALUES
('1', '예금', 1),
('2', '적금', 1),
('3', '일반', 1),
('4', '차감', 1),
('5', '유가증권', 1),
('6', '', 1),
('7', '가지급금', 1),
('8', '받을어음', 1),
('9', '대여금', 1),
('1', '일반재고', 2),
('2', '공정재고', 2),
('3', '환출차감', 2),
('4', '할인차감', 2),
('5', '관세차감', 2),
('6', '평가충당금', 2),
('7', '', 2),
('8', '', 2),
('9', '', 2),
('1', '예금', 3),
('2', '적금', 3),
('3', '일반', 3),
('4', '차감', 3),
('5', '유가증권', 3),
('6', '', 3),
('7', '가지급금', 3),
('8', '받을어음', 3),
('9', '대여금', 3),
('1', '상각', 4),
('2', '비상각', 4),
('3', '임시', 4),
('4', '차감', 4),
('5', '', 4),
('6', '', 4),
('7', '', 4),
('8', '', 4),
('9', '', 4),
('1', '일반', 5),
('2', '차감', 5),
('3', '', 5),
('4', '', 5),
('5', '', 5),
('6', '', 5),
('7', '', 5),
('8', '', 5),
('9', '', 5),
('1', '예금', 6),
('2', '적금', 6),
('3', '일반', 6),
('4', '차감', 6),
('5', '유가증권', 6),
('6', '', 6),
('7', '가지급금', 6),
('8', '받을어음', 6),
('9', '대여금', 6),
('1', '차입금', 7),
('2', '일반', 7),
('3', '차감', 7),
('4', '', 7),
('5', '가수금', 7),
('6', '지급어음', 7),
('7', '증가', 7),
('8', '', 7),
('9', '', 7),
('1', '준비금', 8),
('2', '차입금', 8),
('3', '일반', 8),
('4', '차감', 8),
('5', '충당금', 8),
('6', '사채차입금', 8),
('7', '증가', 8),
('8', '지급어음', 8),
('9', '가수금', 8),
('1', '자본금', 9),
('2', '', 9),
('3', '', 9),
('4', '', 9),
('5', '', 9),
('6', '', 9),
('7', '', 9),
('8', '', 9),
('9', '', 9),
('1', '자본잉여금', 10),
('2', '', 10),
('3', '', 10),
('4', '', 10),
('5', '', 10),
('6', '', 10),
('7', '', 10),
('8', '', 10),
('9', '', 10),
('1', '', 11),
('2', '', 11),
('3', '증가', 11),
('4', '차감', 11),
('5', '', 11),
('6', '', 11),
('7', '', 11),
('8', '', 11),
('9', '', 11),
('1', '', 12),
('2', '', 12),
('3', '평가이익', 12),
('4', '평가손실', 12),
('5', '', 12),
('6', '', 12),
('7', '', 12),
('8', '', 12),
('9', '', 12),
('1', '법정적립금', 13),
('2', '임의적립금', 13),
('3', '미처분이익', 13),
('4', '차기이월', 13),
('5', '', 13),
('6', '', 13),
('7', '', 13),
('8', '', 13),
('9', '', 13),
('1', '매출', 14),
('2', '환입차감', 14),
('3', '할인차감', 14),
('4', '', 14),
('5', '', 14),
('6', '', 14),
('7', '', 14),
('8', '', 14),
('9', '', 14),
('1', '용역', 15),
('2', '제조판매', 15),
('3', '매입판매', 15),
('4', '분양판매', 15),
('5', '', 15),
('6', '', 15),
('7', '', 15),
('8', '', 15),
('9', '', 15),
('1', '원재료비', 16),
('2', '부재료비', 16),
('3', '노무비(근로)', 16),
('4', '노무비(퇴직)', 16),
('5', '제조경비', 16),
('6', '', 16),
('7', '노무비(일용)', 16),
('8', '차감', 16),
('9', '', 16),
('1', '원재료비', 17),
('2', '부재료비', 17),
('3', '노무비(근로)', 17),
('4', '노무비(퇴직)', 17),
('5', '도급경비', 17),
('6', '항목(집계)', 17),
('7', '노무비(일용)', 17),
('8', '차감', 17),
('9', '', 17),
('1', '원재료비', 18),
('2', '부재료비', 18),
('3', '노무비(근로)', 18),
('4', '노무비(퇴직)', 18),
('5', '보관경비', 18),
('6', '', 18),
('7', '노무비(일용)', 18),
('8', '차감', 18),
('9', '', 18),
('1', '원재료비', 19),
('2', '부재료비', 19),
('3', '노무비(근로)', 19),
('4', '노무비(퇴직)', 19),
('5', '분양경비', 19),
('6', '', 19),
('7', '노무비(일용)', 19),
('8', '차감', 19),
('9', '', 19),
('1', '원재료비', 20),
('2', '부재료비', 20),
('3', '노무비(근로)', 20),
('4', '노무비(퇴직)', 20),
('5', '운송경비', 20),
('6', '', 20),
('7', '노무비(일용)', 20),
('8', '차감', 20),
('9', '', 20),
('1', '인건비(근로)', 21),
('2', '인건비(퇴직)', 21),
('3', '경비', 21),
('4', '기타', 21),
('5', '차감', 21),
('6', '', 21),
('7', '노무비(일용)', 21),
('8', '', 21),
('9', '', 21),
('1', '수입이자', 22),
('2', '일반', 22),
('3', '평가환입', 22),
('4', '준비금환입', 22),
('5', '차감', 22),
('6', '', 22),
('7', '', 22),
('8', '', 22),
('9', '', 22),
('1', '지급이자', 23),
('2', '일반', 23),
('3', '상각', 23),
('4', '평가손실', 23),
('5', '준비금전입', 23),
('6', '특별상각', 23),
('7', '', 23),
('8', '', 23),
('9', '', 23);