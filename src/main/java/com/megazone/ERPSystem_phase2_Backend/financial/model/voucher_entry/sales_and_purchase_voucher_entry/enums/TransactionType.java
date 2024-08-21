package com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

public enum TransactionType {
    SALES("Sales"), // 매출
    PURCHASE("Purchase"); // 매입

    private final String name;

    TransactionType(String name) {
        this.name = name;
    }

    @JsonCreator
    public static TransactionType of(String progress) {
        return Arrays.stream(TransactionType.values())
                .filter(i -> i.name.equals(progress))
                .findAny()
                .orElse(null);
    }
}
