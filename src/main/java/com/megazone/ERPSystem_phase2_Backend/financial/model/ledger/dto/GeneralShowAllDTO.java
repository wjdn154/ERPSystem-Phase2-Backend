package com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeneralShowAllDTO {
    private String accountCode;
    private String accountName;
    private Map<String,GeneralShowDTO> allShows;
}
