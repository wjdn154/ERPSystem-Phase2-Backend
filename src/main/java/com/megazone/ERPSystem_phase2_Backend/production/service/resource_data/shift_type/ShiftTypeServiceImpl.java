package com.megazone.ERPSystem_phase2_Backend.production.service.resource_data.shift_type;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShiftTypeServiceImpl {
    private final ShiftTypeRepository shiftTypeRepository;

    public ShiftType createShiftType(String name, String description, Long duration) {
        ShiftType shiftType = new ShiftType(name, description, duration);
        return shiftTypeRepository.save(shiftType);
    }
}
