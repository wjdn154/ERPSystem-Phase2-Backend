package com.megazone.ERPSystem_phase2_Backend.financial.service.ledger;

import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.*;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.general_voucher_entry.resolvedVoucher.ResolvedVoucherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountLedgerServiceImpl implements AccountLedgerService {
    private final ResolvedVoucherRepository resolvedVoucherRepository;

    @Override
    public List<AccountLedgerAccListDTO> getAccountLedgerAccList(AccountLedgerSearchDTO dto) {
        return resolvedVoucherRepository.accountLedgerList(dto);
    }

    @Override
    public AccountLedgerShowAllListDTO getAccountLedgerShow(AccountLedgerDetailEntryDTO dto) {
// 전기이월금 임의로 설정
        BigDecimal previousTotalDebitAmount = BigDecimal.ZERO;
        BigDecimal previousTotalCreditAmount = BigDecimal.ZERO;
        BigDecimal previousTotalCashAmount = new BigDecimal("1000000000"); // 전기이월금 임시설정

// 쿼리 실행 결과 가져오기
        List<AccountLedgerShowDTO> result = resolvedVoucherRepository.accountLedgerDetail(dto);

        BigDecimal cumulativeTotalDebitAmount = BigDecimal.ZERO;      // 누계 입금
        BigDecimal cumulativeTotalCreditAmount = BigDecimal.ZERO;     // 누계 출금
        BigDecimal cumulativeTotalCashAmount = previousTotalCashAmount; // 누계 잔액

        YearMonth currentMonth = null;
        List<AccountLedgerShowDTO> currentMonthList = new ArrayList<>();
        List<AccountLedgerShowAllDTO> monthlyCashJournalList = new ArrayList<>();

        BigDecimal monthlyTotalDepositAmount = BigDecimal.ZERO;         // 월계 입금
        BigDecimal monthlyTotalWithdrawalAmount = BigDecimal.ZERO;      // 월계 출금

        for (AccountLedgerShowDTO dtoItem : result) {
            YearMonth itemMonth = YearMonth.from(dtoItem.getVoucherDate());

            // 새로운 달로 넘어갈 때
            if (currentMonth != null && !itemMonth.equals(currentMonth)) {
                // 월계 및 누계 계산
                AccountLedgerShowAllDTO monthlyDTO = AccountLedgerShowAllDTO.create(
                        currentMonthList,
                        monthlyTotalDepositAmount,
                        monthlyTotalWithdrawalAmount,
                        monthlyTotalDepositAmount.subtract(monthlyTotalWithdrawalAmount), // 월계 잔액
                        cumulativeTotalDebitAmount,
                        cumulativeTotalCreditAmount,
                        cumulativeTotalCashAmount
                );
                monthlyCashJournalList.add(monthlyDTO);
                currentMonthList = new ArrayList<>();

                // 월계 초기화
                monthlyTotalDepositAmount = BigDecimal.ZERO;
                monthlyTotalWithdrawalAmount = BigDecimal.ZERO;
            }

            // 입출금에 따른 누적 계산
            cumulativeTotalDebitAmount = cumulativeTotalDebitAmount.add(dtoItem.getDebitAmount());
            cumulativeTotalCreditAmount = cumulativeTotalCreditAmount.add(dtoItem.getCreditAmount());
            cumulativeTotalCashAmount = cumulativeTotalCashAmount.add(dtoItem.getDebitAmount()).subtract(dtoItem.getCreditAmount());

            // 월계 계산
            monthlyTotalDepositAmount = monthlyTotalDepositAmount.add(dtoItem.getDebitAmount());
            monthlyTotalWithdrawalAmount = monthlyTotalWithdrawalAmount.add(dtoItem.getCreditAmount());

            // 잔액을 cashAmount에 설정
            dtoItem.setCashAmount(cumulativeTotalCashAmount);

            // 현재 월 리스트에 추가
            currentMonthList.add(dtoItem);
            currentMonth = itemMonth;
        }

// 마지막 달 처리
        if (!currentMonthList.isEmpty()) {
            AccountLedgerShowAllDTO monthlyDTO = AccountLedgerShowAllDTO.create(
                    currentMonthList,
                    monthlyTotalDepositAmount,
                    monthlyTotalWithdrawalAmount,
                    monthlyTotalDepositAmount.subtract(monthlyTotalWithdrawalAmount), // 월계 잔액
                    cumulativeTotalDebitAmount,
                    cumulativeTotalCreditAmount,
                    cumulativeTotalCashAmount
            );
            monthlyCashJournalList.add(monthlyDTO);
        }

// 최종적으로 누적 및 월계 결과 반환
        return AccountLedgerShowAllListDTO.create(
                previousTotalDebitAmount,
                previousTotalCreditAmount,
                previousTotalCashAmount,
                monthlyCashJournalList
        );
    }
}
