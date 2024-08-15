<<<<<<< HEAD
//package com.megazone.ERPSystem_phase2_Backend.hr.model.hr_payment;
//
//
//import com.megazone.ERPSystem_phase2_Backend.hr.model.hr_management.Employee;
//import com.megazone.ERPSystem_phase2_Backend.hr.model.hr_management.Users;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.math.BigDecimal;
//
//@Data
//@Entity
//@NoArgsConstructor
//@AllArgsConstructor
//
//public class Salary {
//    @Id
//    @GeneratedValue
//    private Long id;
//
//    // �⺻ �޿�
//    @Column(name = "base_salary")
//    private BigDecimal baseSalary;
//
//    // �ñ�
//    @Column(name = "hour_salary")
//    private BigDecimal hourSalary;
//
//    // �ϱ�
//    @Column(name = "day_salary")
//    private BigDecimal daySalary;
//
//    /***** �������� *****/
//    // �������
//    @Column(name = "overtime_allowance")
//    private BigDecimal overtimeAllowance;
//
//    // �߰�����
//    @Column(name = "nighttime_allowance")
//    private BigDecimal NighttimeAllowance;
//
//    // ���ϱٷμ���
//    @Column(name = "holiday_allowance")
//    private BigDecimal HolidayAllowance;
//
//    // ��������
//    @Column(name = "annual_allowance")
//    private BigDecimal AnnualAllowance;
//
//    /***** ��������� *****/
//    // ���ټ���
//    @Column(name = "full_attendance_allowance")
//    private BigDecimal FullAttendanceAllowance;
//
//    // �������
//    @Column(name = "incentive_allowance")
//    private BigDecimal IncentiveAllowance;
//
//    // ������ ?
//    @Column(name = "subsidy")
//    private BigDecimal subsidy;
//
//    @Column(name = "bouns")
//    private BigDecimal bouns;
//
//    /***** �ջ� *****/
//    // �ѱ޿�
//    @Column(name = "gross_salary")
//    private BigDecimal grossSalary;
//
//    // ���޿�
//    @Column(name = "net_salary")
//    private BigDecimal netsalary;
//
//    @Column(name = "month")
//    private String month;
//
///********** ���� **********/
//
//    // ���ο���
//    @Column(name = "national_pension")
//    private BigDecimal NationalPension;
//
//    // �ǰ�����
//    @Column(name = "nationl_health_insurance")
//    private BigDecimal NationalHealthInsurance;
//
//    // ��뺸��
//    @Column(name = "employment_insurance")
//    private BigDecimal EmploymentInsurance;
//
//    // ����� �����
//    @Column(name = "long_term_care_insurance")
//    private BigDecimal LongTermCareInsurance;
//
//    // ������� ���� ����
//    @Column(name = "industrial_accident_compensation_insurance")
//    private BigDecimal IndustrialAccidentCompensationInsurance;
//
//    // �ҵ漼
//    @Column(name = "income_tax")
//    private BigDecimal IncomeTax;
//
//    // �������� �ҵ漼
//
//    // ������������ �ҵ漼
//
//
//    // �������� ��Ư��
//
//    // ����ȸ��
//    /***** ��Ÿ ���� *****/
//    // ��� ����
//    @Column(name = "absenteeism_deduct")
//    private BigDecimal AbsenteeismDeduct;
//
//    // ���� ����
//    @Column(name = "leave_early_deduct")
//    private BigDecimal LeaveEarlyDeduct;
//
//    // ���� ����
//    @Column(name = "late_deduct")
//    private BigDecimal LateDeduct;
//
//    // �����ް�? ����
//    @Column(name = "leave_deduct")
//    private BigDecimal LeaveDeduct;
//
//
//    @OneToOne
//    @JoinColumn(name = "empId", nullable = false, updatable = false, insertable = false)
//    private Users employee;
//    private int empId;
//
//}
=======
package com.megazone.ERPSystem_phase2_Backend.hr.model.hr_payment;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor

public class Salary {
    @Id
    @GeneratedValue
    private Long id;

    // 기본 급여
    @Column(name = "base_salary")
    private BigDecimal baseSalary;

    // 시급
    @Column(name = "hour_salary")
    private BigDecimal hourSalary;

    // 일급
    @Column(name = "day_salary")
    private BigDecimal daySalary;

    /**** 법정수당 ****/

    // 연장수당
    @Column(name = "overtime_allowance")
    private BigDecimal overtimeAllowance;

    // 야간수당
    @Column(name = "nighttime_allowance")
    private BigDecimal NighttimeAllowance;

    // 휴일근로수당
    @Column(name = "holiday_allowance")
    private BigDecimal HolidayAllowance;

    // 연차수당
    @Column(name = "annual_allowance")
    private BigDecimal AnnualAllowance;

    /**** 비법정수당 ****/

    // 만근수당
    @Column(name = "full_attendance_allowance")
    private BigDecimal FullAttendanceAllowance;

    // 장려수당
    @Column(name = "incentive_allowance")
    private BigDecimal IncentiveAllowance;

    // 보조금 ?
    @Column(name = "subsidy")
    private BigDecimal subsidy;

    @Column(name = "bouns")
    private BigDecimal bouns;

    /**** 합산 ****/

    // 총급여
    @Column(name = "gross_salary")
    private BigDecimal grossSalary;

    // 순급여
    @Column(name = "net_salary")
    private BigDecimal netsalary;

    @Column(name = "month")
    private String month;

    /********* 공제 *********/


    // 국민연금
    @Column(name = "national_pension")
    private BigDecimal NationalPension;

    // 건강보험
    @Column(name = "nationl_health_insurance")
    private BigDecimal NationalHealthInsurance;

    // 고용보험
    @Column(name = "employment_insurance")
    private BigDecimal EmploymentInsurance;

    // 장기요양 보험료
    @Column(name = "long_term_care_insurance")
    private BigDecimal LongTermCareInsurance;

    // 산업재해 보상 보험
    @Column(name = "industrial_accident_compensation_insurance")
    private BigDecimal IndustrialAccidentCompensationInsurance;

    // 소득세
    @Column(name = "income_tax")
    private BigDecimal IncomeTax;

    // 상조회비
    @Column(name = "fee_of_mutual_aid_society")
    private BigDecimal FeeOfMutualAidSociety;

    // 연말정산 소득세

    // 연말정산지방 소득세

    // 연말정산 농특세


    /**** 기타 공제 ****/

    // 결근 공제
    @Column(name = "absenteeism_deduct")
    private BigDecimal AbsenteeismDeduct;

    // 조퇴 공제
    @Column(name = "leave_early_deduct")
    private BigDecimal LeaveEarlyDeduct;

    // 지각 공제
    @Column(name = "late_deduct")
    private BigDecimal LateDeduct;

    // 무급휴가? 공제
    @Column(name = "leave_deduct")
    private BigDecimal LeaveDeduct;

    // Employee -> Salary 연관관계 매핑 필요함
}
>>>>>>> origin/develop
