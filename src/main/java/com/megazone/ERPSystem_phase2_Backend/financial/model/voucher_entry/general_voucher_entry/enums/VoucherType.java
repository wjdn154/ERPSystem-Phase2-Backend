package com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

public enum VoucherType {
    DEPOSIT("Deposit"), // 입금
    WITHDRAWAL("Withdrawal"), // 출금
    DEBIT("Debit"), // 차변
    CREDIT("Credit"); // 대변

    private final String name;

    VoucherType(String name) {
        this.name = name;
    }

    @JsonCreator
    public static VoucherType of(String progress) {
        return Arrays.stream(VoucherType.values())
                .filter(i -> i.name.equals(progress))
                .findAny()
                .orElse(null);
    }
}

