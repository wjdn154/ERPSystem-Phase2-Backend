package com.megazone.ERPSystem_phase2_Backend.financial.service.basic_information_management.client;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.*;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.dto.ClientDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.enums.TransactionType;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.client.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ClientServiceImpl implements ClientService {

    private final AddressRepository addressRepository;
    private final BankAccountRepository bankAccountRepository;
    private final BankRepository bankRepository;
    private final ClientRepository clientRepository;
    private final BusinessInfoRepository businessInfoRepository;
    private final CategoryRepository categoryRepository;
    private final ContactInfoRepository contactInfoRepository;
    private final DepartmentEmployeeRepository departmentEmployeeRepository;
    private final FinancialInfoRepository financialInfoRepository;
    private final LiquorRepository liquorRepository;
    private final ManageInfoRepository manageInfoRepository;

    /**
     * 거래처 정보를 저장하고 DTO로 반환     *
     * @param clientDTO 저장할 거래처 정보가 담긴 DTO
     * @return 저장된 거래처 정보를 DTO로 변환하여 Optional로 반환
     */
    @Override
    public Optional<ClientDTO> saveClient(ClientDTO clientDTO) {
        Client client = new Client();

        // 주소 정보
        Address address = new Address();
        address.setPostalCode(clientDTO.getAddress().getPostalCode());
        address.setRoadAddress(clientDTO.getAddress().getRoadAddress());
        address.setDetailedAddress(clientDTO.getAddress().getDetailedAddress());
        addressRepository.save(address);
        client.setAddress(address);

        // 업태 및 종목 정보
        BusinessInfo businessInfo = new BusinessInfo();
        businessInfo.setBusinessType(clientDTO.getBusinessInfo().getBusinessType());
        businessInfo.setBusinessItem(clientDTO.getBusinessInfo().getBusinessItem());
        businessInfoRepository.save(businessInfo);
        client.setBusinessInfo(businessInfo);

        // 연락처 정보
        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setPhoneNumber(clientDTO.getContactInfo().getPhoneNumber());
        contactInfo.setFaxNumber(clientDTO.getContactInfo().getFaxNumber());
        contactInfoRepository.save(contactInfo);
        client.setContactInfo(contactInfo);

        // 재무 정보
        FinancialInfo financialInfo = new FinancialInfo();
        financialInfo.setCollateralAmount(clientDTO.getFinancialInfo().getCollateralAmount());
        financialInfo.setCreditLimit(clientDTO.getFinancialInfo().getCreditLimit());
        financialInfoRepository.save(financialInfo);
        client.setFinancialInfo(financialInfo);

        // 업체 담당자 정보
        ManagerInfo managerInfo = new ManagerInfo();
        managerInfo.setClientManagerPhoneNumber(clientDTO.getManagerInfo().getClientManagerPhoneNumber());
        managerInfo.setClientManagerEmail(clientDTO.getManagerInfo().getClientManagerEmail());
        manageInfoRepository.save(managerInfo);
        client.setManagerInfo(managerInfo);

        // 주류 정보
        client.setLiquor(liquorRepository.findById(clientDTO.getLiquor().getId()).orElseThrow(() -> new RuntimeException("해당 주류 코드가 존재하지 않습니다.")));

        // 은행 계좌 정보
        BankAccount bankAccount = new BankAccount();
        bankAccount.setBank(bankRepository.findById(clientDTO.getBankAccount().getBank().getId()).orElseThrow(() -> new RuntimeException("해당 은행이 존재하지 않습니다.")));
        bankAccount.setAccountNumber(clientDTO.getBankAccount().getAccountNumber());
        bankAccount.setAccountHolder(clientDTO.getBankAccount().getAccountHolder());
        bankAccountRepository.save(bankAccount);
        client.setBankAccount(bankAccount);

        // 거래 유형
        client.setCategory(categoryRepository.findById(clientDTO.getCategory().getId()).orElseThrow(() -> new RuntimeException("해당 거래처 분류 코드가 존재하지 않습니다.")));

        // 기타 정보
        client.setDepartmentEmployee(clientDTO.getDepartmentEmployee());
        client.setTransactionType(TransactionType.valueOf(clientDTO.getTransactionType()));
        client.setPrintClientName(clientDTO.getPrintClientName());
        client.setBusinessRegistrationNumber(clientDTO.getBusinessRegistrationNumber());
        client.setIdNumber(clientDTO.getIdNumber());
        client.setRepresentativeName(clientDTO.getRepresentativeName());
        client.setTransactionStartDate(clientDTO.getTransactionStartDate());
        client.setTransactionEndDate(clientDTO.getTransactionEndDate());
        client.setRemarks(clientDTO.getRemarks());
        client.setIsActive(clientDTO.getIsActive());

        // 클라이언트 정보를 저장하고 DTO로 변환하여 반환함
        return Optional.of(new ClientDTO(clientRepository.save(client)));
    }

    /**
     * 거래처 정보를 수정하고 DTO로 반환
     * @param id 거래처 ID
     * @param clientDTO 수정할 거래처 정보가 담긴 DTO
     * @return 수정된 거래처 정보를 DTO로 변환하여 Optional로 반환
     */
    @Override
    public Optional<ClientDTO> updateClient(Long id, ClientDTO clientDTO) {
        return clientRepository.findById(id).map(client -> {

            // 주소 정보 수정
            Address address = client.getAddress();
            address.setPostalCode(clientDTO.getAddress().getPostalCode());
            address.setRoadAddress(clientDTO.getAddress().getRoadAddress());
            address.setDetailedAddress(clientDTO.getAddress().getDetailedAddress());
            addressRepository.save(address);
            client.setAddress(address);

            // 업태 및 종목 정보 수정
            BusinessInfo businessInfo = client.getBusinessInfo();
            businessInfo.setBusinessType(clientDTO.getBusinessInfo().getBusinessType());
            businessInfo.setBusinessItem(clientDTO.getBusinessInfo().getBusinessItem());
            businessInfoRepository.save(businessInfo);
            client.setBusinessInfo(businessInfo);

            // 연락처 정보 수정
            ContactInfo contactInfo = client.getContactInfo();
            contactInfo.setPhoneNumber(clientDTO.getContactInfo().getPhoneNumber());
            contactInfo.setFaxNumber(clientDTO.getContactInfo().getFaxNumber());
            contactInfoRepository.save(contactInfo);
            client.setContactInfo(contactInfo);

            // 재무 정보 수정
            FinancialInfo financialInfo = client.getFinancialInfo();
            financialInfo.setCollateralAmount(clientDTO.getFinancialInfo().getCollateralAmount());
            financialInfo.setCreditLimit(clientDTO.getFinancialInfo().getCreditLimit());
            financialInfoRepository.save(financialInfo);
            client.setFinancialInfo(financialInfo);

            // 업체 담당자 정보 수정
            ManagerInfo managerInfo = client.getManagerInfo();
            managerInfo.setClientManagerPhoneNumber(clientDTO.getManagerInfo().getClientManagerPhoneNumber());
            managerInfo.setClientManagerEmail(clientDTO.getManagerInfo().getClientManagerEmail());
            manageInfoRepository.save(managerInfo);
            client.setManagerInfo(managerInfo);

            // 주류 정보 수정
            client.setLiquor(liquorRepository.findById(clientDTO.getLiquor().getId()).orElseThrow(() -> new RuntimeException("해당 주류 코드가 존재하지 않습니다.")));

            // 은행 계좌 정보 수정
            BankAccount bankAccount = client.getBankAccount();
            bankAccount.setBank(bankRepository.findById(clientDTO.getBankAccount().getBank().getId()).orElseThrow(() -> new RuntimeException("해당 은행이 존재하지 않습니다.")));
            bankAccount.setAccountNumber(clientDTO.getBankAccount().getAccountNumber());
            bankAccount.setAccountHolder(clientDTO.getBankAccount().getAccountHolder());
            bankAccountRepository.save(bankAccount);
            client.setBankAccount(bankAccount);

            // 거래처 분류 수정
            client.setCategory(categoryRepository.findById(clientDTO.getCategory().getId()).orElseThrow(() -> new RuntimeException("해당 거래처 분류 코드가 존재하지 않습니다.")));

            // 기타 정보 수정
            client.setDepartmentEmployee(clientDTO.getDepartmentEmployee());
            client.setTransactionType(TransactionType.valueOf(clientDTO.getTransactionType()));
            client.setPrintClientName(clientDTO.getPrintClientName());
            client.setBusinessRegistrationNumber(clientDTO.getBusinessRegistrationNumber());
            client.setIdNumber(clientDTO.getIdNumber());
            client.setRepresentativeName(clientDTO.getRepresentativeName());
            client.setTransactionStartDate(clientDTO.getTransactionStartDate());
            client.setTransactionEndDate(clientDTO.getTransactionEndDate());
            client.setRemarks(clientDTO.getRemarks());
            client.setIsActive(clientDTO.getIsActive());

            // 수정된 클라이언트 정보를 저장하고 DTO로 변환하여 반환
            return new ClientDTO(clientRepository.save(client));
        });
    }
}