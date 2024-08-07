package com.megazone.ERPSystem_phase2_Backend.hr.model.hr_management;

// ������ ���� �� ���� ���̺�

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

// ������ ���� �� ���� ���̺�

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Performance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date evaluationDate; // 평가일

    @Column(nullable = false)
    private String score; // 평가 점수

    @Column(nullable = false)
    private String comments; // 평가 내용
}
