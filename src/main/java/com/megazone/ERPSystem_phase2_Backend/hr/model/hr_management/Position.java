package com.megazone.ERPSystem_phase2_Backend.hr.model.hr_management;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

    @OneToMany(mappedBy = "position")// 사원 참조
    private List<Employee> employee;

    @Column(nullable = false)
    private String positionName; // 직위 이름 ( 예 부장, 과장 )

    @Column
    private String description; // 직위 설명 ( 예 : 직무에 대한 설명 )

    //@Column

}