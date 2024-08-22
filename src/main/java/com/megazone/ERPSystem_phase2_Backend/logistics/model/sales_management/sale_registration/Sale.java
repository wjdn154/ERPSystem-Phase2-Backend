package com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.sale_registration;

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

    // 사원_id 참조
    @Column
    private Long employeeId;

    // 일자
    @Column(nullable = false)
    private LocalDate date;

    // 비고
    @Column
    private String remarks;
}
