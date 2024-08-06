package com.megazone.ERPSystem_phase2_Backend.hr.model.hr_management;

// 직원의 성과 평가 정보 테이블

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

// 직원의 성과 평가 정보 테이블

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Performance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date evaluationDate; // 평가 날짜

    @Column(nullable = false)
    private String score; // 성과 점수

    @Column(nullable = false)
    private String comments; // 평가 코멘트
}
