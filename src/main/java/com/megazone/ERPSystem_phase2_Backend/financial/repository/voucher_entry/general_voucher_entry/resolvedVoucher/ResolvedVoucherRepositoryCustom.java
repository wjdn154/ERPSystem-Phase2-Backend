package com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.general_voucher_entry.resolvedVoucher;

import com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.FinancialStatementsLedgerSearchDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.FinancialStatementsLedgerShowDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.*;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.dto.ResolvedVoucherDeleteDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.dto.ResolvedVoucherShowDTO;

import java.util.List;

public interface ResolvedVoucherRepositoryCustom {
    List<Long> deleteVoucherByManager(ResolvedVoucherDeleteDTO dto);

    List<GeneralShowDTO> generalSelectShow(GeneralSelectDTO dto);

    List<GeneralAccountListDTO> generalList(GeneralDTO dto);

    List<ClientLedgerShowDTO> clientLedgerList(ClientLedgerSearchDTO dto);

    List<ClientListDTO> clientAndAccountSubjectLedgerList(ClientAndAccountSubjectLedgerSearchDTO dto);

    List<ClientAndAccountSubjectLedgerShowDetailDTO> clientAndAccountSubjectLedgerDetail(ClientAndAccountSubjectLedgerShowDetailConditionDTO dto);

    List<CashJournalShowDTO> cashJournalShow(CashJournalSearchDTO dto);

    List<AccountLedgerAccListDTO> accountLedgerList(AccountLedgerSearchDTO dto);

    List<AccountLedgerShowDTO> accountLedgerDetail(AccountLedgerDetailEntryDTO dto);

    List<DailyAndMonthJournalDTO> dailyLedgerList(DailyAndMonthJournalSearchDTO dto);

    List<ResolvedVoucherShowDTO> voucherPrintShowList(VoucherPrintSearchDTO dto);

    List<FinancialStatementsLedgerShowDTO> financialStatementsShow(FinancialStatementsLedgerSearchDTO dto);
}
