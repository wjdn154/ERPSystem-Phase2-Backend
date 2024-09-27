package com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClientAndAccountSubjectLedgerSearchDTO {
    private LocalDate startDate;
    private LocalDate endDate;
    private String startAccountSubjectCode;
    private String endAccountSubjectCode;
    private String startClientCode;
    private String endClientCode;
}
