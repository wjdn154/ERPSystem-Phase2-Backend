package com.megazone.ERPSystem_phase2_Backend.hr.model.salary_ledger;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.common.aliasing.qual.Unique;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "salary_ledger")
@Table(name = "salary_ledger")
public class SalaryLedgerDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Unique
    private Long id;

    private String code;

    private String description;

}
