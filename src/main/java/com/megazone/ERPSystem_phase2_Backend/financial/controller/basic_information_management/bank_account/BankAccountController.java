package com.megazone.ERPSystem_phase2_Backend.financial.controller.basic_information_management.bank_account;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.bank_account.dto.BankAccountDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.dto.ClientDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.common.dto.BankDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.bank_account.BankAccountRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.client.ClientRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.service.basic_information_management.bank_account.BankAccountService;
import com.megazone.ERPSystem_phase2_Backend.financial.service.basic_information_management.client.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/financial/bankAccount")
@RequiredArgsConstructor
public class BankAccountController {

    private final BankAccountService bankAccountService;
    private final BankAccountRepository bankAccountRepository;


    /**
     * 은행 계좌 등록
     * @param bankAccountDTO 수정할 은행 계좌 정보 DTO
     * @return 등록한 은행 계좌 정보를 담은 BankAccountDTO 객체를 반환.
     */
    @PostMapping("/save")
    public ResponseEntity<BankAccountDTO> registerClient(@RequestBody BankAccountDTO bankAccountDTO) {
        Optional<BankAccountDTO> savedBankAccount = bankAccountService.saveBankAccount(bankAccountDTO);

        return savedBankAccount
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    /**
     * 은행 계좌 수정
     * @param id 수정할 은행 계좌의 ID
     * @param bankAccountDTO 수정할 은행 계좌 DTO
     * @return 수정된 은행 계좌 정보를 담은 BankAccountDTO 객체를 반환.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<BankAccountDTO> updateClient(@PathVariable("id") Long id, @RequestBody BankAccountDTO bankAccountDTO) {
        Optional<BankAccountDTO> updatedBankAccount = bankAccountService.updateBankAccount(id, bankAccountDTO);
        return updatedBankAccount
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // 은행 리스트 조회
    @PostMapping("/fetchBankList")
    public ResponseEntity<Object> fetchBankList() {
        List<BankDTO> bankDTOS = bankAccountService.fetchBankList();

        if(!bankDTOS.isEmpty()) return ResponseEntity.status(HttpStatus.OK).body(bankDTOS);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}