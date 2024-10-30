package com.megazone.ERPSystem_phase2_Backend.hr.model.salary_ledger;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.enums.TaxType;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalaryLedgerAllowance {
    private String name; // 수당이름
    private BigDecimal amount; // 수당금액
}
