package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.dto;

import com.megazone.ERPSystem_phase2_Backend.financial.model.common.dto.BankDTO;
import lombok.*;

/**
 * 은행 계좌 정보 DTO
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BankAccountDTO {
    private BankDTO bank; // 은행 정보
    private String accountNumber; // 계좌번호
    private String accountHolder; // 예금주
}