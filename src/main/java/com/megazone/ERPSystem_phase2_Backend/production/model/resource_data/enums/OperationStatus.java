package com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.enums;

/**
* 설비 상태를 정의하는 enum 클래스.
* */

public enum OperationStatus {

    BEFORE_OPERATION,   //가동 전
    OPERATING,          //작동 중
    MAINTENANCE,        //유지보수 중
    FAILURE,            //고장
    REPAIRING           //수리 중
}
