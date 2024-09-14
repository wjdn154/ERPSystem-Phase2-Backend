//package com.megazone.ERPSystem_phase2_Backend.hr.service.payment;
//
//import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Employee;
//import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.EmployeeOneDTO;
//import com.megazone.ERPSystem_phase2_Backend.hr.model.pay_salary_management.Allowance;
//import com.megazone.ERPSystem_phase2_Backend.hr.model.pay_salary_management.AllowanceAmount;
//import com.megazone.ERPSystem_phase2_Backend.hr.model.pay_salary_management.Salary;
//import com.megazone.ERPSystem_phase2_Backend.hr.model.pay_salary_management.dto.*;
//import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Department.DepartmentRepository;
//import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Employee.EmployeeRepository;
//import com.megazone.ERPSystem_phase2_Backend.hr.repository.payment.AllowanceRepository;
//import com.megazone.ERPSystem_phase2_Backend.hr.repository.payment.DeductionRepository;
//import com.megazone.ERPSystem_phase2_Backend.hr.repository.payment.Salary.SalaryRepository;
//import com.megazone.ERPSystem_phase2_Backend.hr.repository.payment.SocialInsuranceRate.AllowanceAmountRepository;
//import com.megazone.ERPSystem_phase2_Backend.hr.repository.payment.SocialInsuranceRate.DeductionAmountRepository;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//@Transactional
//public class SalaryServiceImpl implements SalaryService{
//
//    private final SalaryRepository salaryRepository;
//    private final AllowanceAmountRepository allowanceAmountRepository;
//    private final DeductionAmountRepository deductionAmountRepository;
//
//    @Override
//    public List<SalaryListDTO> findAllEmployeesAndSalary() {
//        List<Salary> salaries = salaryRepository.findAll();
//
//
//        List<SalaryListDTO> dtos = new ArrayList<>();
//        for (Salary salary : salaries) {
//            dtos.add(SalaryListDTO.create(salary));
//        }
//
//        return dtos;
//    }
//
//    @Override
//    public Optional<SalaryListDTO> findEmployeeSalaryBySalaryId(Long id) {
//        return null;
//    }
//
//    @Override
//    public SalarySaveDTO saveSalary(SalarySaveDTO salarySaveDTO) {
//
//        for (AllowanceAmountDTO allowance : salarySaveDTO.getAllowanceAmount()) {
//            AllowanceAmount allowanceAmount = allowanceAmountRepository.findById(allowance.getAllowanceId()).orElseThrow(
//                    () -> new RuntimeException("해당하는 수당이 없습니다."));
//            allowanceAmount.setAmount(allowance.getAmount());
//        }
//
//
//    }
//}
