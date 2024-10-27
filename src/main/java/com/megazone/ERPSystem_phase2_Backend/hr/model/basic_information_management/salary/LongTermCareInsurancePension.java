package com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.salary;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.common.aliasing.qual.Unique;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 *  장기요양보험 정보 테이블
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "long_term_care_insurance_pension")
@Table(name = "long_term_care_insurance_pension")
public class LongTermCareInsurancePension {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Unique
    private Long id;

    private String code; // 장기요양보험 코드

    private BigDecimal rate; // 장기요양보험 코드별 적용 요율

    private String description; // 설명

    private LocalDate startDate;

    private LocalDate endDate;
}
