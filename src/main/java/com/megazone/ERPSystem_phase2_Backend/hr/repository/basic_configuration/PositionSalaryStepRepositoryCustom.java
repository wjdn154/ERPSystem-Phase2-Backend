package com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_configuration;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.dto.PositionSalaryStepDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.dto.PositionSalaryStepDateDetailDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.dto.PositionSalaryStepSearchDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.salary_ledger.dto.SalaryLedgerAllowanceDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.salary_ledger.dto.SalaryLedgerAllowanceShowDTO;

import java.math.BigDecimal;
import java.util.List;

public interface PositionSalaryStepRepositoryCustom {
    List<PositionSalaryStepDTO> show(Long positionId);

    List<PositionSalaryStepDTO> endDateShow(PositionSalaryStepSearchDTO dto);

    List<PositionSalaryStepDateDetailDTO> dateList(Long positionId);

    BigDecimal getSalaryAmount(Long positionId, Long SalaryStepId);

    List<SalaryLedgerAllowanceDTO> getSalaryAllowance(Long positionId, Long SalaryStepId);
}
