package com.megazone.ERPSystem_phase2_Backend.hr.service.salary_ledger;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.Allowance;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.dto.AllowanceShowDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.dto.InsurancePensionCalculatorDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.dto.LongTermCareInsuranceCalculatorDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.enums.TaxType;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Employee;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.salary.LongTermCareInsurancePension;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.salary.Salary;
import com.megazone.ERPSystem_phase2_Backend.hr.model.salary_ledger.SalaryLedger;
import com.megazone.ERPSystem_phase2_Backend.hr.model.salary_ledger.SalaryLedgerAllowance;
import com.megazone.ERPSystem_phase2_Backend.hr.model.salary_ledger.SalaryLedgerDate;
import com.megazone.ERPSystem_phase2_Backend.hr.model.salary_ledger.dto.*;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_configuration.PositionSalaryStepRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Employee.EmployeeRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.salary.*;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.salary_ledger.SalaryLedgerDateRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.salary_ledger.SalaryLedgerRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.service.basic_configuration.AllowanceService;
import com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.salary.EmploymentInsurancePensionService;
import com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.salary.HealthInsurancePensionService;
import com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.salary.LongTermCareInsurancePensionService;
import com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.salary.NationalPensionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class SalaryLedgerServiceImpl implements SalaryLedgerService {

    private final SalaryLedgerRepository salaryLedgerRepository;
    private final SalaryLedgerDateRepository salaryLedgerDateRepository;
    private final EmployeeRepository employeeRepository;
    private final PositionSalaryStepRepository positionSalaryStepRepository;
    private final SalaryRepository salaryRepository;
    private final HealthInsurancePensionService healthInsurancePensionService;
    private final NationalPensionService nationalPensionService;
    private final LongTermCareInsurancePensionService longTermCareInsurancePensionService;
    private final EmploymentInsurancePensionService employmentInsurancePensionService;
    private final LongTermCareInsurancePensionRepository longTermCareInsurancePensionRepository;
    private final IncomeTaxService incomeTaxService;
    private final AllowanceService allowanceService;
    /**
     * 사원 id 받아서 조회하고 해당월에 급여정보 등록한적없으면 새로생성
     * 결산되지않은 급여정보 있으면 불러오고
     * 결산하면 더이상 수정불가
     */

    @Override
    public SalaryLedgerDTO showSalaryLedger(SalaryLedgerSearchDTO dto) {
        SalaryLedgerDTO result = salaryLedgerRepository.findLedger(dto);

        if(result == null) {
            SalaryLedgerDate salaryLedgerDate = salaryLedgerDateRepository.findById(dto.getSalaryLedgerDateId())
                    .orElseThrow(() ->new NoSuchElementException("해당하는 급여 지급정보가 없습니다."));

            Employee employee = employeeRepository.findById(dto.getEmployeeId()).orElseThrow(
                    () -> new NoSuchElementException("해당하는 사원 정보가 없습니다."));

            List<AllowanceShowDTO> allDTOs = allowanceService.show();

            SalaryLedger newLedger = new SalaryLedger();
            newLedger.setSalaryLedgerDate(salaryLedgerDate);
                newLedger.setEmployee(employee);
                newLedger.setNationalPensionAmount(BigDecimal.ZERO);
                newLedger.setPrivateSchoolPensionAmount(BigDecimal.ZERO);
                newLedger.setHealthInsurancePensionAmount(BigDecimal.ZERO);
                newLedger.setEmploymentInsuranceAmount(BigDecimal.ZERO);
                newLedger.setLongTermCareInsurancePensionAmount(BigDecimal.ZERO);
                newLedger.setIncomeTaxPensionAmount(BigDecimal.ZERO);
                newLedger.setLocalIncomeTaxPensionAmount(BigDecimal.ZERO);
                newLedger.setTotalDeductionAmount(BigDecimal.ZERO);
                newLedger.setTotalSalaryAmount(BigDecimal.ZERO);
                newLedger.setNetPayment(BigDecimal.ZERO);

            List<SalaryLedgerAllowance> allowances = allDTOs.stream()
                    .map(dtoItem -> new SalaryLedgerAllowance(dtoItem.getName(), BigDecimal.ZERO))
                    .collect(Collectors.toList());

            newLedger.setAllowance(allowances);  // 변환된 수당 리스트 설정

            SalaryLedger salaryLedger = salaryLedgerRepository.save(newLedger);

            return SalaryLedgerDTO.create(salaryLedger);

        }
        return result;
    }

    /**
     * 급여입력 결산처리
     * @param salaryLedgerId
     * @return
     */
    @Override
    public FinalizedDTO salaryLedgerDeadLineOn(Long salaryLedgerId) {
        SalaryLedger salaryLedger = salaryLedgerRepository.findById(salaryLedgerId).orElseThrow(
                () -> new NoSuchElementException("해당하는 급여입력 정보가없습니다."));

        salaryLedger.setFinalized(true);

        SalaryLedger updateSalaryLedger = salaryLedgerRepository.save(salaryLedger);

        return new FinalizedDTO(updateSalaryLedger.isFinalized(),"급여입력 마감처리가 완료되었습니다.");
    }

    @Override
    public FinalizedDTO salaryLedgerDeadLineOff(Long salaryLedgerId) {
        SalaryLedger salaryLedger = salaryLedgerRepository.findById(salaryLedgerId).orElseThrow(
                () -> new NoSuchElementException("해당하는 급여입력 정보가없습니다."));

        salaryLedger.setFinalized(false);

        SalaryLedger updateSalaryLedger = salaryLedgerRepository.save(salaryLedger);

        return new FinalizedDTO(updateSalaryLedger.isFinalized(),"급여입력 마감처리가 취소되었습니다.");
    }

//    @Override
//    public SalaryLedgerDTO salaryLedgerCalculator(Long salaryLedgerId) {
//
//        SalaryLedger salaryLedger = salaryLedgerRepository.findById(salaryLedgerId).orElseThrow(
//                () -> new NoSuchElementException("해당하는 급여입력 정보가없습니다."));
//
//        if(salaryLedger.isFinalized()) {
//            throw new RuntimeException("급여정보가 마감처리 되었습니다.");
//        }
//
//
//        Salary salary = salaryRepository.findByEmployeeId(salaryLedger.getEmployee().getId());
//        Employee employee = salaryLedger.getEmployee();
//
//
//        InsurancePensionCalculatorDTO calculatorDTO = new InsurancePensionCalculatorDTO(salary.getSalaryStep().getId(),employee.getPosition().getId());
//
//        BigDecimal healthInsurancePensionAmount = healthInsurancePensionService.calculator(calculatorDTO);
//
//        BigDecimal nationalPensionAmount = nationalPensionService.calculator(calculatorDTO);
//
//        BigDecimal employmentInsurancePensionAmount = employmentInsurancePensionService.calculator(calculatorDTO);
//
//        LongTermCareInsurancePension longTermCareInsurancePension = longTermCareInsurancePensionRepository.findByCode(salary.getLongTermCareInsurancePensionCode());
//
//        LongTermCareInsuranceCalculatorDTO longTermDto = new LongTermCareInsuranceCalculatorDTO(healthInsurancePensionAmount,
//                longTermCareInsurancePension.getId());
//
//        BigDecimal LongTermCareInsurancePensionAmount = longTermCareInsurancePensionService.calculator(longTermDto);
//
//        BigDecimal salaryAmount =  positionSalaryStepRepository.getSalaryAmount(employee.getPosition().getId(),salary.getSalaryStep().getId());
//
//        BigDecimal incomeTaxPensionAmount = incomeTaxService.incomeTaxCalculator(salaryAmount.multiply(BigDecimal.valueOf(12)));
//
//        BigDecimal localIncomeTaxPensionAmount = incomeTaxPensionAmount.multiply(BigDecimal.valueOf(0.1)).setScale(0, BigDecimal.ROUND_HALF_UP);
//
//        // 직급,호봉별 표준 수당 리스트 변환 후 설정
//        List<SalaryLedgerAllowanceDTO> allowanceDTOs = positionSalaryStepRepository
//                .getSalaryAllowance(employee.getId(), salary.getSalaryStep().getId());
//
//
//        salaryLedger.setNationalPensionAmount(nationalPensionAmount);
//        salaryLedger.setPrivateSchoolPensionAmount(BigDecimal.ZERO);
//        salaryLedger.setHealthInsurancePensionAmount(healthInsurancePensionAmount);
//        salaryLedger.setEmploymentInsuranceAmount(employmentInsurancePensionAmount);
//        salaryLedger.setLongTermCareInsurancePensionAmount(LongTermCareInsurancePensionAmount);
//        salaryLedger.setIncomeTaxPensionAmount(incomeTaxPensionAmount);
//        salaryLedger.setLocalIncomeTaxPensionAmount(localIncomeTaxPensionAmount);
//
//        List<SalaryLedgerAllowance> originList  = salaryLedger.getAllowance();
//
//        for(int i = 0; i < allowanceDTOs.size(); i++ ){
//            SalaryLedgerAllowance salaryLedgerAllowance = originList.get(i);
//            salaryLedgerAllowance.setAmount(allowanceDTOs.get(i).getAllowanceAmount());
//        }
//
//
//
//
//
//        //////////////////////////////////////
//        List<AllowanceShowDTO> allowanceList = allowanceService.show();
//
//        List<SalaryLedgerAllowance> salaryLedgerAllowances = salaryLedger.getAllowance();
//
//        // 총 급여
//        BigDecimal totalSalaryAmount = BigDecimal.ZERO;
//        // 비과세 금액
//        BigDecimal nonTaxableAmount = BigDecimal.ZERO;
//        // 과세 금액
//        BigDecimal taxableAmount = BigDecimal.ZERO;
//
//        for(int i = 0; salaryLedgerAllowances.size() > i; i++ ) {
//            BigDecimal amount = salaryLedgerAllowances.get(i).getAmount();
//
//            totalSalaryAmount = totalSalaryAmount.add(amount);
//            if(allowanceList.get(i).getTaxType().equals(TaxType.TAXABLE)) {
//                taxableAmount = taxableAmount.add(amount);
//            }
//            else {
//                nonTaxableAmount = allowanceService.taxableCalculator(nonTaxableAmount.add(amount), i + 1L);
//            }
//        }
//
//        // 공제총액
//        BigDecimal totalDeductions = BigDecimal.ZERO;
//
//        // 과세소득
//        BigDecimal taxableIncome = totalSalaryAmount.subtract(nonTaxableAmount);
//
//        // 소득세
//        BigDecimal incomeTaxAmount = incomeTaxService.incomeTaxCalculator(taxableIncome);
//        totalDeductions = totalDeductions.add(incomeTaxAmount);
//
//        // 지방소득세
//        BigDecimal localIncomeTaxAmount = incomeTaxAmount.multiply(BigDecimal.valueOf(0.1)).setScale(0, BigDecimal.ROUND_HALF_UP);
//        totalDeductions = totalDeductions.add(localIncomeTaxAmount);
//
//
/////////////////////////////////////////////////////////////////
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//        SalaryLedger updateSalaryLedger = salaryLedgerRepository.save(salaryLedger);
//
//
//
//        return SalaryLedgerDTO.create(updateSalaryLedger);
//
//
//    }

    @Override
    public SalaryLedgerDTO salaryLedgerCalculator(Long salaryLedgerId) {

        SalaryLedger salaryLedger = salaryLedgerRepository.findById(salaryLedgerId).orElseThrow(
                () -> new NoSuchElementException("해당하는 급여입력 정보가없습니다."));

        if(salaryLedger.isFinalized()) {
            throw new RuntimeException("급여정보가 마감처리 되었습니다.");
        }

        Salary salary = salaryRepository.findByEmployeeId(salaryLedger.getEmployee().getId());
        Employee employee = salaryLedger.getEmployee();


        // 직급,호봉별 표준 수당 리스트 변환 후 설정
        List<SalaryLedgerAllowanceDTO> allowanceDTOs = positionSalaryStepRepository
                .getSalaryAllowance(employee.getId(), salary.getSalaryStep().getId());

        List<SalaryLedgerAllowance> originList  = salaryLedger.getAllowance();

        for(int i = 0; i < allowanceDTOs.size(); i++ ){
            SalaryLedgerAllowance salaryLedgerAllowance = originList.get(i);
            salaryLedgerAllowance.setAmount(allowanceDTOs.get(i).getAllowanceAmount());
        }

        return salaryLedgerSetting(SalaryLedgerDTO.create(salaryLedger),salaryLedger);


    }

    @Override
    public SalaryLedgerDTO updateSalaryLedger(SalaryLedgerDTO dto) {
        SalaryLedger salaryLedger = salaryLedgerRepository.findById(dto.getLedgerId()).orElseThrow(
                () -> new NoSuchElementException("해당하는 급여입력 정보가 없습니다."));

        if(salaryLedger.isFinalized()) {
            throw new RuntimeException("급여정보가 마감처리 되었습니다.");
        }

        return salaryLedgerSetting(dto,salaryLedger);
    }

    private SalaryLedgerDTO salaryLedgerSetting(SalaryLedgerDTO dto,SalaryLedger salaryLedger) {

        Salary salary = salaryRepository.findByEmployeeId(salaryLedger.getEmployee().getId());

        List<AllowanceShowDTO> allowanceList = allowanceService.show();

        List<SalaryLedgerAllowance> salaryLedgerAllowances = dto.getAllowance();

        /**
         * 총급여 = 기본급 + 과세수당 + 비과세수당
         *
         * 과세소득 = 총급여 - 비과세 수당
         *
         * 공제계산은 과세소득으로
         *
         * Total salary = Base salary + Taxable allowance + Non-taxable allowance
         *
         * Taxable income = Total salary - Non-taxable allowance
         */

        // 총 급여
        BigDecimal totalSalaryAmount = BigDecimal.ZERO;
        // 비과세 금액
        BigDecimal nonTaxableAmount = BigDecimal.ZERO;
        // 과세 금액
        BigDecimal taxableAmount = BigDecimal.ZERO;

        for(int i = 0; salaryLedgerAllowances.size() > i; i++ ) {
            BigDecimal amount = salaryLedgerAllowances.get(i).getAmount();

            totalSalaryAmount = totalSalaryAmount.add(amount);
            if(allowanceList.get(i).getTaxType().equals(TaxType.TAXABLE)) {
                taxableAmount = taxableAmount.add(amount);
            }
            else {
                nonTaxableAmount = allowanceService.taxableCalculator(nonTaxableAmount.add(amount), i + 1L);
            }
        }

        // 공제총액
        BigDecimal totalDeductions = BigDecimal.ZERO;

        // 과세소득
        BigDecimal taxableIncome = totalSalaryAmount.subtract(nonTaxableAmount);

        // 소득세
        BigDecimal incomeTaxAmount = incomeTaxService.incomeTaxCalculator(taxableIncome);
        totalDeductions = totalDeductions.add(incomeTaxAmount);

        // 지방소득세
        BigDecimal localIncomeTaxAmount = incomeTaxAmount.multiply(BigDecimal.valueOf(0.1)).setScale(0, BigDecimal.ROUND_HALF_UP);
        totalDeductions = totalDeductions.add(localIncomeTaxAmount);

        // 여기부터 공제 계산

        // 건강보험
        BigDecimal healthInsurancePensionAmount = healthInsurancePensionService.calculator(taxableIncome);
        totalDeductions = totalDeductions.add(healthInsurancePensionAmount);

        // 국민연금
        BigDecimal nationalPensionAmount = nationalPensionService.calculator(taxableIncome);
        totalDeductions = totalDeductions.add(nationalPensionAmount);

        // 고용보험
        BigDecimal employmentInsurancePensionAmount = employmentInsurancePensionService.calculator(taxableIncome);
        totalDeductions = totalDeductions.add(employmentInsurancePensionAmount);

        LongTermCareInsurancePension longTermCareInsurancePension = longTermCareInsurancePensionRepository.findByCode(salary.getLongTermCareInsurancePensionCode());

        LongTermCareInsuranceCalculatorDTO longTermDto = new LongTermCareInsuranceCalculatorDTO(healthInsurancePensionAmount,
                longTermCareInsurancePension.getId());

        // 장기요양보험
        BigDecimal LongTermCareInsurancePensionAmount = longTermCareInsurancePensionService.calculator(longTermDto);
        totalDeductions = totalDeductions.add(LongTermCareInsurancePensionAmount);


        // 차인지급액
        BigDecimal netPayment = totalSalaryAmount.subtract(totalDeductions);

        salaryLedger.setAllowance(dto.getAllowances().stream().map(
                one -> new SalaryLedgerAllowance(one.getName(),one.getAmount())).collect(Collectors.toList()));
        salaryLedger.setNationalPensionAmount(nationalPensionAmount);
        salaryLedger.setPrivateSchoolPensionAmount(BigDecimal.ZERO);
        salaryLedger.setHealthInsurancePensionAmount(healthInsurancePensionAmount);
        salaryLedger.setEmploymentInsuranceAmount(employmentInsurancePensionAmount);
        salaryLedger.setLongTermCareInsurancePensionAmount(LongTermCareInsurancePensionAmount);
        salaryLedger.setIncomeTaxPensionAmount(incomeTaxAmount);
        salaryLedger.setLocalIncomeTaxPensionAmount(localIncomeTaxAmount);
        salaryLedger.setTotalSalaryAmount(totalSalaryAmount);
        salaryLedger.setTotalDeductionAmount(totalDeductions);
        salaryLedger.setNetPayment(netPayment);


        SalaryLedger updateSalaryLedger = salaryLedgerRepository.save(salaryLedger);

        return SalaryLedgerDTO.create(updateSalaryLedger);
    }


}

