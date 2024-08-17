package com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.AccountSubject;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.enums.ApprovalStatus;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.enums.VoucherType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "resolved_voucher")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "resolved_voucher")
public class ResolvedVoucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @Column(name = "company_id", nullable = false)
//    private Long companyId; // 유저 회사 ID

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_code",referencedColumnName = "code", nullable = false)
    private AccountSubject accountSubject; // 계정과목 참조

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "vendor_id", nullable = false)
//    private String vendor; // 거래처 참조
//
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "voucherManager_id", nullable = false)
//    private String voucherManager; // 전표 담당자
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "description_id")
//    private String description; // 적요

    private String transactionDescription; // 거래 설명

    @Column(nullable = false)
    private String voucherNumber; // 전표 번호

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VoucherType voucherType; // 전표 구분 ex) 입금, 출금, 차변, 대변

    @Column(nullable = false)
    private BigDecimal debitAmount; // 차변 금액

    @Column(nullable = false)
    private BigDecimal creditAmount; // 대변 금액

    @Column(nullable = false)
    private LocalDate voucherDate; // 전표 날짜 (거래발생일)

    @Column
    private LocalDateTime voucherRegistrationTime; // 전표 등록 시간 (현재 날짜 및 시간)
}
