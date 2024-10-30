package com.megazone.ERPSystem_phase2_Backend.logistics.model.receiving_processing_management.enums;

public enum ReceivingStatus {
    WAITING_FOR_RECEIPT,
    WAITING,        // 입고 대기
    PARTIALLY_RECEIVED, // 부분 입고 완료
    RECEIVED,       // 전체 입고 완료
    CANCELED        // 입고 취소
}

