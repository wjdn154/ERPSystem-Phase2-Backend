package com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 출하지시서 테이블
 * 출하지시서에 대한 정보가 있는 테이블
 * 고객이 주문한 상품의 배송을 안내하는 지시서 - 물류쪽에 출하지시를 내림
 */
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShippingOrder {

    // 고유 식별자
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 판매_id 참조
    @Column
    private Long saleId;

    // 거래처_id - N : 1
    @Column(nullable = false)
    private Long clientId;

    // 사원_id - N : 1
    @Column(nullable = false)
    private Long employeeId;

    // 연락처
    @Column
    private String contact;

    // 출하 창고_id - N : 1
    @Column(nullable = false)
    private Long warehouseId;

    // 주소 - 배송 보낼 주소(거래처 주소가 될 수도 있음)
    @Column
    private String shippingAddress;

    // 품목_id - 1 : N
    @Column
    private Long productId;

    // 수량
    @Column(nullable = false)
    private Integer quantity;

    // 출하예정일자
    @Column(nullable = false)
    private LocalDate shippingDate;

    // 일자 - 출하지시서 입력 일자
    @Column(nullable = false)
    private LocalDate date;

    // 비고
    @Column
    private String remarks;

    // 진행상태 (진행중, 완료)
    @Column(nullable = false)
    private Boolean status;
}
