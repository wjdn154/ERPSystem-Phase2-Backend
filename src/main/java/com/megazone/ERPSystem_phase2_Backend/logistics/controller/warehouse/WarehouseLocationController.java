package com.megazone.ERPSystem_phase2_Backend.logistics.controller.warehouse;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_location.dto.WarehouseLocationRequestDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_location.dto.WarehouseLocationResponseDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.service.warehouse_management.WarehouseLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/logistics/location")
public class WarehouseLocationController {

    private final WarehouseLocationService warehouseLocationService;

    /**
     * 창고 ID에 해당하는 창고 위치 리스트 조회
     * @param warehouseId
     * @return 창고 위치 리스트
     */
    @PostMapping("/{warehouseId}")
    public ResponseEntity<List<WarehouseLocationResponseDTO>> getLocationsByWarehouse(@PathVariable Long warehouseId) {
        try {
            List<WarehouseLocationResponseDTO> locations = warehouseLocationService.getLocationsByWarehouseId(warehouseId);

            return ResponseEntity.ok(locations);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }

    /**
     * 창고 위치 생성
     * @param requestDTO
     * @return 생성된 창고 위치
     */
    @PostMapping("/create")
    public ResponseEntity<?> createWarehouseLocation(@RequestBody WarehouseLocationRequestDTO requestDTO) {
        try {
            WarehouseLocationResponseDTO responseDTO = warehouseLocationService.createWarehouseLocation(requestDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다. 다시 시도해 주세요.");
        }
    }

    /**
     * 창고 위치 수정
     * @param id
     * @param requestDTO
     * @return 수정된 창고 위치
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateWarehouseLocation(@PathVariable Long id, @RequestBody WarehouseLocationRequestDTO requestDTO) {
        try {
            WarehouseLocationResponseDTO responseDTO = warehouseLocationService.updateWarehouseLocation(id, requestDTO);
            return ResponseEntity.ok(responseDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다. 다시 시도해 주세요.");
        }
    }

    /**
     * 창고 위치 삭제
     * @param id
     * @return  삭제 성공 여부
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteWarehouseLocation(@PathVariable("id") Long id) {
        try {
            warehouseLocationService.deleteWarehouseLocation(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
