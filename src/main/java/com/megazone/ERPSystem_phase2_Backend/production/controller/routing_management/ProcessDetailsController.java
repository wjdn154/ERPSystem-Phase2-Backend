package com.megazone.ERPSystem_phase2_Backend.production.controller.routing_management;

import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.dto.WorkcenterDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.dto.WorkerAssignmentDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.ProcessDetails;
import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.dto.ProcessDetailsDTO;
import com.megazone.ERPSystem_phase2_Backend.production.repository.routing_management.ProcessDetails.ProcessDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/production/processDetails")
@RequiredArgsConstructor
public class ProcessDetailsController {

    private final ProcessDetailsRepository processDetailsRepository;

    @GetMapping
    public ResponseEntity<List<ProcessDetailsDTO>> getAllProcessDetails() {
        List<ProcessDetails> processDetailsList = processDetailsRepository.findAll();

        List<ProcessDetailsDTO> processDetailsDTOs = processDetailsList.stream()
                .map(processDetails -> ProcessDetailsDTO.builder()
                        .code(processDetails.getCode())
                        .name(processDetails.getName())
                        .isOutsourced(processDetails.getIsOutsourced())
                        .duration(processDetails.getDuration())
                        .cost(processDetails.getCost())
                        .isUsed(processDetails.getIsUsed())
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.ok(processDetailsDTOs);
    }
}
