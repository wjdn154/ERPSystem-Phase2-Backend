package com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanOfMakeToStock {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id; //pk

    @Column(nullable = false)
    private String name; // 재고생산계획명

    @Column(nullable = true)
    private String remarks; // 추가 설명 또는 비고
}
