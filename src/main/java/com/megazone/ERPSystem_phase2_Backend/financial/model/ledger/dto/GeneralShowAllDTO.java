package com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeneralShowAllDTO {
    private Map<Integer,GeneralShowDTO> allShows;


    // 1월 ~ 12월 초기값 세팅
    public void showInit(int maxMonth) {
        allShows = new HashMap<>();
        
        for (int month = 1; month <= maxMonth; month++) {
            allShows.put(month, GeneralShowDTO.create(month, BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO));
        }
    }
}