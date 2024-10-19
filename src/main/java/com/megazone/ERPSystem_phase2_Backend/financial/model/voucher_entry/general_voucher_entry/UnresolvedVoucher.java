package com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.AccountSubject;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.Client;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company.Company;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.enums.ApprovalStatus;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.enums.VoucherKind;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.enums.VoucherType;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.UnresolvedSaleAndPurchaseVoucher;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Employee;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 미결전표 관리 테이블
 * 일반전표 등록 시 승인되지 않은 미결전표로 등록된다.
 *
 */

@Entity(name = "unresolved_voucher")
@Data
@Table(name = "unresolved_voucher")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UnresolvedVoucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    private AccountSubject accountSubject; // 계정과목 참조

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client; // 거래처 참조

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "voucherManager_id", nullable = false)
    private Employee voucherManager; // 전표 담당자

    private String transactionDescription; // 거래 설명

    @Column(nullable = false)
    private Long voucherNumber; // 전표 번호

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApprovalStatus approvalStatus; // 승인상태 ( 승인대기 승인, 반려 )

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VoucherKind voucherKind; // 전표의 유형 ( 일반전표, 매출매입전표 )

}
