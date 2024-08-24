package com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Position;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Position;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PositionRepository extends JpaRepository<Position, Long> {
    Optional<Position> findById(Long id);
}
