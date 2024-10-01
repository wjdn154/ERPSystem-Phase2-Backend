package com.megazone.ERPSystem_phase2_Backend.financial.controller.basic_information_management.client;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.dto.AccountSubjectDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.dto.AccountSubjectDetailDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.dto.AccountSubjectsAndMemosDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.dto.MemoRequestDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.Client;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.dto.ClientDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company.dto.CompanyDTO;
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

    /**
     * 거래처 등록
     * @param clientDTO 수정할 거래처 DTO
     * @return 등록한 거래처 정보를 담은 ClientDTO 객체를 반환.
     */
    @PostMapping("/save")
    public ResponseEntity<ClientDTO> registerClient(@RequestBody ClientDTO clientDTO) {
        Optional<ClientDTO> savedClient = clientService.saveClient(clientDTO);

        return savedClient
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    @PostMapping("/search")
    public List<ClientDTO> searchClient(@RequestBody Map<String, String> searchText) {
        return clientService.searchClient(searchText.get("searchText"));
    }

    /**
     * 거래처 수정
     * @param id 수정할 거래처의 ID
     * @param clientDTO 수정할 거래처 DTO
     * @return 수정된 거래처 정보를 담은 ClientDTO 객체를 반환.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<ClientDTO> updateClient(@PathVariable("id") Long id, @RequestBody ClientDTO clientDTO) {
        Optional<ClientDTO> updatedClient = clientService.updateClient(id, clientDTO);
        return updatedClient
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}