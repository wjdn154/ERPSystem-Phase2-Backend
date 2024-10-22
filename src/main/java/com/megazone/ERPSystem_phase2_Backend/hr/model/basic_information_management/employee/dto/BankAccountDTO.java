package com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto;


import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.BankAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankAccountDTO {
    private Long bankId;
    private String name;
    private String code;
    private String accountNumber;


    public static BankAccountDTO create(BankAccount bankAccount) {
        return new BankAccountDTO(
                bankAccount.getBank().getId(),
                bankAccount.getBank().getName(),
                bankAccount.getBank().getCode(),
                bankAccount.getAccountNumber());
    }
}
