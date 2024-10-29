package com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management;


import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalePlanDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_plan_id", nullable = false)
    private SalePlan salePlan;

    // 품목
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // 수량
    @Column(nullable = true)
    private Integer quantity;

    // 예상 매출액
    @Column
    private BigDecimal expectedSales;

    @Column
    private String remarks;

}
