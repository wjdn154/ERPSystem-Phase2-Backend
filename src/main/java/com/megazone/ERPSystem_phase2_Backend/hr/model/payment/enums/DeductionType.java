package com.megazone.ERPSystem_phase2_Backend.hr.model.payment.enums;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

public enum DeductionType {
        /* 4대 보험 */
        national, // 국민연금
        health, // 건강보험
        employment, // 고용보험
        workerscompensation, // 산재보험

        /* 세금 관련 */
        incometax, // 소득세
        localincometax, // 지방소득세
        studentloan, // 학자금상환금

        /* 기타 공제 */
        absenteeism, // 결근
        leaveearly, // 조퇴
        late, // 지각
        unpaidleave, // 무급휴가
        ETC // 기타
}
