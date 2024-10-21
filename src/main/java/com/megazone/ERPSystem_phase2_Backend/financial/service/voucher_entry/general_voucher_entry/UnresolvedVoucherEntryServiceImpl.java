package com.megazone.ERPSystem_phase2_Backend.financial.service.voucher_entry.general_voucher_entry;

import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.dto.*;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.UnresolvedVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.enums.ApprovalStatus;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.enums.VoucherKind;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.enums.VoucherType;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.UnresolvedSaleAndPurchaseVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.account_subject.AccountSubjectRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.client.ClientRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.company.CompanyRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.general_voucher_entry.unresolvedVoucher.UnresolvedVoucherRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.sales_and_purchase_voucher_entry.unresolveSaleAndPurchaseVoucher.UnresolvedSaleAndPurchaseVoucherRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Employee;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Permission;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.enums.UserPermission;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Employee.EmployeeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Transactional
public class UnresolvedVoucherEntryServiceImpl implements UnresolvedVoucherEntryService {

    private final ResolvedVoucherService resolvedVoucherService;
    private final UnresolvedVoucherRepository unresolvedVoucherRepository;
    private final AccountSubjectRepository accountSubjectRepository;
    private final EmployeeRepository employeeRepository;
    private final UnresolvedSaleAndPurchaseVoucherRepository unresolvedSaleAndPurchaseVoucherRepository;
    private final ClientRepository clientRepository;

    // 현금 자동분개 시 필요한 계정과목 코드
    private final String cashAccountCode = "101";
    private final String cashTransactionDescription = "현금";

    // 거래처 레포지토리
    // 적요 레포지토리
    // 계정과목 레포지토리
    // 담당자 레포지토리


    /**
//     * 일반전표 등록 메소드
//     * 일반 전표입력에서 전표 정보 입력시 일반전표를 생성하지않고 승인되지않은 전표 생성
//     * @param dtoList 사용자가 일반전표 입력시 작성한 정보를 담은 전송객체 List
//     * @return 생성된 미결전표 반환
//     *
//     * 유저 회사 Entity 필요
//     * 거래처 Entity 필요
//     * 전표 담당자 Entity 필요
//     * 적요 Entity 필요
//     */


