package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.dto;

import lombok.*;

/**
 * 주소 정보
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {
    private String postalCode; // 우편번호
    private String roadAddress; // 도로명 주소
    private String detailedAddress; // 상세 주소
}