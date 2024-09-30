package com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PerformanceOneDTO {
    private String title;             // 평가 제목
    private LocalDate evaluationDate; // 평가 날짜
    private String score;            // 평가 점수
    private String comments;         // 평가 내용
}
