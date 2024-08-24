package com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.Employee;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.*;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.EmployeeDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.EmployeeShowDTO;
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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeServiceImpl implements EmployeeService{

        private final EmployeeRepository employeeRepository;
        //private final ResignedEmployeeRepository resignedEmployeeRepository;
        private final PerformanceRepository performanceRepository;
        @Autowired
        private DepartmentRepository departmentRepository;
        private PositionRepository positionRepository;
        private JobTitleRepository jobTitleRepository;
        private EmployeeBankAccountRepository bankAccountRepository;

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

            // 참조 데이터 설정
//            dto.setDepartmentName(employee.getDepartment().getId());
//            dto.setPositionName(employee.getPosition().getId());
//            dto.setJobTitleName(employee.getJobTitle().getId());
//            dto.setBankAccountNumber(employee.getBankAccount().getAccountNumber());

            // 추가 정보 설정 (예: 성과, 휴가, 근태 요약 등)
            //dto.setPerformanceSummary("Performance summary here...");
            //dto.setLeavesSummary("Leaves summary here...");
            //dto.setAttendanceSummary("Attendance summary here...");

            return dto;
        }

    @Override
    public List<EmployeeShowDTO> findAllEmployees() {

            //엔티티 조회
            //List<Employee> employee1 = employeeRepository.findAll();
            //엔티티 dto로 변환
        return employeeRepository.findAll().stream()
                .map(employee -> new EmployeeShowDTO(
                        employee.getId(),
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



    @Override
    //@Transactional
    public void deleteEmployee(Long id) {
            performanceRepository.deleteByEvaluatorId(id);
            // 먼저 관련된 자식 테이블 데이터 삭제
            //employeeRepository.deleteByEvaluatorId(id);
            // 그 후에 직원 삭제
        employeeRepository.deleteById(id);
    }

//    @Override
//    public Optional<EmployeeDTO> findEmployeeById(Long id) {
//        // 1. 엔티티를 데이터베이스에서 조회
//        Optional<Employee> employeeOptional = employeeRepository.findById(id);
//        // 2. DTO로 변환
//        if (employeeOptional.isPresent()) {
//            Employee employee = employeeOptional.get();
//            EmployeeDTO employeeDTO = new EmployeeDTO();
//
//            // DTO로 변환하는 로직
//            employeeDTO.setId(employee.getId());
//            employeeDTO.setFirstName(employee.getFirstName());
//            employeeDTO.setLastName(employee.getLastName());
//            employeeDTO.setEmail(employee.getEmail());
//            // 필요한 필드들을 채워줍니다.
//
//            // 3. DTO를 Optional로 반환
//            return Optional.of(employeeDTO);
//        } else {
//            // 해당 ID의 직원이 없으면 Optional.empty()를 반환
//            return Optional.empty();
//        }
//    }

    /**
     *  특정 ID에 해당하는 직원의 상세 정보를 조회함.
      * @param id 조회할 직원의 ID
     * @return 해당 ID의 직원 상세 정보를 담은 EmployeeDTO 객체를 반환함.
     */
@Override
public Optional<EmployeeDTO> findEmployeeById(Long id) {
    return employeeRepository.findById(id)
            .map(this::convertToDTO); // 여기서 변환 메서드를 사용
}

    @Override
    public Optional<EmployeeDTO> updateEmployee(Long id, EmployeeDTO employeeDTO) {
        // 1. 기존 직원 엔티티 조회
        Optional<Employee> employeeOptional = employeeRepository.findById(id);

        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();

            // 2. 엔티티 업데이트
            // employeeDTO의 값으로 엔티티의 값을 업데이트
            employee.setFirstName(employeeDTO.getFirstName());
            employee.setLastName(employeeDTO.getLastName());
            employee.setEmail(employeeDTO.getEmail());
            employee.setPhoneNumber(employeeDTO.getPhoneNumber());
            employee.setEmploymentStatus(employeeDTO.getEmploymentStatus());
            employee.setEmploymentType(employeeDTO.getEmploymentType());
            employee.setAddress(employeeDTO.getAddress());
            employee.setHireDate(employeeDTO.getHireDate());
            //employee.setPosition(employeeDTO.getPosition());
            // 필요한 다른 필드들도 업데이트

            // 3. 엔티티 저장
            Employee updatedEmployee = employeeRepository.save(employee);

            // 4. DTO로 변환하여 반환
            EmployeeDTO updatedEmployeeDTO = new EmployeeDTO();
            updatedEmployeeDTO.setId(updatedEmployee.getId());
            updatedEmployeeDTO.setFirstName(updatedEmployee.getFirstName());
            updatedEmployeeDTO.setLastName(updatedEmployee.getLastName());
            updatedEmployeeDTO.setEmail(updatedEmployee.getEmail());
            updatedEmployeeDTO.setPhoneNumber(updatedEmployee.getPhoneNumber());
            updatedEmployeeDTO.setEmploymentStatus(updatedEmployee.getEmploymentStatus());
            updatedEmployeeDTO.setEmploymentType(updatedEmployee.getEmploymentType());
            updatedEmployeeDTO.setAddress(updatedEmployee.getAddress());
            updatedEmployeeDTO.setHireDate(updatedEmployee.getHireDate());
            //updatedEmployeeDTO.setPosition(updatedEmployee.getPosition());
            // 필요한 필드들을 채워줍니다.

            return Optional.of(updatedEmployeeDTO);
        } else {
            // 해당 ID의 직원이 없으면 Optional.empty()를 반환
            return Optional.empty();
        }
    }

    @Override
    public Optional<EmployeeDTO> saveEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        // employeeDTO를 사용하여 Employee 엔티티를 초기화
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setEmail(employeeDTO.getEmail());
        employee.setPhoneNumber(employeeDTO.getPhoneNumber());
        employee.setEmploymentStatus(employeeDTO.getEmploymentStatus());
        employee.setEmploymentType(employeeDTO.getEmploymentType());
        employee.setAddress(employeeDTO.getAddress());
        employee.setHireDate(employeeDTO.getHireDate());
        employee.setDateOfBirth(employeeDTO.getDateOfBirth());
        // Department 설정
        Department department = departmentRepository.findById(employeeDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Department not found"));
        employee.setDepartment(department);

        // Position 설정
        Position position = positionRepository.findById(employeeDTO.getPositionName().getId())
                .orElseThrow(() -> new EntityNotFoundException("Position not found"));
        employee.setPosition(position);

        // JobTitle 설정
        JobTitle jobTitle = jobTitleRepository.findById(employeeDTO.getJobTitleName().getId())
                .orElseThrow(() -> new EntityNotFoundException("JobTitle not found"));
        employee.setJobTitle(jobTitle);

        // BankAccount 설정
        BankAccount bankAccount = bankAccountRepository.findById(employeeDTO.getBankAccountNumber().getId())
                .orElseThrow(() -> new EntityNotFoundException("BankAccount not found"));
        employee.setBankAccount(bankAccount);
//        employee.setDepartment(employeeDTO.getDepartmentName());
//        employee.setPosition(employeeDTO.getPositionName());
//        employee.setJobTitle(employeeDTO.getJobTitleName());
//        employee.setBankAccount(employeeDTO.getBankAccountNumber());

        Employee savedEmployee = employeeRepository.save(employee);
        EmployeeDTO savedEmployeeDTO = new EmployeeDTO(
                savedEmployee.getId(),
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
                savedEmployee.getDepartment(),
                savedEmployee.getPosition(),
                savedEmployee.getJobTitle(),
                savedEmployee.getBankAccount()
        );
        return Optional.of(savedEmployeeDTO);
}


    private EmployeeDTO convertToDTO(Employee employee) {
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
        dto.setProfilePicture(employee.getProfilePicture());
        // 나머지 필드들도 동일하게 설정
        return dto;}}
