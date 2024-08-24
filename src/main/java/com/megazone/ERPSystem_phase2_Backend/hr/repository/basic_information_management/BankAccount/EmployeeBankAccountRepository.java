package com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.BankAccount;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeBankAccountRepository extends JpaRepository<BankAccount, Long> {
    Optional<BankAccount> findById(Long id);
}
