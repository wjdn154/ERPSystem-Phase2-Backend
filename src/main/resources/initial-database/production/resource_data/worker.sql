
-- worker 테이블에 데이터 삽입 (company_id 1과 2로 나눔)
# INSERT INTO worker (training_status, employee_id, company_id)
# VALUES
# -- 1. 정현 박 - 생산 부서, 교육 미이수, company_id 1
# (FALSE, 11, 1),
#
# -- 2. 희연 임 - 생산 부서, 교육 이수, company_id 2
# (TRUE, 3, 1),
#
# -- 4. 태영 이 - 생산 부서, 교육 미이수, company_id 2
# (FALSE, 15, 1),
#
# -- 5. 서준 한 - 생산 부서, 교육 이수, company_id 1
# (TRUE, 23, 1),
#
# -- 6. 민호 최 - 생산 부서, 교육 미이수, company_id 2
# (FALSE, 13, 1),
#
# -- 7. 주원 박 - 생산 부서, 교육 이수, company_id 1
# (TRUE, 17, 1),
#
# -- 8. 도현 최 - 생산 부서, 교육 이수, company_id 2
# (TRUE, 18, 1),
#
# -- 9. 영준 장 - 생산 부서, 교육 이수, company_id 1
# (TRUE, 21, 1),
#
# -- 10. 수빈 최 - 생산 부서, 교육 이수, company_id 2
# (TRUE, 27, 1),
#
# -- 11. 도영 김 - 생산 부서, 교육 미이수, company_id 1
# (FALSE, 34, 1),
#
# -- 12. 태리 한 - 생산 부서, 교육 이수, company_id 2
# (TRUE, 35, 1),
#
# -- 13. 영훈 이 - 생산 부서, 교육 이수, company_id 1
# (TRUE, 36, 1),
#
# -- 14. 서연 강 - 생산 부서, 교육 이수, company_id 2
# (TRUE, 33, 1),
#
# -- 15. 정우 박 - 생산 부서, 교육 이수, company_id 1
# (TRUE, 32, 1);


INSERT INTO worker (training_status, employee_id, company_id)
VALUES
(FALSE, 1, 1),
(TRUE, 2, 1),
(TRUE, 3, 1),
(FALSE, 4, 1),
(TRUE, 5, 1),
(FALSE, 6, 1),
(TRUE, 7, 1),
(TRUE, 8, 1),
(TRUE, 9, 1),
(TRUE, 10, 1),
(FALSE, 11, 1),
(TRUE, 12, 1),
(TRUE, 13, 1),
(TRUE, 14, 1),
(TRUE, 15, 1),
(FALSE, 16, 1),
(TRUE, 17, 1),
(TRUE, 18, 1),
(TRUE, 19, 1),
(TRUE, 20, 1),
(FALSE, 21, 1),
(TRUE, 22, 1),
(TRUE, 23, 1),
(FALSE, 24, 1),
(TRUE, 25, 1),
(FALSE, 26, 1),
(TRUE, 27, 1),
(TRUE, 28, 1),
(TRUE, 29, 1),
(TRUE, 30, 1),
(FALSE, 31, 1),
(TRUE, 32, 1),
(TRUE, 33, 1),
(TRUE, 34, 1),
(TRUE, 35, 1),
(TRUE, 36, 1);


# INSERT INTO worker (training_status, employee_id, company_id)
# VALUES
#     (false, 1, 1), -- employee_id 1 (지호 김), company_id 1
#     (false, 2, 1); -- employee_id 2 (성민 이), company_id 1
#     (false, 3, 1); -- employee_id 3 (수진 최), company_id 2
