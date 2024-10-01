package com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.work_report.enums;

/**작업 상태
 * */
public enum WorkStatus {

    PLANNED,         //계획됨
    IN_PROGRESS,     //진행 중
    ON_HOLD,         //보류됨
    COMPLETED,       //완료됨
    FAILED,          //실패
    CANCELLED        //취소됨
}
