//package com.megazone.ERPSystem_phase2_Backend.production.model.outsourcing;
//
////외주 실적 관리
//
//import com.megazone.ERPSystem_phase2_Backend.production.model.basic_information.ProcessDetails;
//import com.megazone.ERPSystem_phase2_Backend.production.model.basic_information.Workcenter;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.util.Date;
//
//@Entity
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class OutsourcingPerformance {
//
//    @Id @GeneratedValue
//    private Long id;
//
//    @Column
//    private String materialStatus;
//    //자재 사용 유무
//    @Column
//    private Date date;                   //실적일
//
//    @JoinColumn(name = "process_id")
//    @Column
//    private ProcessDetails process;             //공정
//
//    @JoinColumn(name = "workcenter_id")
//    @Column
//    private Workcenter workcenter;       //작업장
//
//    @Column
//    private String sortation;            //구분
//
//    @Column
//    private String performanceClassification;       //실적 구분 (적합/비적합)
//
//    @Column
//    private Long performanceQuantity;               //실적 수량
//
//    @Column
//    private String prosecutor;                      //검사(검사/무검사)
//
//}
