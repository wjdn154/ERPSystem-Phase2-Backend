package com.megazone.ERPSystem_phase2_Backend.financial.service.basic_information_management.credit_card;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.bank_account.BankAccount;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.credit_card.Company;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.credit_card.CreditCard;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.credit_card.Ownership;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.credit_card.dto.CreditCardDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.credit_card.dto.OwnershipDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.credit_card.enums.CardType;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.bank_account.BankAccountRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.credit_card.CreditCardCompanyRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.credit_card.CreditCardRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.credit_card.OwnershipRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Employee;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Employee.EmployeeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CreditCardServiceImpl implements CreditCardService {

    private final CreditCardRepository creditCardRepository;
    private final BankAccountRepository bankAccountRepository;
    private final CreditCardCompanyRepository creditCardCompanyRepository;
    private final OwnershipRepository ownershipRepository;
    private final EmployeeRepository employeeRepository;  // EmployeeRepository 추가


    /**
     * 신용카드 저장.
     *
     * @param creditCardDTO 저장할 카드 정보가 담긴 DTO
     * @return 저장된 카드 정보를 담은 DTO
     */
    @Override
    public Optional<CreditCardDTO> saveCreditCard(CreditCardDTO creditCardDTO) {

        // CreditCard 엔티티 생성
        CreditCard creditCard = new CreditCard();

        // 결제 계좌 조회
        BankAccount paymentAccount = bankAccountRepository.findById(creditCardDTO.getPaymentAccountId())
                .orElseThrow(() -> new IllegalArgumentException("결제 계좌를 찾을 수 없습니다."));
        creditCard.setPaymentAccount(paymentAccount);

        // 카드사 객체 생성
        Company company = new Company();
        company.setNumber(creditCardDTO.getCompany().getNumber());
        company.setName(creditCardDTO.getCompany().getName());
        company.setWebsite(creditCardDTO.getCompany().getWebsite());
        creditCardCompanyRepository.save(company);
        creditCard.setCompany(company);

        // 나머지 정보 저장
        creditCard.setCardNumber(creditCardDTO.getCardNumber());
        creditCard.setTransactionType(creditCardDTO.getTransactionType());
        creditCard.setCardType(CardType.valueOf(creditCardDTO.getCardType()));
        creditCard.setRemarks(creditCardDTO.getRemarks());
        creditCard.setIsActive(creditCardDTO.getIsActive());
        creditCard.setFee(creditCardDTO.getFee());
        creditCard.setPaymentDate(creditCardDTO.getPaymentDate());
        creditCard.setLimitAmount(creditCardDTO.getLimitAmount());

        // 신용카드 먼저 저장
        CreditCard savedCreditCard = creditCardRepository.save(creditCard);

        // Ownership 정보 저장
        if (creditCardDTO.getOwnership() != null) {
            OwnershipDTO ownershipDTO = creditCardDTO.getOwnership();
            Ownership ownership = new Ownership();

            ownership.setCreditCard(savedCreditCard);

            // Owner 설정
            if (ownershipDTO.getOwnerId() != null) {
                Employee owner = employeeRepository.findById(ownershipDTO.getOwnerId())
                        .orElseThrow(() -> new IllegalArgumentException("소유자를 찾을 수 없습니다."));
                ownership.setOwner(owner);
            }

            // Manager 설정
            if (ownershipDTO.getManagerId() != null) {
                Employee manager = employeeRepository.findById(ownershipDTO.getManagerId())
                        .orElseThrow(() -> new IllegalArgumentException("담당자를 찾을 수 없습니다."));
                ownership.setManager(manager);
            }

            ownershipRepository.save(ownership);
            savedCreditCard.setOwnership(ownership);  // CreditCard와 Ownership 연결
        }

        savedCreditCard = creditCardRepository.save(savedCreditCard);

        return Optional.of(new CreditCardDTO(savedCreditCard));
    }

    /**
     * 신용카드 업데이트.
     *
     * @param id 신용카드의 ID
     * @param creditCardDTO 업데이트할 카드 정보가 담긴 DTO
     * @return 업데이트된 카드 정보를 담은 DTO
     */
    @Override
    public Optional<CreditCardDTO> updateCreditCard(Long id, CreditCardDTO creditCardDTO) {
        // 신용카드 조회
        CreditCard existingCreditCard = creditCardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 신용카드를 찾을 수 없습니다."));

        // 결제 계좌 업데이트
        if (creditCardDTO.getPaymentAccountId() != null) {
            BankAccount paymentAccount = bankAccountRepository.findById(creditCardDTO.getPaymentAccountId())
                    .orElseThrow(() -> new IllegalArgumentException("결제 계좌를 찾을 수 없습니다."));
            existingCreditCard.setPaymentAccount(paymentAccount);
        }

        // 카드사 업데이트
        if (creditCardDTO.getCompany() != null) {
            Company company = existingCreditCard.getCompany();
            company.setNumber(creditCardDTO.getCompany().getNumber());
            company.setName(creditCardDTO.getCompany().getName());
            company.setWebsite(creditCardDTO.getCompany().getWebsite());
            creditCardCompanyRepository.save(company);
            existingCreditCard.setCompany(company);
        }

        // 소유자 및 담당자 정보 업데이트
        if (creditCardDTO.getOwnership() != null) {
            OwnershipDTO ownershipDTO = creditCardDTO.getOwnership();

            Ownership ownership = existingCreditCard.getOwnership();
            if (ownership == null) {
                ownership = new Ownership();
                ownership.setCreditCard(existingCreditCard);
            }

            // Owner 설정
            if (ownershipDTO.getOwnerId() != null) {
                Employee owner = employeeRepository.findById(ownershipDTO.getOwnerId())
                        .orElseThrow(() -> new IllegalArgumentException("소유자를 찾을 수 없습니다."));
                ownership.setOwner(owner);
            }

            // Manager 설정
            if (ownershipDTO.getManagerId() != null) {
                Employee manager = employeeRepository.findById(ownershipDTO.getManagerId())
                        .orElseThrow(() -> new IllegalArgumentException("담당자를 찾을 수 없습니다."));
                ownership.setManager(manager);
            }

            ownershipRepository.save(ownership);
            existingCreditCard.setOwnership(ownership);
        }

        // 나머지 정보 업데이트
        existingCreditCard.setCardNumber(creditCardDTO.getCardNumber());
        existingCreditCard.setTransactionType(creditCardDTO.getTransactionType());
        existingCreditCard.setCardType(CardType.valueOf(creditCardDTO.getCardType()));
        existingCreditCard.setRemarks(creditCardDTO.getRemarks());
        existingCreditCard.setIsActive(creditCardDTO.getIsActive());
        existingCreditCard.setFee(creditCardDTO.getFee());
        existingCreditCard.setPaymentDate(creditCardDTO.getPaymentDate());
        existingCreditCard.setLimitAmount(creditCardDTO.getLimitAmount());

        // 업데이트된 신용카드 저장
        CreditCard updatedCreditCard = creditCardRepository.save(existingCreditCard);

        return Optional.of(new CreditCardDTO(updatedCreditCard));
    }
}