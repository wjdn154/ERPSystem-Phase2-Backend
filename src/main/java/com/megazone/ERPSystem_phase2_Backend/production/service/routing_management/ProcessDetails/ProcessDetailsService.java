package com.megazone.ERPSystem_phase2_Backend.production.service.routing_management.ProcessDetails;

import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.dto.ProcessDetailsDTO;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface ProcessDetailsService {

    List<ProcessDetailsDTO> getAllProcessDetails();
    ProcessDetailsDTO getProcessDetailsById(Long id);
    ProcessDetailsDTO createProcessDetails(ProcessDetailsDTO processDetailsDTO);
    ProcessDetailsDTO updateProcessDetails(Long id, ProcessDetailsDTO processDetailsDTO);
    void deleteProcessDetails(Long id);


}
