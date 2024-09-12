package com.megazone.ERPSystem_phase2_Backend.production.service.basic_data.process_routing.ProcessDetails;

import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing.dto.ProcessDetailsDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public interface ProcessDetailsService {

    List<ProcessDetailsDTO> getAllProcessDetails(Long company_id);
//    ProcessDetailsDTO getProcessDetailsById(Long id);
    ProcessDetailsDTO createProcessDetails(Long company_id, ProcessDetailsDTO processDetailsDTO);
    ProcessDetailsDTO updateByCode(Long company_id, String code, ProcessDetailsDTO processDetailsDTO);
    ProcessDetailsDTO deleteByCode(Long company_id, String code);

    Optional<ProcessDetailsDTO> getProcessDetailsByCode(Long company_id, String code);

    List<ProcessDetailsDTO> findByNameContaining(Long company_id, String name);

}
