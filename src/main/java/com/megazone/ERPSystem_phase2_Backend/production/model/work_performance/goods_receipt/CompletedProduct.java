package com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.goods_receipt;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.Warehouse;
import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.quality_control.QualityInspection;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 품질검사에서 통과한 개별 제품을 나타내는것이 아니라 통과한 제품의 묶음을 나타냄.
 * 하나의 제품 묶음 (또는 생산된 총 제품량의 일부)이 품질검사를 통과한 경우를 나타내며, 이 제품 묶음은 lot, serialNo를 부여받게 됨.
 * 생산 관리 담당자는 생산완료하여 품질검사를 통과한 품목(완제품)에 한하여 LOT, Serial No. 부여
 * 단, 품질검사에 통과하지 못한 제품은 입고 등록이 불가능해야 한다. 품질검사 통과여부에서 true인것만 입고 등록 가능.
 *
 */

@Entity(name = "quality_control_complete_product")
@Table(name = "quality_control_complete_product")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompletedProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;               // 고유식별자

    @Column(nullable = false)
    private String code;           // 고유코드

    @Column(nullable = false)
    private String title;          // 입고처리지시명

    @Column(nullable = false)
    private Boolean isDone;        // 처리상태

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;           //하나의 품목에 여러개의 완제품

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quality_inspection_id")
    private QualityInspection qualityInspection; // 하나의 품질검사에 여러개의 완제품

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lot_id")
    private Lot lot;                        // LOT 엔티티

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;           // 하나의 창고에 여러개의 품목


}
