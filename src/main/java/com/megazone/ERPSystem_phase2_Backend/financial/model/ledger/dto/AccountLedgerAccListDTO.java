package com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountLedgerAccListDTO {
    private Long accountId;
    private String accountCode;
    private String accountName;

    public static AccountLedgerAccListDTO create(Long accountId, String accountCode, String accountName) {
        return new AccountLedgerAccListDTO(accountId, accountCode, accountName);
    }
}
