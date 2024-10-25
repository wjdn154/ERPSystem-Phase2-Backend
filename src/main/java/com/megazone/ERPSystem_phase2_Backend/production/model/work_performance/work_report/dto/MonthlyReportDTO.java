package com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.work_report.dto;

import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.enums.DailyAndMonthType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyReportDTO {
    private String productCode;
    private String productName;
    private String productStandard;
    private String productUnit;
    private BigDecimal totalPlanned;      // 총 계획 수량
    private BigDecimal totalActual;       // 총 실제 수량

    private BigDecimal janPlanned;        // 1월 계획 수량
    private BigDecimal janActual;         // 1월 실제 수량
    private BigDecimal febPlanned;        // 2월 계획 수량
    private BigDecimal febActual;         // 2월 실제 수량
    private BigDecimal marPlanned;        // 3월 계획 수량
    private BigDecimal marActual;         // 3월 실제 수량
    private BigDecimal aprPlanned;        // 4월 계획 수량
    private BigDecimal aprActual;         // 4월 실제 수량
    private BigDecimal mayPlanned;        // 5월 계획 수량
    private BigDecimal mayActual;         // 5월 실제 수량
    private BigDecimal junPlanned;        // 6월 계획 수량
    private BigDecimal junActual;         // 6월 실제 수량
    private BigDecimal julPlanned;        // 7월 계획 수량
    private BigDecimal julActual;         // 7월 실제 수량
    private BigDecimal augPlanned;        // 8월 계획 수량
    private BigDecimal augActual;         // 8월 실제 수량
    private BigDecimal sepPlanned;        // 9월 계획 수량
    private BigDecimal sepActual;         // 9월 실제 수량
    private BigDecimal octPlanned;        // 10월 계획 수량
    private BigDecimal octActual;         // 10월 실제 수량
    private BigDecimal novPlanned;        // 11월 계획 수량
    private BigDecimal novActual;         // 11월 실제 수량
    private BigDecimal decPlanned;        // 12월 계획 수량
    private BigDecimal decActual;         // 12월 실제 수량
}