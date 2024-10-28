package com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.Performance;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Employee;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Performance;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.PerformanceCreateDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.PerformanceDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.PerformanceOneDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.PerformanceShowDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Employee.EmployeeRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Performance.PerformanceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
public class PerformanceServiceImpl implements PerformanceService {
    private final PerformanceRepository performanceRepository;
    private final EmployeeRepository employeeRepository;

    // 성과 평가 등록
    @Override
    public PerformanceDTO addPerformance(PerformanceCreateDTO performanceDTO) {
        // employeeNumber로 사원 조회
        Employee employee = employeeRepository.findByEmployeeNumber(performanceDTO.getEmployeeNumber())
                .orElseThrow(() -> new RuntimeException("사원을 찾을 수 없습니다."));

        // evaluatorId로 평가자 조회
        Employee evaluator = employeeRepository.findById(performanceDTO.getEvaluatorId())
                .orElseThrow(() -> new RuntimeException("평가자를 찾을 수 없습니다."));

        // Performance 엔티티 생성 및 데이터 설정
        Performance performance = new Performance();
        performance.setEmployee(employee);
        performance.setEvaluator(evaluator);
        performance.setTitle(performanceDTO.getTitle());
        performance.setEvaluationDate(performanceDTO.getEvaluationDate());
        performance.setScore(performanceDTO.getScore());
        performance.setComments(performanceDTO.getComments());

        // Performance 저장
        Performance savedPerformance = performanceRepository.save(performance);

        // 저장 후 PerformanceDTO로 변환
        return new PerformanceDTO(
                employee.getEmployeeNumber(),
                employee.getLastName() + employee.getFirstName(),
                savedPerformance.getTitle(),
                evaluator.getLastName() + evaluator.getFirstName(),
                savedPerformance.getEvaluationDate(),
                savedPerformance.getScore(),
                savedPerformance.getComments()
        );
    }

    // 특정 사원의 성과 평가 조회
    public List<PerformanceShowDTO> getPerformanceByEmployee(Long employeeId) {
        List<Performance> performances = performanceRepository.findByEmployeeId(employeeId);

        // 엔티티를 DTO로 변환
        return performances.stream().map(performance -> new PerformanceShowDTO(
                performance.getId(),
                performance.getEmployee().getEmployeeNumber(),
                performance.getEmployee().getLastName()+performance.getEmployee().getFirstName(),
                performance.getEvaluator().getId(),
                performance.getTitle(),
                performance.getEvaluator().getLastName() + performance.getEvaluator().getFirstName(),
                performance.getEvaluationDate(),
                performance.getScore(),
                performance.getComments()
        )).collect(Collectors.toList());
    }

    @Override
    public List<PerformanceShowDTO> getAllPerformances() {
        List<Performance> performances = performanceRepository.findAll();
        return performances.stream()
                .map(PerformanceShowDTO::fromEntity) // 엔티티를 DTO로 변환
                .collect(Collectors.toList());
    }

    // 성과 평가 수정
    @Override
    public PerformanceShowDTO updatePerformance(Long performanceId, PerformanceOneDTO performanceoneDTO) {
        Performance performance = performanceRepository.findById(performanceId)
                .orElseThrow(() -> new RuntimeException("해당 성과 평가를 찾을 수 없습니다."));

        // DTO에서 받은 값으로 엔티티 필드 업데이트
        performance.setTitle(performanceoneDTO.getTitle());
        performance.setEvaluationDate(performanceoneDTO.getEvaluationDate());
        performance.setScore(performanceoneDTO.getScore());
        performance.setComments(performanceoneDTO.getComments());

        // 변경된 엔티티 저장
        Performance updatedPerformance = performanceRepository.save(performance);

        // 엔티티를 DTO로 변환하여 반환
        return new PerformanceShowDTO(
                updatedPerformance.getId(),
                updatedPerformance.getEmployee().getLastName()+updatedPerformance.getEmployee().getFirstName(),
                updatedPerformance.getEmployee().getEmployeeNumber(),
                updatedPerformance.getEvaluator().getId(),
                updatedPerformance.getTitle(),
                updatedPerformance.getEvaluator().getLastName() + updatedPerformance.getEvaluator().getFirstName(),
                updatedPerformance.getEvaluationDate(),
                updatedPerformance.getScore(),
                updatedPerformance.getComments()
        );
    }


    // 성과 평가 삭제
    public String deletePerformance(Long performanceId) {
        // 성과 평가가 존재하는지 확인
        Performance performance = performanceRepository.findById(performanceId)
                .orElseThrow(() -> new RuntimeException("해당 성과 평가를 찾을 수 없습니다."));

        // 성과 평가 삭제
        performanceRepository.delete(performance);

        // 삭제 성공 메시지 반환
        return "성과 평가가 성공적으로 삭제되었습니다.";
    }
}
