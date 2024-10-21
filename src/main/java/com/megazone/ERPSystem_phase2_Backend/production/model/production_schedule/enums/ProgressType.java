package com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

/**
 * 생산의뢰 또는 생산계획의 진행상태를 나타내는 Enum
 */
public enum ProgressType {

    /**
     * 작성: 생산의뢰 또는 생산계획이 초기 작성 단계에 있는 상태
     */
    CREATED("Created"),

    /**
     * 진행: 생산이 계획대로 정상적으로 진행되고 있는 상태
     */
    IN_PROGRESS("In Progress"),

    /**
     * 미진행: 생산이 시작되지 않은 상태, 준비 또는 대기 중인 상태
     */
    NOT_STARTED("Not Started"),

    /**
     * 진행중단: 생산이 일시적으로 중단된 상태
     */
    HALTED("Halted"),

    /**
     * 완료: 생산이 계획대로 완료된 상태
     */
    COMPLETED("Completed"),

    /**
     * 미완료: 생산이 예정된 기한 내에 완료되지 못한 상태
     */
    INCOMPLETE("Incomplete");

    private final String value;

    ProgressType(String value) {
        this.value = value;
    }

    @JsonCreator
    public static ProgressType fromValue(String value) {
        return Arrays.stream(ProgressType.values())
                .filter(type -> type.name().equalsIgnoreCase(value) || type.getValue().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 ProgressType: " + value));
    }

    @JsonValue
    public String getValue() {
        return this.value;
    }

}
