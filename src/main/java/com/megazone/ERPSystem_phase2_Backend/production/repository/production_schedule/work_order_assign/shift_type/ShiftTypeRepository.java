package com.megazone.ERPSystem_phase2_Backend.production.repository.production_schedule.work_order_assign.shift_type;

import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.work_order_assign.ShiftType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShiftTypeRepository extends JpaRepository<ShiftType, Long>, ShiftTypeRepositoryCustom {
    Optional<ShiftType> findById(Long shiftTypeId);

    Optional<ShiftType> findByName(String shift);

}
