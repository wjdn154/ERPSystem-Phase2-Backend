package com.megazone.ERPSystem_phase2_Backend.hr.service.salary_ledger;

import com.megazone.ERPSystem_phase2_Backend.hr.model.salary_ledger.dto.SalaryLedgerAllowanceShowDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.salary_ledger.SalaryAllowanceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SalaryAllowanceServiceImpl implements SalaryAllowanceService {
    private final SalaryAllowanceRepository salaryAllowanceRepository;

    @Override
    public List<SalaryLedgerAllowanceShowDTO> findSalaryAllowances(Long salaryId) {
        return salaryAllowanceRepository.findSalaryAllowanceList(salaryId);
    }
}
