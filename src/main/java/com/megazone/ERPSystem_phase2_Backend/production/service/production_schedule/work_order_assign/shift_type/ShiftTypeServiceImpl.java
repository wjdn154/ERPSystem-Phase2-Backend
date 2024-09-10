package com.megazone.ERPSystem_phase2_Backend.production.service.production_schedule.work_order_assign.shift_type;

import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.work_order_assign.ShiftType;
import com.megazone.ERPSystem_phase2_Backend.production.repository.production_schedule.work_order_assign.shift_type.ShiftTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShiftTypeServiceImpl implements ShiftTypeService {

    private final ShiftTypeRepository shiftTypeRepository;

    public ShiftType saveShiftType(Long id, String name, String description, Double duration) {
        // 이름이 고유하지 않으면 예외를 던짐
        if (id == null && shiftTypeRepository.findByName(name).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 교대 유형 이름입니다: " + name);
        }

        // Builder 패턴을 사용하여 ShiftType 생성
        ShiftType shiftType = ShiftType.builder()
                .id(id) // null이면 새로 생성, 값이 있으면 수정
                .name(name)
                .description(description)
                .duration(duration)
                .build();

        return shiftTypeRepository.save(shiftType);
    }

    // 등록 시 새로 생성
    public ShiftType createShiftType(String name, String description, Double duration) {
        return saveShiftType(null, name, description, duration); // id는 null로 설정하여 새로 생성
    }

    // 수정 시 기존 ID를 통해 업데이트
    public ShiftType updateShiftType(Long id, String name, String description, Double duration) {
        ShiftType existingShiftType = shiftTypeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 교대근무 유형을 찾을 수 없습니다."));

        return saveShiftType(existingShiftType.getId(), name, description, duration);
    }
}
