package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "company_main_business")
@Table(name = "company_main_business", indexes = {
        @Index(name = "idx_company_main_business_code", columnList = "code")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MainBusiness {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id; // 고유 식별자

    @Column(nullable = false, unique = true) // 업종코드
    private String code;

    @Column(nullable = false) // 업태
    private String businessType;

    @Column(nullable = false) // 종목
    private String item;

    public MainBusiness(String code, String businessType, String item) {
        this.code = code;
        this.businessType = businessType;
        this.item = item;
    }
}