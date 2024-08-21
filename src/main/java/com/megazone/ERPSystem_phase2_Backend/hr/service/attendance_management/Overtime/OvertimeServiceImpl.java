package com.megazone.ERPSystem_phase2_Backend.hr.service.attendance_management.Overtime;

import com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.Overtime;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.attendance_management.Overtime.OvertimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OvertimeServiceImpl implements OvertimeService{

    @Autowired // OvertimeRepository 인스턴스를 자동으루 주입하도록 함.
    private OvertimeRepository overtimeRepository;


    @Override
    public Optional<Overtime> saveOvertime(Overtime overtime) { // saveOvertime : 새로운 연장 근무 기록을 저장.
        return Optional.of(overtimeRepository.save(overtime));
    }

    @Override
    public Optional<Overtime> updateOvertime(Long id, Overtime overtime) {
        if(overtimeRepository.existsById(id)){
            overtime.setId(id);
            return Optional.of(overtimeRepository.save(overtime));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Overtime> getOvertimeById(Long id) {
        return overtimeRepository.findById(id);
    }

    @Override
    public void deleteOvertime(Long id) {
        overtimeRepository.deleteById(id);
    }
}