    @Override
    public List<UnresolvedVoucher> unresolvedVoucherEntry(List<UnresolvedVoucherEntryDTO> dtoList) {

        List<UnresolvedVoucher> unresolvedVoucherList = new ArrayList<UnresolvedVoucher>();
        List<UnresolvedVoucher> savedVoucherList = new ArrayList<UnresolvedVoucher>();
        // 검증 로직
        // 입금&출금 전표인지, 차변&대변 전표인지 확인
        try {
            // 전표 번호 부여
            Long newVoucherNum = null;
            LocalDate currentDate = dtoList.get(0).getVoucherDate();
            LocalDateTime nowTime = LocalDateTime.now();


            newVoucherNum = CreateUnresolvedVoucherNumber(currentDate,dtoList.get(0).getVoucherKind());

            if(depositAndWithdrawalUnresolvedVoucherTypeCheck(dtoList.get(0))) {

                for (UnresolvedVoucherEntryDTO dto : dtoList) {
                    if(dto.getAccountSubjectCode().equals(cashAccountCode)) {
                        throw new IllegalArgumentException("입금 출금 전표는 현금계정과목을 사용할 수 없습니다.");
                    }

                    UnresolvedVoucher savedVoucher = createUnresolvedVoucher(dto,newVoucherNum,nowTime);
                    unresolvedVoucherList.add(savedVoucher);
                    // 입금,출금 전표인 경우 현금 계정과목 자동분개 처리
                    if(depositAndWithdrawalUnresolvedVoucherTypeCheck(dto)) {
                        unresolvedVoucherList.add(createUnresolvedVoucher(autoCreateUnresolvedVoucherDto(dto)
                                ,newVoucherNum,nowTime));
                    }
                }
            }
            else {
                BigDecimal totalDebit = BigDecimal.ZERO;
                BigDecimal totalCredit = BigDecimal.ZERO;

                // 전체 대차차액 검증
                for (UnresolvedVoucherEntryDTO dto : dtoList) {
                    totalDebit = totalDebit.add(dto.getDebitAmount());
                    totalCredit = totalCredit.add(dto.getCreditAmount());
                }

                if(totalDebit.compareTo(totalCredit) != 0) {
                    throw new IllegalArgumentException("저장할 전표에 차액이 발생하였습니다.");
                }

                // 한 거래에 같은 전표 번호 부여.
                for (UnresolvedVoucherEntryDTO dto : dtoList) {
                    UnresolvedVoucher savedVoucher = createUnresolvedVoucher(dto,newVoucherNum,nowTime);
                    unresolvedVoucherList.add(savedVoucher);
                }
            }

                     for(int i = 0; i < unresolvedVoucherList.size(); i++) {
                         savedVoucherList.add(unresolvedVoucherRepository.save(unresolvedVoucherList.get(i)));
                     }
        }
        catch (IllegalArgumentException e) {
            e.getStackTrace();
            //throw new RuntimeException(e.getMessage(), e);
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e.getMessage());
        }
        return savedVoucherList; // 생성된 미결전표 반환
    }

    /**
     * 사용자가 입력한 정보를 통해 미결전표 객체 생성
     * @param dto 사용자가 입력한 미결전표 정보 객체
     * @return 생성된 미결전표
     */
    @Override
    public UnresolvedVoucher createUnresolvedVoucher(UnresolvedVoucherEntryDTO dto, Long voucherNum, LocalDateTime nowTime) {
        UnresolvedVoucher unresolvedVoucher = UnresolvedVoucher.builder()
                .accountSubject(accountSubjectRepository.findByCode(dto.getAccountSubjectCode()).orElseThrow(
                        () -> new IllegalArgumentException("해당하는 코드의 계정과목이 없습니다.")))
                .client(clientRepository.findByCode(dto.getClientCode()).orElseThrow(
                        () -> new IllegalArgumentException("해당하는 코드의 거래처가 없습니다.")))
                .voucherManager(employeeRepository.findById(dto.getVoucherManagerId()).orElseThrow(
                        () -> new IllegalArgumentException("해당하는 사원이 없습니다.")))
                .transactionDescription(dto.getTransactionDescription())
                .voucherNumber(voucherNum)
                .voucherType(dto.getVoucherType())
                .debitAmount(dto.getDebitAmount())
                .creditAmount(dto.getCreditAmount())
                .voucherDate(dto.getVoucherDate())
                .voucherRegistrationTime(nowTime)
                .approvalStatus(ApprovalStatus.PENDING)
                .voucherKind(dto.getVoucherKind())
                .build();
        return unresolvedVoucher;
    }

    /**
     * 사용자가 입력한 전표 타입 체크 메소드
     * @param dto 사용자가 입력한 미결전표 정보 객체
     * @return
     */
    @Override
    public boolean depositAndWithdrawalUnresolvedVoucherTypeCheck(UnresolvedVoucherEntryDTO dto) {
        VoucherType voucherType = dto.getVoucherType();
        if(voucherType == VoucherType.DEPOSIT || voucherType == VoucherType.WITHDRAWAL) {
            return true;
        }
        return false;
    }

    /**
     * 미결전표가 생성될 때 전표의 번호를 부여하는 메소드
     * @param voucherDate 전표날짜
     * @return 입금,출금,차변,대변 조건에 맞게 번호 부여
     */
    @Override
    public Long CreateUnresolvedVoucherNumber(LocalDate voucherDate, VoucherKind voucherKind) {
        // 해당 조건의 날짜에 해당하는 마지막 미결전표 Entity 가져오기

        Long lastVoucherNumber = null;
        Long StartNumber = null;

        Optional<?> voucherBox = Optional.empty();

        switch (voucherKind) {
            case GENERAL:
                voucherBox = unresolvedVoucherRepository.findFirstByVoucherDateAndVoucherKindEqualsOrderByIdDesc(voucherDate, VoucherKind.GENERAL);
                StartNumber = 1L;
                break;
            case SALE_AND_PURCHASE:
                voucherBox = unresolvedSaleAndPurchaseVoucherRepository.findFirstByVoucherDateOrderByIdDesc(voucherDate);
                StartNumber = 50000L;
                break;
        }

        if(voucherBox.isEmpty()) {
            return StartNumber;
        }
        else {
            Object voucher = voucherBox.get();

            if (voucher instanceof UnresolvedVoucher) {
                lastVoucherNumber = ((UnresolvedVoucher) voucher).getVoucherNumber();
            } else if (voucher instanceof UnresolvedSaleAndPurchaseVoucher) {
                lastVoucherNumber = ((UnresolvedSaleAndPurchaseVoucher) voucher).getVoucherNumber();
            }
            return lastVoucherNumber + 1;
        }
    }

    /**
     * 입금 출금 전표를 등록할때 현금 계정과목으로 자동분개하는 메소드
     * @param dto 입금,출금 전표정보 객체
     * @return 입금, 출금 전표를 깊은복사 한후 새로운 DTO객체 반환
     * @throws CloneNotSupportedException 깊은복사가 되지 않을때 오류전달
     */

    @Override
    public UnresolvedVoucherEntryDTO autoCreateUnresolvedVoucherDto(UnresolvedVoucherEntryDTO dto) throws CloneNotSupportedException {
        UnresolvedVoucherEntryDTO autoCreateDto;
        autoCreateDto = dto.clone();
        autoCreateDto.setDebitAmount(BigDecimal.ZERO);
        autoCreateDto.setCreditAmount(BigDecimal.ZERO);

        if(dto.getVoucherType() == VoucherType.DEPOSIT) {
            autoCreateDto.setDebitAmount(dto.getCreditAmount());
        }
        else {
            autoCreateDto.setCreditAmount(dto.getDebitAmount());
        }
        autoCreateDto.setAccountSubjectCode(cashAccountCode);
        autoCreateDto.setTransactionDescription(cashTransactionDescription);

        return autoCreateDto;
    }

    /**
     * 날짜조건에 해당하는 모든 전표조회
     * @param date 사용자가 작성한 날짜 조건 
     * @return 해당 날짜의 모든 전표 반환
     */
    @Override
    public List<UnresolvedVoucher> unresolvedVoucherAllSearch(LocalDate date) {
        List<UnresolvedVoucher> unresolvedVoucherList = new ArrayList<UnresolvedVoucher>();
        try {
            unresolvedVoucherList = unresolvedVoucherRepository.findByVoucherDateOrderByVoucherNumberAsc(date);
            if(unresolvedVoucherList.isEmpty()) {
                throw new NoSuchElementException("해당 날짜에 등록된 미결전표가 없습니다.");
            }
        }
        catch (NoSuchElementException e) {
            e.getStackTrace();
        }
        return unresolvedVoucherList;
    }

    /**
     * 날짜조건에 해당하는 검색조건 전표번호 모두 삭제
     * @dto 삭제할 전표의 날짜, 전표번호List, 당당자 정보 객체
     */
    @Override
    public List<Long> deleteUnresolvedVoucher(UnresolvedVoucherDeleteDTO dto) {

        // 전표에 담당자 이거나, 승인권자면 삭제가능 << 기능구현 필요

        List<Long> deleteVouchers = new ArrayList<>();
        try {
            if(true) { // 전표의 담당자 이거나, 승인권자면 삭제가능 << 기능구현 필요
                deleteVouchers.addAll(unresolvedVoucherRepository.deleteVoucherByManager(dto));
                if(deleteVouchers.isEmpty()) {
                    throw new NoSuchElementException("검색조건에 해당하는 미결전표가 없습니다.");
                }
            }
        }
        catch (Exception e) {
            e.getStackTrace();
        }
        return deleteVouchers;
    }

    /**
     * 차변, 대변 합계 공통 로직
     */
    @Override
    public BigDecimal calculateTotalAmount(List<UnresolvedVoucher> vouchers, Function<UnresolvedVoucher, BigDecimal> amount) {
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (UnresolvedVoucher voucher : vouchers) {
            totalAmount = totalAmount.add(amount.apply(voucher));
        }

        return totalAmount;
    }
    @Override
    public BigDecimal totalDebit(List<UnresolvedVoucher> vouchers) {
        return calculateTotalAmount(vouchers, UnresolvedVoucher::getDebitAmount);
    }
    @Override
    public BigDecimal totalCredit(List<UnresolvedVoucher> vouchers) {
        return calculateTotalAmount(vouchers, UnresolvedVoucher::getCreditAmount);
    }

    @Override
    public List<UnresolvedVoucher> voucherApprovalProcessing(UnresolvedVoucherApprovalDTO dto) {
//        List<UnresolvedVoucher> unresolvedVoucherList = unresolvedVoucherRepository.findAll(); // 초기 데이터 등록용

        if(dto.getApprovalStatus().equals(ApprovalStatus.PENDING)) {
            throw new RuntimeException("승인 대기 상태로는 변경할 수 없습니다.");
        }

        Employee employee = employeeRepository.findById(dto.getApprovalManagerId())
                .orElseThrow(() -> new RuntimeException("해당하는 유저가 없습니다."));

        if((!employee.getUsers().getPermission().getGeneralVoucherPermission().equals(UserPermission.ADMIN)) && !dto.isSuperManager()) {
            throw new RuntimeException("권한이 없습니다.");
        }

        List<UnresolvedVoucher> unresolvedVoucherList = unresolvedVoucherRepository.findApprovalTypeVoucher(dto);


        if(!unresolvedVoucherList.isEmpty())
        {
            unresolvedVoucherList.stream().forEach(
                    unresolvedVoucher -> {unresolvedVoucher.setApprovalStatus(dto.getApprovalStatus());
                    });
            resolvedVoucherService.resolvedVoucherEntry(unresolvedVoucherList);
        }
        else {
            throw new RuntimeException("해당하는 전표가 없습니다.");
        }

        return unresolvedVoucherList;
    }

    @Override
    public UnresolvedVoucherShowAllDTO unresolvedVoucherApprovalSearch(LocalDate date) {


        List<UnresolvedVoucher> unresolvedVoucherList =
                unresolvedVoucherRepository.ApprovalAllSearch(date);

        List<UnresolvedVoucherShowDTO> showDtos = unresolvedVoucherList.stream().map(
                UnresolvedVoucherShowDTO::create).toList();

        UnresolvedVoucherShowAllDTO showAllDto = UnresolvedVoucherShowAllDTO.create(
                date,
                showDtos,
                BigDecimal.ZERO,  // 현재잔액 기능 구현필요 <<<<
                totalDebit(unresolvedVoucherList),
                totalCredit(unresolvedVoucherList)
        );
        return showAllDto;
    }

}
