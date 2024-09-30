package com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientListDTO {
    private Long clientId;
    private String clientCode;
    private String clientName;
    private String clientRegisterNumber;
    private String ownerName;

    public static ClientListDTO create(Long clientId, String clientCode, String clientName, String clientRegisterNumber, String ownerName) {
        return new ClientListDTO(
                clientId,
                clientCode,
                clientName,
                clientRegisterNumber,
                ownerName
        );
    }
}
