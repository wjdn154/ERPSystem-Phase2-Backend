package com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.salary;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.common.aliasing.qual.Unique;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 사학연금 정보 테이블
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "private_school_pension")
@Table(name = "private_school_pension")
public class PrivateSchoolPension {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Unique
    private Long id;

    private BigDecimal rate; // 사학연금 계샨 요율

    private BigDecimal lowerAmount; // 사학연금 최저하한 금액

    private BigDecimal upperAmount; // 사학연금 최고상한 금액

    private LocalDate startDate; // 사학연금 적용 시작 날짜

    private LocalDate endDate; // 사학연금 적용 마감 날짜
}
