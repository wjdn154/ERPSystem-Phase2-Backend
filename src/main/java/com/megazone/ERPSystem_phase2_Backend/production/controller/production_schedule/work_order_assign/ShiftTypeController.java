package com.megazone.ERPSystem_phase2_Backend.production.controller.production_schedule.work_order_assign;

import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.company.CompanyRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Users;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Users.UsersRepository;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.dto.ShiftTypeDTO;
import com.megazone.ERPSystem_phase2_Backend.production.service.production_schedule.work_order_assign.shift_type.ShiftTypeService;
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
    private final CompanyRepository companyRepository;
    private final UsersRepository usersRepository;

    // JWT 인증에서 사용자 정보를 가져오는 메서드
    private Long getCurrentCompanyId() {
        Users user = usersRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        return user.getCompany().getId();
    }


    // POST: 교대유형 전체조회
    @PostMapping("/")
    public ResponseEntity<List<ShiftTypeDTO>> getAllShiftTypes() {
        Long companyId = getCurrentCompanyId();
        List<ShiftTypeDTO> shiftTypes = shiftTypeService.getAllShiftTypes(companyId);
        return ResponseEntity.ok(shiftTypes);
    }

    // POST: 교대유형 상세조회
    @PostMapping("/{id}/")
    public ResponseEntity<ShiftTypeDTO> getShiftTypeById(@PathVariable Long id) {
        Long companyId = getCurrentCompanyId();
        ShiftTypeDTO shiftType = shiftTypeService.getShiftTypeById(id, companyId);
        return ResponseEntity.ok(shiftType);
    }

    // POST: 교대유형 새로 등록
    @PostMapping("/new/")
    public ResponseEntity<ShiftTypeDTO> createShiftType(@RequestBody ShiftTypeDTO shiftTypeDTO) {
        Long companyId = getCurrentCompanyId();
        ShiftTypeDTO createdShiftType = shiftTypeService.createShiftType(shiftTypeDTO, companyId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdShiftType);
    }

    // POST: 교대유형 수정 - 유형이름 같으면 예외
    @PostMapping("/update/")
    public ResponseEntity<ShiftTypeDTO> updateShiftType(@RequestBody ShiftTypeDTO shiftTypeDTO) {
        Long companyId = getCurrentCompanyId();
        ShiftTypeDTO updatedShiftType = shiftTypeService.updateShiftType(shiftTypeDTO, companyId);
        return ResponseEntity.ok(updatedShiftType);
    }

    // POST: 교대유형 삭제 - 사용 중이면 삭제불가
    @PostMapping("/delete/{id}")
    public ResponseEntity<Void> deleteShiftType(@PathVariable Long id) {
        Long companyId = getCurrentCompanyId();
        shiftTypeService.deleteShiftType(id, companyId);
        return ResponseEntity.noContent().build();
    }
}