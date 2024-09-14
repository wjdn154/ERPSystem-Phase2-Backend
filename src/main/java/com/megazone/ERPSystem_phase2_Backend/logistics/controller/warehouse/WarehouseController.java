package com.megazone.ERPSystem_phase2_Backend.logistics.controller.warehouse;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Users;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Users.UsersRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.test.WarehouseListResponseDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.test.WarehouseRequestTestDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.test.WarehouseResponseTestDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.service.warehouse_management.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public ResponseEntity<List<WarehouseListResponseDTO>> getAllWarehouseList() {
        Users user = usersRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        Long companyId = user.getCompany().getId();

        List<WarehouseListResponseDTO> response = warehouseService.getWarehouseListByCompany(companyId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}")
    public ResponseEntity<WarehouseResponseTestDTO> getWarehouseDetailTest(@PathVariable("id") Long id) {
        WarehouseResponseTestDTO warehouseDetail = warehouseService.getWarehouseDetailTest(id);
        return ResponseEntity.ok(warehouseDetail);
    }

    @PostMapping("/saveWarehouse")
    public ResponseEntity<WarehouseResponseTestDTO> saveTestWarehouse(@RequestBody WarehouseRequestTestDTO warehouseRequestTestDTO) {
        Users user = usersRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        Long companyId = user.getCompany().getId();

        WarehouseResponseTestDTO savedWarehouse = warehouseService.saveTestWarehouse(warehouseRequestTestDTO, companyId);
        return ResponseEntity.ok(savedWarehouse);
    }

    @PutMapping("/updateWarehouse/{id}")
    public ResponseEntity<WarehouseResponseTestDTO> updateTestWarehouse(@PathVariable("id") Long id, @RequestBody WarehouseRequestTestDTO dto) {
        WarehouseResponseTestDTO updatedWarehouse = warehouseService.updateTestWarehouse(id, dto);
        return ResponseEntity.ok(updatedWarehouse);
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
