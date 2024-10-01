

INSERT INTO attendance_management (check_time, checkout_time, date, employee_id, attendance_code, status) VALUES
('09:00:00','18:00:00','2024-01-05',1,'0001','PRESENT'),
('09:00:00','18:00:00','2024-01-12',2,'0002','ABSENT'),
('09:00:00','18:00:00','2024-01-18',3,'0003','LEAVE'),
('00:00:00','24:00:00','2024-02-08',4,'0004','PUBLIC_HOLIDAY'),
('09:00:00','18:00:00','2024-02-13',5,'0005','EARLY_LEAVE'),
('09:00:00','18:00:00','2024-02-20',5,'0006','LATE');

INSERT INTO leaves_leavestype (type_name) VALUES
('병가'),
('연차'),
('병가'),
('정기휴가');

INSERT INTO leaves (code, end_date, reason, start_date, status, employee_id, leavestype_id) VALUES
('0001','2024-05-05','두통으로 인한 휴가','2024-05-10','APPROVED','1','1'),
('0002','2024-06-05','연차 사용','2024-06-10','PENDING','2','2'),
('0003','2024-07-05','복통으로 인한 휴가','2024-07-10','APPROVED','3','3'),
('0004','2024-08-05','정기휴가 사용','2024-08-10','PENDING','4','4');