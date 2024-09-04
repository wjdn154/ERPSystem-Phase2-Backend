//package com.megazone.ERPSystem_phase2_Backend.hr.service.payment.Salary;
//
//
//import com.megazone.ERPSystem_phase2_Backend.hr.model.payment.Allowance;
//import com.megazone.ERPSystem_phase2_Backend.hr.model.payment.Deduction;
//import com.megazone.ERPSystem_phase2_Backend.hr.model.payment.Salary;
//import com.megazone.ERPSystem_phase2_Backend.hr.model.payment.dto.SalaryUpdateDTO;
//import com.megazone.ERPSystem_phase2_Backend.hr.repository.payment.Salary.SalaryRepository;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//@Transactional
//public class SalaryServiceImpl implements SalaryService {
//
//    private final SalaryRepository salaryRepository;
//
//    @Override
//    public Optional<Salary> getSalaryByEmployeeId(Long employeeId) {
//        return salaryRepository.findByEmployeeId(employeeId);
//    }
//
//    @Override
//    public Salary saveOrUpdateSalary(Long id, SalaryUpdateDTO dto) {
//        // 기존의 salary를 업데이트하거나, 새로운 salary를 저장함
//        Salary salary = salaryRepository.findById(id).orElse(null);
//
//        List<Deduction> deductions = salary.getDeductions();
//        List<Allowance> allowances = salary.getAllowances();
//
//        for (Deduction deduction : deductions) {
//            if(deduction.getDeductionType() == dto.getDeductionType()) {
//                deduction.setAmount(dto.getDeductionAmount());
//            }
//        }
//
//        for (Allowance allowance : allowances) {
//            if(allowance.getAllowanceType() == dto.getAllowanceType()) {
//                allowance.setAmount(dto.getAllowanceAmount());
//            }
//        }
//
//        return salaryRepository.save(salary);
//    }
//
//    @Override
//    public void deleteSalary(Long id) {
//        salaryRepository.deleteById(id);
//    }
//}
