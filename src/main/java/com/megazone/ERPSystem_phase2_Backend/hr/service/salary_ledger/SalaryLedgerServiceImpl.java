package com.megazone.ERPSystem_phase2_Backend.hr.service.salary_ledger;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Employee;
import com.megazone.ERPSystem_phase2_Backend.hr.model.salary_ledger.SalaryLedger;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Employee.EmployeeRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.salary_ledger.SalaryLedgerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Transactional
@Service
public class SalaryLedgerServiceImpl implements SalaryLedgerService {

    private final SalaryLedgerRepository salaryLedgerRepository;
    private final EmployeeRepository employeeRepository;


    /**
     * 사원 id 받아서 조회하고 해당월에 급여정보 등록한적없으면 새로생성
     * 결산되지않은 급여정보 있으면 불러오고
     * 결산하면 더이상 수정불가
     */

    @Override
    public Object showSalaryLedger(Long searchId) {

        Employee employee = employeeRepository.findById(searchId).orElseThrow(
                () -> new RuntimeException("해당하는 사원이 없습니다."));

        SalaryLedger salaryLedger = salaryLedgerRepository.findById(searchId).orElse(null);
        if(salaryLedger == null) {
            salaryLedger = new SalaryLedger();
            salaryLedger.setEmployee(employee);

        }


        return null;
    }
}
