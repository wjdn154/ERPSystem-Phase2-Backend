package com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

/**
 * 생산의뢰구분을 위한 ENUM
 *
 * - 양산(Mass Production): 대량으로 생산하는 표준화된 생산 방식.
 * - 시험양산(Pilot Production): 대량 생산 전, 제품 품질 확인을 위한 시험용 생산.
 * - 특급수주(Urgent Order): 긴급한 납기 요구로 신속하게 처리되어야 하는 생산 요청.
 * - 샘플(Sample Production): 고객 또는 내부 요청으로 소량의 샘플을 제작.
 * - PMS(Project Management System): 프로젝트 단위로 관리되는 특별한 생산 요청.
 */

public enum ProductionRequestType {

    /**
     * 양산: 대량으로 제품을 생산하는 일반적인 생산 방식
     */
    MASS_PRODUCTION("Mass Production"),

    /**
     * 시험양산: 대량 생산에 앞서 품질 확인을 위해 진행하는 소규모 시험 생산
     */
    PILOT_PRODUCTION("Pilot Production"),

    /**
     * 특급수주: 납기 기한이 급한 고객 주문으로 신속한 생산이 필요한 경우
     */
    URGENT_ORDER("Urgent Order"),

    /**
     * 샘플: 고객이나 내부에서 요구한 소량의 샘플 제품 생산
     */
    SAMPLE("Sample"),

    /**
     * PMS: 프로젝트 단위로 진행되는 생산 의뢰 (예: 특정 고객사 프로젝트)
     */
    PMS("PMS");

    private final String value;

    ProductionRequestType(String value) {
        this.value = value;
    }

    @JsonCreator
    public static ProductionRequestType fromValue(String value) {
        return Arrays.stream(ProductionRequestType.values())
                .filter(type -> type.name().equalsIgnoreCase(value) || type.getValue().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 ProductionRequestType: " + value));
    }

    @JsonValue
    public String getValue() {
        return this.value;
    }

}
