package com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.salary;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.dto.EmploymentInsurancePensionShowDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.dto.InsurancePensionCalculatorDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.salary.EmploymentInsurancePension;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_configuration.PositionSalaryStepRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.salary.EmploymentInsurancePensionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class EmploymentInsurancePensionServiceImpl implements EmploymentInsurancePensionService {
    private final EmploymentInsurancePensionRepository employmentInsurancePensionRepository;
    private final PositionSalaryStepRepository employmentPositionSalaryStepRepository;

    @Override
    public BigDecimal calculator(InsurancePensionCalculatorDTO dto) {
        BigDecimal salaryAmount =  employmentPositionSalaryStepRepository.getSalaryAmount(dto.getPositionId(),dto.getSalaryStepId());

        return salaryAmount.multiply(employmentInsurancePensionRepository.findFirstByEndDateIsNull().orElseThrow(
                () -> new NoSuchElementException("해당하는 국민연금 데이터가 없습니다.")).getEmployeeRate()).setScale(0, BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public BigDecimal calculator(BigDecimal amount) {
        return amount.multiply(employmentInsurancePensionRepository.findFirstByEndDateIsNull().orElseThrow(
                () -> new NoSuchElementException("해당하는 국민연금 데이터가 없습니다.")).getEmployeeRate()).setScale(0, BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public List<EmploymentInsurancePensionShowDTO> showAll() {
        List<EmploymentInsurancePension> list = employmentInsurancePensionRepository.findAll();
        return list.stream().map(
                (result) -> EmploymentInsurancePensionShowDTO.create(result)).toList();
    }
}
