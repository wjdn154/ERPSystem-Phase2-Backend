package com.megazone.ERPSystem_phase2_Backend.hr.controller.payment;


import com.megazone.ERPSystem_phase2_Backend.hr.model.payment.Salary;
import com.megazone.ERPSystem_phase2_Backend.hr.model.payment.dto.SalaryDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.payment.dto.SalaryUpdateDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.service.payment.Salary.SalaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hr/salary")
@RequiredArgsConstructor
public class SalaryController {

    private final SalaryService salaryService;

    @GetMapping("/show/{id}")
    public ResponseEntity<Object> getSalaryByEmployeeId(@PathVariable("id") Long employeeId) {
        Salary salary = salaryService.getSalaryByEmployeeId(employeeId).orElse(null);

        SalaryDTO salaryDTO = SalaryDTO.convertToDTO(salary);

        return (salaryDTO != null) ?
                ResponseEntity.status(HttpStatus.OK).body(salaryDTO) :
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("해당 사원이 없습니다.");
    }

    @PostMapping("/save/{id}")
    public ResponseEntity<SalaryDTO> saveOrUpdateSalary(@PathVariable("id") Long id, @RequestBody SalaryUpdateDTO salaryUpdateDTO) {

        SalaryDTO salaryDTO = SalaryDTO.convertToDTO(salaryService.saveOrUpdateSalary(id,salaryUpdateDTO));

        return salaryDTO != null ?
                ResponseEntity.status(HttpStatus.OK).body(salaryDTO) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @DeleteMapping("/del/{id}")
    public ResponseEntity<Void> deleteSalary(@PathVariable("id") Long id) {
        salaryService.deleteSalary(id);
        return ResponseEntity.noContent().build();
    }
}