package com.megazone.ERPSystem_phase2_Backend.financial.service.voucher_entry.general_voucher_entry;

import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.dto.GeneralVoucherEntryDto;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.UnresolvedVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.enums.ApprovalStatus;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.general_voucher_entry.resolvedVoucher.ResolvedVoucherRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.general_voucher_entry.unresolvedVoucher.UnresolvedVoucherRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class GeneralVoucherEntryServiceImp implements GeneralVoucherEntryService {

    private final ResolvedVoucherRepository resolvedVoucherRepository;
    private final UnresolvedVoucherRepository unresolvedVoucherRepository;

    /**
     * 미결전표 등록 메소드
     * 일반 전표입력에서 전표 정보 입력시 일반전표를 생성하지않고 승인되지않은 전표 생성
     * @param dto 사용자가 일반전표 입력시 작성한 정보를 담은 전송객체
     * @return 생성된 미결전표 반환
     */

    public UnresolvedVoucher unresolvedVoucherEntry(UnresolvedVoucher unresolvedVoucher) {
        UnresolvedVoucher unresolvedVoucher = UnresolvedVoucher.builder()
                .userCompanyId(dto.getUserCompanyId())
                .accountSubjectId(accountSubject) // AccountSubject 객체 설정
                .vendorId(dto.getVendorId()) // Long 타입
                .approvalManagerId(dto.getApprovalManagerId()) // Long 타입
                .descriptionId(dto.getDescriptionId()) // String 타입
                .transactionDescription(dto.getTransactionDescription()) // String 타입
                .voucherNumber(dto.getVoucherNumber()) // Long 타입
                .voucherType(dto.getVoucherType()) // VoucherType enum 타입
                .debitAmount(dto.getDebitAmount()) // BigDecimal 타입
                .creditAmount(dto.getCreditAmount()) // BigDecimal 타입
                .voucherDate(dto.getVoucherDate()) // Date 타입
                .voucherRegistrationTime(LocalDateTime.now()) // 현재 날짜 및 시간
                .approvalStatus(ApprovalStatus.APPROVED) // ApprovalStatus enum 타입
                .build(); // 빌더 패턴으로 객체 생성

        // 미결전표 저장
        return unresolvedVoucherRepository.save(unresolvedVoucher); // 생성된 미결전표 반환
    }


}
