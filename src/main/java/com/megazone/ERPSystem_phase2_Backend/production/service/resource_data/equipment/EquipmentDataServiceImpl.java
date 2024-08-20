package com.megazone.ERPSystem_phase2_Backend.production.service.resource_data.equipment;

import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.EquipmentData;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.dto.EquipmentDataDTO;
import com.megazone.ERPSystem_phase2_Backend.production.repository.resource_data.equipment.EquipmentDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class EquipmentDataServiceImpl implements EquipmentDataService{

    private final EquipmentDataRepository equipmentDataRepository;

    @Override
    public Optional<EquipmentDataDTO> saveEquipment(EquipmentDataDTO dto) {

//        //dto를 엔티티로 변환함
//        EquipmentData equipmentData = dto.toEntity();
//        //엔티티 저장
//        EquipmentData saveEquipment = equipmentDataRepository.save(equipmentData);
//
//        //엔티티를 dto로 변환하여 반환
//        EquipmentDataDTO equipmentDataDTO = new EquipmentDataDTO(
//
//                saveEquipment.getEquipmentNum(),
//                saveEquipment.getEquipmentName(),
//                saveEquipment.getEquipmentType(),
//                saveEquipment.getManufacturer(),
//                saveEquipment.getModelNumber(),
//                saveEquipment.getInstallDate(),
//                saveEquipment.getPurchaseDate(),
//                saveEquipment.getOperationStatus(),
//                saveEquipment.getCost(),
//                saveEquipment.getLocation(),
//                saveEquipment.getEquipmentImg()
//        );
        return Optional.of(null);
    }

    @Override
    public Optional<EquipmentDataDTO> updateEquipment(Long id, EquipmentDataDTO dto) {

        return Optional.empty();
    }


    @Override
    public List<EquipmentDataDTO> findAllEquipmentDataDetails() {

//        return equipmentDataRepository.findAll().stream()
//                .map(equipmentData -> new EquipmentDataDTO(
//                        equipmentData.getId(),
//                        equipmentData.getEquipmentNum(),
//                        equipmentData.getEquipmentName(),
//                        equipmentData.getModelNumber(),
//                        equipmentData.getManufacturer()
//
//                )).collect(Collectors.toList());
        return null;

    }

    @Override
    public Optional<EquipmentDataDTO> findEquipmentDataDetailById(Long id) {

        return null;
    }

    @Override
    public void deleteEquipment(Long id) {
        //해당 아이디 가져옴
        EquipmentData equipmentData = equipmentDataRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 아이디로 설비를 조회할 수 없습니다 : "+id));
        //해당 아이디 설비정보 삭제
        equipmentDataRepository.delete(equipmentData);
    }
}
