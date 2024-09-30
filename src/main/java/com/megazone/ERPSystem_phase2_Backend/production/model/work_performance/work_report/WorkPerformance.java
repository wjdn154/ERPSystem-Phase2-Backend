package com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.work_report;


import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.common_scheduling.ProductionOrder;
import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.goods_receipt.enums.WorkStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**작업실적
 * 생산된 총 수량을 기록함.
 * */
@Entity(name = "work_report_work_performance")
@Table(name = "work_report_work_performance")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkPerformance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id; // 고유 ID

    @Column(nullable = false)
    private String name;    // 작업실적명

    @Column(nullable = true)
    private String description;   // 추가 설명 또는 비고

    @Column(nullable = false)
    private BigDecimal actualQuantity;    // 실제 생산량

    /**인건비(작업에 투입된 시간 x 시간당 임금) -> 급여
     * 재료비(사용된 원자재의 양 x 원자재의 단가)
     * 장비 및 기계 사용 비용(장비의 유지보수 비용, 전력 사용 비용 등 )
     * 풀량품 처리 비용(불량품 수량 x 뷸량품 처리 단가)
     * */
    @Column(nullable = false)
    private BigDecimal WorkCost;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private WorkStatus workStatus;           // 작업 상태

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_daily_report_id")
    private WorkDailyReport workDailyReport;  // 하나의 일별 보고서에 여러 개의 작업 실적

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "production_order_id", nullable = false)
    private ProductionOrder productionOrder; // 연관 작업지시

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;               // 연관 품목 (코드, 이름)

}
