package com.megazone.ERPSystem_phase2_Backend.production.model.resource_data;


import com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.Attendance;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Employee;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.Workcenter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//작업자 관리 테이블

@Entity(name = "worker")
@Table(name = "worker")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Worker {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private String trainingStatue;       //교육이수 여부. (이수/미이수)


    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id")
    private Employee employee;    //인사 기본정보 참조 (사원번호,작업자 성,이름, 부서, 직위, 직책, 생년월일, 전화번호,고용상태,고용유형,고용일)
    

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attendance_Id")
    private Attendance attendance;       //인사 근태관리 참조(출근시간, 퇴근시간, 휴게시간). (출근/지각/조퇴/결근)

}
