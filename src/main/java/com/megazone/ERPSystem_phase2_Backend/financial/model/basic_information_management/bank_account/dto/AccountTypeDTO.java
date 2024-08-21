package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.bank_account.dto;

import lombok.*;

/**
 * 예금 유형 정보
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AccountTypeDTO {

    private Long id; // 예금 유형 ID
    private String code; // 예금 유형 코드
    private String typeName; // 예금 유형명 (보통예금, 당좌예금 등)

}