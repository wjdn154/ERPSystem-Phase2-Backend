package com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.client;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientBankAccountRepository extends JpaRepository<BankAccount, Long> {
}