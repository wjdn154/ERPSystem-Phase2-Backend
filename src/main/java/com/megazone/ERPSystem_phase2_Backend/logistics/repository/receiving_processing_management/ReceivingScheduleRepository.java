package com.megazone.ERPSystem_phase2_Backend.logistics.repository.receiving_processing_management;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.receiving_processing_management.ReceivingSchedule;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.receiving_processing_management.enums.ReceivingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReceivingScheduleRepository extends JpaRepository<ReceivingSchedule, Long>, ReceivingScheduleRepositoryCustom {

    List<ReceivingSchedule> findAllByReceivingDateBetweenAndStatus(LocalDate startDate, LocalDate endDate, ReceivingStatus receivingStatus);

    boolean existsByPendingInventoryNumber(Long pendingInventoryNumber);

}
