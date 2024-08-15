package com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.quality_control;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 생산 관리 담당자는 생산완료하여 품질검사를 통과한 품목에 한하여 LOT, Serial No. 부여 후 입고 처리 등록할 수 있어야 한다.
 * 단, 품질검사에 통과하지 못한 제품은 입고 등록이 불가능해야 한다. 입고 처리 시, 제품이 입고될 창고를 선택할 수 있어야 한다.
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InboundRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id; // 고유식별자

    @Column(nullable = false)
    private String code; // 고유코드

    @Column(nullable = false)
    private String title; // 입고처리지시명

    @Column(nullable = false)
    private Boolean isDone; // 처리상태

    // 품목

    // 품질검사 - 충족여부

    // LOT

    // Serial

    // 물류 창고 ( 입고 위치, 담당자 등 )

}