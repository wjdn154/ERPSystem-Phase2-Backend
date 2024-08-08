//package com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.order_registration;
//
//import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.quotation_registration.Quotation;
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.time.LocalDate;
//
///**
// * 주문서 테이블
// * 주문서에 대한 정보가 있는 테이블
// */
//@Entity
//@Getter
//@Builder
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@AllArgsConstructor(access = AccessLevel.PROTECTED)
//public class Order {
//
//    // 고유 식별자
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    // 견적서_id 조인
////    @OneToMany(mappedBy = "")
////    @JoinColumn(name = "quotation_id", nullable = false)
////    private Quotation quotation;
//
//    // 일자
//    @Column(nullable = false)
//    private LocalDate date;
//
//    // 납기 일자
//    @Column(nullable = false)
//    private LocalDate deliveryDate;
//
//    // 비고
//    @Column
//    private String remarks;
//
//}
