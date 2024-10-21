package com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.enums;

/**
 * 생산의뢰 또는 생산계획의 진행상태를 나타내는 Enum
 *
 * 생산 의뢰(ProductionRequest) 생성 시 → ProgressType.CREATED 상태로 자동 설정.
 * 생산 의뢰(ProductionRequest) 확정(isConfirmed = true && confirmedQuantity 입력) 시 → ProgressType.NOT_STARTED 상태로 전환 및 MPS 생성.
 * MPS(주생산계획)를 통해 작업지시(ProductionOrder) 생성 시 → ProgressType.IN_PROGRESS로 자동 전환.
 * 생산 계획이 완료되면 → ProgressType.COMPLETED 로 자동 전환.
 * 생산 계획이 기한 내 완료되지 않으면 → ProgressType.INCOMPLETE 으로 자동 전환.
 * 작업(WorkPerformance의 WorkStatus가 ON_HOLD, FAILED, CANCELLED) 중단 시 → ProgressType.HALTED 로 자동 전환.

 */
public enum ProgressType {

    /**
     * 작성: 생산의뢰 또는 생산계획이 초기 작성 단계에 있는 상태
     */
    CREATED("작성"),

    /**
     * 진행: 생산이 계획대로 정상적으로 진행되고 있는 상태
     */
    IN_PROGRESS("진행"),

    /**
     * 미진행: 생산이 시작되지 않은 상태, 준비 또는 대기 중인 상태
     */
    NOT_STARTED("미진행"),

    /**
     * 진행중단: 생산이 일시적으로 중단된 상태
     */
    HALTED("진행중단"),

    /**
     * 완료: 생산이 계획대로 완료된 상태
     */
    COMPLETED("완료"),

    /**
     * 미완료: 생산이 예정된 기한 내에 완료되지 못한 상태
     */
    INCOMPLETE("미완료");

    private final String description;

    // ENUM 생성자
    ProgressType(String description) {
        this.description = description;
    }

    // 상태 설명을 반환하는 메소드
    public String getDescription() {
        return description;
    }
}
