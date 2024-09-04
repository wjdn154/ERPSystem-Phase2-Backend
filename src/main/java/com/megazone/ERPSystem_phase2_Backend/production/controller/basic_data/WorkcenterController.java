package com.megazone.ERPSystem_phase2_Backend.production.controller.basic_data;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.WarehouseResponseDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.basic_information_management.warehouse.WarehouseRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.service.warehouse_management.WarehouseService;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.Workcenter;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.dto.WorkcenterDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.dto.WorkerAssignmentDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.dto.EquipmentDataDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.dto.ProcessDetailsDTO;
import com.megazone.ERPSystem_phase2_Backend.production.service.basic_data.workcenter.WorkcenterService;
import com.megazone.ERPSystem_phase2_Backend.production.service.routing_management.ProcessDetails.ProcessDetailsService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/production/workcenters")
@RequiredArgsConstructor
public class WorkcenterController {

    private final WorkcenterService workcenterService;
    private final ProcessDetailsService processDetailsService;

    // 1. 전체 작업장 조회
    @PostMapping
    public ResponseEntity<List<WorkcenterDTO>> getAllWorkcenters() {
        List<WorkcenterDTO> workcenterDTOs = workcenterService.findAll();
        return ResponseEntity.ok(workcenterDTOs);
    }


    // 2. 이름으로 작업장 리스트 검색 조회
    @PostMapping("/search")
    public ResponseEntity<List<WorkcenterDTO>> getWorkcentersByName(
            @RequestParam("name") String name) {
        List<WorkcenterDTO> workcenterDTOs = workcenterService.findByNameContaining(name);
        return ResponseEntity.ok(workcenterDTOs);
    }

    // 3. 코드로 작업장 세부 정보 조회
    @PostMapping("/details/{code}")
    public ResponseEntity<WorkcenterDTO> getWorkcenterDetailByCode(
            @PathVariable("code") String code) {
        Optional<WorkcenterDTO> workcenterDTO = workcenterService.findByCode(code);

        return workcenterDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // 4. 새로운 작업장 생성
    @PostMapping("/create")
    public ResponseEntity<Workcenter> saveWorkcenter(@RequestBody WorkcenterDTO workcenterDTO) {
        Workcenter savedWorkcenter = workcenterService.save(workcenterDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedWorkcenter);
    }

    // 5. 코드로 수정
    @PostMapping("/update/{code}")
    public ResponseEntity<WorkcenterDTO> updateWorkcenter(
            @PathVariable("code") String code,
            @RequestBody WorkcenterDTO workcenterDTO) {
        try {
            Optional<WorkcenterDTO> updatedWorkcenterDTO = workcenterService.updateByCode(code, workcenterDTO);
            if (updatedWorkcenterDTO.isPresent()) {
                return ResponseEntity.ok(updatedWorkcenterDTO.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    // 6. 코드 매칭 삭제
    @PostMapping("/delete")
    public ResponseEntity<String> deleteWorkcenter(@RequestParam("code") String code) {
        try {
            workcenterService.deleteByCode(code);
            return ResponseEntity.ok("작업장이 성공적으로 삭제되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("삭제할 수 없습니다: " + e.getMessage());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("작업장을 찾을 수 없습니다: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("예상치 못한 오류가 발생했습니다.");
        }
    }

    // 7. 작업장 관리 내에서 공장 리스트 조회
    @PostMapping("/factories")
    public ResponseEntity<List<WarehouseResponseDTO>> getWorkcenterFactories() {

        List<WarehouseResponseDTO> factoryDTOs = workcenterService.findAllFactories();
        return ResponseEntity.ok(factoryDTOs);
    }

    // 8. 생산 공정 정보 조회
    @PostMapping("/processes/{processCode}")
    public ResponseEntity<ProcessDetailsDTO> getProcessByCode(@PathVariable String processCode) {
        Optional<ProcessDetailsDTO> processDetailsDTO = processDetailsService.getProcessDetailsByCode(processCode);
        return processDetailsDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // 9. 설비 코드로 작업장 설비 정보 조회
    @PostMapping("/equipments/{equipmentCode}")
    public ResponseEntity<List<EquipmentDataDTO>> getEquipmentsByWorkcenterCode(@PathVariable("equipmentCode") String equipmentCode) {
        List<EquipmentDataDTO> equipmentDataDTOs = workcenterService.findEquipmentByWorkcenterCode(equipmentCode);
        return ResponseEntity.ok(equipmentDataDTOs);
    }

    // 10. 작업자 배정 이력 조회
    @PostMapping("/workerAssignments/{workcenterCode}")
    public ResponseEntity<List<WorkerAssignmentDTO>> getWorkerAssignmentsByWorkcenterCode(@PathVariable("workcenterCode") String workcenterCode) {
        List<WorkerAssignmentDTO> workerAssignmentDTOs = workcenterService.findWorkerAssignmentsByWorkcenterCode(workcenterCode);
        return ResponseEntity.ok(workerAssignmentDTOs);
    }

}

