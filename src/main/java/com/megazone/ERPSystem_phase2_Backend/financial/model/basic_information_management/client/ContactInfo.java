package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

// 연락처 테이블
@Entity(name = "client_contact_info")
@Table(name = "client_contact_info")
@Getter
@Setter
public class ContactInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String phoneNumber; // 전화번호

    private String faxNumber; // 팩스번호
}
