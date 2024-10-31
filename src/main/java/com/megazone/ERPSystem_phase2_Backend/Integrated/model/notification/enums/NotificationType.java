package com.megazone.ERPSystem_phase2_Backend.Integrated.model.notification.enums;

public enum NotificationType {
    NEW_VOUCHER("신규 전표"),
    APPROVAL_VOUCHER("전표 승인"),
    REJECT_VOUCHER("전표 반려"),
    CHANGE_PERMISSION("권한 변경"),
    SHIPPING_ORDER("출고 지시"),      // 출고 지시 알림
    SHIPPED_ORDER("출고 완료"),       // 출고 완료 알림
    SHIPPING_ORDER_COMPLETE("출고 완료"), // 전체 출고 완료 알림
    NEW_RECEIVING_SCHEDULE("새로운 입고 스케줄"),
    RECEIVING_COMPLETE("입고 처리 완료"),
    NEW_MAINTENANCE_HISTORY("신규 유지보수"),
    UPDATE_MAINTENANCE_HISTORY("유지보수 변경"),
    NEW_EQUIPMENT_DATA("신규 설비"),
    UPDATE_EQUIPMENT_DATA("설비 변경"),
    NEW_MATERIAL("신규 자재"),
    UPDATE_MATERIAL("자재 변경"),
    UPDATE_WORKER("작업자 교육이수여부 변경");


    private final String koreanName;

    NotificationType(String koreanName) {
        this.koreanName = koreanName;
    }

    public String getKoreanName() {
        return koreanName;
    }
}