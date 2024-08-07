package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject;

import jakarta.persistence.*;
import lombok.Getter;

/**
 * 대체적요 테이블
 */
@Entity
@Table(name = "account_subject_transfer_memo")
@Getter
public class AccountSubjectTransferMemo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 적요 ID

    @Column(nullable = false)
    private String content; // 적요 내용

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private AccountSubject accountSubject; // 참조하는 계정과목
}