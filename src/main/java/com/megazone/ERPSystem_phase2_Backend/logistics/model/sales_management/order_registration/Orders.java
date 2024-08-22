package com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.order_registration;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 * 주문서 테이블
 * 주문서에 대한 정보가 있는 테이블
 */
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Orders {

    // 고유 식별자
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 견적서_id 참조
//    @OneToMany(mappedBy = "")
//    @JoinColumn(name = "quotation_id", nullable = false)
    @Column(nullable = false)
    private Long quotationId;

    // 사원_id 참조
    @Column
    private Long employeeId;

    // 일자
    @Column(nullable = false)
    private LocalDate date;

    // 납기 일자
    @Column(nullable = false)
    private LocalDate deliveryDate;

    // 비고
    @Column
    private String remarks;

}
