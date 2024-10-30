package com.megazone.ERPSystem_phase2_Backend.hr.service.basic_configuration;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.Allowance;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.SalaryStep;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.dto.AllowanceEntryDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.dto.AllowanceShowDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.dto.SalaryStepEntryDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_configuration.AllowanceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class AllowanceServiceImpl implements AllowanceService {
    private final AllowanceRepository allowanceRepository;

    @Override
    public List<AllowanceShowDTO> show() {
        List<AllowanceShowDTO> allowances = allowanceRepository.findAll().stream().map(
                (allowance) -> AllowanceShowDTO.create(allowance)
        ).toList();

        return allowances;
    }

    @Override
    public String entry(AllowanceEntryDTO dto) {
        allowanceRepository.save(createAllowance(dto));
        return "등록완료";
    }

    public Allowance createAllowance(AllowanceEntryDTO dto) {
        Allowance salaryStep = new Allowance();

        Allowance lastSalaryStep = allowanceRepository.findTopByOrderByIdDesc().orElseThrow(
                () -> new RuntimeException("해당하는 수당을 찾을수 없습니다."));

        String newCode = String.valueOf(Integer.parseInt(lastSalaryStep.getCode()) + 1);

        salaryStep.setName(dto.getName());
        salaryStep.setCode(newCode);
        salaryStep.setDescription(dto.getDescription());

        return salaryStep;
    }

    @Override
    public BigDecimal taxableCalculator(BigDecimal amount, Long allowanceId) {
        Allowance allowance = allowanceRepository.findById(allowanceId).orElseThrow(
                () -> new NoSuchElementException("해당하는 수당을 찾을 수 없습니다."));

        if(amount.compareTo(allowance.getLimitAmount()) > 0) {
            // 한도 금액이 초과되었을 경우, 한도 금액만 비과세로 반환
            return allowance.getLimitAmount();
        } else {
            // 한도 내 금액이면 해당 금액을 그대로 비과세로 반환
            return amount;
        }
    }
}
