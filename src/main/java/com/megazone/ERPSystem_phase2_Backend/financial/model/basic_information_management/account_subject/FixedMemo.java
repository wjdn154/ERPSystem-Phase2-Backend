package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 고정적요 테이블
 */
@Entity(name = "account_subject_fixed_memo")
@Table(name = "account_subject_fixed_memo")
@ToString
@Getter
@Setter
public class FixedMemo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 적요 ID

    private String code; // 적요 코드

    @Column(nullable = false)
    private String category; // 적요구분

    @Column(nullable = false)
    private String content; // 적요 내용

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_subject_code", referencedColumnName = "code")
    private AccountSubject accountSubject; // 참조하는 계정과목

    public FixedMemo(Long id, String category, String content) {
        this.id = id;
        this.category = category;
        this.content = content;
    }

    public FixedMemo() {

    }
}