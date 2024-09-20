package com.megazone.ERPSystem_phase2_Backend.hr.model.pay_social_insurance_management;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity(name = "social_insurance_amount")
@Table(name = "social_insurance_amount")
public class SocialInsuranceAmount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private BigDecimal EmploymentAmount;

    @Column
    private BigDecimal HealthAmount;

    @Column
    private BigDecimal InsuranceAmount;

    @Column
    private BigDecimal NationalAmount;

    @Column
    private BigDecimal LongTermCareAmount;
}
