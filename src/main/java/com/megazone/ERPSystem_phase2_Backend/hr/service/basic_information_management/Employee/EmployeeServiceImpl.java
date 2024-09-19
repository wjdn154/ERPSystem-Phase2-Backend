package com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.Employee;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.*;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.*;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.BankAccount.EmployeeBankAccountRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Department.DepartmentRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Employee.EmployeeRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.JobTitle.JobTitleRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Performance.PerformanceRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Position.PositionRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Users.UsersRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PerformanceRepository performanceRepository;
    private final DepartmentRepository departmentRepository;
    private final PositionRepository positionRepository;
    private final JobTitleRepository jobTitleRepository;
    private final EmployeeBankAccountRepository bankAccountRepository;
    private final UsersRepository usersRepository;

    // 사원 리스트 조회
    @Override
    public List<EmployeeShowDTO> findAllEmployees() {
        //엔티티 dto로 변환
        return employeeRepository.findAll().stream()
                .map(employee -> new EmployeeShowDTO(
                        employee.getId(),
                        employee.getEmployeeNumber(),
                        employee.getFirstName(),
                        employee.getLastName(),
                        employee.getEmploymentStatus(),
                        employee.getEmploymentType(),
                        employee.getEmail(),
                        employee.getHireDate(),
                        employee.getDepartment().getDepartmentCode(),
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


    public EmployeeFindDTO getEmployeeDTO(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));

        EmployeeFindDTO dto = new EmployeeFindDTO();
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
//    public void deleteEmployeeById(Long id) {
//        employeeRepository.deleteById(id); // DB에서 사원을 삭제
//    }

    @Override
    public Optional<EmployeeFindDTO> updateEmployee(Long id, EmployeeDataDTO dto) {
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
            EmployeeFindDTO updatedEmployeeDTO = new EmployeeFindDTO(
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
    public EmployeeDTO saveEmployee(EmployeeCreateDTO dto) {

        String employeeNumber = createEmployeeNumber(dto);
        // Employee 엔티티를 초기화합니다.
        Employee employee = new Employee();
        employee.setEmployeeNumber(employeeNumber);
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setDateOfBirth(dto.getDateOfBirth());
        employee.setPhoneNumber(dto.getPhoneNumber());
        employee.setEmploymentStatus(dto.getEmploymentStatus());
        employee.setEmploymentType(dto.getEmploymentType());
        employee.setEmail(dto.getEmail());
        employee.setAddress(dto.getAddress());
        employee.setHireDate(dto.getHireDate());
        employee.setHouseholdHead(dto.isHouseholdHead());
        employee.setProfilePicture(dto.getProfilePicture());

        // Department 설정
        if (dto.getDepartmentId() != null) {
            Department department = departmentRepository.findById(dto.getDepartmentId())
                    .orElseThrow(() -> new EntityNotFoundException("부서를 찾을 수 없습니다."));
            employee.setDepartment(department);
        }

        // Position 설정
        if (dto.getPositionId() != null) {
            Position position = positionRepository.findById(dto.getPositionId())
                    .orElseThrow(() -> new EntityNotFoundException("직위를 찾을 수 없습니다."));
            employee.setPosition(position);
        }

        // JobTitle 설정
        if (dto.getJobTitleId() != null) {
            JobTitle jobTitle = jobTitleRepository.findById(dto.getJobTitleId())
                    .orElseThrow(() -> new EntityNotFoundException("직책을 찾을 수 없습니다."));
            employee.setJobTitle(jobTitle);
        }

        // BankAccount 설정
        BankAccount newBankAccount = new BankAccount();
        newBankAccount.setBankName(dto.getBankAccountDTO().getBankName());
        newBankAccount.setAccountNumber(dto.getBankAccountDTO().getAccountNumber());
        newBankAccount.setEmployee(employee);
        employee.setBankAccount(newBankAccount);
        bankAccountRepository.save(newBankAccount);

        // 사원 정보를 저장합니다.
        Employee savedEmployee = employeeRepository.save(employee);

        // 저장된 정보를 DTO로 변환하여 반환합니다.
        return EmployeeDTO.create(savedEmployee);
    }

//    @Override
//    public String createEmployeeNumber(){
//        // 입사일이  20240914001 <- 1번
//        //         20240914002 <- 2번
//        //   1 2 3 4
//
//        // 1, 2, 3 번 사원이 등록되어있음
//        // 4
//        // 마지막번호인 3번을 찾는다.
//
//        // hireDate를 "yyMMdd" 형식으로 변환
//
//        String newEmployeeNumber;
//        Employee lastEmployee = employeeRepository.findFirstByOrderByIdDesc().orElse(null);
//
//
//        // 3번을 찾아서 1을 더한값을 신규등록 사원에게 부여
//        if(lastEmployee == null) {
//            newEmployeeNumber = "1";
//        }else {
//            String lastNumber = lastEmployee.getEmployeeNumber();
//            newEmployeeNumber = String.valueOf(Integer.parseInt(lastNumber) + 1);
//        }
//        // 걔는 4번
//        return newEmployeeNumber;
//    }



    public String createEmployeeNumber(EmployeeCreateDTO dto) {
        LocalDate hireDate = dto.getHireDate();  // dto에서 hireDate 가져오기

        // hireDate를 "yyMMdd" 형식으로 변환
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
        String hireDateStr = hireDate.format(formatter);  // 예: "240914" (2024년 9월 14일)

        // 마지막 사원을 ID 기준으로 내림차순으로 찾는다.
        Employee lastEmployee = employeeRepository.findFirstByOrderByIdDesc().orElse(null);

        String newEmployeeNumber;

        if (lastEmployee == null || !lastEmployee.getEmployeeNumber().startsWith(hireDateStr)) {
            // 마지막 사원이 없거나, 사번이 현재 입사일로 시작하지 않으면 001로 시작
            newEmployeeNumber = hireDateStr + "001";
        } else {
            // 마지막 사원의 사번에서 일련번호 추출
            String lastNumber = lastEmployee.getEmployeeNumber();
            String lastSequence = lastNumber.substring(6);  // hireDate 이후의 3자리 숫자만 추출
            int newSequence = Integer.parseInt(lastSequence) + 1;

            // 새로운 일련번호는 3자리로 포맷팅 (예: 001, 002, 003, ...)
            String formattedSequence = String.format("%03d", newSequence);
            newEmployeeNumber = hireDateStr + formattedSequence;
        }

        return newEmployeeNumber;  // 예: "240914001", "240914002"
    }

}
