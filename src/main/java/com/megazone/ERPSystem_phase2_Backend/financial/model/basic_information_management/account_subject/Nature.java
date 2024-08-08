package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * 계정과목 성격 테이블
 */
@Entity(name = "account_subject_nature")
@Table(name = "account_subject_nature")
@Getter
@Setter
public class Nature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "accounting_subject_structure_id", nullable = false)
    private Structure structure; // 계정과목 체계 참조

    @Column(nullable = false)
    private String code; // 성격 코드

    @Column(nullable = false)
    private String name; // 성격 이름
}