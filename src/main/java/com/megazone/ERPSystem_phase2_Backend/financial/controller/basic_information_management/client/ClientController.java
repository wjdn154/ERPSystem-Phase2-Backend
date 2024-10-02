package com.megazone.ERPSystem_phase2_Backend.financial.controller.basic_information_management.client;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.dto.AccountSubjectDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.dto.AccountSubjectDetailDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.dto.AccountSubjectsAndMemosDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.dto.MemoRequestDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.Client;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.dto.CategoryDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.dto.ClientDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.dto.LiquorDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company.dto.CompanyDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.common.dto.BankDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.account_subject.AccountSubjectRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.client.ClientRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.service.basic_information_management.account_subject.AccountSubjectService;
import com.megazone.ERPSystem_phase2_Backend.financial.service.basic_information_management.client.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/financial/client")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;
    private final ClientRepository clientRepository;


    /**
     * 모든 거래처 정보 조회
     * @return 모든 거래처 정보를 담은 ClientDTO 객체를 반환.
     */
    @PostMapping("/fetchClientList")
    public ResponseEntity<Object> fetchClientList() {
        return clientService.fetchClientList();
    }

    /**
     * id로 거래처 상세 정보 조회
     */
    @PostMapping("/fetchClient/{id}")
    public ResponseEntity<Object> fetchClient(@PathVariable("id") Long id) {
        return clientService.fetchClient(id);
    }

    @PostMapping("/fetchLiquorList")
    public ResponseEntity<Object> fetchLiquorList() {
        List<LiquorDTO> liquorList = clientService.fetchLiquorList();
        return liquorList.isEmpty() ? ResponseEntity.status(HttpStatus.BAD_REQUEST).body("조회된 데이터가 없습니다.") : ResponseEntity.ok(liquorList);
    }

    @PostMapping("/fetchCategoryList")
    public ResponseEntity<Object> fetchCategoryList() {
        List<CategoryDTO> categoryList = clientService.fetchCategoryList();
        return categoryList.isEmpty() ? ResponseEntity.status(HttpStatus.BAD_REQUEST).body("조회된 데이터가 없습니다.") : ResponseEntity.ok(categoryList);
    }

    @PostMapping("/fetchBankList")
    public ResponseEntity<Object> fetchBankList() {
        List<BankDTO> bankList = clientService.fetchBankList();
        return bankList.isEmpty() ? ResponseEntity.status(HttpStatus.BAD_REQUEST).body("조회된 데이터가 없습니다.") : ResponseEntity.ok(bankList);
    }


    /**
     * 거래처 등록
     * @param clientDTO 수정할 거래처 DTO
     * @return 등록한 거래처 정보를 담은 ClientDTO 객체를 반환.
     */
    @PostMapping("/save")
    public ResponseEntity<Object> registerClient(@RequestBody ClientDTO clientDTO) {
        Long savedClient = clientService.saveClient(clientDTO);

        if (savedClient == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        return ResponseEntity.ok("저장 완료");
    }

    @PostMapping("/search")
    public List<ClientDTO> searchClient(@RequestBody Map<String, String> searchText) {
        return clientService.searchClient(searchText.get("searchText"));
    }

    /**
     * 거래처 수정
     * @param clientDTO 수정할 거래처 DTO
     * @return 수정된 거래처 정보를 담은 ClientDTO 객체를 반환.
     */
    @PutMapping("/update")
    public ResponseEntity<Object> updateClient(@RequestBody ClientDTO clientDTO) {
        System.out.println("clientDTO.toString() = " + clientDTO.toString());
        Long updatedClient = clientService.updateClient(clientDTO);

        if (updatedClient == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        return ResponseEntity.ok("수정 완료");
    }
}