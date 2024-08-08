package com.megazone.ERPSystem_phase2_Backend.financial.service.basic_information_management.voucher_entry.general_voucher_entry;

import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.dto.GeneralVoucherEntryDto;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.UnresolvedVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.enums.VoucherType;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.general_voucher_entry.unresolvedVoucher.UnresolvedVoucherRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.service.voucher_entry.general_voucher_entry.UnresolvedVoucherEntryService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

@SpringBootTest
@Transactional
public class UnresolvedVoucherEntryServiceImpTest {
//    @Autowired
//    private ResolvedVoucherRepository resolvedVoucherRepository;
    @Autowired
    private UnresolvedVoucherRepository unresolvedVoucherRepository;
//    @Autowired
//    private AccountSubjectRepository accountSubjectRepository;

    @Autowired
    private UnresolvedVoucherEntryService unresolvedVoucherEntryService;

    @BeforeEach
    void testSetUp() {
//        resolvedVoucherRepository.deleteAll();
        unresolvedVoucherRepository.deleteAll();
//        accountSubjectRepository.deleteAll();

    }




    @Test
    public void saveTestMockOne() {
        // Given
        List<GeneralVoucherEntryDto> generalVoucherEntryDtoList = new ArrayList<GeneralVoucherEntryDto>();
        GeneralVoucherEntryDto dto = new GeneralVoucherEntryDto();
        dto.setUserCompanyId(1L);
        dto.setAccountSubjectId(1L);
        dto.setVendorId(1L);
        dto.setApprovalManagerId(1L);
        dto.setDescriptionId(1L);
        dto.setTransactionDescription("테스트 적요설명 1");
        dto.setVoucherType(VoucherType.DEPOSIT);
        dto.setDebitAmount(BigDecimal.valueOf(5000000));
        dto.setCreditAmount(BigDecimal.valueOf(5000000));
        dto.setVoucherDate(LocalDate.parse("2024-05-07"));
        dto.setVoucherRegistrationTime(LocalDateTime.now());
        generalVoucherEntryDtoList.add(dto);

        List<UnresolvedVoucher> unresolvedVoucherList =  unresolvedVoucherEntryService.unresolvedVoucherEntry(generalVoucherEntryDtoList);

        // When
        UnresolvedVoucher unresolvedVoucher = unresolvedVoucherRepository.findById(1L).get();

        // Then
        assertNotNull("저장된 미결전표가 없습니다.",unresolvedVoucher);
    }

    /**
     *  출금 전표 등록 테스트
     *  출금 전표는 차변만 입력 되야 하고,
     *  등록시 현금 대변 자동분개가 되어야 한다.
     */

    @Test
    public void withdrawalVoucherSaveTest() {

        List<GeneralVoucherEntryDto> generalVoucherEntryDtoList = new ArrayList<GeneralVoucherEntryDto>();

        GeneralVoucherEntryDto dto = new GeneralVoucherEntryDto();
        dto.setUserCompanyId(1L);
        dto.setAccountSubjectId(1L);
        dto.setVendorId(1L);
        dto.setApprovalManagerId(1L);
        dto.setDescriptionId(1L);
        dto.setTransactionDescription("입금 전표 테스트 적요");
        dto.setVoucherType(VoucherType.WITHDRAWAL);
//        dto.setDebitAmount(BigDecimal.valueOf(5000000));
//        dto.setCreditAmount(BigDecimal.ZERO);
        dto.setDebitAmount(BigDecimal.ZERO);
        dto.setCreditAmount(BigDecimal.valueOf(50000000));
        dto.setVoucherDate(LocalDate.parse("2024-05-07"));
        dto.setVoucherRegistrationTime(LocalDateTime.now());
        generalVoucherEntryDtoList.add(dto);

         List<UnresolvedVoucher> unresolvedVoucherList = unresolvedVoucherEntryService.unresolvedVoucherEntry(generalVoucherEntryDtoList);

         String voucherNumber = unresolvedVoucherList.get(0).getVoucherNumber();

         assertEquals("현금 계정과목 자동분개 실패",2, unresolvedVoucherList.size());

         BigDecimal debit = BigDecimal.ZERO;
        BigDecimal credit = BigDecimal.ZERO;

         for(UnresolvedVoucher unresolvedVoucher : unresolvedVoucherList) {
             assertEquals("분개된 출금전표 번호 불일치", unresolvedVoucher.getVoucherNumber(),voucherNumber);
             debit = debit.add(unresolvedVoucher.getDebitAmount());
             credit = credit.add(unresolvedVoucher.getCreditAmount());
             System.out.println(unresolvedVoucher.toString());
         }
         assertEquals("전표 차액 발생", debit,credit);

    }

