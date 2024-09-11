package com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.general_voucher_entry.resolvedVoucher;

import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.GeneralShowDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.ResolvedVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.dto.ResolvedVoucherDeleteDTO;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ResolvedVoucherRepositoryCustom {
    List<Long> deleteVoucherByManager(ResolvedVoucherDeleteDTO dto,Long companyId);
    List<GeneralShowDTO> generalSearch(@Param("startDate") LocalDate startDate,
                                       @Param("endDate") LocalDate endDate,
                                       @Param("startAccountCode") String startAccountCode,
                                       @Param("endAccountCode") String endAccountCode,
                                       Long companyId);
}
