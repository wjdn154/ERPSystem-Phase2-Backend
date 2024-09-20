package com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeneralAccountListDTO {
    private Long accountId;
    private String accountName;
    private String accountCode;


    public static GeneralAccountListDTO create(Long accountId, String accountName, String accountCode) {
        return new GeneralAccountListDTO(accountId, accountName, accountCode);
    }
}
