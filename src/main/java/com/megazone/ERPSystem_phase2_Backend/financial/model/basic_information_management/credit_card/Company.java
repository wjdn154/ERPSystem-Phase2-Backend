package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.credit_card;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.*;

// 카드사 정보를 나타내는 엔티티 클래스
@Entity
@Table(name = "credit_card_company")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; // 카드사명

    private String number; // 전화번호

    private String website; // 홈페이지
}