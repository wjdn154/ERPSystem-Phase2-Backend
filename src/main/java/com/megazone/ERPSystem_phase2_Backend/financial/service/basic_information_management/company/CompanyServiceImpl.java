package com.megazone.ERPSystem_phase2_Backend.financial.service.basic_information_management.company;

import com.megazone.ERPSystem_phase2_Backend.Integrated.model.dashboard.RecentActivity;
import com.megazone.ERPSystem_phase2_Backend.Integrated.model.dashboard.enums.ActivityType;
import com.megazone.ERPSystem_phase2_Backend.Integrated.model.notification.enums.ModuleType;
import com.megazone.ERPSystem_phase2_Backend.Integrated.model.notification.enums.NotificationType;
import com.megazone.ERPSystem_phase2_Backend.Integrated.model.notification.enums.PermissionType;
import com.megazone.ERPSystem_phase2_Backend.Integrated.repository.dashboard.RecentActivityRepository;
import com.megazone.ERPSystem_phase2_Backend.Integrated.service.notification.NotificationService;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company.*;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company.dto.*;
import com.megazone.ERPSystem_phase2_Backend.financial.model.common.Contact;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.company.*;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.common.ContactRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final CorporateTypeRepository corporateTypeRepository;
    private final CorporateKindRepository corporateKindRepository;
    private final RepresentativeRepository representativeRepository;
    private final CompanyAddressRepository companyAddressRepository;
    private final ContactRepository contactRepository;
    private final MainBusinessRepository mainBusinessRepository;
    private final TaxOfficeRepository taxOfficeRepository;
    private final RecentActivityRepository recentActivityRepository;
    private final NotificationService notificationService;

    @Override
    public List<CompanyDTO> findAllCompany() {
        List<Company> companies = companyRepository.findAll();
        return companies.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CompanyDTO> searchCompany(String searchText) {
        List<Company> companies;

        // 검색 텍스트가 없으면 모든 회사 조회
        if(searchText != null) {
            companies = companyRepository.findByNameContaining(searchText); // 검색어로 회사 조회
        }else {
            companies = companyRepository.findAll(); // 모든 회사 조회
        }

        return companies.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * 회사 정보를 저장함.
     *
     * @param companyDTO 저장할 회사 정보가 담긴 DTO
     * @return 저장된 회사 정보를 담은 DTO
     */
    @Override
    public Optional<CompanyDTO> saveCompany(CompanyDTO companyDTO) {

        // DTO를 엔티티로 변환함
        Company company = mapToEntity(companyDTO);

        // 엔티티를 저장함
        Company savedCompany = companyRepository.save(company);
        recentActivityRepository.save(RecentActivity.builder()
                .activityDescription(company.getName() + " 회사 추가")
                .activityType(ActivityType.FINANCE)
                .activityTime(LocalDateTime.now())
                .build());

        notificationService.createAndSendNotification(
                ModuleType.ALL,
                PermissionType.ADMIN,
                company.getName() + " 회사 추가",
                NotificationType.NEW_COMPANY);

        // 저장된 엔티티를 DTO로 변환하여 반환함
        return Optional.of(mapToDTO(savedCompany));
    }

    /**
     * 회사 정보를 수정함.
     *
     * @param id 수정할 회사의 ID
     * @param companyDTO 수정할 회사 정보가 담긴 DTO
     * @return 수정된 회사 정보를 담은 DTO
     */
    @Override
    public Optional<CompanyDTO> updateCompany(Long id, CompanyDTO companyDTO) {
        // ID로 기존 회사를 조회함
        return companyRepository.findById(id).map(existingCompany -> {
            // 기존 회사의 정보를 새로운 정보로 업데이트함
            existingCompany.setCorporateType(saveCorporateType(companyDTO.getCorporateType()));
            existingCompany.setCorporateKind(saveCorporateKind(companyDTO.getCorporateKind()));
            existingCompany.setRepresentative(updateRepresentative(existingCompany.getRepresentative(), companyDTO.getRepresentative())); // unique 제약조건 검증
            existingCompany.setAddress(saveAddress(companyDTO.getAddress()));
            existingCompany.setContact(saveContact(companyDTO.getContact()));
            existingCompany.setMainBusiness(findMainBusiness(companyDTO.getMainBusiness().getCode()));
            existingCompany.setBusinessTaxOffice(findTaxOffice(companyDTO.getBusinessTaxOffice().getCode()));
            existingCompany.setHeadquarterTaxOffice(findTaxOffice(companyDTO.getHeadquarterTaxOffice().getCode()));
            existingCompany.setLocalIncomeTaxOffice(companyDTO.getLocalIncomeTaxOffice());
            existingCompany.setIsSme(companyDTO.getIsSme());
            existingCompany.setBusinessRegistrationNumber(updateBusinessRegistrationNumber(existingCompany.getBusinessRegistrationNumber(), companyDTO.getBusinessRegistrationNumber())); // unique 제약조건 검증
            existingCompany.setCorporateRegistrationNumber(updateCorporateRegistrationNumber(existingCompany.getCorporateRegistrationNumber(), companyDTO.getCorporateRegistrationNumber())); // unique 제약조건 검증
            existingCompany.setBusinessType(companyDTO.getBusinessType());
            existingCompany.setBusinessItem(companyDTO.getBusinessItem());
            existingCompany.setEstablishmentDate(companyDTO.getEstablishmentDate());
            existingCompany.setOpeningDate(companyDTO.getOpeningDate());
            existingCompany.setClosingDate(companyDTO.getClosingDate());
            existingCompany.setName(companyDTO.getName());
            existingCompany.setEntityType(companyDTO.getEntityType());
            existingCompany.setFiscalYearStart(companyDTO.getFiscalYearStart());
            existingCompany.setFiscalYearEnd(companyDTO.getFiscalYearEnd());
            existingCompany.setFiscalCardinalNumber(companyDTO.getFiscalCardinalNumber());

            // 회사 정보를 업데이트하고 저장함
            Company savedCompany = companyRepository.save(existingCompany);

            recentActivityRepository.save(RecentActivity.builder()
                    .activityDescription(savedCompany.getName() + " 회사 수정")
                    .activityType(ActivityType.FINANCE)
                    .activityTime(LocalDateTime.now())
                    .build());

            notificationService.createAndSendNotification(
                    ModuleType.ALL,
                    PermissionType.ADMIN,
                    savedCompany.getName() + " 회사 수정",
                    NotificationType.UPDATE_COMPANY);

            // 수정된 엔티티를 DTO로 변환하여 반환함
            return mapToDTO(savedCompany);
        });
    }

    private Representative updateRepresentative(Representative currentRepresentative, RepresentativeDTO representativeDTO) {
        // 주민등록번호가 변경되었을 때만 중복 체크 및 업데이트 수행
        if (!currentRepresentative.getIdNumber().equals(representativeDTO.getIdNumber())) {
            representativeRepository.findByIdNumber(representativeDTO.getIdNumber())
                    .ifPresent(existingRepresentative -> {
                        throw new IllegalArgumentException("이미 존재하는 주민등록번호입니다: " + representativeDTO.getIdNumber());
                    });
            currentRepresentative.setIdNumber(representativeDTO.getIdNumber());
        }
        currentRepresentative.setName(representativeDTO.getName());
        currentRepresentative.setIsForeign(representativeDTO.getIsForeign());

        return representativeRepository.save(currentRepresentative);
    }

    private String updateBusinessRegistrationNumber(String currentNumber, String newNumber) {
        // 사업자등록번호가 변경되었을 때만 중복 체크 및 업데이트 수행
        if (!currentNumber.equals(newNumber)) {
            companyRepository.findByBusinessRegistrationNumber(newNumber)
                    .ifPresent(existingCompany -> {
                        throw new IllegalArgumentException("이미 존재하는 사업자등록번호입니다: " + newNumber);
                    });
            return newNumber;
        }
        return currentNumber;
    }

    private String updateCorporateRegistrationNumber(String currentNumber, String newNumber) {
        // 법인등록번호가 변경되었을 때만 중복 체크 및 업데이트 수행
        if (!currentNumber.equals(newNumber)) {
            companyRepository.findByCorporateRegistrationNumber(newNumber)
                    .ifPresent(existingCompany -> {
                        throw new IllegalArgumentException("이미 존재하는 법인등록번호입니다: " + newNumber);
                    });
            return newNumber;
        }
        return currentNumber;
    }

    /**
     * 회사 DTO를 엔티티로 변환함.
     *
     * @param companyDTO 변환할 회사 DTO
     * @return 변환된 회사 엔티티
     */
    private Company mapToEntity(CompanyDTO companyDTO) {
        return new Company(
                saveCorporateType(companyDTO.getCorporateType()), // 법인구분 엔티티 저장 후 반환
                saveCorporateKind(companyDTO.getCorporateKind()), // 법인종류별 구분 엔티티 저장 후 반환
                saveRepresentative(companyDTO.getRepresentative()), // 대표자 정보 엔티티 저장 후 반환
                saveAddress(companyDTO.getAddress()), // 주소 정보 엔티티 저장 후 반환
                saveContact(companyDTO.getContact()), // 연락처 정보 엔티티 저장 후 반환
                findMainBusiness(companyDTO.getMainBusiness().getCode()), // 주업종 코드 조회
                findTaxOffice(companyDTO.getBusinessTaxOffice().getCode()), // 사업장 관할 세무서 조회
                findTaxOffice(companyDTO.getHeadquarterTaxOffice().getCode()), // 본점 관할 세무서 조회
                companyDTO.getLocalIncomeTaxOffice(), // 지방소득세납세지
                companyDTO.getIsSme(), // 중소기업 여부
                companyDTO.getBusinessRegistrationNumber(), // 사업자등록번호
                companyDTO.getCorporateRegistrationNumber(), // 법인등록번호
                companyDTO.getBusinessType(), // 업태
                companyDTO.getBusinessItem(), // 종목
                companyDTO.getEstablishmentDate(), // 설립연월일
                companyDTO.getOpeningDate(), // 개업연월일
                companyDTO.getClosingDate(), // 폐업연월일
                companyDTO.getName(), // 회사명
                companyDTO.getEntityType(), // 구분 (법인, 개인)
                companyDTO.getFiscalYearStart(), // 회계연도 시작일
                companyDTO.getFiscalYearEnd(), // 회계연도 마지막일
                companyDTO.getFiscalCardinalNumber() // 회계연도 기수
        );
    }

    /**
     * 회사 엔티티를 DTO로 변환함.
     *
     * @param company 변환할 회사 엔티티
     * @return 변환된 회사 DTO
     */
    private CompanyDTO mapToDTO(Company company) {
        return new CompanyDTO(
                company.getId(), // ID
                new CorporateTypeDTO(company.getCorporateType()), // 법인구분 정보
                new CorporateKindDTO(company.getCorporateKind()), // 법인종류별 구분 정보
                new RepresentativeDTO(company.getRepresentative()), // 대표자 정보
                new AddressDTO(company.getAddress()), // 주소 정보
                new ContactDTO(company.getContact()), // 연락처 정보
                new MainBusinessDTO(company.getMainBusiness()), // 주업종 코드 정보
                new TaxOfficeDTO(company.getBusinessTaxOffice()), // 사업장 관할 세무서 정보
                new TaxOfficeDTO(company.getHeadquarterTaxOffice()), // 본점 관할 세무서 정보
                company.getLocalIncomeTaxOffice(), // 지방소득세납세지
                company.getIsSme(), // 중소기업 여부
                company.getBusinessRegistrationNumber(), // 사업자등록번호
                company.getCorporateRegistrationNumber(), // 법인등록번호
                company.getBusinessType(), // 업태
                company.getBusinessItem(), // 종목
                company.getEstablishmentDate(), // 설립연월일
                company.getOpeningDate(), // 개업연월일
                company.getClosingDate(), // 폐업연월일
                company.getName(), // 회사명
                company.getEntityType(), // 구분 (법인, 개인)
                company.getFiscalYearStart(), // 회계연도 시작일
                company.getFiscalYearEnd(), // 회계연도 마지막일
                company.getFiscalCardinalNumber() // 회계연도 기수
        );
    }

    /**
     * 법인구분을 저장함.
     *
     * @param dto 법인구분 DTO
     * @return 저장된 법인구분 엔티티
     */
    private CorporateType saveCorporateType(CorporateTypeDTO dto) {
        return corporateTypeRepository.save(new CorporateType(dto.getCode(), dto.getType(), dto.getDescription()));
    }

    /**
     * 법인종류별 구분을 저장함.
     *
     * @param dto 법인종류별 구분 DTO
     * @return 저장된 법인종류별 구분 엔티티
     */
    private CorporateKind saveCorporateKind(CorporateKindDTO dto) {
        return corporateKindRepository.save(new CorporateKind(dto.getCode(), dto.getKind(), dto.getDescription()));
    }

    /**
     * 대표자 정보를 저장함.
     *
     * @param dto 대표자 DTO
     * @return 저장된 대표자 엔티티
     */
    private Representative saveRepresentative(RepresentativeDTO dto) {
        return representativeRepository.save(new Representative(dto.getName(), dto.getIdNumber(), dto.getIsForeign()));
    }

    /**
     * 주소 정보를 저장함.
     *
     * @param dto 주소 DTO
     * @return 저장된 주소 엔티티
     */
    private Address saveAddress(AddressDTO dto) {
        return companyAddressRepository.save(new Address(
                dto.getBusinessPostalCode(), // 사업장 우편번호
                dto.getBusinesseAddress(), // 사업장 주소
                dto.getBusinesseAddressDetail(), // 사업장 상세 주소
                dto.getIsBusinesseNewAddress(), // 사업장 신규 주소 여부
                dto.getBusinessePlace(), // 사업장 장소
                dto.getHeadquarterPostalCode(), // 본점 우편번호
                dto.getHeadquarterAddress(), // 본점 주소
                dto.getHeadquarterAddressDetail(), // 본점 상세 주소
                dto.getIsHeadquarterNewAddress(), // 본점 신규 주소 여부
                dto.getHeadquarterPlace() // 본점 장소
        ));
    }

    /**
     * 연락처 정보를 저장함.
     *
     * @param dto 연락처 DTO
     * @return 저장된 연락처 엔티티
     */
    private Contact saveContact(ContactDTO dto) {
        return contactRepository.save(new Contact(dto.getPhone(), dto.getFax()));
    }

    /**
     * 주업종을 코드로 조회함.
     *
     * @param code 주업종 코드
     * @return 저장된 주업종 코드 엔티티
     */
    private MainBusiness findMainBusiness(String code) {
        return mainBusinessRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("주업종 코드가 올바르지 않습니다."));
    }

    /**
     * 세무서를 코드로 조회함.
     *
     * @param code 세무서 코드
     * @return 조회된 세무서 엔티티
     * @throws IllegalArgumentException 세무서 정보가 올바르지 않을 때 예외 발생
     */
    private TaxOffice findTaxOffice(String code) {
        return taxOfficeRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("세무서 정보가 올바르지 않습니다."));
    }
}