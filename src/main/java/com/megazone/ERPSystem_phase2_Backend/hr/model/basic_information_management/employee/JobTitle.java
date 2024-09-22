package com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 직책 테이블

@Data
@AllArgsConstructor
@Entity(name="employee_job_title")
@NoArgsConstructor
@Table(name="employee_job_title")
public class JobTitle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code; // 직책번호
    
    @Column(nullable = false)
    private String titleName; // 직책 이름 ( 예 : 소프트웨어 개발자 )
    
    @Column
    private String description; // 직책 설명


}
