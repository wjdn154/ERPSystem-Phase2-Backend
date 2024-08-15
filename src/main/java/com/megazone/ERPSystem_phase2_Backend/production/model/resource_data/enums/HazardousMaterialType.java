package com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.enums;

/**
 * 유해물질 유형을 정의하는 enum 클래스입니다.
 * 각 항목은 주요 환경 규제에서 다루는 유해물질 그룹을 나타냅니다.
 */

public enum HazardousMaterialType {

    // 중금속류 (Heavy Metals)
    LEAD,           // 납
    MERCURY,        // 수은
    CADMIUM,        // 카드뮴
    HEXAVALENT_CHROMIUM,  // 6가 크롬
    NICKEL,         // 니켈

    // 할로겐화합물 (Halogenated Compounds)
    CHLORINATED_COMPOUNDS,  // 염소화합물
    BROMINATED_FLAME_RETARDANTS,  // 브롬화 난연제

    // 유기 화합물 (Organic Compounds)
    PHTHALATES,     // 프탈레이트
    POLYCHLORINATED_BIPHENYLS,  // 폴리염화비페닐 (PCBs)

    // 기타 유해 물질 (Other Hazardous Substances)
    ASBESTOS,       // 석면
    ARSENIC,        // 비소
    BENZENE         // 벤젠
}
