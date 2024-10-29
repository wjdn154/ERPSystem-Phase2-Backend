package com.megazone.ERPSystem_phase2_Backend.logistics.model.shipping_processing_management.enums;

public enum ShippingStatus {
    WAITING_FOR_SHIPMENT,    // 출하 대기
    SHIPPED,                 // 출하 완료
    IN_TRANSIT,              // 운송 중
    DELIVERED,               // 배송 완료
    CANCELLED,                // 출하 취소
    ORDER_FOR_SHIPMENT
}

