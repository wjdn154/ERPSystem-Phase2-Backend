package com.megazone.ERPSystem_phase2_Backend.production.model.resource_data;


import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Employee;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.Workcenter;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workcenter_id")
    private Workcenter workcenter;    //작업장 테이블 참조 . 작업 배치 (작업 배치 미정이 있을수도 있음)

    @Column(nullable = true)
    private String trainingStatue;  //교육이수 여부. (이수/미이수)


    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id")
    private Employee employee;   //인사 기본정보 참조
    

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "salary_Id", nullable = false)
//    private Salary salary;    인사 근태관리 참조(출근시간, 퇴근시간, 휴게시간). (출근/지각/조퇴/결근)

}
