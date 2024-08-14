package com.megazone.ERPSystem_phase2_Backend.production.model.work_performance;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkInspections {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id; // 고유식별자

    @Column(nullable = false)
    private String code; // 지정검사코드 (식별자와 별도의 지정코드)

    @Column(nullable = false)
    private String name; // 실적검사명

    @Column(nullable = true)
    private String description; // 검사 설명

    @Column(nullable = false)
    private Boolean isPassed; // 검사통과여부
}
