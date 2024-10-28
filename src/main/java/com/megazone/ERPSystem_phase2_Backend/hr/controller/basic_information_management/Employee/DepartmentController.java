package com.megazone.ERPSystem_phase2_Backend.hr.controller.basic_information_management.Employee;


import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.DepartmentCreateDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.DepartmentDetailDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.DepartmentShowDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.Department.DepartmentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hr")
public class DepartmentController {
    private final DepartmentService departmentService;

    // 모든 부서 조회
    @PostMapping("/department/all")
    public ResponseEntity<List<DepartmentShowDTO>> findAllDepartments() {
        List<DepartmentShowDTO> departments = departmentService.findAllDepartments();
        return ResponseEntity.ok(departments);
    }

    // 부서id 로 부서 조회
    @PostMapping("/department/{id}")
    public ResponseEntity<DepartmentDetailDTO> getDepartmentById(@PathVariable("id") Long id) {
        Optional<DepartmentDetailDTO> department = departmentService.findDepartmentById(id);

        return department.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 부서 등록
    @PostMapping("/department/createDepartment")
    public ResponseEntity<DepartmentCreateDTO> createDepartment(@RequestBody DepartmentCreateDTO dto) {
        DepartmentCreateDTO createdDepartment = departmentService.saveDepartment(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDepartment);
    }

    // 부서 삭제
    @DeleteMapping("/department/delete/{id}")
    public ResponseEntity<String> deleteDepartment(@PathVariable("id") Long id) {

        // 부서에 속한 사원이 있는지 확인
        if (departmentService.hasEmployees(id)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("부서에 속한 사원이 있습니다. 부서를 삭제하기 전에 사원을 다른 부서로 옮기십시오.");
        }

        // 사원이 없는 경우 부서 삭제
        departmentService.deleteDepartment(id);
        return ResponseEntity.ok("부서가 성공적으로 삭제되었습니다.");
    }

    @PostMapping("/department/update/{id}")
    public ResponseEntity<DepartmentDetailDTO> updateDepartment(
            @PathVariable("id") Long id,
            @RequestBody DepartmentCreateDTO dto) {
        System.out.println("Received DTO: " + dto);
        System.out.println("Received Department Code: " + dto.getDepartmentCode());
        System.out.println("Received Department Name: " + dto.getDepartmentName());
        System.out.println("Received Location: " + dto.getLocation());

        try {
            DepartmentDetailDTO updatedDepartment = departmentService.updateDepartment(id, dto);
            return ResponseEntity.ok(updatedDepartment);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
