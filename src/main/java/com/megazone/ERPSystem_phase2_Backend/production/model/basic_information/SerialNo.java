
package com.megazone.ERPSystem_phase2_Backend.production.model.basic_information;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

/**
 * 개별 제품이나 구성 요소를 식별하는 고유 번호 부여 엔티티
 * 개별 제품 추적하여 특정 생산품의 상태, 위치, 품질 상태 등 추적 위함
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SerialNo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 고유 ID

    @Column(nullable = false, unique = true)
    private String serialNumber; // 고유 일련번호

    @Column(nullable = false)
    private LocalDate productionDate; // 생산일자

    @Column(nullable = true)
    private LocalDate expirationDate; // 유효기간 (있는 경우)

    @Column(nullable = false)
    private String status; // 상태 (예: '생산', '출고', '반품', '폐기' 등)

    // 연관관계 필드
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lot_id", nullable = false)
    private Lot lot; // LOT 엔티티와의 연관관계

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "warehouse_id")
//    private Warehouse warehouse; // 창고 엔티티와의 연관관계
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "quality_inspection_id")
//    private QualityInspection qualityInspection; // 품질 검사 정보와의 연관관계
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "customer_id")
//    private Customer customer; // 고객사 정보와의 연관관계 (회계모듈)
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "sales_order_id")
//    private SalesOrder salesOrder; // 판매관리 정보와의 연관관계 (판매관리 모듈)
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "shipment_id")
//    private Shipment shipment; // 운송관리 정보와의 연관관계 (운송관리 모듈)

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "return_id")
//    private Return return; // 반품관리 정보와의 연관관계 (반품관리 모듈 - 반품일자, 사유, 상태)

}
