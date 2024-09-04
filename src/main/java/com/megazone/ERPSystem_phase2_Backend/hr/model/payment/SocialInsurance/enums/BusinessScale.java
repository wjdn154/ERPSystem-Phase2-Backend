package com.megazone.ERPSystem_phase2_Backend.hr.model.payment.SocialInsurance.enums;

public enum BusinessScale {
    LessThan150, // 150 미만 0.25%
    SupportCompany, // 150 이상 우선지원대상기업 0.45%
    LessThan1000, // 150 이상 1000 미만 0.65%
    MoreThan1000,NationalLocalGovernment // 1000 이상, 국가지방자치단체 0.85%
}
