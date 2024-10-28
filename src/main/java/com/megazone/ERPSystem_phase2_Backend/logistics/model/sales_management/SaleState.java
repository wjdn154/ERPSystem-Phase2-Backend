package com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management;

public enum SaleState {
    WAITING_FOR_SHIPMENT,   // 출하 대기 상태
    SHIPMENT_COMPLETED,     // 출하 완료 상태
    IN_PROGRESS,            // 진행중
    COMPLETED,              // 진행완료
    CANCELED                // 취소
}