    /**
     * 차변, 대변 전표는 대차차액이 항상 0이어야 하고
     * 생성시 전표 번호가 모두 같아야한다.
     */


    @Test
    public void debitAndCreditVoucherSaveTest() {
        List<GeneralVoucherEntryDto> generalVoucherEntryDtoList = new ArrayList<GeneralVoucherEntryDto>();

        GeneralVoucherEntryDto dto1 = new GeneralVoucherEntryDto();
        dto1.setUserCompanyId(1L);
        dto1.setAccountSubjectId(1L);
        dto1.setVendorId(1L);
        dto1.setApprovalManagerId(1L);
        dto1.setDescriptionId(1L);
        dto1.setTransactionDescription("테스트 적요설명 2");
        dto1.setVoucherType(VoucherType.DEBIT);
        dto1.setDebitAmount(BigDecimal.valueOf(5000000));
        dto1.setCreditAmount(BigDecimal.ZERO);
        dto1.setVoucherDate(LocalDate.parse("2024-05-07"));
        dto1.setVoucherRegistrationTime(LocalDateTime.now());
        generalVoucherEntryDtoList.add(dto1);


        GeneralVoucherEntryDto dto2 = new GeneralVoucherEntryDto();
        dto2.setUserCompanyId(1L);
        dto2.setAccountSubjectId(1L);
        dto2.setVendorId(1L);
        dto2.setApprovalManagerId(1L);
        dto2.setDescriptionId(1L);
        dto2.setTransactionDescription("테스트 적요설명 4");
        dto2.setVoucherType(VoucherType.CREDIT);
        dto2.setDebitAmount(BigDecimal.ZERO);
        dto2.setCreditAmount(BigDecimal.valueOf(5000000));
        dto2.setVoucherDate(LocalDate.parse("2024-05-07"));
        dto2.setVoucherRegistrationTime(LocalDateTime.now());
        generalVoucherEntryDtoList.add(dto2);


        List<UnresolvedVoucher> unresolvedVoucherList = unresolvedVoucherEntryService.unresolvedVoucherEntry(generalVoucherEntryDtoList);

        String voucherNumber = unresolvedVoucherList.get(0).getVoucherNumber();

        BigDecimal debit = BigDecimal.ZERO;
        BigDecimal credit = BigDecimal.ZERO;

        for(UnresolvedVoucher unresolvedVoucher : unresolvedVoucherList) {
            assertEquals("분개된 출금전표 번호 불일치", unresolvedVoucher.getVoucherNumber(),voucherNumber);
            debit = debit.add(unresolvedVoucher.getDebitAmount());
            credit = credit.add(unresolvedVoucher.getCreditAmount());
            System.out.println(unresolvedVoucher.toString());
        }
        assertEquals("전표 차액 발생", debit,credit);
    }


    /**
     *  입금 전표 등록 테스트
     *  입금 전표는 대변만 입력 되야 하고,
     *  등록시 현금 차변 자동분개가 되어야 한다.
     */


    @Test
    public void depositVoucherSaveTest() {
        // Given
        List<GeneralVoucherEntryDto> generalVoucherEntryDtoList = new ArrayList<GeneralVoucherEntryDto>();

        GeneralVoucherEntryDto dto1 = new GeneralVoucherEntryDto();
        dto1.setUserCompanyId(1L);
        dto1.setAccountSubjectId(1L);
        dto1.setVendorId(1L);
        dto1.setApprovalManagerId(1L);
        dto1.setDescriptionId(1L);
        dto1.setTransactionDescription("테스트 적요설명 1");
        dto1.setVoucherType(VoucherType.DEPOSIT);
        dto1.setDebitAmount(BigDecimal.valueOf(5000000));
        dto1.setCreditAmount(BigDecimal.ZERO);
        dto1.setVoucherDate(LocalDate.parse("2024-05-07"));
        dto1.setVoucherRegistrationTime(LocalDateTime.now());
        generalVoucherEntryDtoList.add(dto1);

        List<UnresolvedVoucher> unresolvedVoucherList = unresolvedVoucherEntryService.unresolvedVoucherEntry(generalVoucherEntryDtoList);

        String voucherNumber = unresolvedVoucherList.get(0).getVoucherNumber();

        assertEquals("현금 계정과목 자동분개 실패",2, unresolvedVoucherList.size());

        BigDecimal debit = BigDecimal.ZERO;
        BigDecimal credit = BigDecimal.ZERO;

        for(UnresolvedVoucher unresolvedVoucher : unresolvedVoucherList) {
            assertEquals("분개된 출금전표 번호 불일치", unresolvedVoucher.getVoucherNumber(),voucherNumber);
            debit = debit.add(unresolvedVoucher.getDebitAmount());
            credit = credit.add(unresolvedVoucher.getCreditAmount());
            System.out.println(unresolvedVoucher.toString());
        }
        assertEquals("전표 차액 발생", debit,credit);


    }

