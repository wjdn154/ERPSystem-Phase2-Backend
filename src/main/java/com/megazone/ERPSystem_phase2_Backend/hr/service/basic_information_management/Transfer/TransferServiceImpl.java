package com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.Transfer;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Department;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Employee;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Transfer;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.TransferCreateDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.TransferShowDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Department.DepartmentRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Employee.EmployeeRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Transfer.TransferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {
    private final TransferRepository transferRepository;
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    @Override
    public TransferShowDTO createTransfer(TransferCreateDTO dto) {
        // 사원 정보 조회 (employee_id를 통해)
        Employee employee = employeeRepository.findById(dto.getEmployee_id())
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee ID: " + dto.getEmployee_id()));

        // 출발 부서 정보 조회
        Department fromDepartment = departmentRepository.findByDepartmentName(dto.getFromDepartmentName());

        // 도착 부서 정보 조회
        Department toDepartment = departmentRepository.findByDepartmentName(dto.getToDepartmentName());

        // Transfer 엔티티 생성 및 설정
        Transfer transfer = new Transfer();
        transfer.setTransferDate(dto.getTransfer_date());
        transfer.setEmployee(employee);  // 사원 객체 설정
        transfer.setFromDepartment(fromDepartment);  // 출발 부서 설정
        transfer.setToDepartment(toDepartment);  // 도착 부서 설정
        transfer.setTransferType(dto.getTransferType());  // 발령 유형 설정
        transfer.setReason(dto.getReason());  // 발령 사유 설정

        // Transfer 저장
        Transfer savedTransfer = transferRepository.save(transfer);
        return TransferShowDTO.create(savedTransfer);
    }
}
