package com.megazone.ERPSystem_phase2_Backend.production.service.resource_data.equipment;

import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.EquipmentData;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.dto.EquipmentDataDTO;
import com.megazone.ERPSystem_phase2_Backend.production.repository.resource_data.equipment.EquipmentDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class EquipmentDataServiceImpl implements EquipmentDataService{

    private final EquipmentDataRepository equipmentDataRepository;

    @Override
    public Optional<EquipmentDataDTO> saveEquipment(EquipmentDataDTO dto) {

        //dto를 엔티티로 변환하고 저장함.
        EquipmentData equipmentData = dto.toEntity(dto);

        return Optional.empty();
    }

    @Override
    public Optional<EquipmentDataDTO> updateEquipment(Long id, EquipmentDataDTO dto) {
        return Optional.empty();
    }

    @Override
    public void deleteEquipment(Long id) {

    }

    @Override
    public List<EquipmentDataDTO> findAllEquipmentDataDetails() {
        return List.of();
    }

    @Override
    public Optional<EquipmentDataDTO> findEquipmentDataDetailById(Long id) {
        return Optional.empty();
    }
}
