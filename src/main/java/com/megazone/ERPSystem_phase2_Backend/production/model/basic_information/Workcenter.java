//package com.megazone.ERPSystem_phase2_Backend.production.model.basic_information;
//
//import com.megazone.ERPSystem_phase2_Backend.hr.model.hr_management.Employee;
//import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.MaterialData;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.util.List;
//
///**
// * 작업장 정보 테이블
// */
//
//@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class Workcenter {
//
//    // public enum WorkcenterType { PRODUCTION, ASSEMBLY, QUALITY_CONTROL, PACKAGING, MAINTENANCE, R_AND_D, TEST, LOGISTICS };
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(nullable = false)
//    private Long id; // 고유식별자
//
//    @Column(nullable = false)
//    private String code; // 작업장코드 (식별자와 별도의 지정코드)
//
//    @Column(nullable = false)
//    private String name; // 작업장명
//
//    @Column(nullable = true)
//    private String description; // 작업장 설명
//
//    @Column(nullable = true)
//    private Long inputPersonnel; // 투입인원수
//
//    @Column(nullable = false)
//    private boolean active; // 사용 여부
//
////    @ManyToOne(fetch = FetchType.LAZY)
////    @JoinColumn(name="factory", nullable = false)
////    private Factory factory;     // 공장 엔티티 from 물류 창고관리의 공장
//
//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(
//            name="workcenter_process",
//            joinColumns = @JoinColumn(name="workcenter_id"),
//            inverseJoinColumns = @JoinColumn(name="process_id")
//    )
//    private List<ProcessDetails> processes; // 연관 공정 목록
//
//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(
//            name="workcenter_material",
//            joinColumns = @JoinColumn(name="workcneter_id"),
//            inverseJoinColumns = @JoinColumn(name="material_id")
//    )
//    private List<MaterialData> materialDataList; // 연관 자재 목록
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="responsible", nullable = true)
//    private Employee responsible; // 연관 책임자 (HR)
//
//}
