package com.megazone.ERPSystem_phase2_Backend.production.model.work_order;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 작업지시 엔티티
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkOrderInstruction {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id; // 고유 ID

    @Column(nullable = false)
    private String name; // 작업지시명

    @Column(nullable = true)
    private String remarks; // 추가 설명 또는 비고

}
