package com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Performance;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PerformanceShowDTO {
    private Long employeeId;
    private String employeeNumber;   // 평가 대상 사원의 사원 번호
    private String employeeName;
    private Long evaluatorId;        // 평가자의 ID
    private String title;
    private String evaluatorName;
    private LocalDate evaluationDate; // 평가 날짜
    private String score;            // 평가 점수
    private String comments;         // 평가 내용

    public static PerformanceShowDTO fromEntity(Performance performance) {
        PerformanceShowDTO dto = new PerformanceShowDTO();
        dto.setEmployeeId(performance.getEmployee().getId());
        dto.setEmployeeNumber(performance.getEmployee().getEmployeeNumber());
        dto.setEmployeeName(performance.getEmployee().getLastName()+performance.getEmployee().getFirstName());
        dto.setEvaluatorId(performance.getEvaluator().getId());
        dto.setTitle(performance.getTitle());
        dto.setEvaluatorName(performance.getEvaluator().getLastName()+ performance.getEvaluator().getFirstName());
        dto.setEvaluationDate(performance.getEvaluationDate());
        dto.setScore(performance.getScore());
        dto.setComments(performance.getComments());
        return dto;
    }
}
