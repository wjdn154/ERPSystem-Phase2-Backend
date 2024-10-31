package com.megazone.ERPSystem_phase2_Backend.hr.service.salary_ledger;

import com.megazone.ERPSystem_phase2_Backend.Integrated.model.dashboard.RecentActivity;
import com.megazone.ERPSystem_phase2_Backend.Integrated.model.dashboard.enums.ActivityType;
import com.megazone.ERPSystem_phase2_Backend.Integrated.model.notification.enums.ModuleType;
import com.megazone.ERPSystem_phase2_Backend.Integrated.model.notification.enums.NotificationType;
import com.megazone.ERPSystem_phase2_Backend.Integrated.model.notification.enums.PermissionType;
import com.megazone.ERPSystem_phase2_Backend.Integrated.repository.dashboard.RecentActivityRepository;
import com.megazone.ERPSystem_phase2_Backend.Integrated.service.notification.NotificationService;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.enums.ApprovalStatus;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
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
                newLedger.setIncomeTaxAmount(BigDecimal.ZERO);
                newLedger.setLocalIncomeTaxAmount(BigDecimal.ZERO);
                newLedger.setTotalDeductionAmount(BigDecimal.ZERO);
                newLedger.setTotalSalaryAmount(BigDecimal.ZERO);
                newLedger.setNetPayment(BigDecimal.ZERO);
                newLedger.setTaxableAmount(BigDecimal.ZERO);
                newLedger.setNonTaxableAmount(BigDecimal.ZERO);
                newLedger.setTaxableIncome(BigDecimal.ZERO);

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
                .getSalaryAllowance(employee.getPosition().getId(), salary.getSalaryStep().getId());


        List<AllowanceShowDTO> allDTOs = allowanceService.show();

        List<SalaryLedgerAllowance> allowances = allDTOs.stream()
                .map(dtoItem -> new SalaryLedgerAllowance(dtoItem.getName(), BigDecimal.ZERO))
                .collect(Collectors.toList());

        salaryLedger.setAllowance(allowances);

        for(int i = 0; i < allowanceDTOs.size(); i++ ){
            allowances.get(i).setAmount(allowanceDTOs.get(i).getAllowanceAmount());
        }

        SalaryLedgerDTO salaryLedgerDTO = SalaryLedgerDTO.create(salaryLedger);

        return salaryLedgerSetting(salaryLedgerDTO,salaryLedger);


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

