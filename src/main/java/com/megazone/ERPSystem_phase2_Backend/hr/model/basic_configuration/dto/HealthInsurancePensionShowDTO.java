package com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.dto;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.salary.HealthInsurancePension;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HealthInsurancePensionShowDTO {
    private Long id;
    private BigDecimal companyRate; // 건강보험 기업 적용 요율
    private BigDecimal employeeRate; // 건강보험 가입자 적용 요율
    private BigDecimal lowerAmount; // 건강보험 최저하한 금액
    private BigDecimal upperAmount; // 건강보험 최고상한 금액
    private LocalDate startDate; // 건강보험기준 적용 시작 날짜
    private LocalDate endDate; // 건강보험기준 적용 마감 날짜

    public static HealthInsurancePensionShowDTO create(HealthInsurancePension pension) {
        return new HealthInsurancePensionShowDTO(
                pension.getId(),
                pension.getCompanyRate(),
                pension.getEmployeeRate(),
                pension.getLowerAmount(),
                pension.getUpperAmount(),
                pension.getStartDate(),
                pension.getEndDate()
        );
    }
}
