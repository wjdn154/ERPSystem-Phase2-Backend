package com.megazone.ERPSystem_phase2_Backend.hr.controller.payment;


import com.megazone.ERPSystem_phase2_Backend.hr.model.payment.Salary;
import com.megazone.ERPSystem_phase2_Backend.hr.service.payment.Salary.SalaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/salary")
@RequiredArgsConstructor
public class SalaryController {

    private final SalaryService salaryService;

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<Salary> getSalaryByEmployeeId(@PathVariable Long employeeId) {
        Optional<Salary> salary = salaryService.getSalaryByEmployeeId(employeeId);
        return salary.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<Salary> saveOrUpdateSalary(@RequestBody Salary salary) {
        Salary savedSalary = salaryService.saveOrUpdateSalary(salary);
        return ResponseEntity.ok(savedSalary);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSalary(@PathVariable Long id) {
        salaryService.deleteSalary(id);
        return ResponseEntity.noContent().build();
    }
}