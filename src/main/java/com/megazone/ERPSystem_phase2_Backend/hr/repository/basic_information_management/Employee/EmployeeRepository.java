package com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Employee;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Employee;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.EmployeeShowDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

// Employee 엔티티와 ID 타입(Long)을 정의
public interface EmployeeRepository extends JpaRepository<Employee, Long>, EmployeeRepositoryCustom {
    // 해당 부서에 속한 사원이 존재하는지 확인하는 메서드
    boolean existsByDepartmentId(Long departmentId);

    Optional<Employee> findByEmail(String email);

    Optional<Employee> findFirstByOrderByIdDesc();

    Optional<Employee> findByEmployeeNumber(String employeeNumber); // 사원번호로 조회

    //Optional<EmployeeDepartmentDTO> findByDepartmentId(Long id);

    List<Employee> findByDepartmentId(Long departmentId);


//    // 모든 Employee 조회 (Soft Deleted 포함)
//    @Query("SELECT e FROM Employee e WHERE e.isDeleted = false OR e.isDeleted = true")
//    List<Employee> findAllIncludingDeleted();
}
