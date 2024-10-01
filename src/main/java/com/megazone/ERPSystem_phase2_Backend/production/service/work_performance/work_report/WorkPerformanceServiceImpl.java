package com.megazone.ERPSystem_phase2_Backend.production.service.work_performance.work_report;

import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.goods_receipt.dto.WorkPerformanceDetailDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.goods_receipt.dto.WorkPerformanceListDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class WorkPerformanceServiceImpl implements WorkPerformanceService{

    @Override
    public List<WorkPerformanceListDTO> findAllWorkPerformance() {
        return List.of();
    }

    @Override
    public Optional<WorkPerformanceDetailDTO> findWorkPerformanceById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<WorkPerformanceDetailDTO> createWorkPerformance(WorkPerformanceDetailDTO dto) {
        return Optional.empty();
    }

    @Override
    public Optional<WorkPerformanceDetailDTO> updateWorkPerformance(Long id, WorkPerformanceDetailDTO dto) {
        return Optional.empty();
    }
}
