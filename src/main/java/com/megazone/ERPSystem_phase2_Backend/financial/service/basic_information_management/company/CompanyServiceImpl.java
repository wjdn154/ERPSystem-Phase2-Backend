package com.megazone.ERPSystem_phase2_Backend.financial.service.basic_information_management.company;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company.*;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company.dto.*;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.company.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final CorporateTypeRepository corporateTypeRepository;
    private final CorporateKindRepository corporateKindRepository;
    private final RepresentativeRepository representativeRepository;
    private final AddressRepository addressRepository;
    private final ContactRepository contactRepository;
    private final MainBusinessRepository mainBusinessRepository;
    private final TaxOfficeRepository taxOfficeRepository;

    @Override
    public Optional<CompanyDTO> saveCompany(CompanyDTO companyDTO) {

        // DTO를 엔티티로 변환
        CorporateType corporateType = createCorporateType(companyDTO); // CorporateType 엔티티 생성
        CorporateKind corporateKind = createCorporateKind(companyDTO); // CorporateKind 엔티티 생성
        Representative representative = createRepresentative(companyDTO); // Representative 엔티티 생성
        Address address = createAddress(companyDTO); // Address 엔티티 생성
        Contact contact = createContact(companyDTO); // Contact 엔티티 생성
        MainBusiness mainBusinessCode = createMainBusiness(companyDTO); // MainBusiness 엔티티 생성

        TaxOffice businessTaxOffice = taxOfficeRepository.findByCode(companyDTO.getBusinessTaxOffice().getCode())
                .orElseThrow(() -> new IllegalArgumentException("사업장 관할 세무서 정보가 올바르지 않습니다.")); // TaxOffice 엔티티 생성

        TaxOffice headquarterTaxOffice = taxOfficeRepository.findByCode(companyDTO.getHeadquarterTaxOffice().getCode())
                .orElseThrow(() -> new IllegalArgumentException("본점 관할 세무서 정보가 올바르지 않습니다.")); // TaxOffice 엔티티 생성

        // 검증로직 추가해야함

        Company savedCompany = CreateCompany(
                companyDTO,
                corporateType,
                corporateKind,
                representative,
                address,
                contact,
                mainBusinessCode,
                businessTaxOffice,
                headquarterTaxOffice
        ); // Company 엔티티 생성

        return getCompanyDTO(savedCompany); // 엔티티를 DTO로 변환하여 반환
    }

    private static Optional<CompanyDTO> getCompanyDTO(Company savedCompany) {
        return Optional.of(new CompanyDTO(
                savedCompany.getId(),
                new CorporateTypeDTO(
                        savedCompany.getCorporateType().getId(),
                        savedCompany.getCorporateType().getCode(),
                        savedCompany.getCorporateType().getType(),
                        savedCompany.getCorporateType().getDescription()
                ),
                new CorporateKindDTO(
                        savedCompany.getCorporateKind().getId(),
                        savedCompany.getCorporateKind().getCode(),
                        savedCompany.getCorporateKind().getKind(),
                        savedCompany.getCorporateKind().getDescription()
                ),
                new RepresentativeDTO(
                        savedCompany.getRepresentative().getId(),
                        savedCompany.getRepresentative().getName(),
                        savedCompany.getRepresentative().getIdNumber(),
                        savedCompany.getRepresentative().getIsForeign()
                ),
                new AddressDTO(
                        savedCompany.getAddress().getId(),
                        savedCompany.getAddress().getBusinessPostalCode(),
                        savedCompany.getAddress().getBusinesseAddress(),
                        savedCompany.getAddress().getBusinesseAddressDetail(),
                        savedCompany.getAddress().getIsBusinesseNewAddress(),
                        savedCompany.getAddress().getBusinessePlace(),
                        savedCompany.getAddress().getHeadquarterPostalCode(),
                        savedCompany.getAddress().getHeadquarterAddress(),
                        savedCompany.getAddress().getHeadquarterAddressDetail(),
                        savedCompany.getAddress().getIsHeadquarterNewAddress(),
                        savedCompany.getAddress().getHeadquarterPlace()
                ),
                new ContactDTO(
                        savedCompany.getContact().getId(),
                        savedCompany.getContact().getBusinessPhone(),
                        savedCompany.getContact().getFax()
                ),
                new MainBusinessDTO(
                        savedCompany.getMainBusinessCode().getId(),
                        savedCompany.getMainBusinessCode().getCode(),
                        savedCompany.getMainBusinessCode().getBusinessType(),
                        savedCompany.getMainBusinessCode().getItem()
                ),
                new TaxOfficeDTO(
                        savedCompany.getBusinessTaxOffice().getId(),
                        savedCompany.getBusinessTaxOffice().getCode(),
                        savedCompany.getBusinessTaxOffice().getRegion()
                ),
                new TaxOfficeDTO(
                        savedCompany.getHeadquarterTaxOffice().getId(),
                        savedCompany.getHeadquarterTaxOffice().getCode(),
                        savedCompany.getHeadquarterTaxOffice().getRegion()
                ),
                savedCompany.getLocalIncomeTaxOffice(),
                savedCompany.getIsSme(),
                savedCompany.getBusinessRegistrationNumber(),
                savedCompany.getCorporateRegistrationNumber(),
                savedCompany.getBusinessType(),
                savedCompany.getBusinessItem(),
                savedCompany.getEstablishmentDate(),
                savedCompany.getOpeningDate(),
                savedCompany.getClosingDate(),
                savedCompany.getName(),
                savedCompany.getEntityType(),
                savedCompany.getFiscalYearStart(),
                savedCompany.getFiscalYearEnd(),
                savedCompany.getFiscalCardinalNumber()
        ));
    }

    private Company CreateCompany(CompanyDTO companyDTO, CorporateType corporateType, CorporateKind corporateKind, Representative representative, Address address, Contact contact, MainBusiness mainBusinessCode, TaxOffice businessTaxOffice, TaxOffice headquarterTaxOffice) {
        Company company = new Company(
                corporateType,
                corporateKind,
                representative,
                address,
                contact,
                mainBusinessCode,
                businessTaxOffice,
                headquarterTaxOffice,
                companyDTO.getLocalIncomeTaxOffice(),
                companyDTO.getIsSme(),
                companyDTO.getBusinessRegistrationNumber(),
                companyDTO.getCorporateRegistrationNumber(),
                companyDTO.getBusinessType(),
                companyDTO.getBusinessItem(),
                companyDTO.getEstablishmentDate(),
                companyDTO.getOpeningDate(),
                companyDTO.getClosingDate(),
                companyDTO.getName(),
                companyDTO.getEntityType(),
                companyDTO.getFiscalYearStart(),
                companyDTO.getFiscalYearEnd(),
                companyDTO.getFiscalCardinalNumber()
        );
        Company savedCompany = companyRepository.save(company);
        return savedCompany;
    }

    private MainBusiness createMainBusiness(CompanyDTO companyDTO) {
        MainBusiness mainBusinessCode = new MainBusiness(
                companyDTO.getMainBusinessCode().getCode(),
                companyDTO.getMainBusinessCode().getBusinessType(),
                companyDTO.getMainBusinessCode().getItem()
        );
        mainBusinessRepository.save(mainBusinessCode);
        return mainBusinessCode;
    }

    private Contact createContact(CompanyDTO companyDTO) {
        Contact contact = new Contact(
                companyDTO.getContact().getBusinessPhone(),
                companyDTO.getContact().getFax()
        );
        contactRepository.save(contact);
        return contact;
    }

    private Address createAddress(CompanyDTO companyDTO) {
        Address address = new Address(
                companyDTO.getAddress().getBusinessPostalCode(),
                companyDTO.getAddress().getBusinesseAddress(),
                companyDTO.getAddress().getBusinesseAddressDetail(),
                companyDTO.getAddress().getIsBusinesseNewAddress(),
                companyDTO.getAddress().getBusinessePlace(),
                companyDTO.getAddress().getHeadquarterPostalCode(),
                companyDTO.getAddress().getHeadquarterAddress(),
                companyDTO.getAddress().getHeadquarterAddressDetail(),
                companyDTO.getAddress().getIsHeadquarterNewAddress(),
                companyDTO.getAddress().getHeadquarterPlace()
        );
        addressRepository.save(address);
        return address;
    }

    private Representative createRepresentative(CompanyDTO companyDTO) {
        Representative representative = new Representative(
                companyDTO.getRepresentative().getName(),
                companyDTO.getRepresentative().getIdNumber(),
                companyDTO.getRepresentative().getIsForeign()
        );
        representativeRepository.save(representative);
        return representative;
    }

    private CorporateKind createCorporateKind(CompanyDTO companyDTO) {
        CorporateKind corporateKind = new CorporateKind(
                companyDTO.getCorporateKind().getCode(),
                companyDTO.getCorporateKind().getKind(),
                companyDTO.getCorporateKind().getDescription()
        );
        corporateKindRepository.save(corporateKind);
        return corporateKind;
    }

    private CorporateType createCorporateType(CompanyDTO companyDTO) {
        CorporateType corporateType = new CorporateType(
                companyDTO.getCorporateType().getCode(),
                companyDTO.getCorporateType().getType(),
                companyDTO.getCorporateType().getDescription()
        );
        corporateTypeRepository.save(corporateType);
        return corporateType;
    }
}