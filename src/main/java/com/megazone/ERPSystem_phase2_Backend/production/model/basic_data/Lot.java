package com.megazone.ERPSystem_phase2_Backend.production.model.basic_data;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.quality_control.InboundRegistration;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity(name="basic_data_lot")
@Table(name="basic_data_lot")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"previousLot", "inboundRegistration", "serialNoList"}) // 순환 참조 방지
public class Lot {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id; // 고유 ID

    @Column(nullable = false, unique = true)
    private String lotNumber; // 고유 로트 번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "previous_lot_id", nullable = true)
    private Lot previousLot;     // 이전 LOT

    @Column(nullable = true)
    private String remarks; // 추가 설명 또는 비고

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "item_id", nullable = false)
//    private Item item; // 제품(품목) 엔티티와의 연관관계
    @Column(nullable = false)
    private String product; // TODO 제품 연관관계

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "production_receipt_id", nullable = false)
    private InboundRegistration inboundRegistration; // 생산입고처리 엔티티와의 연관관계

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "warehouse_id")
//    private Inventory inventory; // TODO 창고 재고
    @Column(nullable = false)
    private String inventory; // TODO 재고 연관관계

    @OneToMany(mappedBy = "lot")
    private List<SerialNo> serialNoList = new ArrayList<>(); // 이 LOT에 속하는 Serial No. 목록

}
