package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * 계정과목 체계 테이블
 */
@Entity(name = "account_subject_structure")
@Table(name = "account_subject_structure")
@Getter
@Setter
public class Structure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 계정과목 체계 ID

    @Column(unique = true, nullable = false)
    private String code; // 계정과목 체계 코드
    @Column(nullable = false)
    private String name; // 계정과목 체계명
    @Column(nullable = false)
    private Integer min; // 최소값
    @Column(nullable = false)
    private Integer max; // 최대값
}