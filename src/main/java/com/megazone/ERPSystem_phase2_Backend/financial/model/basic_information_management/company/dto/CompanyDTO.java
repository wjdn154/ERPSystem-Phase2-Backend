package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company.dto;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company.MainBusiness;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company.TaxOffice;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company.enums.EntityType;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDTO {
   private Long id; // 고유식별자
   private CorporateTypeDTO corporateType; // 법인구분
   private CorporateKindDTO corporateKind; // 법인종류
   private RepresentativeDTO representative; // 대표자
   private AddressDTO address; // 주소
   private ContactDTO contact; // 연락처
   private MainBusinessDTO mainBusiness; // 주요업종코드
   private TaxOfficeDTO businessTaxOffice; // 사업자세무서
   private TaxOfficeDTO headquarterTaxOffice; // 본점세무서
   private String localIncomeTaxOffice; // 지방소득세무서
   private Boolean isSme; // 중소기업 여부
   private String businessRegistrationNumber; // 사업자등록번호
   private String corporateRegistrationNumber; // 법인등록번호
   private String businessType; // 업태
   private String businessItem; // 종목
   private LocalDate establishmentDate; // 설립일
   private LocalDate openingDate; // 개업일
   private LocalDate closingDate; // 폐업일
   private String name; // 회사명
   private EntityType entityType; // 업태
   private LocalDate fiscalYearStart; // 회계년도시작일
   private LocalDate fiscalYearEnd; // 회계년도종료일
   private Long fiscalCardinalNumber; // 회계기수
}
