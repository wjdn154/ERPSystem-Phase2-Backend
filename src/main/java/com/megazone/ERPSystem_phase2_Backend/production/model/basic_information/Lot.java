package com.megazone.ERPSystem_phase2_Backend.production.model.basic_information;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lot {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id; // 고유 ID

    @Column(nullable = false, unique = true)
    private String lotNumber; // 고유 로트 번호

    @Column(nullable = true)
    private String remarks; // 추가 설명 또는 비고

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "item_id", nullable = false)
//    private Item item; // 제품(품목) 엔티티와의 연관관계
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "production_receipt_id", nullable = false)
//    private ProductionReceipt productionReceipt; // 생산입고처리 엔티티와의 연관관계
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "warehouse_id")
//    private Warehouse warehouse; // 보관 창고 위치와의 연관관계
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "quality_inspection_id")
//    private QualityInspection qualityInspection; // 품질 검사 정보와의 연관관계

    @OneToMany(mappedBy = "lot")
    private List<SerialNo> serialNoList; // 이 LOT에 속하는 Serial No. 목록

//    @OneToMany(mappedBy = "lot")
//    private List<Inventory> inventories; // 재고 정보와의 연관관계
}
