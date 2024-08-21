package com.megazone.ERPSystem_phase2_Backend.financial.service.voucher_entry.sales_and_purchase_voucher_entry;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.AccountSubject;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.UnresolvedVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.enums.ApprovalStatus;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.enums.VoucherType;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.JournalEntry;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.UnresolvedSaleAndPurchaseVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.VatType;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto.UnresolvedSaleAndPurchaseVoucherEntryDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.enums.ElectronicTaxInvoiceStatus;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.enums.TransactionType;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.account_subject.AccountSubjectRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.general_voucher_entry.unresolvedVoucher.UnresolvedVoucherRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.sales_and_purchase_voucher_entry.unresolveSaleAndPurchaseVoucher.JorunalEntryTypeSetupRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.sales_and_purchase_voucher_entry.unresolveSaleAndPurchaseVoucher.JournalEntryRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.sales_and_purchase_voucher_entry.unresolveSaleAndPurchaseVoucher.UnresolvedSaleAndPurchaseVoucherRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.sales_and_purchase_voucher_entry.unresolveSaleAndPurchaseVoucher.VatTypeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.eclipse.jdt.internal.compiler.problem.ProblemSeverities.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UnresolvedSaleAndPurchaseVoucherServiceImpl implements UnresolvedSaleAndPurchaseVoucherService {

    private final VatTypeRepository vatTypeRepository;
    private final JournalEntryRepository journalEntryRepository;
    private final UnresolvedSaleAndPurchaseVoucherRepository unresolvedSaleAndPurchaseVoucherRepository;
    private final AccountSubjectRepository accountSubjectRepository;
    private final UnresolvedVoucherRepository unresolvedVoucherRepository;

    @Override
    public UnresolvedSaleAndPurchaseVoucher save(UnresolvedSaleAndPurchaseVoucherEntryDTO dto) {
        return unresolvedSaleAndPurchaseVoucherRepository.save(create(dto));
    }

    public UnresolvedSaleAndPurchaseVoucher create(UnresolvedSaleAndPurchaseVoucherEntryDTO dto) {
        BigDecimal supplyAmount = createSupplyAmount(dto.getQuantity(),dto.getUnitPrice());

        VatType vatType = vatTypeRepository.findByCode(dto.getVatTypeCode()).orElseThrow(
                () -> new IllegalArgumentException("해당하는 코드의 세금과목이 없습니다."));

        JournalEntry journalEntry = journalEntryRepository.findByCodeAndTransactionType(dto.getJournalEntryCode(),vatType.getTransactionType()).orElseThrow(
                () -> new IllegalArgumentException("해당하는 코드의 분개유형이 없습니다."));

        return UnresolvedSaleAndPurchaseVoucher.builder()
                .vatType(vatType)
                .journalEntry(journalEntry)
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

    public UnresolvedVoucher createAutoUnresolvedVoucher(AccountSubject accountSubject, String transactionDescription,
                                       String voucherNumber, VoucherType voucherType,
                                       BigDecimal debitAmount, BigDecimal creditAmount,
                                       LocalDate voucherDate, LocalDateTime nowTime
                                       ) {
        return UnresolvedVoucher.builder()
                .accountSubject(accountSubject)
                .transactionDescription(transactionDescription)
                .voucherNumber(voucherNumber)
                .voucherType(voucherType)
                .debitAmount(debitAmount)
                .creditAmount(creditAmount)
                .voucherDate(voucherDate)
                .voucherRegistrationTime(nowTime)
                .approvalStatus(ApprovalStatus.PENDING).build();
    }

    public String CreateVoucherNumber(LocalDate voucherDate) {
        // 해당 조건의 날짜에 해당하는 마지막 미결전표 Entity 가져오기
        UnresolvedVoucher lastUnresolvedVoucher = unresolvedSaleAndPurchaseVoucherRepository.findFirstByVoucherDateOrderByIdDesc(voucherDate).orElse(null);

        if(lastUnresolvedVoucher == null) {
            return "50000";
        }
        else {
            int voucherNum = Integer.parseInt(lastUnresolvedVoucher.getVoucherNumber()) + 1;
            return String.valueOf(voucherNum);
        }
    }

    public Object AutoCreateVoucher(UnresolvedSaleAndPurchaseVoucher voucher) {
        List<UnresolvedVoucher> unresolvedVoucherList = new ArrayList<>();
        TransactionType transactionType = voucher.getVatType().getTransactionType();
        AccountSubject accountSubject = voucher.getJournalEntry().getJournalEntryTypeSetup().getAccountSubject();
        BigDecimal supplyAmount = createSupplyAmount(voucher.getQuantity(), voucher.getUnitPrice());
        BigDecimal vatAmount = createVatAmount(voucher.getQuantity(), voucher.getUnitPrice());
        String voucherNum = CreateVoucherNumber(voucher.getVoucherDate());
        String itemName = voucher.getItemName();
        BigDecimal quantity = voucher.getQuantity();
        BigDecimal unitPrice = voucher.getUnitPrice();
        LocalDate date = voucher.getVoucherDate();
        LocalDateTime nowTime = LocalDateTime.now();


        switch (voucher.getJournalEntry().getCode()) {
            case "1":
                if (transactionType == TransactionType.SALES) {
                    unresolvedVoucherList.add(createAutoUnresolvedVoucher(accountSubject,
                            createTransactionDescription(itemName, quantity,unitPrice),
                            voucherNum, VoucherType.DEPOSIT, supplyAmount, BigDecimal.ZERO, date, nowTime));

                    if (vatAmount.compareTo(BigDecimal.ZERO) > 0) {
                        unresolvedVoucherList.add(createAutoUnresolvedVoucher(accountSubjectRepository.findById(153L).orElse(null),
                                createTransactionDescription(itemName, quantity,unitPrice),voucherNum, VoucherType.DEPOSIT, vatAmount, BigDecimal.ZERO, date, nowTime));
                    }
                } else if (transactionType == TransactionType.PURCHASE) {
                    unresolvedVoucherList.add(createAutoUnresolvedVoucher(accountSubject,
                            createTransactionDescription(itemName, quantity,unitPrice),
                            voucherNum, VoucherType.WITHDRAWAL, supplyAmount, BigDecimal.ZERO, date, nowTime));

                    if (vatAmount.compareTo(BigDecimal.ZERO) > 0) {
                        unresolvedVoucherList.add(createAutoUnresolvedVoucher(accountSubjectRepository.findById(35L).orElse(null),
                                createTransactionDescription(itemName, quantity,unitPrice),
                                voucherNum, VoucherType.WITHDRAWAL, vatAmount, BigDecimal.ZERO, date, nowTime));
                    }
                }
                break;
            case "2":
            case "3":
                if (transactionType == TransactionType.SALES) {
                    unresolvedVoucherList.add(createAutoUnresolvedVoucher(accountSubject,
                            createTransactionDescription(itemName, quantity,unitPrice),
                            voucherNum, VoucherType.DEBIT, supplyAmount.add(vatAmount), BigDecimal.ZERO, date, nowTime));


                    unresolvedVoucherList.add(
                            createAutoUnresolvedVoucher(
                                    journalEntryRepository.findByCodeAndTransactionType("1", transactionType).orElse(null).
                                            getJournalEntryTypeSetup().getAccountSubject(),
                                    createTransactionDescription(itemName, quantity,unitPrice),
                                    voucherNum, VoucherType.CREDIT, vatAmount, BigDecimal.ZERO, date, nowTime));

                    if (vatAmount.compareTo(BigDecimal.ZERO) > 0) {
                        unresolvedVoucherList.add(createAutoUnresolvedVoucher(accountSubject,
                                createTransactionDescription(itemName, quantity,unitPrice),
                                voucherNum, VoucherType.CREDIT, supplyAmount, BigDecimal.ZERO, date, nowTime));
                    }
                } else if (transactionType == TransactionType.PURCHASE) {
                    if (transactionType == TransactionType.SALES) {
                        unresolvedVoucherList.add(createAutoUnresolvedVoucher(accountSubject,
                                createTransactionDescription(itemName, quantity,unitPrice),
                                voucherNum, VoucherType.CREDIT, supplyAmount.add(vatAmount), BigDecimal.ZERO, date, nowTime));


                        unresolvedVoucherList.add(
                                createAutoUnresolvedVoucher(
                                        journalEntryRepository.findByCodeAndTransactionType("1", transactionType).orElse(null).
                                                getJournalEntryTypeSetup().getAccountSubject(),
                                        createTransactionDescription(itemName, quantity,unitPrice),
                                        voucherNum, VoucherType.DEBIT, vatAmount, BigDecimal.ZERO, date, nowTime));

                        if (vatAmount.compareTo(BigDecimal.ZERO) > 0) {
                            unresolvedVoucherList.add(createAutoUnresolvedVoucher(accountSubject,
                                    createTransactionDescription(itemName, quantity,unitPrice),
                                    voucherNum, VoucherType.DEBIT, supplyAmount, BigDecimal.ZERO, date, nowTime));
                        }
                    }
                    break;
                }
        }
        return unresolvedVoucherRepository.saveAll(unresolvedVoucherList);
    }
}
