package com.megazone.ERPSystem_phase2_Backend.production.model.resource_data;


import com.megazone.ERPSystem_phase2_Backend.hr.model.hr_management.Employee;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//작업자 관리 테이블

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Worker {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String placement;    //작업 배치 (작업 배치 미정이 있을수도 있음)

    @Column(nullable = false)
    private String trainingStatue;  //교육이수 여부


    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id")
    private Employee employee;   //인사 기본정보 참조
    

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "salary_Id", nullable = false)
//    private Salary salary;    인사 근태관리 참조(출근시간, 퇴근시간, 휴게시간)

}
