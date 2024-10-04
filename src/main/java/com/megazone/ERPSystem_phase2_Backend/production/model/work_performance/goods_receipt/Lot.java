package com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.goods_receipt;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
/**특정 제품의 생산 또는 입고 단위. 각 lot에 대한 정보와 그에 속하는 serial 번호 리스트르 참조함.
 * */
@Entity(name="basic_data_lot")
@Table(name="basic_data_lot")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"previousLot", "serialNoList"}) // 순환 참조 방지
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

    @OneToMany(mappedBy = "lot")
    private List<SerialNo> serialNoList = new ArrayList<>(); // 이 LOT에 속하는 Serial No. 목록

}
