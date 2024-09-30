package com.megazone.ERPSystem_phase2_Backend.financial.service.ledger;

import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.DailyAndMonthJournalSearchDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.DailyAndMonthJournalShowDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.general_voucher_entry.resolvedVoucher.ResolvedVoucherRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class DailyAndMonthJournalServiceImpl implements DailyAndMonthJournalService {
    private final ResolvedVoucherRepository resolvedVoucherRepository;

    @Override
    public Object dailyLedgerShow(DailyAndMonthJournalSearchDTO dto) {
        List<DailyAndMonthJournalShowDTO> dtoList = resolvedVoucherRepository.dailyLedgerList(dto);

        // 그룹화된 데이터를 저장할 맵
        Map<String, DailyAndMonthJournalShowDTO> groupedLedgerMap = new HashMap<>();

        for (DailyAndMonthJournalShowDTO item : dtoList) {
            // 그룹화 기준: accountStructureLargeCategory + accountStructureMediumCategory + accountCode
            String key = item.getAccountStructureMediumCategory() + "-" + item.getAccountStructureSmallCategory();
            // 기존에 해당 키가 있다면 값을 가져와서 합산
            DailyAndMonthJournalShowDTO groupedLedger = groupedLedgerMap.getOrDefault(key, new DailyAndMonthJournalShowDTO());

            // 차변, 대변 금액을 합산
            groupedLedger.setCashTotalDebit(groupedLedger.getCashTotalDebit().add(item.getCashTotalDebit()));
            groupedLedger.setCashTotalCredit(groupedLedger.getCashTotalCredit().add(item.getCashTotalCredit()));
            groupedLedger.setSubTotalDebit(groupedLedger.getSubTotalDebit().add(item.getSubTotalDebit()));
            groupedLedger.setSubTotalCredit(groupedLedger.getSubTotalCredit().add(item.getSubTotalCredit()));
            groupedLedger.setSumTotalDebit(groupedLedger.getSumTotalDebit().add(item.getSumTotalDebit()));
            groupedLedger.setSumTotalCredit(groupedLedger.getSumTotalCredit().subtract(item.getSumTotalCredit()));

            // 그룹화된 값을 다시 맵에 저장
            groupedLedgerMap.put(key, groupedLedger);
        }

        // 결과를 반환하기 전에 그룹화된 데이터를 리스트로 변환
        List<DailyAndMonthJournalShowDTO> groupedLedgerList = new ArrayList<>(groupedLedgerMap.values());

        return groupedLedgerList;
    }
}
