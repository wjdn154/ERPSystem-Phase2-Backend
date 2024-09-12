package com.megazone.ERPSystem_phase2_Backend.hr.service.payment;

import com.megazone.ERPSystem_phase2_Backend.hr.model.pay_salary_management.dto.SalaryListDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SalaryServiceImpl implements SalaryService{

    // 수정해야함
    @Override
    public List<SalaryListDTO> findAllEmployeesAndSalary() {
        return List.of();
    }
}
