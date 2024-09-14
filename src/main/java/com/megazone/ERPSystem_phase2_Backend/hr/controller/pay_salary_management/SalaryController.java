package com.megazone.ERPSystem_phase2_Backend.hr.controller.pay_salary_management;

import com.megazone.ERPSystem_phase2_Backend.hr.model.pay_salary_management.Salary;
import com.megazone.ERPSystem_phase2_Backend.hr.model.pay_salary_management.dto.SalaryListDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.pay_salary_management.dto.SalarySaveDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.service.payment.SalaryService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ResponseEntity<String> getAllSalaryList() {
        List<SalaryListDTO> dtos = salaryService.findAllEmployeesAndSalary();

        return !dtos.isEmpty() ? ResponseEntity.status(HttpStatus.OK).body("김만수이") :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("태종");
    }

    @PostMapping("/save")
    public ResponseEntity<SalarySaveDTO> saveSalary(@RequestBody SalarySaveDTO salarySaveDTO) {
        SalarySaveDTO dto = salaryService.saveSalary(salarySaveDTO);
        return null;
    }
}
