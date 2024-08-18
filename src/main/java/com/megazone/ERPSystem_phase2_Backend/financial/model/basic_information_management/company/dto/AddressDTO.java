package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company.dto;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company.Address;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
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

    public AddressDTO(AddressDTO address) {
        this.businessPostalCode = address.getBusinessPostalCode();
        this.businesseAddress = address.getBusinesseAddress();
        this.businesseAddressDetail = address.getBusinesseAddressDetail();
        this.isBusinesseNewAddress = address.getIsBusinesseNewAddress();
        this.businessePlace = address.getBusinessePlace();
        this.headquarterPostalCode = address.getHeadquarterPostalCode();
        this.headquarterAddress = address.getHeadquarterAddress();
        this.headquarterAddressDetail = address.getHeadquarterAddressDetail();
        this.isHeadquarterNewAddress = address.getIsHeadquarterNewAddress();
        this.headquarterPlace = address.getHeadquarterPlace();
    }

    public AddressDTO(Address address) {
        this.businessPostalCode = address.getBusinessPostalCode();
        this.businesseAddress = address.getBusinesseAddress();
        this.businesseAddressDetail = address.getBusinesseAddressDetail();
        this.isBusinesseNewAddress = address.getIsBusinesseNewAddress();
        this.businessePlace = address.getBusinessePlace();
        this.headquarterPostalCode = address.getHeadquarterPostalCode();
        this.headquarterAddress = address.getHeadquarterAddress();
        this.headquarterAddressDetail = address.getHeadquarterAddressDetail();
        this.isHeadquarterNewAddress = address.getIsHeadquarterNewAddress();
        this.headquarterPlace = address.getHeadquarterPlace();
    }
}
