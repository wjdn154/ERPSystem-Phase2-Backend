package com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


// 거래처원장 검색조건 DTO
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientLedgerSearchDTO {
    private LocalDate startDate;
    private LocalDate endDate;
    private String accountCode;
    private String clientStartCode;
    private String clientEndCode;
}


