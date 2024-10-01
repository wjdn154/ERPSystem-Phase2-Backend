package com.megazone.ERPSystem_phase2_Backend.production.controller.production_schedule.common_scheduling;

import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.company.CompanyRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Users;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Users.UsersRepository;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.dto.ShiftTypeDTO;
import com.megazone.ERPSystem_phase2_Backend.production.service.production_schedule.common_scheduling.ShiftType.ShiftTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/production/shiftType")
@RequiredArgsConstructor
public class ShiftTypeController {
    private final ShiftTypeService shiftTypeService;

    // POST: 교대유형 전체조회
    @PostMapping
    public ResponseEntity<List<ShiftTypeDTO>> getAllShiftTypes() {
        List<ShiftTypeDTO> shiftTypes = shiftTypeService.getAllShiftTypes();
        return ResponseEntity.ok(shiftTypes);
    }

    // POST: 교대유형 상세조회
    @PostMapping("/{id}")
    public ResponseEntity<ShiftTypeDTO> getShiftTypeById(@PathVariable("id") Long id) {
        ShiftTypeDTO shiftType = shiftTypeService.getShiftTypeById(id);
        return ResponseEntity.ok(shiftType);
    }

    // POST: 교대유형 새로 등록
    @PostMapping("/new")
    public ResponseEntity<ShiftTypeDTO> createShiftType(@RequestBody ShiftTypeDTO shiftTypeDTO) {
        ShiftTypeDTO createdShiftType = shiftTypeService.createShiftType(shiftTypeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdShiftType);
    }

    // POST: 교대유형 수정 - 유형이름 같으면 예외
    @PostMapping("/update/{id}")
    public ResponseEntity<ShiftTypeDTO> updateShiftType(@RequestBody ShiftTypeDTO shiftTypeDTO) {
        ShiftTypeDTO updatedShiftType = shiftTypeService.updateShiftType(shiftTypeDTO);
        return ResponseEntity.ok(updatedShiftType);
    }

    // POST: 교대유형 삭제 - 사용 중이면 삭제불가
    @PostMapping("/delete/{id}")
    public ResponseEntity<Void> deleteShiftType(@PathVariable("id") Long id) {
        shiftTypeService.deleteShiftType(id);
        return ResponseEntity.noContent().build();
    }
}