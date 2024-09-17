INSERT INTO social_insurance_rate (insurance_type, business_scale, employee_rate, employer_rate) VALUES
('EmploymentInsurance','LessThan150', 0.9,1.15),
('EmploymentInsurance','SupportCompany', 0.9,1.35),
('EmploymentInsurance','LessThan1000', 0.9,1.55),
('EmploymentInsurance','MoreThan1000NationalLocalGovernment', 0.9,1.75),
('HealthInsurance',null, 0.9,1.15),
('IndustrialAccident',null, 0,1.15),
('NationalPension',null, 4.5,4.5),
('LongTermCareInsurance',null, 6.475,6.475);