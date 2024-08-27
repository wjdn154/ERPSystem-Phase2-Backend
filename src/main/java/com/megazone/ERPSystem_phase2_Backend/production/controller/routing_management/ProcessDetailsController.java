package com.megazone.ERPSystem_phase2_Backend.production.controller.routing_management;

import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.ProcessDetails;
import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.dto.ProcessDetailsDTO;
import com.megazone.ERPSystem_phase2_Backend.production.repository.routing_management.ProcessDetails.ProcessDetailsRepository;
import com.megazone.ERPSystem_phase2_Backend.production.service.routing_management.ProcessDetails.ProcessDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/production/processDetails")
@RequiredArgsConstructor
public class ProcessDetailsController {

    private final ProcessDetailsRepository processDetailsRepository;
    private final ProcessDetailsService processDetailsService;

    // GET: 모든 ProcessDetails 조회
    @GetMapping
    public ResponseEntity<List<ProcessDetailsDTO>> getAllProcessDetails() {
        List<ProcessDetailsDTO> processDetailsDTOs = processDetailsService.getAllProcessDetails();
        return ResponseEntity.ok(processDetailsDTOs);
    }

    // GET: ID로 특정 ProcessDetails 조회
    @GetMapping("/{id}")
    public ResponseEntity<ProcessDetailsDTO> getProcessDetailsById(@PathVariable Long id) {
        ProcessDetailsDTO processDetailsDTO = processDetailsService.getProcessDetailsById(id);
        return ResponseEntity.ok(processDetailsDTO);
    }

    // POST: 새로운 ProcessDetails 생성
    @PostMapping
    public ResponseEntity<ProcessDetailsDTO> createProcessDetails(@RequestBody ProcessDetailsDTO processDetailsDTO) {
        ProcessDetailsDTO createdProcessDetails = processDetailsService.createProcessDetails(processDetailsDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProcessDetails);
    }

    // PUT: 기존 ProcessDetails 수정
    @PutMapping("/{id}")
    public ResponseEntity<ProcessDetailsDTO> updateProcessDetails(@PathVariable Long id, @RequestBody ProcessDetailsDTO processDetailsDTO) {
        ProcessDetailsDTO updatedProcessDetails = processDetailsService.updateProcessDetails(id, processDetailsDTO);
        return ResponseEntity.ok(updatedProcessDetails);
    }

    // DELETE: 특정 ProcessDetails 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProcessDetails(@PathVariable Long id) {
        processDetailsService.deleteProcessDetails(id);
        return ResponseEntity.noContent().build();
    }

}
