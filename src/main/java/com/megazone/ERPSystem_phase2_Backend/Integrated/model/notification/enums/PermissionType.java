package com.megazone.ERPSystem_phase2_Backend.Integrated.model.notification.enums;

public enum PermissionType {
    ALL("전체 권한"),
    USER("사용자 권한"),
    ADMIN("관리자 권한");

    private final String koreanName;

    PermissionType(String koreanName) {
        this.koreanName = koreanName;
    }

    public String getKoreanName() {
        return koreanName;
    }
}