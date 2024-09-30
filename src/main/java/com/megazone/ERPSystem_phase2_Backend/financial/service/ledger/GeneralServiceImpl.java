package com.megazone.ERPSystem_phase2_Backend.financial.service.ledger;

import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.*;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.general_voucher_entry.resolvedVoucher.ResolvedVoucherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class GeneralServiceImpl implements GeneralService {
    private final ResolvedVoucherRepository resolvedVoucherRepository;

    @Override
    public List<GeneralAccountListDTO> getGeneralShow(GeneralDTO dto) {
        List<GeneralAccountListDTO> generalAccountListDTOS = resolvedVoucherRepository.generalList(dto);
        return generalAccountListDTOS;
    }

    @Override
    public GeneralShowAllDTO getGeneralSelectShow(GeneralSelectDTO dto) {
        // 임시 초기잔액 설정
        BigDecimal previousDebitAmount= BigDecimal.valueOf(0);
        BigDecimal previousCreditAmount= BigDecimal.valueOf(0);
        BigDecimal previousCashAmount= BigDecimal.valueOf(1000000000);
        BigDecimal totalDebitAmount= previousDebitAmount;
        BigDecimal totalCreditAmount = previousCreditAmount;
        BigDecimal totalCashAmount = previousCashAmount;

        List<GeneralShowDTO> generalSelectDTOS = resolvedVoucherRepository.generalSelectShow(dto);

        GeneralShowAllDTO generalShowAllDTO = new GeneralShowAllDTO();
        generalShowAllDTO.showInit(dto.getEndDate().getMonthValue());

        // 월별로 누적 잔액 계산
        for (GeneralShowDTO generalShowDTO : generalSelectDTOS) {
            // 월별 누적 잔액 계산
            totalCashAmount = totalCashAmount.add(generalShowDTO.getTotalDebit())
                    .subtract(generalShowDTO.getTotalCredit());
            totalDebitAmount = totalDebitAmount.add(generalShowDTO.getTotalDebit());
            totalCreditAmount = totalCreditAmount.add(generalShowDTO.getTotalCredit());

            // 월별 누적 잔액 설정
            generalShowDTO.setTotalCash(totalCashAmount);

            // 월별 데이터 저장
            generalShowAllDTO.getAllShows().put(generalShowDTO.getMonth(), generalShowDTO);
        }
        generalShowAllDTO.setPreviousDebitAmount(previousDebitAmount);
        generalShowAllDTO.setPreviousCreditAmount(previousCreditAmount);
        generalShowAllDTO.setPreviousCashAmount(previousCashAmount);
        generalShowAllDTO.setTotalDebitAmount(totalDebitAmount);
        generalShowAllDTO.setTotalCreditAmount(totalCreditAmount);
        generalShowAllDTO.setTotalCashAmount(totalCashAmount);

        return generalShowAllDTO;
    }


}
