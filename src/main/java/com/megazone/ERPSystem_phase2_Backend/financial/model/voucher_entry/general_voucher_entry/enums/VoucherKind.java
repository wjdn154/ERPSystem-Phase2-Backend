package com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

public enum VoucherKind {
    GENERAL("General"),
    SALE_AND_PURCHASE("Sale_and_Purchase");

    private final String name;

    VoucherKind(String name) {
        this.name = name;
    }

    @JsonCreator
    public static VoucherKind of(String progress) {
        return Arrays.stream(VoucherKind.values())
                .filter(i -> i.name.equals(progress))
                .findAny()
                .orElse(null);
    }
}
