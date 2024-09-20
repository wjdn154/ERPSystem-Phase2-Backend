package com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientLedgerShowDTO {
    private String clientCode;
    private String clientName;
    private String clientRegisterNumber;
    private String ownerName;
    private BigDecimal previousCash;
    private BigDecimal debitTotalAmount;
    private BigDecimal creditTotalAmount;
    private BigDecimal cashTotalAmount;
    private String managerDepartment;
    private String managerName;

    public static ClientLedgerShowDTO create(String clientCode, String clientName, String clientRegisterNumber,
                                             String ownerName, BigDecimal previousCash, BigDecimal debitTotalAmount,
                                             BigDecimal creditTotalAmount, BigDecimal cashTotalAmount, String managerDepartment,
                                             String managerName) {

        return new ClientLedgerShowDTO(
                clientCode,
                clientName,
                clientRegisterNumber,
                ownerName,
                previousCash,
                debitTotalAmount,
                creditTotalAmount,
                cashTotalAmount,
                managerDepartment,
                managerName
        );
    }

}
