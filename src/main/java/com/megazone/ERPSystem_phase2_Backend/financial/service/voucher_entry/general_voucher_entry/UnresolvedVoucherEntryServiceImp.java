package com.megazone.ERPSystem_phase2_Backend.financial.service.voucher_entry.general_voucher_entry;

import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.dto.GeneralVoucherEntryDto;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.UnresolvedVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.enums.ApprovalStatus;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.enums.VoucherType;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.account_subject.AccountSubject.AccountSubjectRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.general_voucher_entry.resolvedVoucher.ResolvedVoucherRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.general_voucher_entry.unresolvedVoucher.UnresolvedVoucherRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class UnresolvedVoucherEntryServiceImp implements UnresolvedVoucherEntryService {

    private final ResolvedVoucherRepository resolvedVoucherRepository;
    private final UnresolvedVoucherRepository unresolvedVoucherRepository;
    private final AccountSubjectRepository accountSubjectRepository;
    
    // 현금 자동분개 시 필요한 계정과목 코드
    private final String cashAccountCode = "101";

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
    public List<UnresolvedVoucher> unresolvedVoucherEntry(List<GeneralVoucherEntryDto> dtoList) {

        List<UnresolvedVoucher> unresolvedVoucherList = new ArrayList<UnresolvedVoucher>();
        // 검증 로직
        // 입금&출금 전표인지, 차변&대변 전표인지 확인
        try {
            // 전표 번호 부여
            String newVoucherNum = CreateUnresolvedVoucherNumber(dtoList.get(0).getVoucherDate());

            if(depositAndWithdrawalUnresolvedVoucherTypeCheck(dtoList.get(0))) {
                GeneralVoucherEntryDto unresolvedVoucherDto = dtoList.get(0);
                UnresolvedVoucher savedVoucher = createUnresolvedVoucher(unresolvedVoucherDto,newVoucherNum);
                unresolvedVoucherList.add(savedVoucher);
                // 입금,출금 전표인 경우 현금 계정과목 자동분개 처리
                if(depositAndWithdrawalUnresolvedVoucherTypeCheck(unresolvedVoucherDto)) {
                    unresolvedVoucherList.add(createUnresolvedVoucher(autoCreateUnresolvedVoucherDto(unresolvedVoucherDto),newVoucherNum));
                }
            }
            else {
                BigDecimal totalDebit = BigDecimal.ZERO;
                BigDecimal totalCredit = BigDecimal.ZERO;

                // 전체 대차차액 검증
                for (GeneralVoucherEntryDto dto : dtoList) {
                    totalDebit = totalDebit.add(dto.getDebitAmount());
                    totalCredit = totalCredit.add(dto.getCreditAmount());
                }

                if(!totalDebit.equals(totalCredit)) {
                    throw new IllegalArgumentException("저장할 전표에 차액이 발생하였습니다.");
                }

                // 한 거래에 같은 전표 번호 부여.
                for (GeneralVoucherEntryDto dto : dtoList) {
                    UnresolvedVoucher savedVoucher = createUnresolvedVoucher(dto,newVoucherNum);
                    unresolvedVoucherList.add(savedVoucher);
                }
            }

            for(UnresolvedVoucher savedVoucher : unresolvedVoucherList) {
                unresolvedVoucherRepository.save(savedVoucher);
            }
        }
        catch (IllegalArgumentException e) {
            e.getStackTrace();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        return unresolvedVoucherList; // 생성된 미결전표 반환
    }

    /**
     * 사용자가 입력한 정보를 통해 미결전표 객체 생성
     * @param dto 사용자가 입력한 미결전표 정보 객체
     * @return 생성된 미결전표
     */
    @Override
    public UnresolvedVoucher createUnresolvedVoucher(GeneralVoucherEntryDto dto, String voucherNum) {
        UnresolvedVoucher unresolvedVoucher = UnresolvedVoucher.builder()
                .userCompanyId(dto.getUserCompany())
                .accountSubject(dto.getAccountSubject())
                .vendor(dto.getVendor())
                .approvalManager(dto.getApprovalManager())
                .voucherManager(dto.getVoucherManager())
                .description(dto.getDescription())
                .transactionDescription(dto.getTransactionDescription())
                .voucherNumber(voucherNum)
                .voucherType(dto.getVoucherType())
                .debitAmount(dto.getDebitAmount())
                .creditAmount(dto.getCreditAmount())
                .voucherDate(dto.getVoucherDate())
                .voucherRegistrationTime(LocalDateTime.now())
                .approvalStatus(ApprovalStatus.PENDING)
                .build();
        return unresolvedVoucher;
    }

    /**
     * 사용자가 입력한 전표 타입 체크 메소드
     * @param dto 사용자가 입력한 미결전표 정보 객체
     * @return
     */
    @Override
    public boolean depositAndWithdrawalUnresolvedVoucherTypeCheck(GeneralVoucherEntryDto dto) {
        VoucherType voucherType = dto.getVoucherType();
        if(voucherType.equals(VoucherType.DEPOSIT) || voucherType.equals(VoucherType.WITHDRAWAL)) {
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
    public String CreateUnresolvedVoucherNumber(LocalDate voucherDate) {
        // 해당 조건의 날짜에 해당하는 마지막 미결전표 Entity 가져오기
        UnresolvedVoucher lastUnresolvedVoucher = unresolvedVoucherRepository.findFirstByVoucherDateOrderByIdDesc(voucherDate).orElse(null);

        if(lastUnresolvedVoucher == null) {
            return "1";
        }
        else {
            int voucherNum = Integer.parseInt(lastUnresolvedVoucher.getVoucherNumber()) + 1;
            return String.valueOf(voucherNum);
        }
    }

    /**
     * 입금 출금 전표를 등록할때 현금 계정과목으로 자동분개하는 메소드
     * @param dto 입금,출금 전표정보 객체
     * @return 입금, 출금 전표를 깊은복사 한후 새로운 DTO객체 반환
     * @throws CloneNotSupportedException 깊은복사가 되지 않을때 오류전달
     */

    @Override
    public GeneralVoucherEntryDto autoCreateUnresolvedVoucherDto(GeneralVoucherEntryDto dto) throws CloneNotSupportedException {
        GeneralVoucherEntryDto autoCreateDto = new GeneralVoucherEntryDto();
        autoCreateDto = dto.clone();
        autoCreateDto.setDebitAmount(BigDecimal.ZERO);
        autoCreateDto.setCreditAmount(BigDecimal.ZERO);

        if(dto.getVoucherType().equals(VoucherType.DEPOSIT)) {
            autoCreateDto.setCreditAmount(dto.getDebitAmount());
        }
        else {
            autoCreateDto.setDebitAmount(dto.getCreditAmount());
        }
        autoCreateDto.setAccountSubject(Long.parseLong(cashAccountCode));

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
            System.out.println(unresolvedVoucherList.toString());
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
     * @param date 사용자가 지정한 검색 날짜 조건
     * @param voucherNumberList 사용자가 선택한 삭제할 전표 번호 리스트
     * @return 삭제된 모든 미결전표 반환
     */
    @Override
    public List<UnresolvedVoucher> deleteUnresolvedVoucher(LocalDate date, List<String> voucherNumberList) {
        List<UnresolvedVoucher> deleteVouchers = new ArrayList<>();

        try {
            for(String voucherNum : voucherNumberList) {
                deleteVouchers.addAll(unresolvedVoucherRepository.deleteAllByVoucherDateAndVoucherNumber(date,voucherNum));
            }

            if(deleteVouchers.isEmpty()) {
                throw new NoSuchElementException("검색조건에 해당하는 미결전표가 없습니다.");
            }
        }
        catch (Exception e) {
            e.getStackTrace();
        }
        return deleteVouchers;
    }

    /**
     * 미결전표 수정 메소드
     * 사용자가 선택한 전표 수정 후 대차차액을 비교하여 차액이 0이면 수정완료
     * 차액이 0이아니라면 취소
     * @param dto 사용자가 수정한 미결전표 전송 객체
     * @return
     */
    /**
     * 미결전표 수정 메소드
     * 사용자가 선택한 전표 수정 후 대차차액을 비교하여 차액이 0이면 수정완료
     * 차액이 0이아니라면 취소
     * @param unVoucherId 사용자가 수정한 미결전표 ID
     * @param dto 사용자가 수정한 미결전표 정보
     * @return
     */

    public List<UnresolvedVoucher> updateUnresolvedVoucher(Long unVoucherId, GeneralVoucherEntryDto dto) {
        // 하나를 수정하는데 수정후에 대차차액 안맞으면 수정불가
        // 대차차액 맞으면 수정가능

        try {
            UnresolvedVoucher unresolvedVoucher = unresolvedVoucherRepository.findById(unVoucherId).orElseThrow
                    (() -> new IllegalArgumentException("해당하는 미결전표가 없습니다."));



        }
        catch (Exception e) {

        }
        return null;
    }
}
