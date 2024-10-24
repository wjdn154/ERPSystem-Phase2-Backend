INSERT INTO worker (training_status, employee_id, company_id)
VALUES
    (false, 1, 1), -- employee_id 1 (지호 김), company_id 1
    (false, 2, 1); -- employee_id 2 (성민 이), company_id 1
#     (false, 3, 1); -- employee_id 3 (수진 최), company_id 2

-- worker 테이블에 데이터 삽입 (company_id 1과 2로 나눔)
# INSERT INTO worker (id, training_status, employee_id, company_id)
# VALUES
# -- 1. 정현 박 - 생산 부서, 교육 미이수, company_id 1
# (1, FALSE, 11, 1),
#
# -- 2. 희연 임 - 생산 부서, 교육 이수, company_id 2
# (2, TRUE, 3, 2),
#
# -- 3. 현서 박 - 생산 부서, 교육 이수, company_id 1
# (3, TRUE, 11, 1),
#
# -- 4. 태영 이 - 생산 부서, 교육 미이수, company_id 2
# (4, FALSE, 15, 2),
#
# -- 5. 서준 한 - 생산 부서, 교육 이수, company_id 1
# (5, TRUE, 23, 1),
#
# -- 6. 민호 최 - 생산 부서, 교육 미이수, company_id 2
# (6, FALSE, 13, 2),
#
# -- 7. 주원 박 - 생산 부서, 교육 이수, company_id 1
# (7, TRUE, 17, 1),
#
# -- 8. 도현 최 - 생산 부서, 교육 이수, company_id 2
# (8, TRUE, 18, 2),
#
# -- 9. 영준 장 - 생산 부서, 교육 이수, company_id 1
# (9, TRUE, 21, 1),
#
# -- 10. 수빈 최 - 생산 부서, 교육 이수, company_id 2
# (10, TRUE, 27, 2),
#
# -- 11. 도영 김 - 생산 부서, 교육 미이수, company_id 1
# (11, FALSE, 34, 1),
#
# -- 12. 태리 한 - 생산 부서, 교육 이수, company_id 2
# (12, TRUE, 35, 2),
#
# -- 13. 영훈 이 - 생산 부서, 교육 이수, company_id 1
# (13, TRUE, 36, 1),
#
# -- 14. 서연 강 - 생산 부서, 교육 이수, company_id 2
# (14, TRUE, 33, 2),
#
# -- 15. 정우 박 - 생산 부서, 교육 이수, company_id 1
# (15, TRUE, 32, 1);

# INSERT INTO worker (id, training_status, employee_id)
# VALUES
# -- 1. 정현 박 - 생산 부서, 교육 미이수
# (1, FALSE, 11),
#
# -- 2. 희연 임 - 생산 부서, 교육 이수
# (2, TRUE, 3),
#
# -- 3. 현서 박 - 생산 부서, 교육 이수
# (3, TRUE, 11),
#
# -- 4. 태영 이 - 생산 부서, 교육 미이수
# (4, FALSE, 15),
#
# -- 5. 서준 한 - 생산 부서, 교육 이수
# (5, TRUE, 23),
#
# -- 6. 민호 최 - 생산 부서, 교육 미이수
# (6, FALSE, 13),
#
# -- 7. 주원 박 - 생산 부서, 교육 이수
# (7, TRUE, 17),
#
# -- 8. 도현 최 - 생산 부서, 교육 이수
# (8, TRUE, 18),
#
# -- 9. 영준 장 - 생산 부서, 교육 이수
# (9, TRUE, 21),
#
# -- 10. 수빈 최 - 생산 부서, 교육 이수
# (10, TRUE, 27),
#
# -- 11. 도영 김 - 생산 부서, 교육 미이수
# (11, FALSE, 34),
#
# -- 12. 태리 한 - 생산 부서, 교육 이수
# (12, TRUE, 35),
#
# -- 13. 영훈 이 - 생산 부서, 교육 이수
# (13, TRUE, 36),
#
# -- 14. 서연 강 - 생산 부서, 교육 이수
# (14, TRUE, 33),
#
# -- 15. 정우 박 - 생산 부서, 교육 이수
# (15, TRUE, 32);
