


INSERT INTO Allowances (name, allowance_type, amount, allowances_id) VALUES
                                                          ('연장수당', 'overtime', 30000,1),
                                                          ('야간수당', 'nighttime', 60000,1),
                                                          ('휴일근로수당', 'holiday', 40000,1),
                                                          ('주휴수당', 'paidholiday', 80000,1);


INSERT INTO deductions (name, deduction_type , amount,deductions_id) VALUES
                                                           ('국민연금', 'national', 900,1),
                                                           ('건강보험', 'health', 1200,1),
                                                           ('고용보험', 'employment', 400,1);

INSERT INTO salary (base_salary,gross_pay, net_pay, salary_date, employee_id) VALUES
    (2400000,280000,267000,'2024-05-05',1),
    (2500000,290000,277000,'2024-05-05',2),
    (2600000,300000,287000,'2024-05-05',3);

