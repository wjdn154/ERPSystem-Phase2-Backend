package com.megazone.ERPSystem_phase2_Backend.production.controller.routing_management;

import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.dto.ProcessDetailsDTO;
import com.megazone.ERPSystem_phase2_Backend.production.repository.routing_management.ProcessDetails.ProcessDetailsRepository;
import com.megazone.ERPSystem_phase2_Backend.production.service.routing_management.ProcessDetails.ProcessDetailsService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/production/processDetails")
@RequiredArgsConstructor
public class ProcessDetailsController {

    private final ProcessDetailsRepository processDetailsRepository;
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

    @PostMapping("/create")
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



    // DELETE: 특정 ProcessDetails 삭제
    @PostMapping("/delete")
    public ProcessDetailsDTO deleteProcessDetails(@RequestParam("code") String code) {
        try {
             return processDetailsService.deleteByCode(code);
        } catch (EntityNotFoundException e) {
             throw new IllegalArgumentException("해당 코드의 공정을 찾을 수 없습니다: " + code);
        } catch (Exception e) {
             throw new RuntimeException("공정 정보를 삭제하는 중에 오류가 발생했습니다.");
        }
    }
}

