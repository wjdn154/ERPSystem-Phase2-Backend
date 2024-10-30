package com.megazone.ERPSystem_phase2_Backend.production.service.resource_data.equipment;


import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.dto.EquipmentDataDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.dto.EquipmentDataShowDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.dto.EquipmentDataUpdateDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.dto.ListEquipmentDataDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface EquipmentDataService {

    //설비 생성
    Optional<EquipmentDataShowDTO> saveEquipment(EquipmentDataDTO dto);

    //설비 수정
    Optional<EquipmentDataUpdateDTO> updateEquipment(Long id, EquipmentDataUpdateDTO dto, MultipartFile imageFile);


    //설비 삭제
    void deleteEquipment(Long id);

    //설비 리스트 조회
    List<ListEquipmentDataDTO> findAllEquipmentDataDetails();

    //개별 설비 조회
    Optional<EquipmentDataShowDTO> findEquipmentDataDetailById(Long id);


}
