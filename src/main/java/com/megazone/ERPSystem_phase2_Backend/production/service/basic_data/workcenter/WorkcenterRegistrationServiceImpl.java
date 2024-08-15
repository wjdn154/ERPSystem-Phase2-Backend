package com.megazone.ERPSystem_phase2_Backend.production.service.basic_data.workcenter;

import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.Workcenter;
import com.megazone.ERPSystem_phase2_Backend.production.repository.basic_data.Workcenter.WorkcenterRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkcenterRegistrationServiceImpl implements WorkcenterRegistrationService {

    private final WorkcenterRepository workcenterRepository;

    @Override
    @Transactional
    public List<Workcenter> findByName(String name) {
        return workcenterRepository.findByName(name);
    }

    @Override
    @Transactional
    public List<Workcenter> findByCode(String code) {
        return workcenterRepository.findByCode(code);
    }

    @Override
    public List<Workcenter> findByActive(Boolean active) {
        return workcenterRepository.findByActive(active);
    }

    @Override
    @Transactional
    public Workcenter save(Workcenter workcenter) {
        return workcenterRepository.save(workcenter);
    }

    @Override
    @Transactional
    public Workcenter findById(Long id) {
        return workcenterRepository.findById(id).orElse(null);
    }


    @Override
    @Transactional
    public void deleteById(Long id) {
        workcenterRepository.deleteById(id);
    }

}
