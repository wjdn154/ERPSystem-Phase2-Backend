package com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

public enum ApprovalStatus {
    PENDING("Pending"),
    APPROVED("Approved"),
    REJECTED("Rejected");

    private final String name;

    ApprovalStatus(String name) {
        this.name = name;
    }

    @JsonCreator
    public static ApprovalStatus of(String progress) {
        return Arrays.stream(ApprovalStatus.values())
                .filter(i -> i.name.equals(progress))
                .findAny()
                .orElse(null);
    }
}
