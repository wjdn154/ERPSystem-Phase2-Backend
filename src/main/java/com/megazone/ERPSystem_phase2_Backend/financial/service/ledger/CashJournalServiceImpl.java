package com.megazone.ERPSystem_phase2_Backend.financial.service.ledger;

import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.CashJournalSearchDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.CashJournalShowAllDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.CashJournalShowAllListDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.CashJournalShowDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.general_voucher_entry.resolvedVoucher.ResolvedVoucherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CashJournalServiceImpl implements CashJournalService {
    private final ResolvedVoucherRepository resolvedVoucherRepository;

    @Override
    public CashJournalShowAllListDTO showAll(CashJournalSearchDTO dto) {
        // 전기이월금 임의로 설정
        BigDecimal previousTotalDepositAmount = BigDecimal.ZERO;
        BigDecimal previousTotalWithdrawalAmount = BigDecimal.ZERO;
        BigDecimal previousTotalCashAmount = new BigDecimal("1000000000"); // 전기이월금 임시설정

        // 쿼리 실행 결과 가져오기
        List<CashJournalShowDTO> result = resolvedVoucherRepository.cashJournalShow(dto);

        List<CashJournalShowAllDTO> monthlyCashJournalList = new ArrayList<>();

        BigDecimal cumulativeTotalDepositAmount = BigDecimal.ZERO;      // 누계 입금
        BigDecimal cumulativeTotalWithdrawalAmount = BigDecimal.ZERO;   // 누계 출금
        BigDecimal cumulativeTotalCashAmount = previousTotalCashAmount; // 누계 잔액

        YearMonth currentMonth = null;
        List<CashJournalShowDTO> currentMonthList = new ArrayList<>();


        BigDecimal monthlyTotalDepositAmount = BigDecimal.ZERO;         // 월계 입금
        BigDecimal monthlyTotalWithdrawalAmount = BigDecimal.ZERO;      // 월계 출금

        for (CashJournalShowDTO dtoItem : result) {
            YearMonth itemMonth = YearMonth.from(dtoItem.getVoucherDate());

            // 새로운 달로 넘어갈 때
            if (currentMonth != null && !itemMonth.equals(currentMonth)) {
                // 월계 및 누계 계산
                CashJournalShowAllDTO monthlyDTO = CashJournalShowAllDTO.create(
                        currentMonthList,
                        monthlyTotalDepositAmount,
                        monthlyTotalWithdrawalAmount,
                        monthlyTotalDepositAmount.subtract(monthlyTotalWithdrawalAmount), // 월계 잔액
                        cumulativeTotalDepositAmount,
                        cumulativeTotalWithdrawalAmount,
                        cumulativeTotalCashAmount
                );
                monthlyCashJournalList.add(monthlyDTO);
                currentMonthList = new ArrayList<>();

                // 월계 초기화
                monthlyTotalDepositAmount = BigDecimal.ZERO;
                monthlyTotalWithdrawalAmount = BigDecimal.ZERO;
            }

            // 입출금에 따른 누적 계산
            cumulativeTotalDepositAmount = cumulativeTotalDepositAmount.add(dtoItem.getDepositAmount());
            cumulativeTotalWithdrawalAmount = cumulativeTotalWithdrawalAmount.add(dtoItem.getWithdrawalAmount());
            cumulativeTotalCashAmount = cumulativeTotalCashAmount.add(dtoItem.getDepositAmount()).subtract(dtoItem.getWithdrawalAmount());

            // 월계 계산
            monthlyTotalDepositAmount = monthlyTotalDepositAmount.add(dtoItem.getDepositAmount());
            monthlyTotalWithdrawalAmount = monthlyTotalWithdrawalAmount.add(dtoItem.getWithdrawalAmount());

            // 잔액을 cashAmount에 설정
            dtoItem.setCashAmount(cumulativeTotalCashAmount);

            currentMonthList.add(dtoItem);
            currentMonth = itemMonth;
        }

        // 마지막 달 처리
        if (!currentMonthList.isEmpty()) {
            CashJournalShowAllDTO monthlyDTO = CashJournalShowAllDTO.create(
                    currentMonthList,
                    monthlyTotalDepositAmount,
                    monthlyTotalWithdrawalAmount,
                    monthlyTotalDepositAmount.subtract(monthlyTotalWithdrawalAmount), // 월계 잔액
                    cumulativeTotalDepositAmount,
                    cumulativeTotalWithdrawalAmount,
                    cumulativeTotalCashAmount
            );
            monthlyCashJournalList.add(monthlyDTO);
        }

        return CashJournalShowAllListDTO.create(
                previousTotalDepositAmount,
                previousTotalWithdrawalAmount,
                previousTotalCashAmount,
                monthlyCashJournalList
        );
    }
}
