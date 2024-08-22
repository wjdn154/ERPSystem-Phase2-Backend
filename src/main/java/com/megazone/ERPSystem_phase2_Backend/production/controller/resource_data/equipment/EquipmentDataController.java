package com.megazone.ERPSystem_phase2_Backend.production.controller.resource_data.equipment;

import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.EquipmentData;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.dto.EquipmentDataDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.dto.EquipmentDataShowDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.dto.ListEquipmentDataDTO;
import com.megazone.ERPSystem_phase2_Backend.production.repository.resource_data.equipment.EquipmentDataRepository;
import com.megazone.ERPSystem_phase2_Backend.production.service.resource_data.equipment.EquipmentDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class EquipmentDataController {

    private final EquipmentDataService equipmentDataService;
    private final EquipmentDataRepository equipmentDataRepository;

    //설비 리스트 조회
    @PostMapping("/api/production/equipmentDatas")
    public ResponseEntity<List<ListEquipmentDataDTO>> getAllEquipmentDataDetails(){
        //서비스에서 모든 설비정보를 가져옴
        List<ListEquipmentDataDTO> result = equipmentDataService.findAllEquipmentDataDetails();

        return (result != null) ?
                ResponseEntity.status(HttpStatus.OK).body(result):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    //설비 상세 조회
    @PostMapping("/api/production/equipmentData/{id}")
    public ResponseEntity<EquipmentDataShowDTO> getEquipmentDataById(@PathVariable Long id){

        //서비스에서 해당 아이디의 설비 상세 정보를 가져옴
        Optional<EquipmentDataShowDTO> result = equipmentDataService.findEquipmentDataDetailById(id);

        //해당 아이디의 설비정보가 존재하지 않을 경우 404 상태 반환.
        return result.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    //설비 상세 정보 등록
    @PostMapping("/api/production/equipmentData/createEquipment")
    public ResponseEntity<EquipmentDataDTO> saveEquipmentDataById(@RequestBody EquipmentDataDTO dto){

        //서비스에 해당 아이디의 설비 상세 정보를 등록함.
        Optional<EquipmentDataDTO> result = equipmentDataService.saveEquipment(dto);

        return result.map(ResponseEntity::ok)
                .orElseGet( () -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    //설비 정보 수정
    @PostMapping("/api/production/equipmentData/updateEquipment/{id}")
    public ResponseEntity<EquipmentDataDTO> updateEquipmentDataById(@PathVariable Long id, @RequestBody EquipmentDataDTO dto){

        //서비스에서 해당 아이디의 설비 상세 정보를 수정함.
        Optional<EquipmentDataDTO> result = equipmentDataService.updateEquipment(id,dto);

        return result.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    //설비 정보 삭제
    @DeleteMapping("/api/production/equipmentData/deleteEquipment/{id}")
    public ResponseEntity<String> deleteEquipmentDataById(@PathVariable Long id){
        try {
            //서비스에서 해당 아이디의 설비 상세 정보를 삭제함
            equipmentDataService.deleteEquipment(id);
            return ResponseEntity.noContent().build();   //204반환
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
