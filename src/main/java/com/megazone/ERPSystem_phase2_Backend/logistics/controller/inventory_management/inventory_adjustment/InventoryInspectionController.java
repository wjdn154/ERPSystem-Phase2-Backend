package com.megazone.ERPSystem_phase2_Backend.logistics.controller.inventory_management.inventory_adjustment;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory_adjustment.dto.InventoryInspectionRequestDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory_adjustment.dto.InventoryInspectionResponseDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory_adjustment.dto.InventoryInspectionResponseListDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory_adjustment.dto.InventoryInspectionStatusResponseListDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.service.inventory_management.inventory_inspection.InventoryInspectionDetailServiceImpl;
import com.megazone.ERPSystem_phase2_Backend.logistics.service.inventory_management.inventory_inspection.InventoryInspectionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/logistics/inventory/inspection")
public class InventoryInspectionController {

    private final InventoryInspectionService inventoryInspectionService;
    private final InventoryInspectionDetailServiceImpl inventoryInspectionDetailService;

    @PostMapping("/")
    public ResponseEntity<?> getInspectionsByDateRange(@RequestParam("startDate") LocalDate startDate, @RequestParam("endDate") LocalDate endDate) {
        try {
            List<InventoryInspectionResponseListDTO> inspections = inventoryInspectionService.getInspectionsByDateRange(startDate, endDate);

            if (inspections == null || inspections.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body(Collections.emptyList());
            }

            return ResponseEntity.status(HttpStatus.OK)
                    .body(inspections);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("서버 내부 오류가 발생했습니다. 오류 메시지: " + e.getMessage());
        }
    }

    @PostMapping("/details")
    public ResponseEntity<List<InventoryInspectionStatusResponseListDTO>> getInspectionStatusByDateRange(@RequestParam("startDate") LocalDate startDate, @RequestParam("endDate") LocalDate endDate) {
        try {
            List<InventoryInspectionStatusResponseListDTO> inspectionStatus = inventoryInspectionDetailService.getInspectionStatusByDateRange(startDate, endDate);

            if (inspectionStatus == null || inspectionStatus.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body(Collections.emptyList());
            }

            return ResponseEntity.status(HttpStatus.OK)
                    .body(inspectionStatus);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList());
        }
    }

    /**
     * 재고 실사 ID로 재고 실사 데이터 조회
     * @param id 재고 실사 ID
     * @return 재고 실사 데이터
     */
    @PostMapping("/{id}")
    public ResponseEntity<?> getInspectionById(@PathVariable("id") Long id) {
        try {
            InventoryInspectionResponseDTO inspectionResponse = inventoryInspectionService.getInspectionById(id);

            if (inspectionResponse == null) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body("해당 ID (" + id + ")에 대한 재고 실사 데이터를 찾을 수 없습니다.");
            }

            return ResponseEntity.status(HttpStatus.OK)
                    .body(inspectionResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("서버 내부 오류가 발생했습니다. 오류 메시지: " + e.getMessage());
        }
    }

    /**
     * 재고 실사 등록
     * @param requestDTO 재고 실사 등록 요청 DTO
     * @return 재고 실사 등록 결과
     */
    @PostMapping("/create")
    public ResponseEntity<String> createInventoryInspection(@RequestBody InventoryInspectionRequestDTO requestDTO) {
        try {
            InventoryInspectionResponseDTO createdInspection = inventoryInspectionService.createInventoryInspection(requestDTO);

            if (createdInspection == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("재고 실사 등록에 실패하였습니다.");
            }
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("재고 실사 등록이 완료되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("서버 내부 오류가 발생했습니다. 오류 메시지: " + e.getMessage());
        }
    }

    /**
     * 재고 실사 수정
     * @param id 재고 실사 ID
     * @param updateDTO 재고 실사 수정 요청 DTO
     * @return 재고 실사 수정 결과
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateInspection(@PathVariable("id") Long id, @RequestBody InventoryInspectionRequestDTO updateDTO) {
        try {
            InventoryInspectionResponseDTO updatedInspection = inventoryInspectionService.updateInventoryInspection(id, updateDTO);

            if (updatedInspection == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("해당 ID (" + id + ")에 대한 재고 실사 데이터를 찾을 수 없습니다. 수정할 수 없습니다.");
            }

            Map<String, Object> response = new HashMap<>();
            response.put("message", "재고 실사 수정이 완료되었습니다. 전표 번호: " + updatedInspection.getInspectionNumber());
            response.put("updatedInspection", updatedInspection);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("잘못된 요청입니다. 오류 메시지: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("서버 내부 오류가 발생했습니다. 오류 메시지: " + e.getMessage());
        }
    }

    /**
     * 재고 실사 삭제
     * @param id 재고 실사 ID
     * @return 재고 실사 삭제 결과
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteInspectionById(@PathVariable("id") Long id) {
        try {
            inventoryInspectionService.deleteInspectionById(id);

            return ResponseEntity.status(HttpStatus.OK)
                    .body("재고 실사 삭제가 완료되었습니다.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("해당 ID (" + id + ")에 대한 재고 실사 데이터를 찾을 수 없습니다. 삭제할 수 없습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("서버 내부 오류가 발생했습니다. 오류 메시지: " + e.getMessage());
        }
    }

    /**
     * 사용자가 조정 요철할 수 있는 API
     * 사용자가 입력한 재고 실사에 맞게 장부 수량데이터들이 업데이트 되어야 함
     * @param id
     * @return ResponseEntity String (성공 메시지) or ResponseEntity String (실패 메시지) or ResponseEntity String (오류 메시지)
     */
    @PostMapping("/adjustRequest/{id}")
    public ResponseEntity<String> adjustRequest(@PathVariable("id") Long id) {
        try {
            inventoryInspectionService.adjustRequest(id);
            return ResponseEntity.ok("조정 요청이 성공적으로 완료되었습니다.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("해당 ID (" + id + ")에 대한 재고 실사 데이터를 찾을 수 없습니다. 조정 요청할 수 없습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("서버 내부 오류가 발생했습니다. 오류 메시지: " + e.getMessage());
        }
    }

}
