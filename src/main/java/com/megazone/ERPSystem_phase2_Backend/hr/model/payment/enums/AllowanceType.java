package com.megazone.ERPSystem_phase2_Backend.hr.model.payment.enums;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

public enum AllowanceType {
    LongService, // 근속수당
    overtime, // 연장수당
    nighttime, // 야간수당
    holiday, // 휴일근로수당
    annual, // 연차수당
    paidholiday,// 주휴수당

    /*** 비법정수당 ***/
    fulltime, // 만근수당
    jobgrade, // 직급수당
    job, // 직무수당
    incentive, // 장려수당
    family, // 가족수당
    bonus, // 상여금
    performance, // 성과급
    holidays, // 명절상여금
    ETC // 기타
    }
