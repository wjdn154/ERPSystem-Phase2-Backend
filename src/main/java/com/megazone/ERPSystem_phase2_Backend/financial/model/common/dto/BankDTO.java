package com.megazone.ERPSystem_phase2_Backend.financial.model.common.dto;
import lombok.*;

/**
 * 은행 정보 DTO
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BankDTO {
    private Long id;
    private String code; // 은행 코드
    private String name; // 은행명
    private String businessNumber; // 사업자등록번호
}