//        List<SalaryLedgerAllowance> salaryLedgerAllowances = dto.getAllowances().stream().map(
//                one -> new SalaryLedgerAllowance(one.getName(),one.getAmount())).collect(Collectors.toList());
        List<SalaryLedgerAllowanceShowDTO> salaryLedgerAllowances = dto.getAllowances();

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
        salaryLedger.setIncomeTaxAmount(incomeTaxAmount);
        salaryLedger.setLocalIncomeTaxAmount(localIncomeTaxAmount);
        salaryLedger.setTotalSalaryAmount(totalSalaryAmount);
        salaryLedger.setTotalDeductionAmount(totalDeductions);
        salaryLedger.setTaxableAmount(taxableAmount);
        salaryLedger.setNonTaxableAmount(nonTaxableAmount);
        salaryLedger.setNetPayment(netPayment);
        salaryLedger.setTaxableIncome(taxableIncome);


        SalaryLedger updateSalaryLedger = salaryLedgerRepository.save(salaryLedger);

        return SalaryLedgerDTO.create(updateSalaryLedger);
    }

    @Override
    public List<PaymentStatusManagementShowDTO> showPaymentStatusManagement(PaymentStatusManagementSearchDTO dto) {
        List<PaymentStatusManagementShowDTO> results = new ArrayList<>(salaryLedgerRepository.showPaymentStatusManagement(dto));
        PaymentStatusManagementShowDTO totalPaymentStatus = new PaymentStatusManagementShowDTO();

        totalPaymentStatus.setId(null);
        totalPaymentStatus.setDescriptionName("합계");
        totalPaymentStatus.setNationalPensionAmount(BigDecimal.ZERO);
        totalPaymentStatus.setPrivateSchoolPensionAmount(BigDecimal.ZERO);
        totalPaymentStatus.setHealthInsurancePensionAmount(BigDecimal.ZERO);
        totalPaymentStatus.setEmploymentInsuranceAmount(BigDecimal.ZERO);
        totalPaymentStatus.setLongTermCareInsurancePensionAmount(BigDecimal.ZERO);
        totalPaymentStatus.setIncomeTaxAmount(BigDecimal.ZERO);
        totalPaymentStatus.setLocalIncomeTaxPensionAmount(BigDecimal.ZERO);
        totalPaymentStatus.setTotalSalaryAmount(BigDecimal.ZERO);
        totalPaymentStatus.setTaxableIncome(BigDecimal.ZERO);
        totalPaymentStatus.setTotalDeductionAmount(BigDecimal.ZERO);
        totalPaymentStatus.setNetPayment(BigDecimal.ZERO);
        totalPaymentStatus.setCount(0);

        for(int i = 0; i < results.size(); i++) {
            totalPaymentStatus.setNationalPensionAmount(totalPaymentStatus.getNationalPensionAmount().add(results.get(i).getNationalPensionAmount()));
            totalPaymentStatus.setPrivateSchoolPensionAmount(totalPaymentStatus.getPrivateSchoolPensionAmount().add(results.get(i).getPrivateSchoolPensionAmount()));
            totalPaymentStatus.setHealthInsurancePensionAmount(totalPaymentStatus.getHealthInsurancePensionAmount().add(results.get(i).getHealthInsurancePensionAmount()));
            totalPaymentStatus.setEmploymentInsuranceAmount(totalPaymentStatus.getEmploymentInsuranceAmount().add(results.get(i).getEmploymentInsuranceAmount()));
            totalPaymentStatus.setLongTermCareInsurancePensionAmount(totalPaymentStatus.getLongTermCareInsurancePensionAmount().add(results.get(i).getLongTermCareInsurancePensionAmount()));
            totalPaymentStatus.setIncomeTaxAmount(totalPaymentStatus.getIncomeTaxAmount().add(results.get(i).getIncomeTaxAmount()));
            totalPaymentStatus.setLocalIncomeTaxPensionAmount(totalPaymentStatus.getLocalIncomeTaxPensionAmount().add(results.get(i).getLocalIncomeTaxPensionAmount()));
            totalPaymentStatus.setTotalSalaryAmount(totalPaymentStatus.getTotalSalaryAmount().add(results.get(i).getTotalSalaryAmount()));
            totalPaymentStatus.setTaxableIncome(totalPaymentStatus.getTaxableIncome().add(results.get(i).getTaxableIncome()));
            totalPaymentStatus.setTotalDeductionAmount(totalPaymentStatus.getTotalDeductionAmount().add(results.get(i).getTotalDeductionAmount()));
            totalPaymentStatus.setNetPayment(totalPaymentStatus.getNetPayment().add(results.get(i).getNetPayment()));
            totalPaymentStatus.setCount(totalPaymentStatus.getCount() + results.get(i).getCount());

            List<SalaryLedgerAllowanceShowDTO> allowances = results.get(i).getAllowances();
            for(int j = 0; j < allowances.size(); j++) {
                if(i == 0) {
                    totalPaymentStatus.getAllowances().add(allowances.get(j));
                }
                else {
                    BigDecimal totalPaymentAmount = totalPaymentStatus.getAllowances().get(j).getAmount();
                    BigDecimal allowanceAmount = results.get(j).getAllowances().get(j).getAmount();
                    totalPaymentStatus.getAllowances().get(j).setAmount(totalPaymentAmount.add(allowanceAmount));
                }

            }
        }
        results.add(totalPaymentStatus);


        return results;
    }
}

