package com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Employee;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

// Employee 엔티티와 ID 타입(Long)을 정의
public interface EmployeeRepository extends JpaRepository<Employee, Long>, EmployeeRepositoryCustom {
    // 해당 부서에 속한 사원이 존재하는지 확인하는 메서드
    boolean existsByDepartmentId(Long departmentId);

    Optional<Employee> findByEmail(String email);

    Optional<Employee> findFirstByOrderByIdDesc();

    Optional<Employee> findByEmployeeNumber(String employeeNumber); // 사원번호로 조회

}
