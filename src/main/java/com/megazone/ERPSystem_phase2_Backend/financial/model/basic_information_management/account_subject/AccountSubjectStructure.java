package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * 계정체계 테이블
 */
@Entity
@Table(name = "account_subject_structure")
@Getter
@Setter
public class AccountSubjectStructure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 계정체계 ID

    @Column(unique = true, nullable = false)
    private String code; // 계정체계 코드
    @Column(nullable = false)
    private String name; // 계정체계명
    @Column(nullable = false)
    private Integer min; // 최소값
    @Column(nullable = false)
    private Integer max; // 최대값
}