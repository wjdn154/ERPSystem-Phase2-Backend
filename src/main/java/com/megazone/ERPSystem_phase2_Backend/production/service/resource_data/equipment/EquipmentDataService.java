package com.megazone.ERPSystem_phase2_Backend.production.service.resource_data.equipment;


import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.EquipmentData;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.dto.EquipmentDataDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.dto.EquipmentDataShowDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.dto.ListEquipmentDataDTO;

import java.util.List;
import java.util.Optional;

public interface EquipmentDataService {

    //설비 생성
    Optional<EquipmentDataDTO> saveEquipment(EquipmentDataDTO dto);

    //설비 수정
    Optional<EquipmentDataDTO> updateEquipment(Long id, EquipmentDataDTO dto);

    //설비 삭제
    void deleteEquipment(Long id);

    //설비 리스트 조회
    List<ListEquipmentDataDTO> findAllEquipmentDataDetails();

    //개별 설비 조회
    Optional<EquipmentDataShowDTO> findEquipmentDataDetailById(Long id);


}
