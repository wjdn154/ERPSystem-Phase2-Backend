package com.megazone.ERPSystem_phase2_Backend.production.controller.resource_data.equipment;

import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.dto.ListMaintenanceHistoryDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.dto.MaintenanceHistoryDetailDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.dto.MaintenanceHistoryDetailShowDTO;
import com.megazone.ERPSystem_phase2_Backend.production.service.resource_data.equipment.MaintenanceHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/production")
public class MaintenanceHistoryController {

    private final MaintenanceHistoryService maintenanceHistoryService;

    //유지보수 이력 리스트 조회   url 변경함
    @PostMapping("/maintenanceHistorys/{companyId}")
    public ResponseEntity<List<ListMaintenanceHistoryDTO>> getAllMaintenanceHistory(@PathVariable("companyId") Long companyId){

        //서비스에서 모든 유지보수이력 정보를 가져옴
        List<ListMaintenanceHistoryDTO> result = maintenanceHistoryService.findAllMaintenanceHistory(companyId);

        return (result != null)?
                ResponseEntity.status(HttpStatus.OK).body(result):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    //유지보수 이력 상세 조회
    @PostMapping("/maintenanceHistory/{id}")
    public ResponseEntity<MaintenanceHistoryDetailShowDTO> getMaintenanceHistoryDetail(@PathVariable("id") Long id){

        //서비스에서 해당 아이디의 유지보수 이력 상세 정보를 가져옴.
        Optional<MaintenanceHistoryDetailShowDTO> result = maintenanceHistoryService.findMaintenanceHistoryById(id);

        //해당 아이디의 유지보수 이력 정보가 존재하지 않을 경우 404 상태 반환
        return result.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    //유지보수 이력 상세 등록
    @PostMapping("/maintenanceHistory/createMaintenance")
    public ResponseEntity<MaintenanceHistoryDetailShowDTO> saveMaintenanceHistory(@RequestBody MaintenanceHistoryDetailDTO dto){

        //서비스에서 유지보수 이력 상세정보를 등록함.
        Optional<MaintenanceHistoryDetailShowDTO> result = maintenanceHistoryService.saveMaintenanceHistory(dto);

        //등록 실패시 500 상태 반환
        return result.map(ResponseEntity :: ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    //유지보수 이력 상세 수정
    @PutMapping("/maintenanceHistory/updateMaintenance/{id}")
    public ResponseEntity<MaintenanceHistoryDetailShowDTO> updateMaintenanceHistory(@PathVariable("id") Long id, @RequestBody MaintenanceHistoryDetailShowDTO dto){

        //서비스에서 해당 아이디의 유지보수 이력 상세정보를 수정함
        Optional<MaintenanceHistoryDetailShowDTO> result = maintenanceHistoryService.updateMaintenanceHistory(id, dto);
        
        //수정 실패시 500 상태 반환
        return result.map(ResponseEntity:: ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    //유지보수 이력 상세 삭제
    @DeleteMapping("/maintenanceHistory/deleteMaintenance/{id}")
    public ResponseEntity<String> deleteMaintenanceHistory(@PathVariable("id") Long id){

        try {
            //서비스에서 해당 아이디의 유지보수 이력 상세정보를 삭제함
            maintenanceHistoryService.deleteMaintenanceHistory(id);
            return ResponseEntity.noContent().build();    //204 반환.
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); //500 반환
        }
    }
}
