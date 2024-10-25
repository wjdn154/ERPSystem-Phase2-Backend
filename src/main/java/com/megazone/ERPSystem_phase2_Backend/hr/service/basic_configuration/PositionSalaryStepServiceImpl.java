package com.megazone.ERPSystem_phase2_Backend.hr.service.basic_configuration;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.dto.PositionSalaryStepAllowanceDetailDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.dto.PositionSalaryStepDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.dto.PositionSalaryStepSearchDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.dto.PositionSalaryStepShowDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_configuration.PositionSalaryStepRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class PositionSalaryStepServiceImpl implements PositionSalaryStepService {
    private final PositionSalaryStepRepository positionSalaryStepRepository;

    @Override
    public List<PositionSalaryStepShowDTO> show(Long positionId) {

        List<PositionSalaryStepDTO> queryResults = positionSalaryStepRepository.show(positionId);
        return showCreate(queryResults);
    }

    @Override
    public List<PositionSalaryStepShowDTO> endDateShow(PositionSalaryStepSearchDTO dto) {
        List<PositionSalaryStepDTO> queryResults = positionSalaryStepRepository.endDateShow(dto);
        return showCreate(queryResults);
    }

    public List<PositionSalaryStepShowDTO> showCreate(List<PositionSalaryStepDTO> queryResults) {

        Map<Long, PositionSalaryStepShowDTO> maps = new HashMap<>();

        for(PositionSalaryStepDTO dto : queryResults) {
            PositionSalaryStepShowDTO resultDto;
            if(maps.containsKey(dto.getSalaryStepId())) {
                resultDto = maps.get(dto.getSalaryStepId());
            }
            else {
                maps.put(dto.getSalaryStepId(), PositionSalaryStepShowDTO.create(dto));
                resultDto = maps.get(dto.getSalaryStepId());
                resultDto.setAllowances(new ArrayList<>());
            }
            resultDto.getAllowances().add(
                    PositionSalaryStepAllowanceDetailDTO.create(
                            dto.getAllowanceId(),
                            dto.getAllowanceName(),
                            dto.getAmount()
                    ));
            BigDecimal totalAmount = resultDto.getTotalAllowance().add(dto.getAmount());
            resultDto.setTotalAllowance(totalAmount);
        }

        List<PositionSalaryStepShowDTO> result = new ArrayList<>(maps.values());
        Collections.sort(result, Comparator.comparing(PositionSalaryStepShowDTO::getPositionSalaryStepId));

        return result;
    }
}
