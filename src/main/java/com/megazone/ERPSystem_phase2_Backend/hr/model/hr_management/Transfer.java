package com.megazone.ERPSystem_phase2_Backend.hr.model.hr_management;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

// �߷� ��� ���� ���̺�

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date transferDate; // �߷� ��¥

    @Column(nullable = false)
    private String transferType; // �߷� ���� ( promotion : ����, demotion, lateral )

    @Column(nullable = false)
    private String reason; // �߷� ����
}
