package com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PerformanceCreateDTO {
    private String employeeNumber; // 평가 대상 사원
    private Long evaluatorId;
    private String title;
    private LocalDate evaluationDate; // 평가 날짜
    private String score; // 평가 점수
    private String comments; // 평가 내용
}