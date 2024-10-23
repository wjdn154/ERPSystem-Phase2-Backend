package com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.work_report;


import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.common_scheduling.ProductionOrder;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**작업실적
 * 특정 작업 지시를 통해 만들어진 제품들의 총 생산량을 나타냄.
 * 즉, 하나의 작업실적은 하나의 제품 타입(하나의 product)에 대한 생산 실적을 의미하며, 그 생산된 총량을 기록됨.
 * 작업 실적은 제품의 묶음 전체를 다룸.
 * */
@Entity(name = "work_report_work_performance")
@Table(name = "work_report_work_performance")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkPerformance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(nullable = false) private Long id; // 고유 ID
    @Column(nullable = false) private BigDecimal quantity; // 실적 수량
    @Column(nullable = false) private BigDecimal defectiveQuantity; //불량 수량
    @Column(nullable = false) private BigDecimal acceptableQuantity; // 양품 수량
    @Column(nullable = false) private LocalDate workDate; //작업 시간
    @Column(nullable = false) private Long workers; //작업 인원

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "production_order_id", nullable = false)
    private ProductionOrder productionOrder; // 연관 작업지시

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product; // 연관 품목 (코드, 이름)
}

/**인건비(작업에 투입된 시간 x 시간당 임금) -> 급여
 * 재료비(사용된 원자재의 양 x 원자재의 단가)
 * 장비 및 기계 사용 비용(장비의 유지보수 비용, 전력 사용 비용 등 )
 * 풀량품 처리 비용(불량품 수량 x 뷸량품 처리 단가)
 * */


/**
 * 위 내역을 가지고 WorkDailyReport에서 아래 내용 계산 가능
 * 생산 효율성 (%) = (actualQuantity / 계획 생산량) × 100
 * 단위당 생산 비용 = workCost / actualQuantity
 * 인력 생산성 = actualQuantity / workers
 * 불량률 (%) = (dailyDefectiveQuantity / dailyProductionQuantity) × 100 << 품질관리 제외하여 불가
 * 작업시간 = ProductionOrder.endDateTime - startDateTime
 */