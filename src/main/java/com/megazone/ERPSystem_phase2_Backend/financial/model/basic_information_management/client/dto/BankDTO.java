package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.dto;
import lombok.*;

/**
 * 은행 정보
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BankDTO {
    private Long id;
    private String code; // 은행 코드
    private String bankName; // 은행명
}