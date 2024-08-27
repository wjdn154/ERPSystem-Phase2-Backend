package com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 * 판매서 테이블
 * 판매서에 대한 정보가 있는 테이블
 * 등록된 주문을 바탕으로 실제 판매를 등록
 */
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Sale {

    // 고유 식별자
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 주문서_id 조인
//    @OneToMany(mappedBy = "")
//    @JoinColumn(name = "order_id", nullable = false)
    private Long orderId;

    // 거래처_id - N : 1
    @Column(nullable = false)
    private Long clientId;

    // 사원_id - N : 1
    @Column(nullable = false)
    private Long employeeId;

    // 창고_id - N : 1
    @Column(nullable = false)
    private Long warehouseId;

    // 통화_id - N : 1
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "currency_id", nullable = false)
    private Long currencyId;

    // 품목_id - 1 : N
    @Column
    private Long productId;

    // 수량
    @Column(nullable = false)
    private Integer quantity;

    // 공급가액 - 수량 * 단가
    @Column(nullable = false)
    private Double supplyPrice;

    // 부가세율 적용 or 미적용
    @Column(nullable = false)
    private Boolean vatType;

    // 부가세 - 공급가액의 10%
    @Column
    private Double vat;

    // 일자
    @Column(nullable = false)
    private LocalDate date;

    // 비고
    @Column
    private String remarks;
}
