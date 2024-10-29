package com.megazone.ERPSystem_phase2_Backend.hr.service.salary_ledger;

import com.megazone.ERPSystem_phase2_Backend.hr.model.salary_ledger.dto.SalaryLedgerDateShowDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.salary_ledger.SalaryLedgerDateRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SalaryLedgerDateServiceImpl implements SalaryLedgerDateService {
    private final SalaryLedgerDateRepository salaryLedgerDateRepository;

    @Override
    public List<SalaryLedgerDateShowDTO> showAll() {
        return salaryLedgerDateRepository.findAll().stream().map(
                (result) -> SalaryLedgerDateShowDTO.create(result)
        ).toList();
    }
}
