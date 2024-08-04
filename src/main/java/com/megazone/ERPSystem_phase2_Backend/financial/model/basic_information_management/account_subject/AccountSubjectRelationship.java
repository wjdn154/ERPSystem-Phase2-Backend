package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * 계정과목 관계 테이블
 */
@Entity
@Table(name = "account_subject_relationship")
@Getter
@Setter
public class AccountSubjectRelationship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_subject_id", nullable = false)
    private AccountSubject accountSubject; // 계정과목 참조

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "related_account_subject_id", nullable = false)
    private AccountSubject relatedAccountSubject; // 관련 계정과목 참조

    @Column(nullable = false)
    private String relationshipType; // 관계 유형 (예: 부모, 자식 등)
}