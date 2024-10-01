package com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PerformanceDTO {
    private String employeeNumber;   // 평가 대상 사원의 사원 번호
    private String employeeName;     // 평가 대상 사원의 이름
    private String title;
    private String evaluatorName;    // 평가자의 이름
    private LocalDate evaluationDate; // 평가 날짜
    private String score;            // 평가 점수
    private String comments;         // 평가 내용
}
