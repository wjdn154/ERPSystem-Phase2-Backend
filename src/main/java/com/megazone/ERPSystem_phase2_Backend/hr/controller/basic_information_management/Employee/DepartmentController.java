package com.megazone.ERPSystem_phase2_Backend.hr.controller.basic_information_management.Employee;


import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.DepartmentCreateDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.DepartmentDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.DepartmentShowDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Department.DepartmentRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.Department.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hr")
public class DepartmentController {
    @Autowired
    private final DepartmentService departmentService;
    private final DepartmentRepository departmentRepository;

    // 모든 부서 조회
    @PostMapping("/department/all")
    public ResponseEntity<List<DepartmentShowDTO>> findAllDepartments() {
        List<DepartmentShowDTO> departments = departmentService.findAllDepartments();
        return ResponseEntity.ok(departments);
    }

    // 부서id 로 부서 조회
    @GetMapping("/department/{id}")
    public ResponseEntity<DepartmentShowDTO> getDepartmentById(@PathVariable("id") Long id) {
        Optional<DepartmentShowDTO> department = departmentService.findDepartmentById(id);

        return department.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    // 부서 등록
    @PostMapping("/department/createDepartment")
    public ResponseEntity<DepartmentCreateDTO> createDepartment(@RequestBody DepartmentDTO dto) {
        DepartmentCreateDTO createdDepartment = departmentService.saveDepartment(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDepartment);
    }
}
