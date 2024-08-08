package com.megazone.ERPSystem_phase2_Backend.hr.service.hr_management.Employee;

import com.megazone.ERPSystem_phase2_Backend.hr.model.hr_management.Employee;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.hr_management.Employee.EmployeeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeServiceImpl {

    private EmployeeRepository employeeRepository;

    //@Override
    //public Employee saveEmployee(Employee employee) {return null;}

    //@Override
    //public void deleteEmployee(Long employeeId){

    }