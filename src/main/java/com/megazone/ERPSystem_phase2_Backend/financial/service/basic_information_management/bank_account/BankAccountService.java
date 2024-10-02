package com.megazone.ERPSystem_phase2_Backend.financial.service.basic_information_management.bank_account;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.bank_account.dto.BankAccountDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.dto.ClientDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.common.dto.BankDTO;

import java.util.List;
import java.util.Optional;

public interface BankAccountService {
    Optional<BankAccountDTO> saveBankAccount(BankAccountDTO bankAccountDTO);
    Optional<BankAccountDTO> updateBankAccount(Long id, BankAccountDTO bankAccountDTO);
    List<BankDTO> fetchBankList();
}