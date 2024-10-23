package com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


// 직위 정보 저장

@Data
@Entity(name="employee_position")
@Table(name="employee_position")
@NoArgsConstructor
@AllArgsConstructor
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String code; // 직위 번호

    @Column(nullable = false)
    private String positionName; // 직위 이름 ( 예 : 사원-주임-대리-과장-차장-부장-이사-상무-전무-부사장-사장 )

    @Column
    private String description; // 직위 설명 ( 예 : 직무에 대한 설명 )


}