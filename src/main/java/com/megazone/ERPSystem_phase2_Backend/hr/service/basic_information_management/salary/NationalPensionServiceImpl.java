package com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.salary;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.dto.InsurancePensionCalculatorDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.dto.LongTermCareInsuranceShowDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.dto.NationalPensionShowDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.salary.NationalPension;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_configuration.PositionSalaryStepRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.salary.NationalPensionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class NationalPensionServiceImpl implements NationalPensionService {
    private final NationalPensionRepository nationalPensionRepository;
    private final PositionSalaryStepRepository positionSalaryStepRepository;

    @Override
    public BigDecimal calculator(InsurancePensionCalculatorDTO dto) {
        BigDecimal salaryAmount =  positionSalaryStepRepository.getSalaryAmount(dto.getPositionId(),dto.getSalaryStepId());

        NationalPension nationalPension = nationalPensionRepository.findFirstByEndDateIsNull().orElseThrow(
                () -> new NoSuchElementException("해당하는 건강보험 데이터가 없습니다."));

        if(salaryAmount.compareTo(nationalPension.getLowerAmount()) < 0) {
            salaryAmount = nationalPension.getLowerAmount();
        }
        else if(salaryAmount.compareTo(nationalPension.getUpperAmount()) > 0) {
            salaryAmount = nationalPension.getUpperAmount();
        }

        return salaryAmount.multiply(nationalPension.getEmployeeRate()).setScale(0, BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public BigDecimal calculator(BigDecimal amount) {

        NationalPension nationalPension = nationalPensionRepository.findFirstByEndDateIsNull().orElseThrow(
                () -> new NoSuchElementException("해당하는 건강보험 데이터가 없습니다."));

        if(amount.compareTo(nationalPension.getLowerAmount()) < 0) {
            amount = nationalPension.getLowerAmount();
        }
        else if(amount.compareTo(nationalPension.getUpperAmount()) > 0) {
            amount = nationalPension.getUpperAmount();
        }

        return amount.multiply(nationalPension.getEmployeeRate()).setScale(0, BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public List<NationalPensionShowDTO> showAll() {
        List<NationalPension> list = nationalPensionRepository.findAll();
        return list.stream().map(
                (result) ->  NationalPensionShowDTO.create(result)).toList();
    }
}
