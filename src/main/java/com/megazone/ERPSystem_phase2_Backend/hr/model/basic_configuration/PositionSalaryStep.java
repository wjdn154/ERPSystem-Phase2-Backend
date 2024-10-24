package com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Position;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

/**
 * 직급과 호봉 기준 테이블
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "employee_position_salary_step")
@Table(name = "employee_position_salary_step")
public class PositionSalaryStep {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "salary_steps")
    private SalaryStep salarySteps; // 호봉 테이블

    @ManyToOne
    @JoinColumn(name = "positions")
    private Position positions; // 직급 테이블

    @OneToMany(mappedBy = "positionSalaryStep",cascade = CascadeType.ALL)
    private List<PositionSalaryStepAllowance> allowances; // 수당 중간테이블 조인

    @Column(nullable = false)
    private YearMonth useStartDate; // 직급에 따른 호봉 적용시작 년.월


    private YearMonth useEndDate; // 직급에 따른 호봉 적용마감 년.월 null 이면 아직 적용중인거

}
