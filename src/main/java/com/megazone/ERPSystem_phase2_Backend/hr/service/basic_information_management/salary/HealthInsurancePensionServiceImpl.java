package com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.salary;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.dto.HealthInsurancePensionShowDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.dto.InsurancePensionCalculatorDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.salary.HealthInsurancePension;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.salary.LongTermCareInsurancePension;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_configuration.PositionSalaryStepRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.salary.HealthInsurancePensionRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.salary.LongTermCareInsurancePensionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class HealthInsurancePensionServiceImpl implements HealthInsurancePensionService {
    private final HealthInsurancePensionRepository healthInsurancePensionRepository;
    private final PositionSalaryStepRepository positionSalaryStepRepository;

    @Override
    public BigDecimal calculator(InsurancePensionCalculatorDTO dto) {

        BigDecimal salaryAmount =  positionSalaryStepRepository.getSalaryAmount(dto.getPositionId(),dto.getSalaryStepId());


        HealthInsurancePension healthInsurancePension = healthInsurancePensionRepository.findFirstByEndDateIsNull().orElseThrow(
                () -> new NoSuchElementException("해당하는 건강보험 데이터가 없습니다."));

        if(salaryAmount.compareTo(healthInsurancePension.getLowerAmount()) < 0) {
            salaryAmount = healthInsurancePension.getLowerAmount();
        }
        else if(salaryAmount.compareTo(healthInsurancePension.getUpperAmount()) > 0) {
            salaryAmount = healthInsurancePension.getUpperAmount();
        }

        salaryAmount = salaryAmount.multiply(healthInsurancePension.getEmployeeRate());



        return salaryAmount.setScale(0, BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public BigDecimal calculator(BigDecimal amount) {
        HealthInsurancePension healthInsurancePension = healthInsurancePensionRepository.findFirstByEndDateIsNull().orElseThrow(
                () -> new NoSuchElementException("해당하는 건강보험 데이터가 없습니다."));

        if(amount.compareTo(healthInsurancePension.getLowerAmount()) < 0) {
            amount = healthInsurancePension.getLowerAmount();
        }
        else if(amount.compareTo(healthInsurancePension.getUpperAmount()) > 0) {
            amount = healthInsurancePension.getUpperAmount();
        }

        return amount.multiply(healthInsurancePension.getEmployeeRate()).setScale(0, BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public List<HealthInsurancePensionShowDTO> showAll() {
        List<HealthInsurancePension> list = healthInsurancePensionRepository.findAll();

        return list.stream().map(
                (result) ->  HealthInsurancePensionShowDTO.create(result)).toList();
    }
}