    @Test
    public void diffVoucherSaveTest() {
        List<GeneralVoucherEntryDto> generalVoucherEntryDtoList = new ArrayList<GeneralVoucherEntryDto>();

        GeneralVoucherEntryDto dto1 = new GeneralVoucherEntryDto();
        dto1.setUserCompanyId(1L);
        dto1.setAccountSubjectId(1L);
        dto1.setVendorId(1L);
        dto1.setApprovalManagerId(1L);
        dto1.setDescriptionId(1L);
        dto1.setTransactionDescription("테스트 적요설명 1");
        dto1.setVoucherType(VoucherType.DEBIT);
        dto1.setDebitAmount(BigDecimal.valueOf(5000000));
        dto1.setCreditAmount(BigDecimal.ZERO);
        dto1.setVoucherDate(LocalDate.parse("2024-05-05"));
        dto1.setVoucherRegistrationTime(LocalDateTime.now());
        generalVoucherEntryDtoList.add(dto1);


        GeneralVoucherEntryDto dto2 = new GeneralVoucherEntryDto();
        dto2.setUserCompanyId(1L);
        dto2.setAccountSubjectId(1L);
        dto2.setVendorId(1L);
        dto2.setApprovalManagerId(1L);
        dto2.setDescriptionId(1L);
        dto2.setTransactionDescription("테스트 적요설명 2");
        dto2.setVoucherType(VoucherType.CREDIT);
        dto2.setDebitAmount(BigDecimal.ZERO);
        dto2.setCreditAmount(BigDecimal.valueOf(5000000));
        dto2.setVoucherDate(LocalDate.parse("2024-05-05"));
        dto2.setVoucherRegistrationTime(LocalDateTime.now());
        generalVoucherEntryDtoList.add(dto2);


        GeneralVoucherEntryDto dto3 = new GeneralVoucherEntryDto();
        dto3.setUserCompanyId(1L);
        dto3.setAccountSubjectId(1L);
        dto3.setVendorId(1L);
        dto3.setApprovalManagerId(1L);
        dto3.setDescriptionId(1L);
        dto3.setTransactionDescription("테스트 적요설명 3");
        dto3.setVoucherType(VoucherType.WITHDRAWAL);
        dto3.setDebitAmount(BigDecimal.ZERO);
        dto3.setCreditAmount(BigDecimal.valueOf(5000000));
        dto3.setVoucherDate(LocalDate.parse("2024-05-07"));
        dto3.setVoucherRegistrationTime(LocalDateTime.now());
        generalVoucherEntryDtoList.add(dto3);


        List<UnresolvedVoucher> unresolvedVoucherList = unresolvedVoucherEntryService.unresolvedVoucherEntry(generalVoucherEntryDtoList);

        List<UnresolvedVoucher> unresolvedVouchers = unresolvedVoucherRepository.findAll();


//
//        String voucherNumber = unresolvedVoucherList.get(0).getVoucherNumber();

//        BigDecimal debit = BigDecimal.ZERO;
//        BigDecimal credit = BigDecimal.ZERO;

        for(UnresolvedVoucher unresolvedVoucher : unresolvedVouchers) {
//            assertEquals("분개된 출금전표 번호 불일치", unresolvedVoucher.getVoucherNumber(),voucherNumber);
//            debit = debit.add(unresolvedVoucher.getDebitAmount());
//            credit = credit.add(unresolvedVoucher.getCreditAmount());
            System.out.println(unresolvedVoucher.toString());
        }
//        assertEquals("전표 차액 발생", debit,credit);
    }


    @Test
    public void deapCloneTest() throws CloneNotSupportedException {
        GeneralVoucherEntryDto dto1 = new GeneralVoucherEntryDto();

        dto1.setUserCompanyId(1000000L);


        GeneralVoucherEntryDto dto2 = new GeneralVoucherEntryDto();
        dto2 = dto1;

        dto2.setUserCompanyId(500L);
        System.out.println(dto1.toString());
        System.out.println(dto2.toString());

        GeneralVoucherEntryDto dto3 = new GeneralVoucherEntryDto();

        dto3.setUserCompanyId(1000000L);


        GeneralVoucherEntryDto dto4 = new GeneralVoucherEntryDto();
        dto4 = dto3.clone();

        dto4.setUserCompanyId(500L);
        System.out.println(dto3.toString());
        System.out.println(dto4.toString());
    }
}