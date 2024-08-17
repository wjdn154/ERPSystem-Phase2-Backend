package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
    private Long id; // 주소 ID
    private String businessPostalCode; // 사업장 우편번호
    private String businesseAddress; // 사업장 주소
    private String businesseAddressDetail; // 사업장 상세 주소
    private Boolean isBusinesseNewAddress; // 사업장 신주소 여부
    private String businessePlace; // 사업장 동 (예: 대연동)
    private String headquarterPostalCode; // 본점 우편 번호
    private String headquarterAddress; // 본점 주소
    private String headquarterAddressDetail; // 본점 상세 주소
    private Boolean isHeadquarterNewAddress; // 본점 신주소 여부
    private String headquarterPlace; // 본점 동
}
