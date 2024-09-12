-- ShiftType 데이터 삽입
INSERT INTO common_scheduling_shift_type (name, description, duration, is_used) VALUES
                                                        ('주간', '8시간 주간 근무', 8, TRUE),
                                                        ('야간', '8시간 야간 근무', 8, TRUE),
                                                        ('오전', '8시간 오전 근무', 8, TRUE),
                                                        ('오후', '8시간 오후 근무', 8, TRUE),
                                                        ('주간 12시간', '12시간 주간 근무', 12, FALSE),
                                                        ('야간 12시간', '12시간 야간 근무', 12, FALSE),
                                                        ('4조 3교대', '4개조 3교대 근무', 8, FALSE),
                                                        ('3조 2교대', '3개조 2교대 근무', 12, FALSE),
                                                        ('4조 2교대', '4개조 2교대 근무', 12, FALSE);
