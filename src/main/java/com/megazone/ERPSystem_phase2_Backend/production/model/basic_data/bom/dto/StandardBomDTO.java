package com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.bom.dto;

import com.megazone.ERPSystem_phase2_Backend.production.model.outsourcing.OutsourcingType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StandardBomDTO {

    private Long id;
//    @NotBlank(message = "BOM 코드(bomCode)는 필수 입력값입니다.")
    private String bomCode;               // BOM 코드
    private String bomName;               // BOM 이름
    private Double lossRate;              // BOM 전체 손실율
    private LocalDateTime createdDate; // BOM 생성일자
//    @NotNull(message = "Version은 필수 입력값입니다.")
    private Double version; // BOM 버전

    private List<BomMaterialDTO> bomMaterials; // 자재 목록
    private StandardBomDTO parentBom;
    private List<StandardBomDTO> childBoms;

    private Long parentProductId;
    private String parentProductCode;     // 상위 제품 코드 (Parent Product Code)
    private String parentProductName;     // 상위 제품 이름 (Parent Product)

    private Long childProductId;
    private String childProductCode;      // 하위 제품 코드 (Child Product Code)
    private String childProductName;      // 하위 제품 이름 (Child Product)

    private LocalDate startDate;          // BOM 유효 시작일
    private LocalDate expiredDate;        // BOM 유효 종료일

    private OutsourcingType outsourcingType;  // 외주 구분 (enum 사용)
    private String remarks;
    private Boolean isActive;             // BOM 사용 여부 (활성화 상태)

    // 순환 참조 방지 필드
    @Builder.Default
    private boolean isParent = false;

}


