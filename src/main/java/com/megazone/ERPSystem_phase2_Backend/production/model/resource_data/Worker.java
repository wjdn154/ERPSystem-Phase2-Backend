package com.megazone.ERPSystem_phase2_Backend.production.model.resource_data;


import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company.Company;
import com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.Attendance;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Employee;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.Workcenter;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.work_order_assign.WorkerAssignment;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//작업자 관리 테이블

@Entity(name = "worker")
@Table(name = "worker")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Worker {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Boolean trainingStatue;       //교육이수 여부. (이수/미이수)

    /**인사 기본정보 참조
     * (사원번호,작업자 성,이름, 부서, 직위, 직책, 생년월일, 전화번호,고용상태,고용유형,고용일,프로필 사진)
     * 인사의 부서 참조 (부서 코드, 부서 이름)
     * 인사의 직위 참조 (직위 코드, 직위 이름)
     * 인사의 직책 참조 (직책 코드, 직책 이름)
     * 인사의 근태 참조 (근태 코드, 근태 날짜, 출근시간, 퇴근시간, 근무상태(AttendanceStatus))
     * 인사의 고용상태 (EmploymentStatus)
     * 인사의 고용유형 (EmploymentType)
     * */
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    /**작업자 배치 참조
     * 배치날짜
     * 작업자 배치의 작업장 참조 (작업장 코드, 작업장 이름)
     * */
    @OneToMany(mappedBy = "worker")
    private List<WorkerAssignment> workerAssignments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;                 //회사 테이블(회사 아이디)
}
