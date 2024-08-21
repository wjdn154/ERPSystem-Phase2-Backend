package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.dto;

import lombok.*;

/**
 * 연락처 정보 DTO
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ContactInfoDTO {
    private String phoneNumber; // 전화번호
    private String faxNumber; // 팩스번호
}