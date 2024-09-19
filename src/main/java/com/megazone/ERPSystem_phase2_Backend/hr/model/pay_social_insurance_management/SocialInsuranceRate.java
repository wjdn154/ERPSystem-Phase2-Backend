package com.megazone.ERPSystem_phase2_Backend.hr.model.pay_social_insurance_management;

import com.megazone.ERPSystem_phase2_Backend.hr.model.pay_social_insurance_management.enums.BusinessScale;
import com.megazone.ERPSystem_phase2_Backend.hr.model.pay_social_insurance_management.enums.InsuranceType;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity(name = "social_insurance_rate")
@Table(name = "social_insurance_rate")
public class SocialInsuranceRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InsuranceType insuranceType;

    @Enumerated(EnumType.STRING)
    private BusinessScale businessScale; // 사업장 규모
                                        // 회사 정보에 사업장 규모 있는데, 정보를 반환해서 요율조율 해야하니깐 ?

//    @Column
//    private BigDecimal totalRate;

    @Column
    private BigDecimal employeeRate;

    @Column
    private BigDecimal employerRate;

}
