package com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaxInvoiceLedgerShowAllDTO {
    private String clientCode;
    private String clientName;
    private String clientNumber;
    private int totalVoucherCount;
    private BigDecimal totalSupplyAmount;
    private BigDecimal totalVatAmount;
    private List<TaxInvoiceLedgerShowDTO> allShows;

    public static TaxInvoiceLedgerShowAllDTO create(String clientCode, String clientName, String clientNumber,
                                                    int totalVoucherCount, BigDecimal totalSupplyAmount, BigDecimal totalVatAmount,
                                                    List<TaxInvoiceLedgerShowDTO> allShows) {
        return new TaxInvoiceLedgerShowAllDTO(
                clientCode,
                clientName,
                clientNumber,
                totalVoucherCount,
                totalSupplyAmount,
                totalVatAmount,
                allShows
        );
    }
}