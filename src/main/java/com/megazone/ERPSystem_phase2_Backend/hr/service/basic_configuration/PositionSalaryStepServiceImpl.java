package com.megazone.ERPSystem_phase2_Backend.hr.service.basic_configuration;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.PositionSalaryStep;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_configuration.PositionSalaryStepRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PositionSalaryStepServiceImpl implements PositionSalaryStepService {
    private final PositionSalaryStepRepository positionSalaryStepRepository;

    @Override
    public Object show() {

        List<PositionSalaryStep> results = positionSalaryStepRepository.findAll();

        return results;
    }
}
