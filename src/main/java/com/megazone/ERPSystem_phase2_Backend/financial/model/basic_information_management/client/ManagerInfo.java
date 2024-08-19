package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

// 업체 담당자 정보 테이블
@Entity(name = "client_manager_info")
@Table(name = "client_manager_info")
@Getter
@Setter
public class ManagerInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String clientManagerPhoneNumber; // 업체 담당자 전화번호

    private String clientManagerEmail; // 업체 담당자 이메일
}
