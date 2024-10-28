package com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.salary;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.salary.IncomeTax;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.salary.IncomeTaxRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
