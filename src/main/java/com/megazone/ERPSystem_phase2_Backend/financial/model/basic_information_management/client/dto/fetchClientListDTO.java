package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class fetchClientListDTO {
    private Long id; // id
    private String representativeName; // 대표자명
    private String printClientName; // 상호명
    private String roadAddress; // 도로명 주소
    private String detailedAddress; // 상세 주소
    private String phoneNumber; // 전화번호
    private String businessType; // 사업종류
    private LocalDate transactionStartDate; // 거래 시작일
    private LocalDate transactionEndDate; // 거래 종료일
    private String remarks; // 비고
}
