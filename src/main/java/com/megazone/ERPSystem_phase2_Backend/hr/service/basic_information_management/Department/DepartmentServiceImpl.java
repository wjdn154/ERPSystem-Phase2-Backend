package com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.Department;


import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Department;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Employee;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.DepartmentCreateDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.DepartmentDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.DepartmentShowDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Department.DepartmentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    private DepartmentRepository departmentRepository;


    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    // 부서 리스트 조회
    @Override
    public List<DepartmentShowDTO> findAllDepartments() {
        return departmentRepository.findAll().stream()
                .map(department -> new DepartmentShowDTO(
                        department.getDepartmentCode(),
                        department.getDepartmentName(),
                        department.getLocation(),
                        department.getManagerId()
                )).collect(Collectors.toList());
    }

    // 부서 상세 조회
    @Override
    public Optional<DepartmentShowDTO> findDepartmentById(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("아이디가 올바르지 않습니다."));

        DepartmentShowDTO departmentDTO = convertToDTO(department);

        return Optional.of(departmentDTO);
    }

    private DepartmentShowDTO convertToDTO(Department department) {
        DepartmentShowDTO dto = new DepartmentShowDTO();
        dto.setDepartmentCode(department.getDepartmentCode());
        dto.setDepartmentName(department.getDepartmentName());
        dto.setDepartmentLocation(department.getLocation());
        dto.setManagerId(department.getManagerId());
        return dto;
    }


    // 부서 등록
    @Override
    public DepartmentCreateDTO saveDepartment(DepartmentDTO dto) {
        // DTO를 엔티티로 변환
        Department department = new Department();
        department.setDepartmentCode(dto.getDepartmentCode());
        department.setDepartmentName(dto.getDepartmentName());
        department.setLocation(dto.getDepartmentLocation());
        department.setManagerId(dto.getManagerId());

        // 엔티티를 저장
        Department savedDepartment = departmentRepository.save(department);

        // 저장된 엔티티의 ID를 포함한 DTO를 반환
        return new DepartmentCreateDTO(savedDepartment.getId());
    }
}
