package com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management;

public enum State {
    WAITING_FOR_PURCHASE,   // 발주 대기 상태
    PURCHASE_COMPLETED,     // 발주 완료 상태
    WAITING_FOR_RECEIPT,    // 입고 예정
    RECEIPT_COMPLETED,      // 입고 완료
    CANCELED,               // 취소 상태
    INVOICED,               // 결제중
    ACCOUNTED               // 회계 반영 완료
}
