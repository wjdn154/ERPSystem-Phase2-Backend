package com.megazone.ERPSystem_phase2_Backend.financial.service.voucher_entry.sales_and_purchase_voucher_entry;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.AccountSubject;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.Client;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.UnresolvedVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.dto.UnresolvedVoucherEntryDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.enums.ApprovalStatus;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.enums.VoucherKind;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.enums.VoucherType;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.JournalEntry;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.ResolvedSaleAndPurchaseVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.UnresolvedSaleAndPurchaseVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.VatType;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto.UnresolvedSaleAndPurchaseVoucherApprovalDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto.UnresolvedSaleAndPurchaseVoucherDeleteDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto.UnresolvedSaleAndPurchaseVoucherEntryDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.enums.ElectronicTaxInvoiceStatus;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.enums.TransactionType;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.client.ClientRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.sales_and_purchase_voucher_entry.journalEntry.JournalEntryRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.sales_and_purchase_voucher_entry.unresolveSaleAndPurchaseVoucher.UnresolvedSaleAndPurchaseVoucherRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.sales_and_purchase_voucher_entry.vatType.VatTypeRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.service.voucher_entry.general_voucher_entry.UnresolvedVoucherEntryService;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Employee;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Employee.EmployeeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UnresolvedSaleAndPurchaseVoucherServiceImpl implements UnresolvedSaleAndPurchaseVoucherService {

    private final VatTypeRepository vatTypeRepository;
    private final JournalEntryRepository journalEntryRepository;
    private final UnresolvedSaleAndPurchaseVoucherRepository unresolvedSaleAndPurchaseVoucherRepository;
    private final UnresolvedVoucherEntryService unresolvedVoucherEntryService;
    private final ResolvedSaleAndPurchaseVoucherService resolvedSaleAndPurchaseVoucherService;
    private final EmployeeRepository employeeRepository;
    private final ClientRepository clientRepository;

    @Override
    public UnresolvedSaleAndPurchaseVoucher save(UnresolvedSaleAndPurchaseVoucherEntryDTO dto) {
        return unresolvedSaleAndPurchaseVoucherRepository.save(create(dto));
    }

    public UnresolvedSaleAndPurchaseVoucher create(UnresolvedSaleAndPurchaseVoucherEntryDTO dto) {
        BigDecimal supplyAmount = createSupplyAmount(dto.getQuantity(),dto.getUnitPrice());

        VatType vatType = vatTypeRepository.findByCode(dto.getVatTypeCode());
        JournalEntry journalEntry = journalEntryRepository.findByCodeAndTransactionType(dto.getJournalEntryCode(),vatType.getTransactionType());
        Client client = clientRepository.findByCode(dto.getClientCode()).orElseThrow(
                () -> new RuntimeException("해당하는 코드의 거래처가 없습니다."));
        Employee employee = employeeRepository.findById(dto.getVoucherManagerId()).orElseThrow(
                () -> new RuntimeException("해당하는 사원이 없습니다."));

        UnresolvedSaleAndPurchaseVoucher unresolvedSaleAndPurchaseVoucher = UnresolvedSaleAndPurchaseVoucher.builder()
                .vatType(vatType)
                .journalEntry(journalEntry)
                .client(client)
                .voucherManager(employee)
                .voucherDate(dto.getVoucherDate())
                .itemName(dto.getItemName())
                .quantity(dto.getQuantity())
                .unitPrice(dto.getUnitPrice())
                .supplyAmount(supplyAmount)
                .vatAmount(createVatAmount(supplyAmount,vatType.getTaxRate()))
                .electronicTaxInvoiceStatus(ElectronicTaxInvoiceStatus.UNPUBLISHED)
                .voucherRegistrationTime(LocalDateTime.now())
                .approvalStatus(ApprovalStatus.PENDING)
                .build();
        unresolvedSaleAndPurchaseVoucher.setUnresolvedVouchers(AutoCreateVoucher(unresolvedSaleAndPurchaseVoucher));
        unresolvedSaleAndPurchaseVoucher.setVoucherNumber(unresolvedSaleAndPurchaseVoucher.getUnresolvedVouchers().get(0).
                getVoucherNumber());
        return unresolvedSaleAndPurchaseVoucher;
    }

    /**
     * 공급가액 계산 메소드
     */
    public BigDecimal createSupplyAmount(BigDecimal quantity, BigDecimal unitPrice) {
        return quantity.multiply(unitPrice);
    }

    /**
     * 부가세 계산 메소드
     */
    public BigDecimal createVatAmount(BigDecimal supplyAmount, BigDecimal vatRate) {
        return supplyAmount.multiply(vatRate);
    }

    public String createTransactionDescription(String itemName, BigDecimal quantity, BigDecimal unitPrice) {
        return itemName + quantity + " X " + unitPrice;

    }

    public UnresolvedVoucher createAutoUnresolvedVoucher(AccountSubject accountSubject, VoucherType voucherType,
                                                         Employee employee, Client client,
                                                         String transactionDescription, BigDecimal debitAmount, BigDecimal creditAmount,
                                                         LocalDate voucherDate) {
        return UnresolvedVoucher.builder()
                .accountSubject(accountSubject)
                .client(client)
                .voucherType(voucherType)
                .voucherManager(employee)
                .transactionDescription(transactionDescription)
                .debitAmount(debitAmount)
                .creditAmount(creditAmount)
                .voucherDate(voucherDate)
                .voucherKind(VoucherKind.SALE_AND_PURCHASE)
                .build();
    }

//    과세유형으로 매출 매입인지 확인
//
//    분개유형으로 1, 2, 3 확인
//
//    현금
//    부가세유형이 매출이면   입금    분개코드+(매출or매입)조회 계정과목(차변/대변), 부가세예수금(차변/대변)
//    부가세유형이 매입이면   출금    분개코드+(매출or매입)조회 계정과목(차변/대변), 부가세대급금(차변/대변)
//    부가세 유형에 따른 세금이 발생하지않으면 부가세분개 안함
//
//
//    외상
//    부가세유형이 매출이면  매출채권차변(공급가액 + 부가세), 부가세예수금대변 세금금액(세금없는 과목은 X), 매출대변(공급가액)
//    부가세유형이 매입이면  매입채무대변(공급가액 + 부가세), 부가세대급금차변 세금금액(세금없는 과목은 X), 매입차변(공급가액)
//
//
//    카드
//    부가세유형이 매출이면 신용카드매출채권차변(공급가액 + 부가세), 부가세예수금대변 세금금액(세금없는 과목은X), 매출대변(공급가액)
//    부가세유형이 매입이면 신용카드매입채무대변(공급가액 + 부가세), 부가세대급금 세금금액(세금없는 과목은X), 매입차변(공급가액)

    public List<UnresolvedVoucher> AutoCreateVoucher(UnresolvedSaleAndPurchaseVoucher voucher) {
        List<UnresolvedVoucher> unresolvedVoucherList = new ArrayList<UnresolvedVoucher>();
        List<UnresolvedVoucherEntryDTO> unresolvedVoucherEntryDTOS;

        TransactionType transactionType = voucher.getVatType().getTransactionType();
        AccountSubject journalEntryAccountSubject = voucher.getJournalEntry().getJournalEntryTypeSetup().getAccountSubject();
        AccountSubject vatTypeAccountSubject = voucher.getVatType().getAccountSubject();
        Client client = voucher.getClient();
        Employee employee = voucher.getVoucherManager();
        BigDecimal supplyAmount = createSupplyAmount(voucher.getQuantity(), voucher.getUnitPrice());
        BigDecimal vatAmount = createVatAmount(supplyAmount,voucher.getVatType().getTaxRate());
        String itemName = voucher.getItemName();
        BigDecimal quantity = voucher.getQuantity();
        BigDecimal unitPrice = voucher.getUnitPrice();
        LocalDate date = voucher.getVoucherDate();


        switch (voucher.getJournalEntry().getCode()) {
            case "1":
                if (transactionType == TransactionType.SALES) {
                    unresolvedVoucherList.add(createAutoUnresolvedVoucher(journalEntryAccountSubject,
                            VoucherType.DEPOSIT,
                            employee,
                            client,
                            createTransactionDescription(itemName, quantity,unitPrice),
                            BigDecimal.ZERO,
                            supplyAmount,
                            date));

                    if (vatAmount.compareTo(BigDecimal.ZERO) > 0) {
                        unresolvedVoucherList.add(createAutoUnresolvedVoucher(vatTypeAccountSubject,
                                VoucherType.DEPOSIT,
                                employee,
                                client,
                                createTransactionDescription(itemName, quantity,unitPrice),
                                BigDecimal.ZERO,
                                vatAmount,
                                date));
                    }
                } else if (transactionType == TransactionType.PURCHASE) {
                    unresolvedVoucherList.add(createAutoUnresolvedVoucher(journalEntryAccountSubject,
                            VoucherType.WITHDRAWAL,
                            employee,
                            client,
                            createTransactionDescription(itemName, quantity,unitPrice),
                            supplyAmount,
                            BigDecimal.ZERO,
                            date));

                    if (vatAmount.compareTo(BigDecimal.ZERO) > 0) {
                        unresolvedVoucherList.add(createAutoUnresolvedVoucher(
                                vatTypeAccountSubject,
                                VoucherType.WITHDRAWAL,
                                employee,
                                client,
                                createTransactionDescription(itemName, quantity,unitPrice),
                                vatAmount,
                                BigDecimal.ZERO,
                                date));
                    }
                }
                break;
            case "2":
            case "3":
                // 분개유형 처리
                if (transactionType == TransactionType.SALES) {
                    unresolvedVoucherList.add(createAutoUnresolvedVoucher(
                            journalEntryAccountSubject,
                            VoucherType.DEBIT,
                            employee,
                            client,
                            createTransactionDescription(itemName, quantity,unitPrice),
                            supplyAmount.add(vatAmount),
                            BigDecimal.ZERO,
                            date));

                    // 매출or매입 + 필수분개유형 처리
                    unresolvedVoucherList.add(
                            createAutoUnresolvedVoucher(
                                    journalEntryRepository.findByCodeAndTransactionType("1", transactionType)
                                            .getJournalEntryTypeSetup().getAccountSubject(),
                                    VoucherType.CREDIT,
                                    employee,
                                    client,
                                    createTransactionDescription(itemName, quantity,unitPrice),
                                    BigDecimal.ZERO,
                                    supplyAmount,
                                    date));

                    // 부가세 분개처리
                    if (vatAmount.compareTo(BigDecimal.ZERO) > 0) {
                        unresolvedVoucherList.add(createAutoUnresolvedVoucher(
                                vatTypeAccountSubject,
                                VoucherType.CREDIT,
                                employee,
                                client,
                                createTransactionDescription(itemName, quantity,unitPrice),
                                BigDecimal.ZERO,
                                vatAmount,
                                date));
                    }
                } else if (transactionType == TransactionType.PURCHASE) {

                        unresolvedVoucherList.add(createAutoUnresolvedVoucher(journalEntryAccountSubject,
                                VoucherType.CREDIT,
                                employee,
                                client,
                                createTransactionDescription(itemName, quantity,unitPrice),
                                BigDecimal.ZERO,
                                supplyAmount.add(vatAmount),
                                date));


                        unresolvedVoucherList.add(
                                createAutoUnresolvedVoucher(
                                        journalEntryRepository.findByCodeAndTransactionType("1", transactionType)
                                                .getJournalEntryTypeSetup().getAccountSubject(),
                                        VoucherType.DEBIT,
                                        employee,
                                        client,
                                        createTransactionDescription(itemName, quantity,unitPrice),
                                        supplyAmount,
                                        BigDecimal.ZERO,
                                        date));

                        if (vatAmount.compareTo(BigDecimal.ZERO) > 0) {
                            unresolvedVoucherList.add(createAutoUnresolvedVoucher(
                                    vatTypeAccountSubject,
                                    VoucherType.DEBIT,
                                    employee,
                                    client,
                                    createTransactionDescription(itemName, quantity,unitPrice),
                                    vatAmount,
                                    BigDecimal.ZERO,
                                    date));
                        }
                    }
                    break;
        }

        unresolvedVoucherEntryDTOS = unresolvedVoucherList.stream().map((changeVoucher) -> { return UnresolvedVoucherEntryDTO.create(changeVoucher);})
                .toList();
        return unresolvedVoucherEntryService.unresolvedVoucherEntry(unresolvedVoucherEntryDTOS);
    }

    @Override
    public List<UnresolvedSaleAndPurchaseVoucher> searchAllVoucher(LocalDate date) {

        List<UnresolvedSaleAndPurchaseVoucher> voucherList = new ArrayList<UnresolvedSaleAndPurchaseVoucher>();

        try {
            voucherList = unresolvedSaleAndPurchaseVoucherRepository.findByVoucherDateOrderByVoucherNumberAsc(date);
            if(voucherList.isEmpty()) {
                throw new NoSuchElementException("해당 날짜에 등록된 미결전표가 없습니다.");
            }
        }
        catch (NoSuchElementException e) {
            e.getStackTrace();
        }
        return voucherList;
    }

    @Override
    public String deleteVoucher(UnresolvedSaleAndPurchaseVoucherDeleteDTO dto) {
        try {
            if(true) { // 전표의 담당자 이거나, 승인권자면 삭제가능 << 기능구현 필요
                dto.getSearchVoucherNumList().forEach((voucherNumber) -> {
                                    unresolvedSaleAndPurchaseVoucherRepository.deleteByVoucherNumberAndVoucherDate(
                                            voucherNumber, dto.getSearchDate());});
            }
        }
        catch (Exception e) {
            e.getStackTrace();
            return null;
        }
        return "미결 매출매입전표 삭제 성공";
    }


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
    public List<UnresolvedVoucher> searchVoucher(String voucherNumber) {
        return unresolvedSaleAndPurchaseVoucherRepository.findByVoucherNumber(voucherNumber).getUnresolvedVouchers();
    }

    @Override
    public List<UnresolvedSaleAndPurchaseVoucher> ApprovalProcessing(UnresolvedSaleAndPurchaseVoucherApprovalDTO dto) {

        List<UnresolvedSaleAndPurchaseVoucher> unresolvedVoucherList = unresolvedSaleAndPurchaseVoucherRepository.findApprovalTypeVoucher(dto);
        try {
            if(dto.getApprovalStatus().equals(ApprovalStatus.PENDING)) {
                throw new IllegalArgumentException("승인 대기 상태로는 변경할 수 없습니다.");
            }

            if(!unresolvedVoucherList.isEmpty())
            {
                unresolvedVoucherList.stream().forEach(
                        unresolvedVoucher -> {
                            unresolvedVoucher.setApprovalStatus(dto.getApprovalStatus());
                            unresolvedVoucher.getUnresolvedVouchers().forEach(
                                    unresolvedVouchers -> unresolvedVouchers.setApprovalStatus(dto.getApprovalStatus())
                            );
                        });
                // 연관된 매출매입전표 모두 생성
                resolvedSaleAndPurchaseVoucherService.resolvedVoucherEntry(unresolvedVoucherList);
            }
            else {
                throw new IllegalArgumentException("권한 또는 해당하는 전표가 없습니다.");
            }
        }
        catch (Exception e) {
            e.getStackTrace();
            return null;
        }
        return unresolvedVoucherList;
    }

}
