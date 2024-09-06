package com.megazone.ERPSystem_phase2_Backend.production.repository.resource_data.shift_type;

import java.util.Optional;

public interface ShiftTypeRepository {
    Optional<Object> findById(Long shiftTypeId);

}
