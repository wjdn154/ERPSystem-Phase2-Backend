package com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto;

import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.VatType;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.enums.TransactionType;

public class VatTypeGiveDTO {
    private String vatTypeName;
    private TransactionType transactionType;

    public static VatTypeShowDTO create(VatType vatType) {
        return new VatTypeShowDTO(
                vatType.getCode(),
                vatType.getVatName(),
                vatType.getDescription(),
                vatType.getTransactionType()
        );
    }
}
