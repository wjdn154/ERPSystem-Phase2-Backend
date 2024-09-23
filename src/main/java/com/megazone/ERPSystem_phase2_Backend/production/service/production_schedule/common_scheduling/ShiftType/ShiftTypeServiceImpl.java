package com.megazone.ERPSystem_phase2_Backend.production.service.production_schedule.common_scheduling.ShiftType;

import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.dto.ShiftTypeDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.common_scheduling.ShiftType;
import com.megazone.ERPSystem_phase2_Backend.production.repository.production_schedule.common_scheduling.shift_type.ShiftTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShiftTypeServiceImpl implements ShiftTypeService {

    private final ShiftTypeRepository shiftTypeRepository;

    @Override
    public List<ShiftTypeDTO> getAllShiftTypes() {
        // 모든 ShiftType 엔티티를 조회한 후 DTO로 변환하여 반환
        return shiftTypeRepository.findAll().stream()
                .map(this::convertToDTO) // 엔티티를 DTO로 변환
                .collect(Collectors.toList()); // 리스트로 수집하여 반환
    }

    @Override
    public ShiftTypeDTO getShiftTypeById(Long id) {
        // ID로 ShiftType 엔티티를 조회하고 없으면 예외를 던짐
        ShiftType shiftType = shiftTypeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 교대근무 유형을 찾을 수 없습니다."));
        // 조회한 엔티티를 DTO로 변환하여 반환
        return convertToDTO(shiftType);
    }

    @Override
    public ShiftTypeDTO createShiftType(ShiftTypeDTO shiftTypeDTO) {
        // 동일한 이름을 가진 ShiftType이 존재하는지 확인
        if (shiftTypeRepository.findByName(shiftTypeDTO.getName()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 교대근무 유형입니다: " + shiftTypeDTO.getName());
        }

        // DTO에서 ShiftType 엔티티를 생성
        ShiftType shiftType = ShiftType.builder()
                .name(shiftTypeDTO.getName())
                .description(shiftTypeDTO.getDescription())
                .duration(shiftTypeDTO.getDuration())
                .build();
        // 엔티티를 저장한 후 DTO로 변환하여 반환
        ShiftType savedShiftType = shiftTypeRepository.save(shiftType);
        return convertToDTO(savedShiftType);
    }

    @Override
    public ShiftTypeDTO updateShiftType(ShiftTypeDTO shiftTypeDTO) {
        // ID로 기존 ShiftType 엔티티를 조회하고 없으면 예외를 던짐
        ShiftType existingShiftType = shiftTypeRepository.findById(shiftTypeDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 교대근무 유형을 찾을 수 없습니다."));

        // 동일한 이름을 가진 ShiftType이 존재하는지 확인 (자신의 이름 제외)
        if (shiftTypeRepository.findByName(shiftTypeDTO.getName())
                .filter(shiftType -> !shiftType.getId().equals(shiftTypeDTO.getId()))
                .isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 교대근무 유형입니다: " + shiftTypeDTO.getName());
        }

        // 조회한 엔티티의 필드를 DTO의 값으로 업데이트
        existingShiftType.setName(shiftTypeDTO.getName());
        existingShiftType.setDescription(shiftTypeDTO.getDescription());
        existingShiftType.setDuration(shiftTypeDTO.getDuration());

        // 업데이트된 엔티티를 저장한 후 DTO로 변환하여 반환
        ShiftType updatedShiftType = shiftTypeRepository.save(existingShiftType);
        return convertToDTO(updatedShiftType);
    }

    @Override
    public void deleteShiftType(Long id) {
        // ID로 ShiftType 엔티티를 조회하고 없으면 예외를 던짐
        ShiftType shiftType = shiftTypeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 교대근무 유형을 찾을 수 없습니다."));

        // 교대 유형이 사용 중인지 확인하고 사용 중이면 예외를 던짐
        if (shiftTypeInUse(id)) {
            throw new IllegalStateException("해당 교대유형은 현재 사용 중이므로 삭제할 수 없습니다.");
        }

        // 엔티티를 삭제
        shiftTypeRepository.delete(shiftType);
    }

    // 교대 유형이 사용 중인지 확인하는 메서드
    private boolean shiftTypeInUse(Long id) {
        Boolean isUsed = shiftTypeRepository.findIsUsedById(id);
        return isUsed != null && isUsed; // 사용 중이면 true, 아니면 false
    }

    // ShiftType 엔티티를 ShiftTypeDTO로 변환
    private ShiftTypeDTO convertToDTO(ShiftType shiftType) {
        if (shiftType == null) {
            return null; // 엔티티가 null이면 null 반환
        }
        return ShiftTypeDTO.builder()
                .id(shiftType.getId())
                .name(shiftType.getName())
                .description(shiftType.getDescription())
                .duration(shiftType.getDuration())
                .used(shiftType.getIsUsed())
                .build();
    }

    // ShiftTypeDTO를 ShiftType 엔티티로 변환
    private ShiftType convertToEntity(ShiftTypeDTO shiftTypeDTO) {
        if (shiftTypeDTO == null) {
            return null; // DTO가 null이면 null 반환
        }
        return ShiftType.builder()
                .id(shiftTypeDTO.getId())
                .name(shiftTypeDTO.getName())
                .description(shiftTypeDTO.getDescription())
                .duration(shiftTypeDTO.getDuration())
                .isUsed(shiftTypeDTO.getUsed())
                .build();
    }
}
