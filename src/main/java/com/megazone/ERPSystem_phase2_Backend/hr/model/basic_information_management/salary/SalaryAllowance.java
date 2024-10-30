package com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.salary;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.Allowance;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


/**
 * 급여에서 추가되는 수당
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "salary_allowance")
@Table(name = "salary_allowance")
public class SalaryAllowance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "salary_id", nullable = false)
    private Salary salary; // 급여 테이블과 다대일 관계

    @ManyToOne
    @JoinColumn(name = "allowance_id", nullable = false)
    private Allowance allowance; // 수당 테이블과 다대일 관계

    private BigDecimal amount; // 수당 금액
}