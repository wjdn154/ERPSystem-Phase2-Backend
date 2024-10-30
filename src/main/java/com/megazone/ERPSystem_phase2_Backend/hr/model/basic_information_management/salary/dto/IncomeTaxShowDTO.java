package com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.salary.dto;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.salary.IncomeTax;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncomeTaxShowDTO {
    private BigDecimal lowAmount; // 과세표준 하한
    private BigDecimal highAmount; // 과세표준 상한
    @Column(precision = 10, scale = 2)
    private BigDecimal rate; // 세율
    private BigDecimal taxCredit; // 누진공제액

    public static IncomeTaxShowDTO create(IncomeTax incomeTax) {
        return new IncomeTaxShowDTO(
                incomeTax.getLowAmount(),
                incomeTax.getHighAmount(),
                incomeTax.getRate(),
                incomeTax.getTaxCredit()
        );
    }
}
