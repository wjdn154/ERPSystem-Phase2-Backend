package com.megazone.ERPSystem_phase2_Backend.production.service.routing_management.ProcessDetails;

import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.dto.ProcessDetailsDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public interface ProcessDetailsService {

    List<ProcessDetailsDTO> getAllProcessDetails();
//    ProcessDetailsDTO getProcessDetailsById(Long id);
    ProcessDetailsDTO createProcessDetails(ProcessDetailsDTO processDetailsDTO);
    ProcessDetailsDTO updateByCode(String code, ProcessDetailsDTO processDetailsDTO);
    ProcessDetailsDTO deleteByCode(String code);

    Optional<ProcessDetailsDTO> getProcessDetailsByCode(String code);

    List<ProcessDetailsDTO> findByNameContaining(String name);

}
