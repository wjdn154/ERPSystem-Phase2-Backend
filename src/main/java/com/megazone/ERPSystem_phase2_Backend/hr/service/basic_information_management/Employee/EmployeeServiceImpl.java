package com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.Employee;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.*;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.*;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.BankAccount.EmployeeBankAccountRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Department.DepartmentRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Employee.EmployeeRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.JobTitle.JobTitleRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Performance.PerformanceRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Position.PositionRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PerformanceRepository performanceRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private PositionRepository positionRepository;
    @Autowired
    private JobTitleRepository jobTitleRepository;
    @Autowired
    private EmployeeBankAccountRepository bankAccountRepository;

    // 사원 리스트 조회
    @Override
    public List<EmployeeShowDTO> findAllEmployees() {
        //엔티티 dto로 변환
        return employeeRepository.findAll().stream()
                .map(employee -> new EmployeeShowDTO(
                        employee.getEmployeeNumber(),
                        employee.getFirstName(),
                        employee.getLastName(),
                        employee.getDateOfBirth(),
                        employee.getPhoneNumber(),
                        employee.getEmploymentStatus(),
                        employee.getEmploymentType(),
                        employee.getEmail(),
                        employee.getAddress(),
                        employee.getHireDate(),
                        employee.isHouseholdHead(),
                        employee.getProfilePicture(),
                        employee.getDepartment().getDepartmentName(),
                        employee.getPosition().getPositionName(),
                        employee.getJobTitle().getTitleName(),
                        employee.getBankAccount().getAccountNumber()
                ))
                .collect(Collectors.toList());
    }

    // 사원 상세 조회
    @Override
    public Optional<EmployeeOneDTO> findEmployeeById(Long id) {
        //엔티티 조회
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("아이디가 올바르지 않습니다."));

        //엔티티를 dto로 변환.
        EmployeeOneDTO employeeDTO = convertToDTO(employee);

        return Optional.of(employeeDTO);
    }

    private EmployeeOneDTO convertToDTO(Employee employee) {
        EmployeeOneDTO dto = new EmployeeOneDTO();
        dto.setId(employee.getId());
        dto.setEmployeeNumber(employee.getEmployeeNumber());
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        dto.setDateOfBirth(employee.getDateOfBirth());
        dto.setPhoneNumber(employee.getPhoneNumber());
        dto.setEmploymentStatus(employee.getEmploymentStatus());
        dto.setEmploymentType(employee.getEmploymentType());
        dto.setEmail(employee.getEmail());
        dto.setAddress(employee.getAddress());
        dto.setHireDate(employee.getHireDate());
        dto.setHouseholdHead(employee.isHouseholdHead());
        dto.setProfilePicture(employee.getProfilePicture());
        dto.setDepartmentName(employee.getDepartment().getDepartmentName());
        dto.setPositionName(employee.getPosition().getPositionName());
        dto.setJobTitleName(employee.getJobTitle().getTitleName());
        dto.setBankAccountNumber(employee.getBankAccount().getAccountNumber());
        return dto;
    }

   ///**
   // * 특정 ID에 해당하는 사원의 상세 정보를 조회함.
   // *
   // * @param employeeNumber 조회할 사원의 사원번호
   // * @return 해당 ID의 사원 상세 정보를 담은 EmployeeDTO 객체를 반환함.
   // */
   //@Override
   //public Optional<EmployeeOneDTO> findEmployeeByEmployeeNumber(String employeeNumber) {
   //    return employeeRepository.findByEmployeeNumber(employeeNumber)
   //            .map(this::convertToDTO); // 여기서 변환 메서드를 사용
   //}

    // 사원 삭제
    @Override
    public void deleteEmployee(Long id) {
        performanceRepository.deleteByEvaluatorId(id);
        employeeRepository.deleteById(id);
    }

    public EmployeeDTO getEmployeeDTO(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));

        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        dto.setDateOfBirth(employee.getDateOfBirth());
        dto.setPhoneNumber(employee.getPhoneNumber());
        dto.setEmploymentStatus(employee.getEmploymentStatus());
        dto.setEmploymentType(employee.getEmploymentType());
        dto.setEmail(employee.getEmail());
        dto.setAddress(employee.getAddress());
        dto.setHireDate(employee.getHireDate());
        dto.setHouseholdHead(employee.isHouseholdHead());
        dto.setDepartmentId(employee.getDepartment().getId());
        dto.setPositionId(employee.getPosition().getId());
        dto.setJobTitleId(employee.getJobTitle().getId());
        dto.setBankAccountId(employee.getBankAccount().getId());
        return dto;
    }

    @Override
    public Optional<EmployeeDTO> updateEmployee(Long id, EmployeeDataDTO dto) {
        // id 에 해당하는 엔티티 데이터 조회
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->  new RuntimeException(id+"에 해당하는 아이디를 찾을 수 없습니다."));

            // 2. 엔티티 업데이트
            // employeeDTO의 값으로 엔티티의 값을 업데이트
            employee.setFirstName(dto.getFirstName());
            employee.setLastName(dto.getLastName());
            employee.setEmail(dto.getEmail());
            employee.setPhoneNumber(dto.getPhoneNumber());
            employee.setEmploymentStatus(dto.getEmploymentStatus());
            employee.setEmploymentType(dto.getEmploymentType());
            employee.setAddress(dto.getAddress());
            employee.setHireDate(dto.getHireDate());
            employee.setDateOfBirth(dto.getDateOfBirth());
            employee.setProfilePicture(dto.getProfilePicture());
            employee.setHouseholdHead(dto.isHouseholdHead());

            // Position 설정
            if (dto.getPositionId() != null) {
                Position position = positionRepository.findById(dto.getPositionId())
                        .orElseThrow(() -> new EntityNotFoundException("Position not found"));
                employee.setPosition(position);
            }

            // Department 설정
            if (dto.getDepartmentId() != null) {
                Department department = departmentRepository.findById(dto.getDepartmentId())
                        .orElseThrow(() -> new EntityNotFoundException("Department not found"));
                employee.setDepartment(department);
            }

            // JobTitle 설정
            if (dto.getJobTitleId() != null) {
                JobTitle jobTitle = jobTitleRepository.findById(dto.getJobTitleId())
                        .orElseThrow(() -> new EntityNotFoundException("JobTitle not found"));
                employee.setJobTitle(jobTitle);
            }

            // BankAccount 설정
            if (dto.getBankAccountId() != null) {
                BankAccount bankAccount = bankAccountRepository.findById(dto.getBankAccountId())
                        .orElseThrow(() -> new EntityNotFoundException("BankAccount not found"));
                employee.setBankAccount(bankAccount);
            }

            // 3. 엔티티 저장
            Employee updatedEmployee = employeeRepository.save(employee);

            // 4. DTO로 변환하여 반환
            EmployeeDTO updatedEmployeeDTO = new EmployeeDTO(
                    updatedEmployee.getId(),
                    updatedEmployee.getEmployeeNumber(),
                    updatedEmployee.getFirstName(),
                    updatedEmployee.getLastName(),
                    updatedEmployee.getDateOfBirth(),
                    updatedEmployee.getPhoneNumber(),
                    updatedEmployee.getEmploymentStatus(),
                    updatedEmployee.getEmploymentType(),
                    updatedEmployee.getEmail(),
                    updatedEmployee.getAddress(),
                    updatedEmployee.getHireDate(),
                    updatedEmployee.isHouseholdHead(),
                    updatedEmployee.getProfilePicture(),
                    updatedEmployee.getDepartment() != null ? updatedEmployee.getDepartment().getId() : null,
                    updatedEmployee.getPosition() != null ? updatedEmployee.getPosition().getId() : null,
                    updatedEmployee.getJobTitle() != null ? updatedEmployee.getJobTitle().getId() : null,
                    updatedEmployee.getBankAccount() != null ? updatedEmployee.getBankAccount().getId() : null
            );
            return Optional.of(updatedEmployeeDTO);
    }

    // 사원 등록. 저장
    @Override
    public Optional<EmployeeDTO> saveEmployee(EmployeeCreateDTO dto) {
        // Check if employeeNumber already exists
        if (employeeRepository.findByEmployeeNumber(dto.getEmployeeNumber()).isPresent()) {
            throw new DuplicateKeyException("Employee number already exists."+ dto.getEmployeeNumber());
        }
        // Employee 엔티티를 초기화합니다.
        Employee employee = new Employee();
        employee.setEmployeeNumber(dto.getEmployeeNumber());
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setEmail(dto.getEmail());
        employee.setPhoneNumber(dto.getPhoneNumber());
        employee.setEmploymentStatus(dto.getEmploymentStatus());
        employee.setEmploymentType(dto.getEmploymentType());
        employee.setAddress(dto.getAddress());
        employee.setHireDate(dto.getHireDate());
        employee.setDateOfBirth(dto.getDateOfBirth());

        // Department 설정
        if (dto.getDepartmentId() != null) {
            Department department = departmentRepository.findById(dto.getDepartmentId())
                    .orElseThrow(() -> new EntityNotFoundException("Department not found"));
            employee.setDepartment(department);
        }

        // Position 설정
        if (dto.getPositionId() != null) {
            Position position = positionRepository.findById(dto.getPositionId())
                    .orElseThrow(() -> new EntityNotFoundException("Position not found"));
            employee.setPosition(position);
        }

        // JobTitle 설정
        if (dto.getJobTitleId() != null) {
            JobTitle jobTitle = jobTitleRepository.findById(dto.getJobTitleId())
                    .orElseThrow(() -> new EntityNotFoundException("JobTitle not found"));
            employee.setJobTitle(jobTitle);
        }

        // BankAccount 설정
        if (dto.getBankAccountId() != null) {
            BankAccount bankAccount = bankAccountRepository.findById(dto.getBankAccountId())
                    .orElseThrow(() -> new EntityNotFoundException("BankAccount not found"));
            employee.setBankAccount(bankAccount);
        }

        // 사원 정보를 저장합니다.
        Employee savedEmployee = employeeRepository.save(employee);

        // 저장된 정보를 DTO로 변환하여 반환합니다.
        EmployeeDTO savedEmployeeDTO = new EmployeeDTO(
                savedEmployee.getId(),
                savedEmployee.getEmployeeNumber(),
                savedEmployee.getFirstName(),
                savedEmployee.getLastName(),
                savedEmployee.getDateOfBirth(),
                savedEmployee.getPhoneNumber(),
                savedEmployee.getEmploymentStatus(),
                savedEmployee.getEmploymentType(),
                savedEmployee.getEmail(),
                savedEmployee.getAddress(),
                savedEmployee.getHireDate(),
                savedEmployee.isHouseholdHead(),
                savedEmployee.getProfilePicture(),
                savedEmployee.getDepartment() != null ? savedEmployee.getDepartment().getId() : null,
                savedEmployee.getPosition() != null ? savedEmployee.getPosition().getId() : null,
                savedEmployee.getJobTitle() != null ? savedEmployee.getJobTitle().getId() : null,
                savedEmployee.getBankAccount() != null ? savedEmployee.getBankAccount().getId() : null
        );

        return Optional.of(savedEmployeeDTO);
    }
}
