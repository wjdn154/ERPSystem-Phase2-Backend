package com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.salary;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.common.aliasing.qual.Unique;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 *  고용보험 정보 테이블
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "employment_insurance_pension")
@Table(name = "employment_insurance_pension")
public class EmploymentInsurancePension {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Unique
    private Long id;

    @Column(precision = 10, scale = 3) private BigDecimal companyRate; // 고용보험 기업 적용 요율
    @Column(precision = 10, scale = 3) private BigDecimal employeeRate; // 고용보험 가입자 적용
    private LocalDate startDate; // 고용보험기준 적용 시작 날짜
    private LocalDate endDate; // 고용보험기준 적용 마감 날짜
}
