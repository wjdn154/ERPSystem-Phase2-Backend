package com.megazone.ERPSystem_phase2_Backend.Integrated.model.notification.enums;


public enum ModuleType {
    ALL("전체 부서"),
    FINANCE("회계 부서"),
    PRODUCTION("생산 부서"),
    LOGISTICS("물류 부서"),
    HR("인사 부서");

    private final String koreanName;

    ModuleType(String koreanName) {
        this.koreanName = koreanName;
    }

    public String getKoreanName() {
        return koreanName;
    }
}