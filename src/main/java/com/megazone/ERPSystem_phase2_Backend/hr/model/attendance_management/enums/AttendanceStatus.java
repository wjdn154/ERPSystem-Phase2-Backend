package com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.enums;

// 출퇴근 상태

public enum AttendanceStatus {
    PRESENT, // 출근
    ABSENT, // 결근
    LEAVE, // 휴가
    PUBLIC_HOLIDAY, // 공휴일
    EARLY_LEAVE, // 조퇴
    LATE, // 지각
    BUSINESS_TRIP, // 출장
    TRAINING, // 교육
    SABBATICAL, // 휴직
    SICK_LEAVE, // 병가
    REMOTE_WORK, // 자택 근무
    ON_DUTY, // 근무
    OVERTIME, // 야근
    SHIFT_WORK, // 교대 근무
    LATE_AND_EARLY_LEAVE // 지각 및 조퇴
}

// 09:00 시 출근
// 18:00 시 퇴근

// 09:10분 부터 지각

// 18:00 시 전 퇴근 => 조퇴