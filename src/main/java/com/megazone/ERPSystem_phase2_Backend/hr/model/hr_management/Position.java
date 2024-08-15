//package com.megazone.ERPSystem_phase2_Backend.hr.model.hr_management;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//// 직위 정보 저장
//
//@Data
//@Entity
//@NoArgsConstructor
//@AllArgsConstructor
//public class Position {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "employee_Id",nullable = false)
//    private Employee employee;
//
//    @Column(nullable = false)
//    private String positionName; // 직위 이름
//
//    @Column
//    private String description; // 직위 설명
//
//    //@Column
//
//}
