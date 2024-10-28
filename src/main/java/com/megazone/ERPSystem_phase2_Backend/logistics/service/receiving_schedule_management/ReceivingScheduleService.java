package com.megazone.ERPSystem_phase2_Backend.logistics.service.receiving_schedule_management;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.receiving_processing_management.dto.ReceivingOrderProcessRequestListDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.receiving_processing_management.dto.ReceivingScheduleResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface ReceivingScheduleService {

    List<ReceivingScheduleResponseDTO> getReceivingSchedulesByDateRange(LocalDate startDate, LocalDate endDate);

    void registerReceivingSchedules(ReceivingOrderProcessRequestListDTO requestListDTO);

    void processReceivingSchedule(Long id);
}
