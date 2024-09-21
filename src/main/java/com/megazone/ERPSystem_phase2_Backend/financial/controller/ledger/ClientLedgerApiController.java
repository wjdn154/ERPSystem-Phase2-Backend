package com.megazone.ERPSystem_phase2_Backend.financial.controller.ledger;

import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.ClientLedgerSearchDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.ClientLedgerShowAllDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.service.ledger.ClientLedgerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// 거래처 원장 API
@RestController
@RequiredArgsConstructor
public class ClientLedgerApiController {
    private final ClientLedgerService clientService;

    @PostMapping("/api/financial/ledger/clientLedger/show")
    public ResponseEntity<ClientLedgerShowAllDTO> showClientLedger(@RequestBody ClientLedgerSearchDTO dto) {
        ClientLedgerShowAllDTO clientLedgerShowAllDTO = clientService.show(dto);

        return clientLedgerShowAllDTO != null ? ResponseEntity.status(HttpStatus.OK).body(clientLedgerShowAllDTO) :
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
