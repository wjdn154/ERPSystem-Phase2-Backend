package com.megazone.ERPSystem_phase2_Backend.production.controller.basic_data.process_routing;

import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.company.CompanyRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Users;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Users.UsersRepository;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing.dto.ProcessDetailsDTO;
import com.megazone.ERPSystem_phase2_Backend.production.repository.basic_data.Workcenter.WorkcenterRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.basic_data.process_routing.ProcessDetails.ProcessDetailsRepository;
import com.megazone.ERPSystem_phase2_Backend.production.service.basic_data.workcenter.WorkcenterService;
import com.megazone.ERPSystem_phase2_Backend.production.service.basic_data.process_routing.ProcessDetails.ProcessDetailsService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/production/processDetails")
@RequiredArgsConstructor
public class ProcessDetailsController {

    private final ProcessDetailsService processDetailsService;

    // GET: 모든 ProcessDetails 조회
    @PostMapping
    public ResponseEntity<List<ProcessDetailsDTO>> getAllProcessDetails() {

        List<ProcessDetailsDTO> processDetailsDTOs = processDetailsService.getAllProcessDetails();
        return ResponseEntity.ok(processDetailsDTOs);
    }

    // GET: CODE로 특정 ProcessDetails 조회
    @PostMapping("/details/{code}")
    public ResponseEntity<Optional<ProcessDetailsDTO>> getProcessDetailsById(@PathVariable("code") String code) {

        Optional<ProcessDetailsDTO> processDetailsDTO = processDetailsService.getProcessDetailsByCode(code);
        return ResponseEntity.ok(processDetailsDTO);
    }

    // 3. 이름으로 공정 리스트 검색 조회
    @PostMapping("/search")
    public ResponseEntity<List<ProcessDetailsDTO>> getProcessDetailsByName(
            @RequestParam("name") String name) {

        List<ProcessDetailsDTO> processDetailsDTOs = processDetailsService.findByNameContaining(name);
        return ResponseEntity.ok(processDetailsDTOs);
    }

    @PostMapping("/new")
    public ProcessDetailsDTO createProcessDetails(@RequestBody ProcessDetailsDTO processDetailsDTO) {

        try {
            return processDetailsService.createProcessDetails(processDetailsDTO);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("입력하신 코드가 이미 존재합니다: " + processDetailsDTO.getCode());
        } catch (Exception e) {
            throw new RuntimeException("공정 정보를 생성하는 중에 오류가 발생했습니다.");
        }
    }

    @PostMapping("/update/{code}")
    public ProcessDetailsDTO updateProcessDetails(@PathVariable("code") String code, @RequestBody ProcessDetailsDTO processDetailsDTO) {

        try {
            return processDetailsService.updateByCode(code, processDetailsDTO);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("해당 코드의 공정을 찾을 수 없습니다: " + code);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("입력하신 코드가 이미 존재합니다: " + processDetailsDTO.getCode());
        } catch (Exception e) {
            throw new RuntimeException("공정 정보를 수정하는 중에 오류가 발생했습니다: " + e.getMessage(), e);
        }
    }

    // DELETE
    @PostMapping("/delete")
    public ProcessDetailsDTO deleteProcessDetails(@RequestParam("code") String code) {

        try {
            return processDetailsService.deleteByCode(code);
        } catch (IllegalArgumentException e) {
            // 코드가 없거나 사용 중인 경우에 대한 예외 처리
            throw new IllegalArgumentException("삭제할 수 없습니다. 이유: " + e.getMessage());
        } catch (Exception e) {
            // 기타 예외에 대한 처리
            throw new RuntimeException("공정 정보를 삭제하는 중에 예상치 못한 오류가 발생했습니다. 상세 정보: " + e.getMessage(), e);
        }
    }


}

