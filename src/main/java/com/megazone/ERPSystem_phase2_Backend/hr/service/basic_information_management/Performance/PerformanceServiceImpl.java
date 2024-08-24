package com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.Performance;

import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Performance.PerformanceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class PerformanceServiceImpl {
    @Autowired
    private PerformanceRepository performanceRepository;

    @Transactional
    public void deleteByEvaluator(Long evaluatorId) {
        performanceRepository.deleteByEvaluatorId(evaluatorId);
    }

}
