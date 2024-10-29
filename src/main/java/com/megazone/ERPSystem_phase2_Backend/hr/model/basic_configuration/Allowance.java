package com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.enums.AllowanceType;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.enums.TaxType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 수당
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "employee_allowance")
@Table
public class Allowance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(nullable = false,unique = true)
    private String code; // 수당코드

    @Column(nullable = false)
    private String name; // 수당이름

    private String description; // 비고

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AllowanceType type; // 수당 타입 (호봉 테이블용 수당, 일반 수당)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaxType taxType; // 과세 여부 (과세, 비과세)

    private BigDecimal limitAmount; // 한도 금액 (비과세일 경우 해당)
}
