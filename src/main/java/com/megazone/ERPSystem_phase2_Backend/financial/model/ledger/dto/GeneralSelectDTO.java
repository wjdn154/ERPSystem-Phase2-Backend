package com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeneralSelectDTO {
    private LocalDate startDate;
    private LocalDate endDate;
    private String accountCode;

    public static GeneralSelectDTO create(LocalDate startDate, LocalDate endDate, String accountCode) {
        return new GeneralSelectDTO(
                startDate,
                endDate,
                accountCode
        );
    }
}
