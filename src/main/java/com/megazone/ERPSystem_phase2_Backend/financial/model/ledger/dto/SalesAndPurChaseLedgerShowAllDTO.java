package com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesAndPurChaseLedgerShowAllDTO {
    private List<SalesAndPurChaseLedgerShowDTO> salesAndPurChaseLedgerShowList;
    private SalesAndPurChaseLedgerDailySumDTO salesAndPurChaseLedgerDailySumDTO;

    public static SalesAndPurChaseLedgerShowAllDTO create(List<SalesAndPurChaseLedgerShowDTO> salesAndPurChaseLedgerShowList,
                                                          SalesAndPurChaseLedgerDailySumDTO salesAndPurChaseLedgerDailySumDTO) {
        return new SalesAndPurChaseLedgerShowAllDTO(
                salesAndPurChaseLedgerShowList,
                salesAndPurChaseLedgerDailySumDTO
        );
    }
}
