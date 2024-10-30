package com.megazone.ERPSystem_phase2_Backend.Integrated.model.notification.enums;

public enum NotificationType {
    NEW_VOUCHER("신규 전표"),
    APPROVAL_VOUCHER("전표 승인"),
    REJECT_VOUCHER("전표 반려"),
    CHANGE_PERMISSION("권한 변경");

    private final String koreanName;

    NotificationType(String koreanName) {
        this.koreanName = koreanName;
    }

    public String getKoreanName() {
        return koreanName;
    }
}