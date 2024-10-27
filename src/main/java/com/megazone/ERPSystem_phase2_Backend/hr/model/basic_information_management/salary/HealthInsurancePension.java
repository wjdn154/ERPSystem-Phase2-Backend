package com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.salary;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.common.aliasing.qual.Unique;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 *  건강보험 정보 테이블
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "health_insurance_pension")
@Table(name = "health_insurance_pension")
public class HealthInsurancePension {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Unique
    private Long id;

    private BigDecimal companyRate; // 건강보험 기업 적용 요율

    private BigDecimal employeeRate; // 건강보험 가입자 적용 요율

    private BigDecimal lowerAmount; // 건강보험 최저하한 금액

    private BigDecimal upperAmount; // 건강보험 최고상한 금액

    private LocalDate startDate; // 건강보험기준 적용 시작 날짜

    private LocalDate endDate; // 건강보험기준 적용 마감 날짜

}
