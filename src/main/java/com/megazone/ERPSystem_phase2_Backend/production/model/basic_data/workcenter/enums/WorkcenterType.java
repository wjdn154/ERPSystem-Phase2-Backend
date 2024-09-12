package com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.workcenter.enums;

// 작업장 유형을 나타내는 enum class

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum WorkcenterType {
    PRESS("Press"),        // 금속 판재를 성형
    WELDING("Welding"), // 용접 작업장: 금속 부품 결합
    PAINT("Paint"), // 차체 도장 및 방청
    MACHINING("Machining"), // 금속 부품 정밀 가공
    ASSEMBLY("Assembly"),  // 부품 및 모듈 조립
    QUALITY_INSPECTION("Quality Inspection"), // 품질 검사 및 확인
    CASTING("Casting"),               // 금속 녹여 성형
    FORGING("Forging"),               // 단조 작업장: 금속을 두드려 성형
    HEAT_TREATMENT("Heat Treatment"),       // 금속 열처리
    PLASTIC_MOLDING("Plastic Molding"); // 플라스틱 부품 성형

    private final String name;

    WorkcenterType(String name) {
        this.name = name;
    }

    @JsonCreator
    public static WorkcenterType of(String type) {
        return Arrays.stream(WorkcenterType.values())
                .filter(i -> i.name.equalsIgnoreCase(type))  // 대소문자 구분 없이 매칭
                .findAny()
                .orElse(null);
    }

    @JsonValue
    public String getName() {
        return this.name;
    }

}
