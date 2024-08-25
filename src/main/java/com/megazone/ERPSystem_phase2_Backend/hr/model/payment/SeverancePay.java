//package com.megazone.ERPSystem_phase2_Backend.hr.model.hr_payment;
//
//import jakarta.persistence.*;
//import lombok.Data;
//
//import java.util.List;
//
//@Data
//@Entity
//@Table
//public class SeverancePay {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
////    @Column
////    private BigDecimal
//
////    @OneToOne(mappedBy = "severancePay", fetch = FetchType.LAZY)
////    private Employee employee; // 사원입사일 참조
////
////    @OneToOne(mappedBy = "severancePay", fetch = FetchType.LAZY)
////    private ResignedEmployee resignedEmployee; // 사원퇴사일 참조
//
//    @OneToMany(mappedBy = "severancePay", fetch = FetchType.LAZY)
//    private List<Salary> salaries; // 월급 참조
//
//
//
//    // 퇴직자 -> 퇴직금 연관관계 매핑 필요함.
//}
