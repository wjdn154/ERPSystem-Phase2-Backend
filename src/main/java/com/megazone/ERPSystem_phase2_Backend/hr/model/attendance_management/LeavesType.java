package com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 휴가 유형 정보를 저장

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class LeavesType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String typeName; // 휴가 유형명( 예 : 병가, 연차, 개인 휴가 등)
}
