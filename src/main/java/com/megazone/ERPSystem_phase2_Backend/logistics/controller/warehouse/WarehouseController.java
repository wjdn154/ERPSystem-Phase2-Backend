package com.megazone.ERPSystem_phase2_Backend.logistics.controller.warehouse;

import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Users.UsersRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.test.WarehouseResponseListDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.test.WarehouseRequestTestDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.test.WarehouseResponseTestDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.service.warehouse_management.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/logistics/warehouse")
public class WarehouseController {

    private final WarehouseService warehouseService;
    private final UsersRepository usersRepository;

    @PostMapping("/")
    public ResponseEntity<List<WarehouseResponseListDTO>> getWarehouseList() {
        List<WarehouseResponseListDTO> warehouseList = warehouseService.getWarehouseList();
        return ResponseEntity.ok(warehouseList);
    }

    @PostMapping("/{id}")
    public ResponseEntity<WarehouseResponseTestDTO> getWarehouseDetail(@PathVariable("id") Long warehouseId) {
        try {
            WarehouseResponseTestDTO warehouseDetail = warehouseService.getWarehouseDetail(warehouseId);
            return ResponseEntity.ok(warehouseDetail);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/createWarehouse")
    public ResponseEntity<WarehouseResponseTestDTO> createWarehouse(@RequestBody WarehouseRequestTestDTO requestDTO) {
        try {
            WarehouseResponseTestDTO createdWarehouse = warehouseService.createWarehouse(requestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdWarehouse);  // 성공적으로 등록된 창고 정보 반환
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();  // 잘못된 요청일 때 400 Bad Request
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();  // 서버 오류 시 500 Internal Server Error
        }
    }

    @PutMapping("/updateWarehouse/{id}")
    public ResponseEntity<WarehouseResponseTestDTO> updateWarehouse(@PathVariable("id") Long warehouseId, @RequestBody WarehouseRequestTestDTO requestDTO) {
        try {
            WarehouseResponseTestDTO updatedWarehouse = warehouseService.updateWarehouse(warehouseId, requestDTO);
            return ResponseEntity.ok(updatedWarehouse);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // 잘못된 요청일 경우
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // 서버 오류 발생 시
        }
    }

    @DeleteMapping("/deleteWarehouse/{id}")
    public ResponseEntity<String> deleteWarehouse(@PathVariable("id") Long id) {
        try {
            String result = warehouseService.deleteWarehouse(id);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("창고 삭제 중 오류가 발생했습니다.");
        }
    }
}
