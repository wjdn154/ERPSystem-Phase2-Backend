package com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.salary;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.dto.LongTermCareInsuranceCalculatorDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.dto.LongTermCareInsuranceShowDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.salary.LongTermCareInsurancePension;
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
public class LongTermCareInsurancePensionServiceImpl implements LongTermCareInsurancePensionService {
    private final LongTermCareInsurancePensionRepository repository;

    @Override
    public List<LongTermCareInsuranceShowDTO> showAll() {
        List<LongTermCareInsurancePension> longTermCareInsurancePension = repository.findAll();
        return longTermCareInsurancePension.stream().map(
                (result) -> LongTermCareInsuranceShowDTO.create(result)).toList();
    }

    @Override
    public BigDecimal calculator(LongTermCareInsuranceCalculatorDTO dto) {
        LongTermCareInsurancePension longTermCareInsurancePension = repository.findFirstByEndDateIsNull().orElseThrow(
        () -> new NoSuchElementException("해당하는 장기요양보험 데이터가 없습니다."));

        BigDecimal result = dto.getHealthInsurancePensionAmount().multiply(longTermCareInsurancePension.getRate());
        return result.setScale(0, BigDecimal.ROUND_HALF_UP);
    }
}
