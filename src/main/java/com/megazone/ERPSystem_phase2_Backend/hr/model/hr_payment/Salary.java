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
