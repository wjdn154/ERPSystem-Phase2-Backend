package com.megazone.ERPSystem_phase2_Backend.production.model.resource_data;


import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.enums.HazardLevel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "production_hazardousMaterial")
@Table(name = "production_hazardousMaterial")
public class HazardousMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String hazardousMaterialCode;     //유해물질 코드

    @Column(nullable = false)
    private String hazardousMaterialName;    //유해물질 이름

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private HazardLevel hazardLevel;         //위험등급 (row, medium, high)

    private String description;              //추가 설명


}
/**
 중금속류 (Heavy Metals)
LEAD,           // 납
MERCURY,        // 수은
CADMIUM,        // 카드뮴
HEXAVALENT_CHROMIUM,  // 6가 크롬
NICKEL,         // 니켈

 할로겐화합물 (Halogenated Compounds)
CHLORINATED_COMPOUNDS,  // 염소화합물
BROMINATED_FLAME_RETARDANTS,  // 브롬화 난연제

 유기 화합물 (Organic Compounds)
PHTHALATES,     // 프탈레이트
POLYCHLORINATED_BIPHENYLS,  // 폴리염화비페닐 (PCBs)

 기타 유해 물질 (Other Hazardous Substances)
ASBESTOS,       // 석면
ARSENIC,        // 비소
BENZENE         // 벤젠
 */