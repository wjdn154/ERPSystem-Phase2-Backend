package com.megazone.ERPSystem_phase2_Backend.hr.service.salary_ledger;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.salary.IncomeTax;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.salary.IncomeTaxRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class IncomeTaxServiceImpl implements IncomeTaxService {
    private final IncomeTaxRepository incomeTaxRepository;


    @Override
    public List<IncomeTax> showBaseIncomeTax() {
        return incomeTaxRepository.findAll();
    }

    @Override
    public BigDecimal incomeTaxCalculator(BigDecimal amount) {

        List<IncomeTax> taxTable = incomeTaxRepository.findAll();
        for(IncomeTax tax : taxTable){
            if (tax.getLowAmount().compareTo(amount) >= 0 && tax.getHighAmount().compareTo(amount) < 0) {
                BigDecimal incomeTaxRate = tax.getRate();
                return incomeTaxRate.multiply(amount).setScale(0, RoundingMode.HALF_UP);
            }
        }
        return null;
    }
}
