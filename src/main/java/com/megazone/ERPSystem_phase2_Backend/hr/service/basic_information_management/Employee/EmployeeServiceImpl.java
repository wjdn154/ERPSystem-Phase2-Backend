package com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.Employee;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company.Company;
import com.megazone.ERPSystem_phase2_Backend.financial.model.common.Bank;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.company.CompanyRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.common.BankRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.*;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.*;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.attendance_management.Attendance.AttendanceRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.BankAccount.EmployeeBankAccountRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Department.DepartmentRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Employee.EmployeeRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.JobTitle.JobTitleRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Performance.PerformanceRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Position.PositionRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.EmployeeImage.EmployeeImageService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    private final CompanyRepository companyRepository;
    private final AttendanceRepository attendanceRepository;
    private final BankRepository bankRepository;
    private final EmployeeImageService employeeImageService;

    // 이미지가 저장된 경로 (src/main/resources/static/uploads/)
    private static final String UPLOAD_DIR = "src/main/resources/static";

    //private final ClientRepository clientRepository;
    //private final ResolvedVoucherRepository resolvedVoucherRepository;

    // 사원 리스트 조회
    @Override
    public List<EmployeeShowDTO> findAllEmployees() {
        //엔티티 dto로 변환
        return employeeRepository.findAllByUser().stream()
                .map(employee -> new EmployeeShowDTO(
                        employee.getId(),
                        employee.getEmployeeNumber(),
                        employee.getFirstName(),
                        employee.getLastName(),
                        employee.getEmploymentStatus(),
                        employee.getEmploymentType(),
                        employee.getEmail(),
                        employee.getHireDate(),
                        employee.getImagePath(),
                        employee.getDepartment().getDepartmentCode(),
                        employee.getDepartment().getDepartmentName(),
                        employee.getPosition().getId(),
                        employee.getPosition().getPositionName(),
                        employee.getJobTitle().getJobTitleName()
//                        employee.getBankAccount().getAccountNumber()
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
        dto.setRegistrationNumber(employee.getRegistrationNumber());
        dto.setPhoneNumber(employee.getPhoneNumber());
        dto.setEmploymentStatus(employee.getEmploymentStatus());
        dto.setEmploymentType(employee.getEmploymentType());
        dto.setEmail(employee.getEmail());
        dto.setAddress(employee.getAddress());
        dto.setHireDate(employee.getHireDate());
        dto.setHouseholdHead(employee.isHouseholdHead());
        dto.setProfilePicture(employee.getImagePath());
        dto.setDepartmentId(employee.getDepartment().getId());
        dto.setDepartmentCode(employee.getDepartment().getDepartmentCode());
        dto.setDepartmentName(employee.getDepartment().getDepartmentName());
        dto.setPositionId(employee.getPosition().getId());
        dto.setPositionCode(employee.getPosition().getPositionCode());
        dto.setPositionName(employee.getPosition().getPositionName());
        dto.setTitleId(employee.getJobTitle().getId());
        dto.setTitleCode(employee.getJobTitle().getJobTitleCode());
        dto.setTitleName(employee.getJobTitle().getJobTitleName());
        // 이미 만들어진 BankAccountDTO 사용
        if (employee.getBankAccount() != null && employee.getBankAccount().getBank() != null) {
            BankAccountDTO bankAccountDTO = BankAccountDTO.create(employee.getBankAccount());
            dto.setBankAccountDTO(bankAccountDTO);  // BankAccountDTO 설정
        } else {
            dto.setBankAccountDTO(null);  // BankAccount가 없는 경우 처리
        }

        return dto;
    }
    // 사원 삭제
    @Override
    public void deleteEmployee(Long employeeId) {
        performanceRepository.deleteByEmployeeId(employeeId);
        attendanceRepository.deleteByEmployeeId(employeeId);
        employeeRepository.deleteById(employeeId);
    }


    @Override
    public ResponseEntity<Object> getAdminPermissionEmployee(Long companyId) {
        Company company = companyRepository.findById(companyId).orElse(null);
        if (company == null) return ResponseEntity.badRequest().body("회사를 찾을 수 없습니다.");
        return ResponseEntity.ok(company.getAdminUsername());
    }

    public EmployeeFindDTO getEmployeeDTO(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("사원을 찾을 수 없습니다"));

        EmployeeFindDTO dto = new EmployeeFindDTO();
        dto.setId(employee.getId());
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        dto.setRegistrationNumber(employee.getRegistrationNumber());
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


    // 사원 정보 수정
    @Override
    public Optional<EmployeeShowToDTO> updateEmployee(Long id, EmployeeDataDTO dto, MultipartFile imageFile) {
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
            employee.setImagePath(dto.getProfilePicture());
            employee.setHouseholdHead(dto.isHouseholdHead());

            // Position 설정
            if (dto.getPositionName() != null) {
                Position position = positionRepository.findByPositionName(dto.getPositionName()).orElseGet(() ->{
                    Position newPosition = new Position();
                    newPosition.setPositionName(dto.getPositionName());
                    return positionRepository.save(newPosition);
                });
                employee.setPosition(position);
            }

            // Department 설정
            if (dto.getDepartmentCode() != null) {
                Department department = departmentRepository.findByDepartmentCode(dto.getDepartmentCode()).orElseGet(() -> {
                    Department newDepartment = new Department();
                    newDepartment.setDepartmentCode(dto.getDepartmentCode());
                    return departmentRepository.save(newDepartment);
                });
                employee.setDepartment(department);
            }

            // JobTitle 설정
            if (dto.getTitleName() != null) {
                JobTitle jobTitle = jobTitleRepository.findByJobTitleName(dto.getTitleName()).orElseGet(() -> {
                    // 입력된 직책이 없을 경우 새로 생성
                    JobTitle newJobTitle = new JobTitle();
                    newJobTitle.setJobTitleName(dto.getTitleName());
                    return jobTitleRepository.save(newJobTitle);  // 새로 생성한 직책을 저장 후 반환
                });
                employee.setJobTitle(jobTitle);
            }

        // BankAccount 수정 로직
        if (dto.getBankId() != null && dto.getAccountNumber() != null) {
            // Bank 엔티티를 ID로 조회
            Bank bank = bankRepository.findById(dto.getBankId())
                    .orElseThrow(() -> new EntityNotFoundException("해당 은행을 찾을 수 없습니다."));

            // 현재 BankAccount가 있는지 확인
            BankAccount currentBankAccount = employee.getBankAccount();
            if (currentBankAccount != null) {
                // 기존 BankAccount 업데이트
                currentBankAccount.setAccountNumber(dto.getAccountNumber());
                currentBankAccount.setBank(bank);
                bankAccountRepository.save(currentBankAccount);  // BankAccount 변경사항 저장
            } else {
                // BankAccount가 없는 경우 새로 생성
                BankAccount newBankAccount = new BankAccount();
                newBankAccount.setAccountNumber(dto.getAccountNumber());
                newBankAccount.setBank(bank);
                employee.setBankAccount(newBankAccount); // Employee에 BankAccount 설정
                bankAccountRepository.save(newBankAccount);  // 새로운 BankAccount 저장
            }
        } else {
            throw new IllegalArgumentException("은행 정보와 계좌번호는 필수 입력 사항입니다.");
        }
        // 이미지가 전송된 경우 기존 이미지 삭제 후 새 이미지 저장
        if (imageFile != null && !imageFile.isEmpty()) {
            // 기존 이미지 경로 삭제 로직
            String oldImagePath = employee.getImagePath();
            if (oldImagePath != null) {
                deleteOldImage(oldImagePath);  // 기존 이미지 삭제
            }

            // 새로운 이미지 업로드 및 경로 설정
            String newImagePath = employeeImageService.uploadEmployeeImage(imageFile);
            employee.setImagePath(newImagePath);
        }

            // 3. 엔티티 저장
            Employee updatedEmployee = employeeRepository.save(employee);

            // 4. DTO로 변환하여 반환
            EmployeeShowToDTO updatedEmployeeDTO = new EmployeeShowToDTO(
                    updatedEmployee.getId(),
                    updatedEmployee.getEmployeeNumber(),
                    updatedEmployee.getFirstName(),
                    updatedEmployee.getLastName(),
                    updatedEmployee.getRegistrationNumber(),
                    updatedEmployee.getPhoneNumber(),
                    updatedEmployee.getEmploymentStatus(),
                    updatedEmployee.getEmploymentType(),
                    updatedEmployee.getEmail(),
                    updatedEmployee.getAddress(),
                    updatedEmployee.getHireDate(),
                    updatedEmployee.isHouseholdHead(),
                    updatedEmployee.getImagePath(),
                    updatedEmployee.getDepartment() != null ? updatedEmployee.getDepartment().getDepartmentName() : null,
                    updatedEmployee.getDepartment() != null ? updatedEmployee.getDepartment().getDepartmentCode() : null,
                    updatedEmployee.getPosition() != null ? updatedEmployee.getPosition().getPositionName() : null,
                    updatedEmployee.getJobTitle() != null ? updatedEmployee.getJobTitle().getJobTitleName() : null,
                    updatedEmployee.getBankAccount() != null ? updatedEmployee.getBankAccount().getId() : null,
                    updatedEmployee.getBankAccount() != null ? updatedEmployee.getBankAccount().getBank().getCode() : null,
                    updatedEmployee.getBankAccount() != null ? updatedEmployee.getBankAccount().getBank().getName() : null,  // 은행 이름 추가
                    updatedEmployee.getBankAccount() != null ? updatedEmployee.getBankAccount().getAccountNumber() : null   // 계좌번호 추가
            );
            return Optional.of(updatedEmployeeDTO);
    }

    // 사원 등록. 저장
    @Override
    public EmployeeDTO saveEmployee(EmployeeCreateDTO dto, MultipartFile imageFile) {

        String employeeNumber = createEmployeeNumber(dto);
        // Employee 엔티티를 초기화합니다.
        Employee employee = new Employee();
        employee.setHireDate(dto.getHireDate());
        employee.setEmployeeNumber(employeeNumber);
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setRegistrationNumber(dto.getRegistrationNumber());
        employee.setPhoneNumber(dto.getPhoneNumber());
        employee.setEmploymentStatus(dto.getEmploymentStatus());
        employee.setEmploymentType(dto.getEmploymentType());
        employee.setEmail(dto.getEmail());
        employee.setAddress(dto.getAddress());
        employee.setHouseholdHead(dto.isHouseholdHead());
        employee.setImagePath(dto.getImagePath());

//        // Department 설정
//        if (dto.getDepartmentId() != null) {
//            Department department = departmentRepository.findById(dto.getDepartmentId())
//                    .orElseThrow(() -> new EntityNotFoundException("부서를 찾을 수 없습니다."));
//            employee.setDepartment(department);
//        }

        if(dto.getDepartmentId() != null) {
            Department department = departmentRepository.findDepartmentById(dto.getDepartmentId());
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

        // BankAccount 설정 및 저장
        if (dto.getBankAccountDTO() != null && dto.getBankAccountDTO().getBankId() != null) {
            // Bank 조회
            Bank bank = bankRepository.findById(dto.getBankAccountDTO().getBankId())
                    .orElseThrow(() -> new EntityNotFoundException("해당 은행을 찾을 수 없습니다."));

            // BankAccount 생성 및 설정
            BankAccount newBankAccount = new BankAccount();
            newBankAccount.setBank(bank);
            newBankAccount.setAccountNumber(dto.getBankAccountDTO().getAccountNumber());

            // BankAccount 저장
            bankAccountRepository.save(newBankAccount);
            // Employee와 BankAccount 연결
            employee.setBankAccount(newBankAccount);

            // BankAccount 저장
            bankAccountRepository.save(newBankAccount);
        } else {
            throw new IllegalArgumentException("은행 정보는 필수입니다.");
        }

        // 이미지 파일 업로드 및 경로 가져오기
        if (imageFile != null && !imageFile.isEmpty()) {
            String imagePath = employeeImageService.uploadEmployeeImage(imageFile);
            employee.setImagePath(imagePath);
        }

// 사원 정보 저장
        Employee savedEmployee = employeeRepository.save(employee);

// 저장된 정보를 DTO로 변환하여 반환
        return EmployeeDTO.create(savedEmployee);
    }

    private void deleteOldImage(String oldImagePath) {
        try {
            // 이미지 경로를 완전한 파일 시스템 경로로 변환
            File file = new File(UPLOAD_DIR + oldImagePath);

            if (file.exists()) {
                Files.delete(Paths.get(file.getPath()));  // 파일 삭제
                System.out.println("기존 이미지 파일이 삭제되었습니다: " + file.getPath());
            }
        } catch (IOException e) {
            // 삭제 실패 시 예외 처리
            System.err.println("이미지 파일 삭제 실패: " + e.getMessage());
            throw new RuntimeException("이미지 파일 삭제 실패", e);
        }
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
        // hireDate가 null인 경우 예외 처리
        if (hireDate == null) {
            throw new IllegalArgumentException("입사일은 필수 값입니다.");
        }

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
