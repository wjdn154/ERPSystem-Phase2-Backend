package com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.Position;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Position;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Position.PositionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PositionServiceImpl implements PositionService {
    private final PositionRepository positionRepository;

    @Override
    public Optional<Position> getPositionById(Long id) {
        return positionRepository.findById(id);
    }
}
