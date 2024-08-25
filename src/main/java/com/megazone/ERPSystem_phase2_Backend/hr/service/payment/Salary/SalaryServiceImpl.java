package com.megazone.ERPSystem_phase2_Backend.hr.service.payment.Salary;


import com.megazone.ERPSystem_phase2_Backend.hr.model.payment.Salary;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.payment.Salary.SalaryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class SalaryServiceImpl implements SalaryService {

    private final SalaryRepository salaryRepository;

    @Override
    public Optional<Salary> getSalaryByEmployeeId(Long employeeId) {
        return salaryRepository.findByEmployeeId(employeeId);
    }

    @Override
    public Salary saveOrUpdateSalary(Salary salary) {
        // 기존의 salary를 업데이트하거나, 새로운 salary를 저장함
        return salaryRepository.save(salary);
    }

    @Override
    public void deleteSalary(Long id) {
        salaryRepository.deleteById(id);
    }
}
