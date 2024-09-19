//package com.megazone.ERPSystem_phase2_Backend.hr.service.payment;
//
//import com.megazone.ERPSystem_phase2_Backend.hr.model.pay_salary_management.dto.SalaryListDTO;
//import com.megazone.ERPSystem_phase2_Backend.hr.model.pay_salary_management.dto.SalarySaveDTO;
//
//import java.util.List;
//import java.util.Optional;
//
//public interface SalaryService {
//    // 모든 사원의 월급 리스트 조회
//    List<SalaryListDTO> findAllEmployeesAndSalary();
//
//    // 사원의 월급 상세 조회
//    Optional<SalaryListDTO> findEmployeeSalaryBySalaryId(Long id);
//
//    SalarySaveDTO saveSalary(SalarySaveDTO salarySaveDTO);
//}