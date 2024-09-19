package com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 주소 정보 테이블
 * 회사등록 시 필요한 주소 데이터 테이블
 */
@Entity(name = "warehouse_address")
@Table(name = "warehouse_address")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id; // 고유 식별자

    @Column(nullable = false) // 창고 주소
    private String warehouseAddress;

    @Column(nullable = true) // 창고 상세주소
    private String detailAddress;

    @Column(nullable = false) // 창고 우편번호
    private String warehousePostalCode;

    @Column(nullable = false) // 창고 동 (예: 대연동)
    private String warehousePlace;

}
