package com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.shipping_order;

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

    // 거래처 - 거래처_id 참조

    // 담당자 - 사원_id 참조

    // 출하창고 - 창고_id 참조

    // 판매_id 참조
    @Column(nullable = false)
    private Long sale_id;
    // 연락처

    // 출하예정일자
    @Column(nullable = false)
    private LocalDate deliveryDate;

    // 주소 - 배송 보낼 주소(거래처 주소가 될 수도 있음)
    @Column
    private String shippingAddress;

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
