package com.megazone.ERPSystem_phase2_Backend.financial.controller.basic_information_management.credit_card;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company.dto.CompanyDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.credit_card.dto.CreditCardDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.company.CompanyRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.credit_card.CreditCardRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.service.basic_information_management.company.CompanyService;
import com.megazone.ERPSystem_phase2_Backend.financial.service.basic_information_management.credit_card.CreditCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class CreditCardController {

    private final CreditCardService creditCardService;
    private final CreditCardRepository creditCardRepository;

    /**
     * 신용카드 등록
     * @param creditCardDTO 등록할 신용카드 정보가 담긴 DTO
     * @return 등록한 신용카드 정보를 담은 creditCardDTO 객체를 반환.
     */
    @PostMapping("/api/financial/creditCard/save")
    public ResponseEntity<CreditCardDTO> saveCompany(@RequestBody CreditCardDTO creditCardDTO) {
        System.out.println("creditCardDTO.toString() = " + creditCardDTO.toString());
        Optional<CreditCardDTO> savedCreditCard = creditCardService.saveCreditCard(creditCardDTO);
        return savedCreditCard
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    /**
     * 신용카드 수정
     * @param id 수정할 신용카드의 ID
     * @param creditCardDTO 수정할 신용카드 정보가 담긴 DTO
     * @return 수정된 신용카드 정보를 담은 creditCardDTO 객체를 반환.
     */
    @PutMapping("/api/financial/creditCard/update/{id}")
    public ResponseEntity<CreditCardDTO> updateCompany(@PathVariable("id") Long id, @RequestBody CreditCardDTO creditCardDTO) {
        Optional<CreditCardDTO> savedCreditCard = creditCardService.updateCreditCard(id, creditCardDTO);
        return savedCreditCard
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

}