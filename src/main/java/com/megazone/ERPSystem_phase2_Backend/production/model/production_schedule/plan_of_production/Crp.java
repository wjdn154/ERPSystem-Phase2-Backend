package com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.plan_of_production;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "plan_of_production_crp")
@Table(name = "plan_of_production_crp")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Crp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id; //pk

    @Column(nullable = false)
    private String name; // CRP명

    @Column(nullable = true)
    private String remarks; // 추가 설명 또는 비고
}
