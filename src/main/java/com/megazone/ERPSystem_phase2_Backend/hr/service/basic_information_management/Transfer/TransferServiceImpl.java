package com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.Transfer;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Department;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Employee;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Transfer;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.TransferType;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.TransferCreateDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.TransferShowDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Department.DepartmentRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Employee.EmployeeRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Transfer.TransferRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.TransferType.TransferTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final TransferTypeRepository transferTypeRepository;

    @Override
    public TransferShowDTO createTransfer(TransferCreateDTO dto) {
        // 사원 정보 조회 (employee_id를 통해)
        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new IllegalArgumentException("잘못된 사원 ID 입력: " + dto.getEmployeeId()));

        // 출발 부서 정보 조회
        Department fromDepartment = departmentRepository.findById(dto.getFromDepartmentId())
                .orElseThrow(() -> new IllegalArgumentException("잘못된 출발 부서 ID: " + dto.getFromDepartmentId()));

        // 도착 부서 정보 조회
        Department toDepartment = departmentRepository.findById(dto.getToDepartmentId())
                .orElseThrow(() -> new IllegalArgumentException("잘못된 도착 부서 ID: " + dto.getToDepartmentId()));

        // 발령 유형 정보 조회
        TransferType transferType = transferTypeRepository.findById(dto.getTransferTypeId())
                .orElseThrow(() -> new IllegalArgumentException("잘못된 발령 유형 ID: " + dto.getTransferTypeId()));

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
        transfer.setTransferType(transferType);
        transfer.setReason(dto.getReason());  // 발령 사유 설정

        // Transfer 저장
        Transfer savedTransfer = transferRepository.save(transfer);
        return TransferShowDTO.create(savedTransfer);
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
    public TransferShowDTO updateTransfer(Long id, TransferCreateDTO dto) {
        Transfer transfer = transferRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("발령 ID 를 찾을 수 없습니다. " + id));

        // 사원 정보 조회
        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new IllegalArgumentException("사원 ID 를 찾을 수 없습니다: " + dto.getEmployeeId()));

        // 출발 부서 정보 조회
        Department fromDepartment = departmentRepository.findByDepartmentName(dto.getFromDepartmentName());

        // 도착 부서 정보 조회
        Department toDepartment = departmentRepository.findByDepartmentName(dto.getToDepartmentName());

        // 발령 유형 정보 조회
        TransferType transferType = transferTypeRepository.findById(dto.getTransferTypeId())
                .orElseThrow(() -> new IllegalArgumentException("발령 유형 정보 ID가 없습니다. : " + dto.getTransferTypeId()));

        transferType.setCode(dto.getTransferTypeCode());  // dto에서 TransferTypeCode 받아온다고 가정
        transferType.setDescription(dto.getTransferTypeDescription());  // dto에서 TransferTypeDescription 받아온다고 가정
        transferTypeRepository.save(transferType); // TransferType 업데이트 저장

        // 기존 발령 정보 업데이트
        transfer.setTransferDate(dto.getTransferDate());
        transfer.setEmployee(employee);
        transfer.setFromDepartment(fromDepartment);
        transfer.setToDepartment(toDepartment);
        transfer.setTransferType(transferType); // 업데이트된 TransferType 설정
        transfer.setReason(dto.getReason());

        Transfer updatedTransfer = transferRepository.save(transfer);
        return TransferShowDTO.create(updatedTransfer);
    }
}
