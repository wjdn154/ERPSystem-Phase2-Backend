package com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.dto;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.salary.NationalPension;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.common.aliasing.qual.Unique;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NationalPensionShowDTO {
    private Long id;
    private BigDecimal companyRate; // 극민연금 기업 적용 요율
    private BigDecimal employeeRate; // 국민연금 가입자 적용 요율
    private BigDecimal lowerAmount; // 국민연금 최저하한 금액
    private BigDecimal upperAmount; // 국민연금 최고상한 금액
    private LocalDate startDate; // 국민연금기준 적용 시작 날짜
    private LocalDate endDate; // 국민연금기준 적용 마감 날짜

    public static NationalPensionShowDTO create(NationalPension pension) {
        return new NationalPensionShowDTO(
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
