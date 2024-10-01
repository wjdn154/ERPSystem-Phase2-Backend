package com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

// 직원의 성과 평가 정보

@Data
@Entity(name="employee_performance")
@Table(name="employee_performance")
@NoArgsConstructor
@AllArgsConstructor
public class Performance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee; // 평가받는 사원

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluator_id", nullable = false)
    private Employee evaluator; // 평가자

    @Column(nullable = false)
    private String title; // 평가 제목

    @Column(nullable = false)
    private LocalDate evaluationDate; // 평가일

    @Column(nullable = false)
    private String score; // 평가 점수

    @Column(nullable = false)
    private String comments; // 평가 내용
}
