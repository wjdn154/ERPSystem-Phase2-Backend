package com.megazone.ERPSystem_phase2_Backend.hr.service.hr_management.Employee;

import com.megazone.ERPSystem_phase2_Backend.hr.model.hr_management.Employee;
import com.megazone.ERPSystem_phase2_Backend.hr.model.hr_management.dto.EmployeeDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.hr_management.Employee.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeServiceImpl implements EmployeeService{

        private final EmployeeRepository employeeRepository;
//        @Autowired
//        private DepartmentRepository departmentRepository;
//        @Autowired
//        private PositionRepository positionRepository;
//        @Autowired
//        private JobTitleRepository jobTitleRepository;
//        @Autowired
//        private BankAccountRepository bankAccountRepository;

        public EmployeeDTO getEmployeeDTO(Long id) {
            Employee employee = employeeRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Employee not found"));

            EmployeeDTO dto = new EmployeeDTO();
            dto.setId(employee.getId());
            dto.setFirstName(employee.getFirstName());
            dto.setLastName(employee.getLastName());
            //dto.setDateOfBirth(employee.getDateOfBirth().toLocalDate());
            dto.setPhoneNumber(employee.getPhoneNumber());
            dto.setEmploymentStatus(employee.getEmploymentStatus());
            dto.setEmploymentType(employee.getEmploymentType());
            dto.setEmail(employee.getEmail());
            dto.setAddress(employee.getAddress());
            dto.setHireDate(employee.getHireDate());
            //dto.setIsHouseholdHead(employee.isHouseholdHead());

            // 참조 데이터 설정
            //dto.setDepartmentName(employee.getDepartment().getName());
            //.setPositionName(employee.getPosition().getName());
            //dto.setJobTitleName(employee.getJobTitle().getName());
            //dto.setBankAccountNumber(employee.getBankAccount().getAccountNumber());

            // 추가 정보 설정 (예: 성과, 휴가, 근태 요약 등)
            //dto.setPerformanceSummary("Performance summary here...");
            //dto.setLeavesSummary("Leaves summary here...");
            //dto.setAttendanceSummary("Attendance summary here...");

            return dto;
        }

    @Override
    public List<EmployeeDTO> findAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(employee -> new EmployeeDTO(
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
                        employee.getProfilePicture()
                        ))
                .toList();
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        return null;
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        return null;
    }

    @Override
    public void deleteEmployee(Long employeeid) {

    }
}