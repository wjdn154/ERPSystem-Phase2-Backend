package com.megazone.ERPSystem_phase2_Backend.financial.service.basic_information_management.bank_account;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.bank_account.AccountType;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.*;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.bank_account.dto.BankAccountDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.bank_account.BankAccount;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.dto.ClientDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.enums.TransactionType;
import com.megazone.ERPSystem_phase2_Backend.financial.model.common.Address;
import com.megazone.ERPSystem_phase2_Backend.financial.model.common.Bank;
import com.megazone.ERPSystem_phase2_Backend.financial.model.common.Contact;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.bank_account.AccountTypeRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.bank_account.BankAccountRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.client.*;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.common.AddressRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.common.BankRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.common.ContactRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final AccountTypeRepository accountTypeRepository;
    private final AddressRepository addressRepository;
    private final BankRepository bankRepository;
    private final ContactRepository contactRepository;

    /**
     * 새로운 은행 계좌를 저장하고 DTO로 반환
     * @param bankAccountDTO 저장할 은행 계좌 정보가 담긴 DTO
     * @return 저장된 은행 계좌 정보를 DTO로 반환
     */
    @Override
    public Optional<BankAccountDTO> saveBankAccount(BankAccountDTO bankAccountDTO) {
        BankAccount bankAccount = new BankAccount();

        // 예금 유형 설정
        AccountType accountType = accountTypeRepository.findById(bankAccountDTO.getAccountType().getId())
                .orElseThrow(() -> new RuntimeException("해당 예금 유형이 존재하지 않습니다."));
        bankAccount.setAccountType(accountType);

        // 은행 정보 설정
        Bank bank = bankRepository.findById(bankAccountDTO.getBank().getId())
                .orElseThrow(() -> new RuntimeException("해당 은행이 존재하지 않습니다."));
        bankAccount.setBank(bank);

        // 주소 정보 설정
        if (bankAccountDTO.getAddress() != null) {
            Address address = new Address();
            address.setPostalCode(bankAccountDTO.getAddress().getPostalCode());
            address.setRoadAddress(bankAccountDTO.getAddress().getRoadAddress());
            address.setDetailedAddress(bankAccountDTO.getAddress().getDetailedAddress());
            addressRepository.save(address);
            bankAccount.setAddress(address);
        }

        // 연락처 정보 설정
        if (bankAccountDTO.getContact() != null) {
            Contact contact = new Contact();
            contact.setPhone(bankAccountDTO.getContact().getPhone());
            contact.setFax(bankAccountDTO.getContact().getFax());
            contactRepository.save(contact);
            bankAccount.setContact(contact);
        }



        // 기타 정보 설정
        bankAccount.setClientName(bankAccountDTO.getClientName());
        bankAccount.setAccountNumber(bankAccountDTO.getAccountNumber());
        bankAccount.setBankBranchName(bankAccountDTO.getBankBranchName());
        bankAccount.setAccountOpeningDate(bankAccountDTO.getAccountOpeningDate());
        bankAccount.setDepositType(bankAccountDTO.getDepositType());
        bankAccount.setMaturityDate(bankAccountDTO.getMaturityDate());
        bankAccount.setInterestRate(bankAccountDTO.getInterestRate());
        bankAccount.setMonthlyPayment(bankAccountDTO.getMonthlyPayment());
        bankAccount.setOverdraftLimit(bankAccountDTO.getOverdraftLimit());
        bankAccount.setBusinessAccount(bankAccountDTO.getBusinessAccount());

        // 은행 계좌 저장
        BankAccount savedBankAccount = bankAccountRepository.save(bankAccount);

        // 저장된 정보를 DTO로 변환하여 반환
        return Optional.of(new BankAccountDTO(savedBankAccount));
    }

    /**
     * 기존 은행 계좌 정보를 수정하고 DTO로 반환
     * @param id 은행 계좌 ID
     * @param bankAccountDTO 수정할 은행 계좌 정보가 담긴 DTO
     * @return 수정된 은행 계좌 정보를 DTO로 반환
     */
    @Override
    public Optional<BankAccountDTO> updateBankAccount(Long id, BankAccountDTO bankAccountDTO) {
        return bankAccountRepository.findById(id).map(bankAccount -> {

            // 예금 유형 업데이트
            AccountType accountType = accountTypeRepository.findById(bankAccountDTO.getAccountType().getId())
                    .orElseThrow(() -> new RuntimeException("해당 예금 유형이 존재하지 않습니다."));
            bankAccount.setAccountType(accountType);

            // 은행 정보 업데이트
            Bank bank = bankRepository.findById(bankAccountDTO.getBank().getId())
                    .orElseThrow(() -> new RuntimeException("해당 은행이 존재하지 않습니다."));
            bankAccount.setBank(bank);

            // 주소 정보 업데이트
            if (bankAccountDTO.getAddress() != null) {
                Address address = addressRepository.findById(bankAccount.getAddress().getId())
                        .orElseThrow(() -> new RuntimeException("해당 주소 정보가 존재하지 않습니다."));
                address.setPostalCode(bankAccountDTO.getAddress().getPostalCode());
                address.setRoadAddress(bankAccountDTO.getAddress().getRoadAddress());
                address.setDetailedAddress(bankAccountDTO.getAddress().getDetailedAddress());
                addressRepository.save(address);
                bankAccount.setAddress(address);
            }

            // 연락처 정보 업데이트
            if (bankAccountDTO.getContact() != null) {
                Contact contact = contactRepository.findById(bankAccount.getContact().getId())
                        .orElseThrow(() -> new RuntimeException("해당 연락처 정보가 존재하지 않습니다."));
                contact.setPhone(bankAccountDTO.getContact().getPhone());
                contact.setFax(bankAccountDTO.getContact().getFax());
                contactRepository.save(contact);
                bankAccount.setContact(contact);
            }

            // 기타 정보 업데이트
            bankAccount.setClientName(bankAccountDTO.getClientName());
            bankAccount.setAccountNumber(bankAccountDTO.getAccountNumber());
            bankAccount.setBankBranchName(bankAccountDTO.getBankBranchName());
            bankAccount.setAccountOpeningDate(bankAccountDTO.getAccountOpeningDate());
            bankAccount.setDepositType(bankAccountDTO.getDepositType());
            bankAccount.setMaturityDate(bankAccountDTO.getMaturityDate());
            bankAccount.setInterestRate(bankAccountDTO.getInterestRate());
            bankAccount.setMonthlyPayment(bankAccountDTO.getMonthlyPayment());
            bankAccount.setOverdraftLimit(bankAccountDTO.getOverdraftLimit());
            bankAccount.setBusinessAccount(bankAccountDTO.getBusinessAccount());

            // 수정된 은행 계좌 저장
            BankAccount updatedBankAccount = bankAccountRepository.save(bankAccount);

            // 수정된 정보를 DTO로 변환하여 반환
            return new BankAccountDTO(updatedBankAccount);
        });
    }
}