package com.megazone.ERPSystem_phase2_Backend.hr.model.payment.SocialInsurance;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company.Company;
import com.megazone.ERPSystem_phase2_Backend.hr.model.payment.Salary;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/*
    고용보험 테이블
*/
@Entity
@Data
public class Employment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @OneToOne
//    private Employee employee // 사업장 규모 파악을 위한 사원 매핑

//    @OneToOne
//    private Company company; // 우선지원대상기업 여부를 위한 회사 매핑

//    @OneToOne
//    private Salary salary; //

    @Column(precision = 6 ,scale = 4)
    private int rate; // 요율 기준소득월액의 (1.8~%, 0.9%, 0.9~%)

    @Column
    private int changing_rate; // 사업장 규모에 따른 추가 요율

    @Column
    private LocalDate ApplyYear; // 요율 적용년도


    // 2024 상한액 월 524만원 하한액 33만원 (매년 7월 갱신)
    // 기준소득월액 (보수월액<비과세 소득을 제외한 급여>에서 천원 미만 금액 절삭한 금액) =

}
