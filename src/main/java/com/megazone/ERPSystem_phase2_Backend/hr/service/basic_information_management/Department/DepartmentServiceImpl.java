package com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.Department;


import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Department;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Employee;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.JobTitle;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Position;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.*;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Department.DepartmentRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Employee.EmployeeRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.JobTitle.JobTitleRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Position.PositionRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    private final PositionRepository positionRepository;
    private final JobTitleRepository jobTitleRepository;

    // 부서 리스트 조회
    @Override
    public List<DepartmentShowDTO> findAllDepartments() {
        return departmentRepository.findAll().stream()
                .map(department -> new DepartmentShowDTO(
                        department.getId(),
                        department.getDepartmentCode(),
                        department.getDepartmentName(),
                        department.getLocation()
                )).collect(Collectors.toList());
    }

    // 부서 상세 조회
    @Override
    public Optional<DepartmentDetailDTO> findDepartmentById(Long id) {
        Optional<Department> department = departmentRepository.findById(id);
//        Department department = departmentRepository.findById(id)
//                .orElseThrow(()-> new IllegalArgumentException("아이디가 올바르지 않습니다."));

        if (department.isPresent()) {
            DepartmentDetailDTO dto = new DepartmentDetailDTO();
            dto.setDepartmentCode(department.get().getDepartmentCode());
            dto.setDepartmentName(department.get().getDepartmentName());
            dto.setLocation(department.get().getLocation());

            // 사원 목록을 조회하여 DTO에 추가
            List<EmployeeDepartmentDTO> employeeDepartmentDTOS = employeeRepository.findByDepartmentId(id).stream()
                    .map(employee -> {
                            String positionName = positionRepository.findById(employee.getPosition().getId())
                            .map(Position::getPositionName).orElse("찾을 수 없습니다.");

                     String titleName = jobTitleRepository.findById(employee.getJobTitle().getId())
                            .map(JobTitle::getJobTitleName)
                            .orElse("찾을 수 없습니다.");

                            return new EmployeeDepartmentDTO(
                            employee.getId(),
                            employee.getEmployeeNumber(),
                            employee.getFirstName(),
                            employee.getLastName(),
                                    positionName,
                                    titleName
                            );
        }).collect(Collectors.toList());

            // 사원 리스트를 DTO에 추가
            dto.setEmployeeDepartmentDTOS(employeeDepartmentDTOS);

            return Optional.of(dto);
        }
        return Optional.empty();
    }

//        DepartmentDetailDTO departmentDTO = convertToDTO(department);
//
//        return Optional.of(departmentDTO);
//    }
//
//    private DepartmentDetailDTO convertToDTO(Department department) {
//        DepartmentDetailDTO dto = new DepartmentDetailDTO();
//        dto.setDepartmentCode(department.getDepartmentCode());
//        dto.setDepartmentName(department.getDepartmentName());
//        dto.setLocation(department.getLocation());
//        return dto;
//    }


    // 부서 등록
    @Override
    public DepartmentCreateDTO saveDepartment(DepartmentCreateDTO dto) {
        // DTO를 엔티티로 변환
        Department department = new Department();
        department.setDepartmentCode(dto.getDepartmentCode());
        department.setDepartmentName(dto.getDepartmentName());
        department.setLocation(dto.getLocation());

        // 엔티티를 저장
        Department savedDepartment = departmentRepository.save(department);

        // 저장된 엔티티의 ID를 포함한 DTO를 반환
        return new DepartmentCreateDTO(
                savedDepartment.getDepartmentCode(),
                savedDepartment.getDepartmentName(),
                savedDepartment.getLocation());
    }

    // 부서에 속한 사원이 있는지 확인
    public boolean hasEmployees(Long departmentId) {
        // 한 번만 existsByDepartmentId 호출하여 결과를 저장
        boolean hasEmployees = employeeRepository.existsByDepartmentId(departmentId);

        // 사원이 있을 경우 예외를 던짐
        if (hasEmployees) {
            throw new RuntimeException("해당 부서에 속한 사원이 있어 삭제할 수 없습니다.");
        }

        // 사원이 없으면 false 반환
        return hasEmployees;
    }

    // 부서 삭제 로직
    public void deleteDepartment(Long departmentId) {
        // 부서가 존재하는지 확인하고 삭제
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("부서를 찾을 수 없습니다."));
        departmentRepository.delete(department);
    }

//    @Override
//    public DepartmentDetailDTO updateDepartment(Long id, DepartmentCreateDTO dto) {
//        // ID에 해당하는 부서를 조회
//        Optional<Department> departmentOpt = departmentRepository.findById(id);
//
//        // 부서가 존재하지 않는 경우 예외 발생
//        if (!departmentOpt.isPresent()) {
//            throw new EntityNotFoundException("해당하는 부서를 찾을 수 없습니다.");
//        }
//
//        // 부서 정보 업데이트
//        Department department = departmentOpt.get();
//        department.setDepartmentName(dto.getDepartmentName());  // 필요한 필드에 따라 업데이트
//        department.setDepartmentCode(dto.getDepartmentCode());
//        department.setLocation(dto.getDepartmentLocation());
//
//        // 저장 후 DTO로 변환하여 반환
//        Department updatedDepartment = departmentRepository.save(department);
//        DepartmentDetailDTO departmentDetailDTO = new DepartmentDetailDTO();
//        departmentDetailDTO.setDepartmentCode(updatedDepartment.getDepartmentCode());
//        departmentDetailDTO.setDepartmentName(updatedDepartment.getDepartmentName());
//        departmentDetailDTO.setLocation(updatedDepartment.getLocation());
//
//        return departmentDetailDTO;
//    }
public DepartmentDetailDTO updateDepartment(Long id, DepartmentCreateDTO dto) {
    // 부서 정보 업데이트
    Department department = departmentRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Department not found with id " + id));

    department.setDepartmentCode(dto.getDepartmentCode());
    department.setDepartmentName(dto.getDepartmentName());
    department.setLocation(dto.getLocation());
    departmentRepository.save(department);

    // 부서에 속한 사원들의 부서 정보 업데이트
    List<Employee> employeesInDepartment = employeeRepository.findByDepartmentId(id);
    employeesInDepartment.forEach(employee -> employee.setDepartment(department));
    employeeRepository.saveAll(employeesInDepartment);  // 한 번에 저장

    // 부서 정보와 관련 사원 정보 포함하여 DepartmentDetailDTO 생성
    List<EmployeeDepartmentDTO> employeeDepartmentDTOS = employeesInDepartment.stream()
            .map(emp -> new EmployeeDepartmentDTO(
                    emp.getId(),
                    emp.getEmployeeNumber(),
                    emp.getFirstName(),
                    emp.getLastName(),
                    emp.getPosition().getPositionName(),
                    emp.getJobTitle().getJobTitleName()
            ))
            .collect(Collectors.toList());

    return new DepartmentDetailDTO(
            department.getDepartmentCode(),
            department.getDepartmentName(),
            department.getLocation(),
            employeeDepartmentDTOS
    );
}
}
