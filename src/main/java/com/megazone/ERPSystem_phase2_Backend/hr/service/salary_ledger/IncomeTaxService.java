package com.megazone.ERPSystem_phase2_Backend.hr.service.salary_ledger;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.salary.IncomeTax;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.salary.dto.IncomeTaxShowDTO;

import java.math.BigDecimal;
import java.util.List;

public interface IncomeTaxService {
    List<IncomeTax> showBaseIncomeTax();

    BigDecimal incomeTaxCalculator(BigDecimal amount);

    List<IncomeTaxShowDTO> show();
}
