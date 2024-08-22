package com.megazone.ERPSystem_phase2_Backend.financial.service.voucher_entry.sales_and_purchase_voucher_entry;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.AccountSubject;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.UnresolvedVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.dto.UnresolvedVoucherEntryDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.enums.ApprovalStatus;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.enums.VoucherKind;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.enums.VoucherType;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.JournalEntry;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.JournalEntryTypeSetup;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.UnresolvedSaleAndPurchaseVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.VatType;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto.UnresolvedSaleAndPurchaseVoucherEntryDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.enums.ElectronicTaxInvoiceStatus;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.enums.TransactionType;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.account_subject.AccountSubjectRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.general_voucher_entry.unresolvedVoucher.UnresolvedVoucherRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.sales_and_purchase_voucher_entry.unresolveSaleAndPurchaseVoucher.JournalEntryRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.sales_and_purchase_voucher_entry.unresolveSaleAndPurchaseVoucher.UnresolvedSaleAndPurchaseVoucherRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.sales_and_purchase_voucher_entry.unresolveSaleAndPurchaseVoucher.VatTypeRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.service.voucher_entry.general_voucher_entry.UnresolvedVoucherEntryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UnresolvedSaleAndPurchaseVoucherServiceImpl implements UnresolvedSaleAndPurchaseVoucherService {

    private final VatTypeRepository vatTypeRepository;
    private final JournalEntryRepository journalEntryRepository;
    private final UnresolvedSaleAndPurchaseVoucherRepository unresolvedSaleAndPurchaseVoucherRepository;
    private final UnresolvedVoucherEntryService unresolvedVoucherEntryService;

    @Override
    public UnresolvedSaleAndPurchaseVoucher save(UnresolvedSaleAndPurchaseVoucherEntryDTO dto) {
        return unresolvedSaleAndPurchaseVoucherRepository.save(create(dto));
    }

    public UnresolvedSaleAndPurchaseVoucher create(UnresolvedSaleAndPurchaseVoucherEntryDTO dto) {
        BigDecimal supplyAmount = createSupplyAmount(dto.getQuantity(),dto.getUnitPrice());

        VatType vatType = vatTypeRepository.findByCode(dto.getVatTypeCode());
        JournalEntry journalEntry = journalEntryRepository.findByCodeAndTransactionType(dto.getJournalEntryCode(),vatType.getTransactionType());


        UnresolvedSaleAndPurchaseVoucher unresolvedSaleAndPurchaseVoucher = UnresolvedSaleAndPurchaseVoucher.builder()
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
                                                         String transactionDescription, BigDecimal debitAmount, BigDecimal creditAmount,
                                                         LocalDate voucherDate) {
        return UnresolvedVoucher.builder()
                .accountSubject(accountSubject)
                .voucherType(voucherType)
                .transactionDescription(transactionDescription)
                .debitAmount(debitAmount)
                .creditAmount(creditAmount)
                .voucherDate(voucherDate)
                .voucherKind(VoucherKind.SALE_AND_PURCHASE)
                .build();
    }

    @Override
    public String CreateVoucherNumber(LocalDate voucherDate) {
        // 해당 조건의 날짜에 해당하는 마지막 미결전표 Entity 가져오기
        UnresolvedSaleAndPurchaseVoucher lastUnresolvedVoucher = unresolvedSaleAndPurchaseVoucherRepository.findFirstByVoucherDateOrderByIdDesc(voucherDate).orElse(null);

        if(lastUnresolvedVoucher == null) {
            return "50000";
        }
        else {
            int voucherNum = Integer.parseInt(lastUnresolvedVoucher.getVoucherNumber()) + 1;
            return String.valueOf(voucherNum);
        }
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
                            createTransactionDescription(itemName, quantity,unitPrice),
                            BigDecimal.ZERO,
                            supplyAmount,
                            date));

                    if (vatAmount.compareTo(BigDecimal.ZERO) > 0) {
                        unresolvedVoucherList.add(createAutoUnresolvedVoucher(voucher.getVatType().getAccountSubject(),
                                VoucherType.DEPOSIT,
                                createTransactionDescription(itemName, quantity,unitPrice),
                                BigDecimal.ZERO,
                                vatAmount,
                                date));
                    }
                } else if (transactionType == TransactionType.PURCHASE) {
                    unresolvedVoucherList.add(createAutoUnresolvedVoucher(journalEntryAccountSubject,
                            VoucherType.WITHDRAWAL,
                            createTransactionDescription(itemName, quantity,unitPrice),
                            supplyAmount,
                            BigDecimal.ZERO,
                            date));

                    if (vatAmount.compareTo(BigDecimal.ZERO) > 0) {
                        unresolvedVoucherList.add(createAutoUnresolvedVoucher(
                                voucher.getVatType().getAccountSubject(),
                                VoucherType.WITHDRAWAL,
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
                                    createTransactionDescription(itemName, quantity,unitPrice),
                                    BigDecimal.ZERO,
                                    vatAmount,
                                    date));

                    // 부가세 분개처리
                    if (vatAmount.compareTo(BigDecimal.ZERO) > 0) {
                        unresolvedVoucherList.add(createAutoUnresolvedVoucher(
                                voucher.getVatType().getAccountSubject(),
                                VoucherType.CREDIT,
                                createTransactionDescription(itemName, quantity,unitPrice),
                                BigDecimal.ZERO,
                                supplyAmount,
                                date));
                    }
                } else if (transactionType == TransactionType.PURCHASE) {

                        unresolvedVoucherList.add(createAutoUnresolvedVoucher(journalEntryAccountSubject,
                                VoucherType.CREDIT,
                                createTransactionDescription(itemName, quantity,unitPrice),
                                BigDecimal.ZERO,
                                supplyAmount.add(vatAmount),
                                date));


                        unresolvedVoucherList.add(
                                createAutoUnresolvedVoucher(
                                        journalEntryRepository.findByCodeAndTransactionType("1", transactionType)
                                                .getJournalEntryTypeSetup().getAccountSubject(),
                                        VoucherType.DEBIT,
                                        createTransactionDescription(itemName, quantity,unitPrice),
                                        vatAmount,
                                        BigDecimal.ZERO,
                                        date));

                        if (vatAmount.compareTo(BigDecimal.ZERO) > 0) {
                            unresolvedVoucherList.add(createAutoUnresolvedVoucher(
                                    voucher.getVatType().getAccountSubject(),
                                    VoucherType.DEBIT,
                                    createTransactionDescription(itemName, quantity,unitPrice),
                                    supplyAmount,
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
}
