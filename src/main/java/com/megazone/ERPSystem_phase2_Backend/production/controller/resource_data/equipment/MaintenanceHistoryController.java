package com.megazone.ERPSystem_phase2_Backend.production.controller.resource_data.equipment;

import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.dto.ListMaintenanceHistoryDTO;
import com.megazone.ERPSystem_phase2_Backend.production.service.resource_data.equipment.MaintenanceHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class MaintenanceHistoryController {

    private final MaintenanceHistoryService maintenanceHistoryService;

    //유지보수 이력 리스트 조회
    @PostMapping("")
    public ResponseEntity<List<ListMaintenanceHistoryDTO>> getAllMaintenanceHistory(){

        //서비스에서 모든 유지보수이력 정보를 가져옴
        List<ListMaintenanceHistoryDTO> result = maintenanceHistoryService.findAllMaintenanceHistory();
        return null;
    }
}
