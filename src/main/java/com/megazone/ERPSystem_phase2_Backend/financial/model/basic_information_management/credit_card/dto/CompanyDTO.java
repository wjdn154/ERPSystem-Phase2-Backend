package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.credit_card.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDTO {
    private Long id;
    private String name; // 카드사명
    private String number; // 전화번호
    private String website; // 홈페이지
}