package com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto;


import com.megazone.ERPSystem_phase2_Backend.financial.model.common.Bank;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.BankAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankAccountDTO {
    private Bank bankName;
    private String accountNumber;


    public static BankAccountDTO create(BankAccount bankAccount) {
        return new BankAccountDTO(
                bankAccount.getBank().getBankAccount().getBank(),
                bankAccount.getAccountNumber());
    }
}
