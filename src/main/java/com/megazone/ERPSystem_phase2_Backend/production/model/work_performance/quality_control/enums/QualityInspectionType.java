package com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.quality_control.enums;

/**품질 검사 유형
 * */
public enum QualityInspectionType {

    DIMENSION_INSPECTION, // 치수 검사. 제품의 치수, 크기, 모양 검사
    VISUAL_INSPECTION, // 외관 검사. 외관 상태 확인 및 시각적인 결함 검사
    FUNCTIONAL_INSPECTION, // 기능 검사. 제품 기능이 요구 사양에 맞게 동작하는지 확인
    STRENGTH_TEST, // 강도 검사. 제품의 인장 강도, 압축 강도 등 강도 검사
    DURABILITY_TEST, // 내구성 검사. 제품의 내구성 테스트
    WEIGHT_INSPECTION, // 중량 검사. 제품의 무게가 기준에 맞는지 확인
    MATERIAL_COMPOSITION_TEST, // 재료 및 성분 검사. 제품의 재료 성분 및 원소 구성 검사
    ELECTRICAL_TEST, // 전기적 검사. 전기 부품 및 회로의 전기적 특성 검사
    TEMPERATURE_TEST, // 온도 검사. 고온, 저온 등 온도에 따른 제품의 성능 테스트
    ENVIRONMENTAL_TEST, // 환경 시험 (예: 습도, UV 등).
    ASSEMBLY_INSPECTION, // 조립 검사. 제품 조립 상태 및 품질 검사
    PRESSURE_TEST, // 압력 검사. 제품의 압력 저항성 테스트
    LEAK_TEST, // 누출 검사. 제품의 누출 여부를 검사
    SURFACE_FINISH_INSPECTION, // 표면 마감 검사. 표면의 마감 상태 확인
    OPERATING_TIME_TEST, // 작동 시간 테스트. 제품의 작동 시간과 그 성능 평가
    EFFICIENCY_TEST, // 효율성 테스트. 제품의 효율성 측정
    SAFETY_INSPECTION, // 안전 검사. 제품의 안전성 확인
    PERFORMANCE_TEST, // 성능 검사. 제품의 전체적인 성능 검사
    PACKAGING_INSPECTION, // 포장 검사. 제품의 포장 상태와 포장 품질 검사
    CLEANLINESS_TEST // 청결 검사. 제품의 청결 상태 확인 및 위생 검사
}
