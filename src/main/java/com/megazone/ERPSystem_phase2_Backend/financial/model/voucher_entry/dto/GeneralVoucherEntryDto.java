package com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.dto;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.AccountSubject;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.enums.VoucherType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeneralVoucherEntryDto implements Cloneable {

    private Long userCompanyId; // 유저 회사 ID

    private Long accountSubjectId; // 계정과목 참조

    private Long vendorId; // 거래처 참조

    private Long approvalManagerId; // 담당자 참조

    private Long descriptionId; // 적요 참조

    private String transactionDescription; // 거래 설명

    private VoucherType voucherType; // 전표 구분 ex) 입금, 출금, 차변, 대변

    private BigDecimal debitAmount; // 차변 금액

    private BigDecimal creditAmount; // 대변 금액

    private LocalDate voucherDate; // 전표 날짜 (거래발생일)

    private LocalDateTime voucherRegistrationTime; // 전표 등록 시간 (현재 날짜 및 시간)

    // 객체 데이터 깊은 복사 메소드
    @Override
    public GeneralVoucherEntryDto clone() throws CloneNotSupportedException {
        return (GeneralVoucherEntryDto)super.clone();
    }

}
