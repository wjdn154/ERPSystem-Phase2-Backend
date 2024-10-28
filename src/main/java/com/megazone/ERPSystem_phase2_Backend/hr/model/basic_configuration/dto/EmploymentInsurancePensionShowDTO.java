package com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.dto;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.salary.EmploymentInsurancePension;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmploymentInsurancePensionShowDTO {
    private Long id;
    private BigDecimal companyRate;
    private BigDecimal employeeRate;
    private LocalDate startDate;
    private LocalDate endDate;

    public static EmploymentInsurancePensionShowDTO create(EmploymentInsurancePension pension) {
        return new EmploymentInsurancePensionShowDTO(
                pension.getId(),
                pension.getCompanyRate(),
                pension.getEmployeeRate(),
                pension.getStartDate(),
                pension.getEndDate()
        );
    }
}
