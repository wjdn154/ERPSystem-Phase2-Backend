package com.megazone.ERPSystem_phase2_Backend.hr.controller.hr_management.Employee;

import com.megazone.ERPSystem_phase2_Backend.hr.model.hr_management.dto.EmployeeDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.hr_management.Employee.EmployeeRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.service.hr_management.Employee.EmployeeService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeRepository employeeRepository;


    /**
     * 모든 직원 정보를 가져옴.
     * @return 모든 직원 정보를 담은 리스트를 반환함.
     */
    @PostMapping("/api/hr/employee")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        List<EmployeeDTO> employees = employeeService.findAllEmployees();
        return ResponseEntity.ok(employees);
    }

//    /**
//     * 특정 ID에 해당하는 직원의 상세 정보를 조회함.
//     * @param id 조회할 직원의 ID
//     * @return 해당 ID의 직원 상세 정보를 담은 EmployeeDTO 객체를 반환함.
//     */
//    @GetMapping("/api/hr/employees/{id}")
//    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable("id") Long id) {
//        Optional<EmployeeDTO> employee = employeeService.findEmployeeById(id);
//        return employee.map(ResponseEntity::ok)
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }
//
//    /**
//     * 새로운 직원을 추가함.
//     * @param employeeDTO 추가할 직원 정보를 담은 DTO
//     * @return 성공 시 추가된 직원 정보를 반환함.
//     */
//    @PostMapping("/api/hr/employees")
//    public ResponseEntity<EmployeeDTO> addEmployee(@RequestBody EmployeeDTO employeeDTO) {
//        Optional<EmployeeDTO> savedEmployee = employeeService.saveEmployee(employeeDTO);
//        return savedEmployee
//                .map(ResponseEntity::ok)
//                .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
//    }
//
//    /**
//     * 직원 정보를 업데이트함.
//     * @param id 업데이트할 직원의 ID
//     * @param employeeDTO 업데이트할 직원 정보를 담은 DTO
//     * @return 업데이트된 직원 정보를 반환함.
//     */
//    @PutMapping("/api/hr/employees/{id}")
//    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable("id") Long id, @RequestBody EmployeeDTO employeeDTO) {
//        Optional<EmployeeDTO> updatedEmployee = employeeService.updateEmployee(id, employeeDTO);
//        return updatedEmployee
//                .map(ResponseEntity::ok)
//                .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
//    }
//
//    /**
//     * 주어진 ID를 가진 직원을 삭제함.
//     * @param id 삭제할 직원의 ID
//     * @return 성공적으로 삭제된 경우, HTTP 204 No Content 반환.
//     */
//    @DeleteMapping("/api/hr/employees/{id}")
//    public ResponseEntity<Void> deleteEmployee(@PathVariable("id") Long id) {
//        try {
//            employeeService.deleteEmployee(id);
//            return ResponseEntity.noContent().build();
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
}
