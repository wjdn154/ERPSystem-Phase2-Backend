package com.megazone.ERPSystem_phase2_Backend.hr.model.payment.SocialInsurance;

import com.megazone.ERPSystem_phase2_Backend.hr.model.payment.Salary;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "social_insurance")
public class SocialInsurance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private Salary salary;

    @Column
    private BigDecimal EmploymentAmount;

    @Column
    private BigDecimal HealthAmount;

    @Column
    private BigDecimal InsuranceAmount;

    @Column
    private BigDecimal NationalAmount;

//    @Column
//    private LocalDate dateOfBirth; // 지급년월 웝급 테이블에 중복항목 필요할까 ?
}
