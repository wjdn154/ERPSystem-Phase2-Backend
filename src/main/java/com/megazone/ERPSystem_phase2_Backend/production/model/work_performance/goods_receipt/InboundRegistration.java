package com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.goods_receipt;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.Warehouse;
import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.quality_control.QualityInspection;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 생산 관리 담당자는 생산완료하여 품질검사를 통과한 품목(완제품)에 한하여 LOT, Serial No. 부여 후 입고 처리 등록할 수 있어야 한다.
 * 단, 품질검사에 통과하지 못한 제품은 입고 등록이 불가능해야 한다. 입고 처리 시, 제품이 입고될 창고를 선택할 수 있어야 한다.
 * 품질검사 통과여부에서 true인것만 입고 등록 가능.
 */

@Entity(name = "quality_control_inbound_registration")
@Table(name = "quality_control_inbound_registration")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class completeProduct {

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
    private QualityInspection qualityInspection; // 연관 품질검사

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lot_id")
    private Lot lot;                        // LOT 엔티티

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;           // 하나의 창고에 여러개의 품목

    public boolean canRegister(){
        return qualityInspection != null && qualityInspection.getIsPassed();
    }

}
