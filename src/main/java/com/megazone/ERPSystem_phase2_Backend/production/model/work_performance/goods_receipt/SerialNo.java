package com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.goods_receipt;

import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.goods_receipt.enums.SerialStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

/**
 * 개별 제품이나 구성 요소를 식별하는 고유 번호 부여 엔티티
 * 개별 제품 추적하여 특정 생산품의 상태, 위치, 품질 상태 등 추적 위함
 * 개별 serial 번호를 lot에 연결함.
 */

@Entity(name="basic_data_serial_no")
@Table(name="basic_data_serial_no")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SerialNo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id; // 고유 ID

    @Column(nullable = false, unique = true)
    private String serialNumber; // 고유 일련번호

    @Column(nullable = false)
    private LocalDate productionDate; // 생산일자

    @Column(nullable = true)
    private LocalDate expirationDate; // 유효기간 (있는 경우)

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SerialStatus status; // 상태 (예: '생산', '재고', '출고', '반품', '폐기' 등)

    // 연관관계 필드
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lot_id", nullable = false)
    private Lot lot; // LOT 엔티티와의 연관관계



}