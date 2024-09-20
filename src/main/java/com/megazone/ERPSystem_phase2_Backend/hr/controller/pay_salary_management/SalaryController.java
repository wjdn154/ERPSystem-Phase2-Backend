package com.megazone.ERPSystem_phase2_Backend.hr.controller.pay_salary_management;

import com.megazone.ERPSystem_phase2_Backend.hr.model.pay_salary_management.dto.SalaryDetailDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.pay_salary_management.dto.SalaryListDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.pay_salary_management.SalaryRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.service.payment.SalaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hr/salary")
public class SalaryController {
    private final SalaryService salaryService;
    private final SalaryRepository salaryRepository;

    // 모든 사원의 월급 리스트 조회
    @PostMapping("/all")
    public ResponseEntity<List<SalaryListDTO>> getAllSalaryList() {
        List<SalaryListDTO> dtos = salaryService.findAllEmployeesAndSalary();
        return !dtos.isEmpty() ? ResponseEntity.status(HttpStatus.OK).body(dtos) :
               ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    // 월급 리스트에서 생성 및 수정
    @PostMapping("/all/insert")
    public ResponseEntity<String> createOrUpdateEmployees(@RequestBody List<SalaryListDTO> salaryListDTOs) {
        salaryService.createOrUpdateSalary(salaryListDTOs);
        return ResponseEntity.status(HttpStatus.OK).body("월급 정보가 성공적으로 생성 또는 수정되었습니다.");
    }

    // 직원 ID로 월급 데이터를 조회
    // 사원 id를 사번으로 바꿔야하나
    @GetMapping("/{employeeId}/{id}")
    public ResponseEntity<SalaryDetailDTO> getSalaryByEmployeeId(@PathVariable Long employeeId,@PathVariable Long id) {
        Optional<SalaryDetailDTO> dto = salaryService.findSalaryByEmployeeId(employeeId);
        // Optional이 값이 있을 경우 OK, 없을 경우 404 반환
        return dto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }



    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSalary(@PathVariable Long id) {
        try{
            salaryService.deleteSalary(id);
            return ResponseEntity.ok("월급을 삭제했습니다.");
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}

