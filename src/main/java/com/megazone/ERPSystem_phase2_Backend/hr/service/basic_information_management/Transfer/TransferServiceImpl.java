package com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.Transfer;

import com.megazone.ERPSystem_phase2_Backend.Integrated.model.dashboard.RecentActivity;
import com.megazone.ERPSystem_phase2_Backend.Integrated.model.dashboard.enums.ActivityType;
import com.megazone.ERPSystem_phase2_Backend.Integrated.model.notification.enums.ModuleType;
import com.megazone.ERPSystem_phase2_Backend.Integrated.model.notification.enums.NotificationType;
import com.megazone.ERPSystem_phase2_Backend.Integrated.model.notification.enums.PermissionType;
import com.megazone.ERPSystem_phase2_Backend.Integrated.repository.dashboard.RecentActivityRepository;
import com.megazone.ERPSystem_phase2_Backend.Integrated.service.notification.NotificationService;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Department;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Employee;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Transfer;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.TransferCreateDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.TransferShowDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.TransferUpdateDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.enums.TransferType;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Department.DepartmentRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Employee.EmployeeRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Transfer.TransferRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {
    private final TransferRepository transferRepository;
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final RecentActivityRepository recentActivityRepository;
    private final NotificationService notificationService;

    @Override
    public Optional<TransferShowDTO> createTransfer(TransferCreateDTO dto) {
        // 사원 정보 조회 (employee_id를 통해)
        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new IllegalArgumentException("잘못된 사원 ID 입력: " + dto.getEmployeeId()));

        // 출발 부서 정보 조회
        Department fromDepartment = departmentRepository.findByDepartmentCode(dto.getFromDepartmentCode())
                .orElseThrow(() -> new IllegalArgumentException("잘못된 출발 부서 Code: " + dto.getFromDepartmentCode()));

        // 도착 부서 정보 조회
        Department toDepartment = departmentRepository.findByDepartmentCode(dto.getToDepartmentCode())
                .orElseThrow(() -> new IllegalArgumentException("잘못된 도착 부서 Code: " + dto.getToDepartmentCode()));


        // 사원의 부서 정보를 도착 부서로 변경
        employee.setDepartment(toDepartment);
        // 사원 정보 업데이트 (변경된 부서 정보 저장)
        employeeRepository.save(employee);

        // Transfer 엔티티 생성 및 설정
        Transfer transfer = new Transfer();
        transfer.setTransferDate(dto.getTransferDate());
        transfer.setEmployee(employee);  // 사원 객체 설정
        transfer.setFromDepartment(fromDepartment);  // 출발 부서 설정
        transfer.setToDepartment(toDepartment);  // 도착 부서 설정
        transfer.setReason(dto.getReason());  // 발령 사유 설정
        transfer.setTransferType(dto.getTransferType());

        // Transfer 저장
        Transfer savedTransfer = transferRepository.save(transfer);

        recentActivityRepository.save(RecentActivity.builder()
                .activityDescription("["+ savedTransfer.getEmployee().getEmployeeNumber() + "]" + savedTransfer.getEmployee().getLastName() + savedTransfer.getEmployee().getFirstName() +" 사원의 발령 등록")
                .activityType(ActivityType.HR)
                .activityTime(LocalDateTime.now())
                .build());

        notificationService.createAndSendNotification(
                ModuleType.HR,
                PermissionType.ADMIN,
                "["+ savedTransfer.getEmployee().getEmployeeNumber() + "]" + savedTransfer.getEmployee().getLastName() + savedTransfer.getEmployee().getFirstName() +" 사원의 발령 등록",
                NotificationType.CREATE_TRANSFER);
        
        TransferShowDTO transferShowDTO = new TransferShowDTO();
        transferShowDTO.setId(savedTransfer.getId());
        transferShowDTO.setEmployeeId(savedTransfer.getEmployee().getId());
        transferShowDTO.setEmployeeNumber(savedTransfer.getEmployee().getEmployeeNumber());
        transferShowDTO.setEmployeeName(savedTransfer.getEmployee().getLastName()+savedTransfer.getEmployee().getFirstName());
        transferShowDTO.setFromDepartmentId(fromDepartment.getId());
        transferShowDTO.setToDepartmentId(toDepartment.getId());
        transferShowDTO.setReason(dto.getReason());
        transferShowDTO.setTransferType(dto.getTransferType());


        return Optional.of(transferShowDTO);
    }

    @Override
    public List<TransferShowDTO> findAllTransfers() {
        return transferRepository.findAll().stream()
                .map(TransferShowDTO::create)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<TransferShowDTO> findTransferById(Long id) {
        return transferRepository.findById(id)
                .map(TransferShowDTO::create); // TransferShowDTO를 반환하도록 변환
    }

    // 발령 기록 수정
    @Override
    public TransferShowDTO updateTransfer(Long id, TransferUpdateDTO dto) {
        Transfer transfer = transferRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("발령 ID 를 찾을 수 없습니다. " + id));

        // 사원 정보 조회
        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new IllegalArgumentException("사원 ID 를 찾을 수 없습니다: " + dto.getEmployeeId()));

        // 출발 부서 정보 조회
        Department fromDepartment = departmentRepository.findByDepartmentName(dto.getFromDepartmentName());

        // 도착 부서 정보 조회
        Department toDepartment = departmentRepository.findByDepartmentName(dto.getToDepartmentName());


        // 기존 발령 정보 업데이트
        transfer.setTransferDate(dto.getTransferDate());
        transfer.setEmployee(employee);
        transfer.setFromDepartment(fromDepartment);
        transfer.setToDepartment(toDepartment);
        transfer.setReason(dto.getReason());
        transfer.setTransferType(dto.getTransferType());

        Transfer updatedTransfer = transferRepository.save(transfer);

        recentActivityRepository.save(RecentActivity.builder()
                .activityDescription("["+ updatedTransfer.getEmployee().getEmployeeNumber() + "]" + updatedTransfer.getEmployee().getLastName() + updatedTransfer.getEmployee().getFirstName() +" 사원의 발령 수정")
                .activityType(ActivityType.HR)
                .activityTime(LocalDateTime.now())
                .build());

        notificationService.createAndSendNotification(
                ModuleType.HR,
                PermissionType.ADMIN,
                "["+ updatedTransfer.getEmployee().getEmployeeNumber() + "]" + updatedTransfer.getEmployee().getLastName() + updatedTransfer.getEmployee().getFirstName() +" 사원의 발령 수정",
                NotificationType.UPDATE_TRANSFER);
        return TransferShowDTO.create(updatedTransfer);
    }
}
