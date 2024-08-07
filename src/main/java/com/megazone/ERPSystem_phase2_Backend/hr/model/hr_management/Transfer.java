package com.megazone.ERPSystem_phase2_Backend.hr.model.hr_management;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

// 발령 기록 저장

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date transferDate; // 발령 날짜

    @Column(nullable = false)
    private String transferType; // 발령 유형 ( promotion :, demotion, lateral )

    @Column(nullable = false)
    private String reason; // 발령 이유
}
