package com.megazone.ERPSystem_phase2_Backend.hr.model.pay_social_insurance_management;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity(name = "social_insurance")
@Table(name = "social_insurance")
public class SocialInsurance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "salary_id", nullable = false)
//    private Salary salary;

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

//    @Column
//    private LocalDate dateOfBirth; // 지급년월 웝급 테이블에 중복항목 필요할까 ?
}
