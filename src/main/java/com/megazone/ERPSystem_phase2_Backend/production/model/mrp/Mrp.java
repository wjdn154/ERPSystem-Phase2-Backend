package com.megazone.ERPSystem_phase2_Backend.production.model.mrp;

import com.megazone.ERPSystem_phase2_Backend.production.model.mrp.bom.StandardBom;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.PlanOfMakeToStock;
import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.ProcessDetails;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 총 소요량 = 공정별 작업계획수량 × 해당공정소요수량 + (해당공정소요수량 × 로스율)
 */

@Entity(name = "mrp_mrp")
@Table(name = "mrp_mrp")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mrp {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id; // 고유식별자

    @Column(nullable = false, unique = true)
    private String code; // MRP코드 (식별자와 별도의 지정코드)

    @Column(nullable = false)
    private String name; // MRP명

    @Column(nullable = true)
    private String remarks; // MRP 설명

    @Column(nullable = false)
    private Boolean isActive; // 사용 여부

    @ManyToOne(fetch = FetchType.LAZY) // 다대일 관계 설정
    @JoinColumn(name = "standard_bom_id", nullable = false)
    private StandardBom standardBom; // BOM

    @ManyToOne(fetch = FetchType.LAZY) // 다대일 관계 설정
    @JoinColumn(name = "process_details_id", nullable = false)
    private ProcessDetails processDetails; // 공정 자재

    @Column(nullable = false)
    private LocalDateTime dueDate; // 자재소요마감일자

    @ManyToOne(fetch = FetchType.LAZY) // 다대일 관계 설정
    @JoinColumn(name = "plan_of_make_to_stock_id", nullable = false)
    private PlanOfMakeToStock planOfMakeToStock; // 연관 재고 기반 생산계획

    @Column(nullable = false)
    private String inventory; // TODO 연관 재고  N: 1
}
