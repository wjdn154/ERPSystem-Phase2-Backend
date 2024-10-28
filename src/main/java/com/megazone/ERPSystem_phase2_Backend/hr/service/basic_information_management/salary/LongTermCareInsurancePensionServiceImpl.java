package com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.salary;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.dto.LongTermCareInsuranceShowDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.salary.LongTermCareInsurancePension;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.salary.LongTermCareInsurancePensionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
}
