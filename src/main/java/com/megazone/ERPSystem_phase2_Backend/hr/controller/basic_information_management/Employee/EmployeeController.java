package com.megazone.ERPSystem_phase2_Backend.hr.controller.basic_information_management.Employee;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Users;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.*;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Employee.EmployeeRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Users.UsersRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.Employee.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hr")
public class EmployeeController {

    @Autowired
    private final EmployeeService employeeService;
    private final EmployeeRepository employeeRepository;
    private final UsersRepository usersRepository;



    @PostMapping("/employee/permission/admin/{companyId}")
    public ResponseEntity<Object> getAdminPermissionEmployee(@PathVariable("companyId") Long companyId) {
        System.out.println("companyId = " + companyId);
        return employeeService.getAdminPermissionEmployee(companyId);
    }


    // 사원 리스트 조회
    @PostMapping("/employee/all")
    public ResponseEntity<List<EmployeeShowDTO>> getAllEmployees() {
        Users user = usersRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        Long company_id = user.getCompany().getId();
        List<EmployeeShowDTO> employees = employeeService.findAllEmployees();
        return ResponseEntity.ok(employees);
    }

    // 사원 상세 조회
    @GetMapping("/employee/{id}")
    public ResponseEntity<EmployeeOneDTO> getEmployeeById(@PathVariable("id") Long id) {
        Users user = usersRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        Long company_id = user.getCompany().getId();
        // 서비스에서 해당 아이디의 사원 상세 정보를 가져옴
        Optional<EmployeeOneDTO> employee = employeeService.findEmployeeById(id);

        // 해당 아이디의 사원정보가 존재하지 않을 경우 404 상태 반환.
        return employee.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 사원 등록
    @PostMapping("/employee/createEmployee")
    public ResponseEntity<EmployeeDTO> saveEmployeeByEmployeeNumber(@RequestBody EmployeeCreateDTO dto) {
        Users user = usersRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        Long company_id = user.getCompany().getId();
        try {
            // EmployeeCreateDTO를 사용하여 사원을 저장합니다.
            Optional<EmployeeDTO> savedEmployee = employeeService.saveEmployee(dto);

            // 저장된 사원이 존재하는 경우 OK 응답을 반환합니다.
            return savedEmployee
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
        } catch (DuplicateKeyException e) {
            // 중복된 사원 번호의 경우
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } catch (Exception e) {
            // 기타 예외의 경우
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    // 사원 정보 수정
    @PutMapping("/employee/updateEmployee/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployeeById(@PathVariable("id") Long id, @RequestBody EmployeeDataDTO dto) {
        Users user = usersRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        Long company_id = user.getCompany().getId();
        Optional<EmployeeDTO> updatedEmployee = employeeService.updateEmployee(id, dto);
        return updatedEmployee.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

     //사원 삭제
    @DeleteMapping("/employee/del/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable("id") Long id) {
        Users user = usersRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        Long company_id = user.getCompany().getId();
        try {
            employeeService.deleteEmployee(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
//    @DeleteMapping("employee/del/{id}")
//    public ResponseEntity<Void> deleteEmployee(@PathVariable("id") Long id) {
//        employeeService.deleteEmployeeById(id);
//        return ResponseEntity.noContent().build(); // 성공적으로 삭제 시 204 No Content 응답
//    }


}
