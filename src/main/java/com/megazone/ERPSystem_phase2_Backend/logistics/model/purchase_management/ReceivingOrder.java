package com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 구매서 테이블
 * 구매서에 대한 정보가 있는 테이블
 */
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceivingOrder {

    // 고유 식별자
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 구매_id 참조
    @Column
    private Long purchaseId;

    // 거래처_id - N : 1
    @Column(nullable = false)
    private Long clientId;

    // 사원_id - N : 1
    @Column(nullable = false)
    private Long employeeId;

    // 연락처
    @Column
    private String contact;

    // 입고될 창고_id - N : 1
    @Column(nullable = false)
    private Long warehouseId;

    // 품목_id - 1 : N
    @Column
    private Long productId;

    // 수량
    @Column(nullable = false)
    private Integer quantity;

    // 입고 예정 일자
    @Column(nullable = false)
    private LocalDate receivingDate;

    // 일자 - 입고지시서 입력 일자
    @Column(nullable = false)
    private LocalDate date;

    // 비고
    @Column
    private String remarks;

    // 진행상태 (진행중, 완료)
    @Column(nullable = false)
    private Boolean status;
}
