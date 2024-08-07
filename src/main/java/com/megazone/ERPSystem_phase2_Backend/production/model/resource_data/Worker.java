package com.megazone.ERPSystem_phase2_Backend.production.model.resource_data;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//작업자 관리 테이블

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Worker {
    @Id @GeneratedValue
    private Long id;

    @Column
    private String placement;    //작업 배치

    @Column
    private String trainingStatue;  //교육이수 여부

    //인사 기본정보 참조
    
    //인사 근태관리 테이블 참조

}
