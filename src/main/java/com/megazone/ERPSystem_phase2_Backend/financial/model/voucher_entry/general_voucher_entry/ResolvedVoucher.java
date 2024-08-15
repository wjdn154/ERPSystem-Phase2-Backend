package com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "resolved_voucher")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "resolved_voucher")
public class ResolvedVoucher {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
}
