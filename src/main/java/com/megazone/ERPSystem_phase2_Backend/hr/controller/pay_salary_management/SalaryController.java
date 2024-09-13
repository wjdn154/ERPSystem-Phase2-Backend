package com.megazone.ERPSystem_phase2_Backend.hr.controller.pay_salary_management;

import com.megazone.ERPSystem_phase2_Backend.hr.model.pay_salary_management.Salary;
import com.megazone.ERPSystem_phase2_Backend.hr.model.pay_salary_management.dto.SalaryListDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.service.payment.SalaryService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hr/salary")
public class SalaryController {
    private final SalaryService salaryService;


    @PostMapping("/all")
    public ResponseEntity<List<SalaryListDTO>> getAllSalaryList() {
        List<SalaryListDTO> dtos = salaryService.findAllEmployeesAndSalary();
        return ResponseEntity.ok().body(dtos);
    }
}
