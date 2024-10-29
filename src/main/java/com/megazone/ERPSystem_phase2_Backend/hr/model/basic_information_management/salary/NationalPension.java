package com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.salary;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.common.aliasing.qual.Unique;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 국민연금 정보 테이블
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "national_pension")
@Table(name = "national_pension")
public class NationalPension {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Unique
    private Long id;

    @Column(precision = 10, scale = 3) private BigDecimal companyRate; // 극민연금 기업 적용 요율
    @Column(precision = 10, scale = 3) private BigDecimal employeeRate; // 국민연금 가입자 적용 요율
    private BigDecimal lowerAmount; // 국민연금 최저하한 금액
    private BigDecimal upperAmount; // 국민연금 최고상한 금액
    private LocalDate startDate; // 국민연금기준 적용 시작 날짜
    private LocalDate endDate; // 국민연금기준 적용 마감 날짜
}
